"use client";

import { useState } from "react";
import { Wallet, ArrowUpRight, ShieldCheck, CheckCircle2, AlertCircle, History, Sparkles, Smartphone } from "lucide-react";
import { validateGhanaPhone, calculateMoMoFee, processMoMoPayout } from "@/lib/momo";

export default function WalletPage() {
  const [balance, setBalance] = useState(1240.50);
  const [pendingRewards, setPendingRewards] = useState(142.00);
  const [amountInput, setAmountInput] = useState("");
  const [network, setNetwork] = useState<"MTN MoMo" | "Telecel Cash" | "AT Money">("MTN MoMo");
  const [phoneNumber, setPhoneNumber] = useState("0244889900");
  const [accountName, setAccountName] = useState("Israel Ewoenam Gokah");
  const [errorMessage, setErrorMessage] = useState<string | null>(null);
  const [successMessage, setSuccessMessage] = useState<string | null>(null);
  const [isProcessing, setIsProcessing] = useState(false);

  const [transactions, setTransactions] = useState([
    {
      id: "tx_1",
      type: "MOMO_WITHDRAWAL",
      amountGhc: 250.00,
      feeGhc: 2.50,
      netPayoutGhc: 247.50,
      network: "MTN MoMo",
      phone: "0244889900",
      status: "COMPLETED",
      date: "2026-08-18 16:45",
      ref: "MOMO_GH_889921",
    },
    {
      id: "tx_2",
      type: "CREATOR_REWARD",
      amountGhc: 480.00,
      feeGhc: 0.00,
      netPayoutGhc: 480.00,
      network: "Viral Fund",
      phone: "-",
      status: "COMPLETED",
      date: "2026-08-17 09:12",
      ref: "REWARD_POST_109",
    },
  ]);

  const numAmount = parseFloat(amountInput) || 0;
  const calculatedFee = calculateMoMoFee(numAmount);
  const netPayout = Math.max(numAmount - calculatedFee, 0);

  const handleWithdraw = (e: React.FormEvent) => {
    e.preventDefault();
    setErrorMessage(null);
    setSuccessMessage(null);

    const validation = validateGhanaPhone(phoneNumber, network);
    if (!validation.isValid) {
      setErrorMessage(validation.error || "Invalid phone number");
      return;
    }

    if (numAmount < 10.0) {
      setErrorMessage("Minimum Mobile Money withdrawal is GH₵ 10.00");
      return;
    }

    if (numAmount > balance) {
      setErrorMessage("Insufficient wallet balance");
      return;
    }

    setIsProcessing(true);

    setTimeout(() => {
      const result = processMoMoPayout(
        {
          userId: "usr_israel",
          amountGhc: numAmount,
          network,
          phoneNumber,
          accountName,
        },
        balance
      );

      setIsProcessing(false);

      if (result.success) {
        setBalance((prev) => prev - numAmount);
        setSuccessMessage(result.message);
        setAmountInput("");

        setTransactions((prev) => [
          {
            id: `tx_${Date.now()}`,
            type: "MOMO_WITHDRAWAL",
            amountGhc: numAmount,
            feeGhc: result.feeGhc,
            netPayoutGhc: result.netPayoutGhc,
            network,
            phone: phoneNumber,
            status: "COMPLETED",
            date: "Just now",
            ref: result.reference,
          },
          ...prev,
        ]);
      } else {
        setErrorMessage(result.message);
      }
    }, 1200);
  };

  return (
    <div className="flex flex-col gap-6 pb-16">
      {/* Wallet Header Card */}
      <div className="bg-gradient-to-br from-blue-900/30 via-[#12101F] to-purple-900/30 border border-white/15 rounded-3xl p-6 backdrop-blur-xl shadow-2xl relative overflow-hidden">
        <div className="absolute top-0 right-0 p-6 opacity-10 pointer-events-none">
          <Wallet className="w-48 h-48 text-white" />
        </div>

        <div className="flex items-center gap-2">
          <span className="px-2.5 py-0.5 rounded-full bg-amber-500/20 text-editorial-gold text-[10px] font-black tracking-wider uppercase border border-amber-500/30">
            GHANA MOBILE MONEY (MoMo)
          </span>
        </div>

        <div className="mt-4">
          <p className="text-xs text-slate-400 font-medium tracking-wider uppercase">Available Creator Balance</p>
          <div className="flex items-baseline gap-2 mt-1">
            <span className="text-4xl md:text-5xl font-black text-white">GH₵ {balance.toFixed(2)}</span>
          </div>
        </div>

        <div className="grid grid-cols-2 gap-4 mt-6 pt-4 border-t border-white/10">
          <div>
            <p className="text-[10px] text-blue-300 uppercase font-bold tracking-widest">Pending Rewards</p>
            <p className="text-lg font-light text-white mt-0.5">GH₵ {pendingRewards.toFixed(2)}</p>
          </div>
          <div>
            <p className="text-[10px] text-green-300 uppercase font-bold tracking-widest">Base Rate Unit</p>
            <p className="text-lg font-light text-white mt-0.5">GH₵ 1.00 / 1K views</p>
          </div>
        </div>
      </div>

      {/* Cashout Form */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <h3 className="text-base font-bold text-white mb-1 flex items-center gap-2">
          <Smartphone className="w-5 h-5 text-editorial-gold" />
          Request Ghana Mobile Money Cashout
        </h3>
        <p className="text-xs text-slate-400 mb-6">
          Instant payouts to MTN MoMo, Telecel Cash, and AT Money with automated 1% network fee deduction.
        </p>

        {errorMessage && (
          <div className="mb-4 p-3 bg-rose-500/10 border border-rose-500/30 rounded-2xl flex items-center gap-2 text-rose-300 text-xs font-semibold">
            <AlertCircle className="w-4 h-4 shrink-0 text-rose-400" />
            <span>{errorMessage}</span>
          </div>
        )}

        {successMessage && (
          <div className="mb-4 p-3 bg-green-500/10 border border-green-500/30 rounded-2xl flex items-center gap-2 text-green-300 text-xs font-semibold">
            <CheckCircle2 className="w-4 h-4 shrink-0 text-green-400" />
            <span>{successMessage}</span>
          </div>
        )}

        <form onSubmit={handleWithdraw} className="space-y-4">
          {/* Telco Selector */}
          <div>
            <label className="text-xs text-slate-300 font-semibold mb-2 block">Select Mobile Money Provider:</label>
            <div className="grid grid-cols-3 gap-3">
              {(["MTN MoMo", "Telecel Cash", "AT Money"] as const).map((net) => (
                <button
                  type="button"
                  key={net}
                  onClick={() => setNetwork(net)}
                  className={`py-2.5 px-3 rounded-2xl border text-xs font-bold transition-all ${
                    network === net
                      ? "bg-editorial-gradient border-blue-400 text-white shadow-lg shadow-blue-600/30"
                      : "bg-white/5 border-white/10 text-slate-300 hover:bg-white/10"
                  }`}
                >
                  {net}
                </button>
              ))}
            </div>
          </div>

          {/* Phone Number */}
          <div>
            <label className="text-xs text-slate-300 font-semibold mb-1 block">MoMo Phone Number (10 digits):</label>
            <input
              type="tel"
              value={phoneNumber}
              onChange={(e) => setPhoneNumber(e.target.value)}
              placeholder="e.g. 0244889900"
              className="w-full bg-white/5 border border-white/10 rounded-2xl px-4 py-3 text-sm text-white focus:outline-none focus:border-editorial-blue transition-colors"
            />
          </div>

          {/* Account Name */}
          <div>
            <label className="text-xs text-slate-300 font-semibold mb-1 block">Registered Account Name:</label>
            <input
              type="text"
              value={accountName}
              onChange={(e) => setAccountName(e.target.value)}
              placeholder="e.g. Israel Ewoenam Gokah"
              className="w-full bg-white/5 border border-white/10 rounded-2xl px-4 py-3 text-sm text-white focus:outline-none focus:border-editorial-blue transition-colors"
            />
          </div>

          {/* Amount */}
          <div>
            <label className="text-xs text-slate-300 font-semibold mb-1 block">Amount to Withdraw (GH₵):</label>
            <input
              type="number"
              min="10"
              step="1"
              value={amountInput}
              onChange={(e) => setAmountInput(e.target.value)}
              placeholder="Min. GH₵ 10.00"
              className="w-full bg-white/5 border border-white/10 rounded-2xl px-4 py-3 text-sm text-white focus:outline-none focus:border-editorial-blue transition-colors"
            />
          </div>

          {/* Payout Breakdown */}
          {numAmount > 0 && (
            <div className="bg-white/5 rounded-2xl p-3 text-xs space-y-1.5 border border-white/10 text-slate-300">
              <div className="flex justify-between">
                <span>Requested Amount:</span>
                <span className="font-bold text-white">GH₵ {numAmount.toFixed(2)}</span>
              </div>
              <div className="flex justify-between text-slate-400">
                <span>MoMo Network Fee (1%):</span>
                <span>- GH₵ {calculatedFee.toFixed(2)}</span>
              </div>
              <div className="flex justify-between font-bold text-green-400 pt-1 border-t border-white/10">
                <span>Net Payout to MoMo:</span>
                <span>GH₵ {netPayout.toFixed(2)}</span>
              </div>
            </div>
          )}

          <button
            type="submit"
            disabled={isProcessing}
            className="w-full py-3 bg-editorial-gradient text-white text-xs font-bold rounded-full shadow-lg shadow-blue-600/30 hover:scale-[1.01] transition-transform disabled:opacity-50"
          >
            {isProcessing ? "Connecting to Ghana MoMo Gateway..." : `Confirm & Cash Out to ${network}`}
          </button>
        </form>
      </div>

      {/* Transaction History Ledger */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <h3 className="text-base font-bold text-white mb-4 flex items-center gap-2">
          <History className="w-5 h-5 text-editorial-blue" />
          Transaction & Payout History
        </h3>

        <div className="space-y-3">
          {transactions.map((tx) => (
            <div
              key={tx.id}
              className="flex items-center justify-between p-3.5 bg-white/5 rounded-2xl border border-white/10 hover:border-white/20 transition-all"
            >
              <div className="flex items-center gap-3">
                <div className="w-10 h-10 rounded-xl bg-editorial-gradient flex items-center justify-center text-white">
                  <ArrowUpRight className="w-5 h-5" />
                </div>
                <div>
                  <p className="text-xs font-bold text-white">{tx.network} Cashout</p>
                  <p className="text-[10px] text-slate-400 font-mono">{tx.ref} • {tx.date}</p>
                </div>
              </div>

              <div className="text-right">
                <p className="text-sm font-black text-white">GH₵ {tx.netPayoutGhc.toFixed(2)}</p>
                <span className="text-[9px] uppercase tracking-wider font-bold text-green-400 bg-green-500/10 px-2 py-0.5 rounded-full">
                  {tx.status}
                </span>
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
