package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.TransactionEntity
import com.example.data.local.entities.WalletEntity
import com.example.domain.MoMoNetwork
import com.example.domain.PayoutResult
import com.example.ui.components.FooterComponent
import com.example.ui.components.GlassButton
import com.example.ui.components.GlassCard
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.MomoGold
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonMint
import com.example.ui.theme.NeonPink
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.launch

@Composable
fun WalletScreen(
    wallet: WalletEntity?,
    transactions: List<TransactionEntity>,
    onProcessWithdrawal: suspend (amount: Double, network: MoMoNetwork, phone: String, accountName: String) -> PayoutResult
) {
    val scope = rememberCoroutineScope()

    var withdrawAmount by remember { mutableStateOf("100") }
    var selectedNetwork by remember { mutableStateOf(MoMoNetwork.MTN) }
    var momoPhone by remember(wallet) { mutableStateOf(wallet?.momoNumber ?: "0244889900") }
    var accountName by remember(wallet) { mutableStateOf(wallet?.momoAccountName ?: "Israel Ewoenam Gokah") }
    var isProcessing by remember { mutableStateOf(false) }
    var payoutResult by remember { mutableStateOf<PayoutResult?>(null) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("wallet_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Header
        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)) {
                Text(
                    text = "Creator Wallet & MoMo 🇬🇭",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Instant Mobile Money payouts in Ghana Cedis (GH₵)",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
            }
        }

        // WALLET BALANCE CARD (Gold / Glass Hero)
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 6.dp),
                shape = RoundedCornerShape(24.dp),
                borderColor = MomoGold.copy(alpha = 0.6f),
                backgroundColor = Color(0xCC1A142D)
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MomoGold.copy(alpha = 0.2f)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = MomoGold, modifier = Modifier.size(20.dp))
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Available Payout Balance",
                                color = TextSecondary,
                                fontSize = 13.sp
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0x33F59E0B))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "GH₵ Cedis",
                                color = MomoGold,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "GH₵ ${String.format("%.2f", wallet?.availableBalanceGhc ?: 485.50)}",
                        color = TextPrimary,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        letterSpacing = 0.5.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Pending Clear", color = TextMuted, fontSize = 11.sp)
                            Text(
                                text = "GH₵ ${String.format("%.2f", wallet?.pendingBalanceGhc ?: 120.00)}",
                                color = MomoGold,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column {
                            Text(text = "Lifetime Earnings", color = TextMuted, fontSize = 11.sp)
                            Text(
                                text = "GH₵ ${String.format("%.2f", wallet?.lifetimeEarningsGhc ?: 1840.00)}",
                                color = NeonMint,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                        Column {
                            Text(text = "Min Payout", color = TextMuted, fontSize = 11.sp)
                            Text(
                                text = "GH₵ 10.00",
                                color = TextPrimary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }
                }
            }
        }

        // WITHDRAWAL FORM
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
                shape = RoundedCornerShape(22.dp),
                borderColor = GlassBorder
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Withdraw to Ghana Mobile Money 📱",
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Select your network, specify amount, and receive funds instantly",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // NETWORK SELECTOR (MTN, Telecel, AT)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(
                            MoMoNetwork.MTN to MomoGold,
                            MoMoNetwork.TELECEL to Color(0xFFEF4444),
                            MoMoNetwork.AT to NeonCyan
                        ).forEach { (network, color) ->
                            val isSelected = selectedNetwork == network
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(if (isSelected) color.copy(alpha = 0.25f) else Color(0xFF1E1736))
                                    .border(1.5.dp, if (isSelected) color else GlassBorderLight, RoundedCornerShape(14.dp))
                                    .clickable { selectedNetwork = network }
                                    .padding(vertical = 10.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(
                                        text = network.displayName.split(" ").first(),
                                        color = if (isSelected) color else TextPrimary,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Text(
                                        text = "1% fee",
                                        color = TextMuted,
                                        fontSize = 9.sp
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Amount input
                    OutlinedTextField(
                        value = withdrawAmount,
                        onValueChange = { withdrawAmount = it },
                        label = { Text("Withdrawal Amount (GH₵)", fontSize = 12.sp) },
                        leadingIcon = { Text("GH₵", color = MomoGold, fontWeight = FontWeight.Bold, modifier = Modifier.padding(start = 12.dp)) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("withdraw_amount_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MomoGold,
                            unfocusedBorderColor = GlassBorderLight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Phone Number
                    OutlinedTextField(
                        value = momoPhone,
                        onValueChange = { momoPhone = it },
                        label = { Text("Ghana MoMo Number (10 digits)", fontSize = 12.sp) },
                        leadingIcon = { Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = NeonCyan) },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("withdraw_phone_input"),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = GlassBorderLight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Account Name
                    OutlinedTextField(
                        value = accountName,
                        onValueChange = { accountName = it },
                        label = { Text("Account Holder Name", fontSize = 12.sp) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = NeonCyan,
                            unfocusedBorderColor = GlassBorderLight,
                            focusedTextColor = TextPrimary,
                            unfocusedTextColor = TextPrimary
                        ),
                        singleLine = true
                    )

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.ErrorOutline, contentDescription = null, tint = NeonPink, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = errorMessage ?: "", color = NeonPink, fontSize = 12.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    if (isProcessing) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = MomoGold, modifier = Modifier.size(28.dp))
                        }
                    } else {
                        GlassButton(
                            text = "Initiate Mobile Money Payout 🚀",
                            onClick = {
                                val amt = withdrawAmount.toDoubleOrNull()
                                if (amt == null || amt < 10.0) {
                                    errorMessage = "Minimum withdrawal is GH₵10.00"
                                    return@GlassButton
                                }
                                if (momoPhone.length < 10) {
                                    errorMessage = "Please enter valid 10-digit Ghana number"
                                    return@GlassButton
                                }

                                isProcessing = true
                                errorMessage = null
                                payoutResult = null

                                scope.launch {
                                    val result = onProcessWithdrawal(amt, selectedNetwork, momoPhone, accountName)
                                    isProcessing = false
                                    if (result.success) {
                                        payoutResult = result
                                    } else {
                                        errorMessage = result.errorMessage ?: "Withdrawal failed"
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "withdraw_submit_button"
                        )
                    }

                    // Success Feedback Alert
                    AnimatedVisibility(visible = payoutResult != null && payoutResult?.success == true) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 14.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0x3310B981))
                                .border(1.dp, NeonMint, RoundedCornerShape(14.dp))
                                .padding(12.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.CheckCircle, contentDescription = null, tint = NeonMint, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(text = "Payout Processed Successfully!", color = NeonMint, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                            }
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "GH₵${String.format("%.2f", payoutResult?.netAmountGhc ?: 0.0)} credited to ${payoutResult?.network} (${payoutResult?.phone}). Ref: ${payoutResult?.reference}",
                                color = TextPrimary,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }
        }

        // TRANSACTION HISTORY (Ledger)
        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 4.dp)) {
                Text(
                    text = "Transaction History & Ledger",
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        if (transactions.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No recent transactions found.", color = TextMuted, fontSize = 13.sp)
                }
            }
        }

        items(transactions, key = { it.id }) { tx ->
            val isCredit = tx.type == "REWARD_EARNED" || tx.type == "BONUS"
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                shape = RoundedCornerShape(14.dp),
                borderColor = GlassBorderLight
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(if (isCredit) Color(0x3310B981) else Color(0x33F43F5E)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = if (isCredit) Icons.Default.ArrowDownward else Icons.Default.ArrowUpward,
                                contentDescription = null,
                                tint = if (isCredit) NeonMint else NeonPink,
                                modifier = Modifier.size(16.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(10.dp))

                        Column {
                            Text(
                                text = if (isCredit) "Creator Engagement Reward" else "MoMo Cashout: ${tx.momoNetwork ?: "Mobile Money"}",
                                color = TextPrimary,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                text = tx.description,
                                color = TextMuted,
                                fontSize = 10.sp,
                                maxLines = 1
                            )
                        }
                    }

                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "${if (isCredit) "+" else "-"}GH₵ ${String.format("%.2f", tx.amountGhc)}",
                            color = if (isCredit) NeonMint else TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = tx.status,
                            color = if (tx.status == "COMPLETED") NeonMint else MomoGold,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Footer
        item {
            FooterComponent()
        }
    }
}
