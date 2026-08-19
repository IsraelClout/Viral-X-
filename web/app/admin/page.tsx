"use client";

import { useState } from "react";
import { ShieldAlert, CheckCircle2, XCircle, Settings, Sliders } from "lucide-react";

export default function AdminPage() {
  const [baseRewardUnit, setBaseRewardUnit] = useState(1.00);
  const [reports, setReports] = useState([
    { id: "rep_1", type: "POST", title: "Alleged bot-driven viral engagement", author: "@suspicious_creator", reason: "Artificially high watch time without retention", status: "PENDING" },
    { id: "rep_2", type: "USER", title: "Impersonation claim", author: "@fake_accra_music", reason: "Copying verified creator identity", status: "PENDING" },
  ]);

  const resolveReport = (id: string, action: "RESOLVED" | "DISMISSED") => {
    setReports((prev) => prev.filter((r) => r.id !== id));
  };

  return (
    <div className="flex flex-col gap-6 pb-16">
      {/* Admin Header */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <div className="flex items-center gap-2">
          <span className="px-2.5 py-0.5 rounded-full bg-rose-500/20 text-editorial-pink text-[10px] font-black tracking-wider uppercase border border-rose-500/30">
            SYSTEM ADMIN
          </span>
          <span className="text-xs text-slate-400">Governance & Trust</span>
        </div>
        <h2 className="text-2xl font-bold text-white mt-1">Admin Moderation & Reward Engine</h2>
        <p className="text-xs text-slate-400 mt-0.5">
          Maintain Ghana creator ecosystem integrity, resolve policy flags, and calibrate platform reward multipliers.
        </p>
      </div>

      {/* Base Rate Configuration */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <h3 className="text-base font-bold text-white mb-2 flex items-center gap-2">
          <Sliders className="w-5 h-5 text-editorial-blue" />
          Base Engagement Unit (Ghana Cedis)
        </h3>
        <p className="text-xs text-slate-400 mb-4">
          Current base reward allocated per 1,000 qualified Ghanaian views before Viral Score exponential weighting.
        </p>
        <div className="flex items-center gap-4">
          <input
            type="number"
            step="0.1"
            value={baseRewardUnit}
            onChange={(e) => setBaseRewardUnit(parseFloat(e.target.value) || 1.0)}
            className="bg-white/5 border border-white/10 rounded-2xl px-4 py-2.5 text-sm text-white w-36 focus:outline-none focus:border-editorial-blue"
          />
          <span className="text-xs text-green-400 font-bold">GH₵ {baseRewardUnit.toFixed(2)} / 1K Views Active</span>
        </div>
      </div>

      {/* Moderation Queue */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <h3 className="text-base font-bold text-white mb-4 flex items-center gap-2">
          <ShieldAlert className="w-5 h-5 text-editorial-pink" />
          Content Moderation Queue ({reports.length})
        </h3>

        {reports.length === 0 ? (
          <p className="text-xs text-slate-400 py-6 text-center">All creator moderation flags have been reviewed and resolved. ✨</p>
        ) : (
          <div className="space-y-3">
            {reports.map((report) => (
              <div key={report.id} className="p-4 bg-white/5 rounded-2xl border border-white/10 flex flex-col md:flex-row md:items-center justify-between gap-4">
                <div>
                  <div className="flex items-center gap-2">
                    <span className="text-[10px] font-bold px-2 py-0.5 rounded-full bg-rose-500/20 text-rose-300 border border-rose-500/30">
                      {report.type}
                    </span>
                    <span className="text-xs font-bold text-white">{report.title}</span>
                  </div>
                  <p className="text-xs text-slate-300 mt-1">{report.reason}</p>
                  <p className="text-[10px] text-slate-500 mt-0.5">Target: {report.author}</p>
                </div>

                <div className="flex items-center gap-2">
                  <button
                    onClick={() => resolveReport(report.id, "RESOLVED")}
                    className="flex items-center gap-1 px-3 py-1.5 bg-green-500/20 text-green-300 border border-green-500/30 text-xs font-bold rounded-xl hover:bg-green-500/30"
                  >
                    <CheckCircle2 className="w-4 h-4" /> Enforce
                  </button>
                  <button
                    onClick={() => resolveReport(report.id, "DISMISSED")}
                    className="flex items-center gap-1 px-3 py-1.5 bg-white/10 text-slate-300 border border-white/10 text-xs font-bold rounded-xl hover:bg-white/20"
                  >
                    <XCircle className="w-4 h-4" /> Dismiss
                  </button>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>
    </div>
  );
}
