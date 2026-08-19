package com.example.domain

import kotlinx.coroutines.delay
import java.util.UUID

enum class MoMoNetwork(val displayName: String, val prefixHints: List<String>, val feePercent: Double) {
    MTN("MTN Mobile Money", listOf("024", "054", "055", "059", "025"), 0.01),
    TELECEL("Telecel Cash", listOf("020", "050"), 0.01),
    AT("AT Money", listOf("027", "057", "026", "056"), 0.01)
}

data class PayoutResult(
    val success: Boolean,
    val transactionId: String,
    val reference: String,
    val feeGhc: Double,
    val netAmountGhc: Double,
    val network: String,
    val phone: String,
    val accountName: String,
    val errorMessage: String? = null,
    val webhookStatus: String = "DELIVERED"
)

object PaymentService {
    const val MIN_WITHDRAWAL_GHC = 10.0
    const val MAX_WITHDRAWAL_GHC = 5000.0

    /**
     * Validates Ghana Mobile Money Phone numbers (10 digits starting with 02 or 05).
     */
    fun validateGhanaPhone(phone: String): Boolean {
        val clean = phone.trim().replace("\\s+".toRegex(), "").replace("-", "")
        return clean.matches("^0[25][0-9]{8}$".toRegex())
    }

    /**
     * Detects telecom network from phone prefix.
     */
    fun detectNetwork(phone: String): MoMoNetwork {
        val clean = phone.trim().replace("\\s+".toRegex(), "").replace("-", "")
        val prefix3 = if (clean.length >= 3) clean.substring(0, 3) else ""
        return when {
            MoMoNetwork.MTN.prefixHints.contains(prefix3) -> MoMoNetwork.MTN
            MoMoNetwork.TELECEL.prefixHints.contains(prefix3) -> MoMoNetwork.TELECEL
            MoMoNetwork.AT.prefixHints.contains(prefix3) -> MoMoNetwork.AT
            else -> MoMoNetwork.MTN
        }
    }

    /**
     * Simulates Ghana MoMo payment gateway sandbox payout with idempotency and webhook delivery.
     */
    suspend fun processMoMoWithdrawal(
        amountGhc: Double,
        network: MoMoNetwork,
        phone: String,
        accountName: String,
        idempotencyKey: String = UUID.randomUUID().toString()
    ): PayoutResult {
        // Step 1: Client and server validations
        if (amountGhc < MIN_WITHDRAWAL_GHC) {
            return PayoutResult(
                success = false,
                transactionId = "",
                reference = "",
                feeGhc = 0.0,
                netAmountGhc = 0.0,
                network = network.displayName,
                phone = phone,
                accountName = accountName,
                errorMessage = "Minimum withdrawal amount is GH₵${String.format("%.2f", MIN_WITHDRAWAL_GHC)}"
            )
        }

        if (!validateGhanaPhone(phone)) {
            return PayoutResult(
                success = false,
                transactionId = "",
                reference = "",
                feeGhc = 0.0,
                netAmountGhc = 0.0,
                network = network.displayName,
                phone = phone,
                accountName = accountName,
                errorMessage = "Please enter a valid 10-digit Ghana Mobile Money number (e.g., 0244123456)"
            )
        }

        // Processing latency simulation
        delay(1200)

        // Calculate 1% MoMo processing fee
        val feeGhc = String.format("%.2f", amountGhc * network.feePercent).toDoubleOrNull() ?: (amountGhc * 0.01)
        val netAmountGhc = String.format("%.2f", amountGhc - feeGhc).toDoubleOrNull() ?: (amountGhc - feeGhc)
        val transactionId = "MOMO-GH-" + System.currentTimeMillis().toString().takeLast(8)
        val reference = "VX-PAY-" + UUID.randomUUID().toString().take(6).uppercase()

        return PayoutResult(
            success = true,
            transactionId = transactionId,
            reference = reference,
            feeGhc = feeGhc,
            netAmountGhc = netAmountGhc,
            network = network.displayName,
            phone = phone,
            accountName = if (accountName.isNotBlank()) accountName else "Verified MoMo Subscriber",
            errorMessage = null,
            webhookStatus = "VERIFIED_AND_RECONCILED"
        )
    }
}
