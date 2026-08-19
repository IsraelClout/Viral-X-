/**
 * GHANA MOBILE MONEY (MoMo) DISBURSEMENT & LEDGER ENGINE
 * Supports: MTN MoMo, Telecel Cash, AT Money
 * Copyright © 2026 by Gokah Israel Ewoenam.
 */

export interface MoMoWithdrawalRequest {
  userId: string;
  amountGhc: number;
  network: "MTN MoMo" | "Telecel Cash" | "AT Money";
  phoneNumber: string;
  accountName: string;
}

export interface MoMoWithdrawalResult {
  success: boolean;
  reference: string;
  feeGhc: number;
  netPayoutGhc: number;
  message: string;
}

export function validateGhanaPhone(phone: string, network: string): { isValid: boolean; error?: string } {
  const cleanPhone = phone.replace(/[^0-9]/g, "");
  if (cleanPhone.length !== 10) {
    return { isValid: false, error: "Ghana phone number must be exactly 10 digits (e.g., 0244889900)" };
  }

  const prefix = cleanPhone.substring(0, 3);
  if (network === "MTN MoMo") {
    const mtnPrefixes = ["024", "054", "055", "059", "053"];
    if (!mtnPrefixes.includes(prefix)) {
      return { isValid: false, error: `Invalid MTN prefix '${prefix}'. Valid: 024, 054, 055, 059, 053` };
    }
  } else if (network === "Telecel Cash") {
    const telecelPrefixes = ["020", "050"];
    if (!telecelPrefixes.includes(prefix)) {
      return { isValid: false, error: `Invalid Telecel prefix '${prefix}'. Valid: 020, 050` };
    }
  } else if (network === "AT Money") {
    const atPrefixes = ["027", "057", "026", "056"];
    if (!atPrefixes.includes(prefix)) {
      return { isValid: false, error: `Invalid AT Money prefix '${prefix}'. Valid: 027, 057, 026, 056` };
    }
  }

  return { isValid: true };
}

export function calculateMoMoFee(amountGhc: number): number {
  // 1% platform & telco processing fee
  const fee = amountGhc * 0.01;
  return Number(Math.max(fee, 0.50).toFixed(2));
}

export function processMoMoPayout(request: MoMoWithdrawalRequest, availableBalanceGhc: number): MoMoWithdrawalResult {
  if (request.amountGhc < 10.0) {
    return {
      success: false,
      reference: "",
      feeGhc: 0,
      netPayoutGhc: 0,
      message: "Minimum withdrawal amount is GH₵ 10.00",
    };
  }

  if (request.amountGhc > availableBalanceGhc) {
    return {
      success: false,
      reference: "",
      feeGhc: 0,
      netPayoutGhc: 0,
      message: "Insufficient wallet balance",
    };
  }

  const validation = validateGhanaPhone(request.phoneNumber, request.network);
  if (!validation.isValid) {
    return {
      success: false,
      reference: "",
      feeGhc: 0,
      netPayoutGhc: 0,
      message: validation.error || "Invalid phone number",
    };
  }

  const feeGhc = calculateMoMoFee(request.amountGhc);
  const netPayoutGhc = Number((request.amountGhc - feeGhc).toFixed(2));
  const reference = `MOMO_GH_${Date.now()}_${Math.floor(Math.random() * 9000 + 1000)}`;

  return {
    success: true,
    reference,
    feeGhc,
    netPayoutGhc,
    message: `Successfully disbursed GH₵ ${netPayoutGhc.toFixed(2)} to ${request.network} (${request.phoneNumber})`,
  };
}
