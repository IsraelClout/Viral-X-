"use client";

import { useState } from "react";
import { BarChart3, TrendingUp, Sparkles, Award, ShieldCheck, Zap, Upload, Video } from "lucide-react";
import { calculateViralScore, estimateEarningsGhc } from "@/lib/viral-score";

export default function CreatorStudioPage() {
  const [views, setViews] = useState(48200);
  const [completionRate, setCompletionRate] = useState(0.88);
  const [likes, setLikes] = useState(12400);
  const [shares, setShares] = useState(1850);
  const [comments, setComments] = useState(842);
  const [averageRating, setAverageRating] = useState(4.9);

  const calculatedViralScore = calculateViralScore({
    viewsCount: views,
    completionRate: completionRate,
    watchTimeSeconds: views * 45,
    likesCount: likes,
    commentsCount: comments,
    sharesCount: shares,
    ratingsCount: 620,
    averageRating: averageRating,
    isGhanaianAudienceVerified: true,
  });

  const estimatedEarnings = estimateEarningsGhc(calculatedViralScore, views, 1.0);

  return (
    <div className="flex flex-col gap-6 pb-16">
      {/* Studio Header */}
      <div className="flex flex-col md:flex-row md:items-center justify-between gap-4 bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <div>
          <div className="flex items-center gap-2">
            <span className="px-2.5 py-0.5 rounded-full bg-purple-500/20 text-editorial-purple text-[10px] font-black tracking-wider uppercase border border-purple-500/30">
              CREATOR DASHBOARD
            </span>
            <span className="text-xs text-slate-400">Accra Server v2.4</span>
          </div>
          <h2 className="text-2xl font-bold text-white mt-1">Creator Studio & Viral Engine</h2>
          <p className="text-xs text-slate-400 mt-0.5">
            Real-time algorithmic monetization calculation based on authentic Ghanaian engagement.
          </p>
        </div>

        <button className="flex items-center justify-center gap-2 px-5 py-2.5 bg-editorial-gradient text-white text-xs font-bold rounded-full shadow-lg shadow-blue-600/30 hover:scale-105 transition-transform">
          <Upload className="w-4 h-4" /> Upload New Content
        </button>
      </div>

      {/* Primary Metrics Grid */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-4">
        {/* Viral Score Gauge Card */}
        <div className="bg-gradient-to-br from-blue-950/40 to-purple-950/40 border border-blue-500/30 rounded-3xl p-6 backdrop-blur-xl flex flex-col justify-between">
          <div className="flex justify-between items-start">
            <span className="text-xs font-bold uppercase tracking-wider text-blue-300">Viral Score Engine</span>
            <Zap className="w-5 h-5 text-editorial-gold" />
          </div>
          <div className="my-6 flex items-baseline gap-2">
            <span className="text-5xl font-black text-white">{calculatedViralScore}</span>
            <span className="text-sm font-semibold text-editorial-blue">/ 100.0</span>
          </div>
          <div className="space-y-1.5">
            <div className="flex justify-between text-[11px] text-slate-300">
              <span>Viral Multiplier</span>
              <span className="font-bold text-green-400">2.45x Active</span>
            </div>
            <div className="h-1.5 w-full bg-white/10 rounded-full overflow-hidden">
              <div className="h-full bg-editorial-blue rounded-full" style={{ width: `${calculatedViralScore}%` }}></div>
            </div>
          </div>
        </div>

        {/* Est. Monetization Card */}
        <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl flex flex-col justify-between">
          <div className="flex justify-between items-start">
            <span className="text-xs font-bold uppercase tracking-wider text-slate-400">Est. Creator Rewards</span>
            <Sparkles className="w-5 h-5 text-editorial-gold" />
          </div>
          <div className="my-6">
            <span className="text-4xl font-black text-white">GH₵ {estimatedEarnings.toFixed(2)}</span>
            <p className="text-[11px] text-slate-400 mt-1">Base unit GH₵1.00 / 1K qualified views</p>
          </div>
          <div className="flex items-center gap-1.5 text-xs text-green-400 font-semibold bg-green-500/10 border border-green-500/20 px-3 py-1.5 rounded-xl">
            <ShieldCheck className="w-4 h-4" /> Ready for MoMo Cashout
          </div>
        </div>

        {/* Verified Audience Card */}
        <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl flex flex-col justify-between">
          <div className="flex justify-between items-start">
            <span className="text-xs font-bold uppercase tracking-wider text-slate-400">Ghanaian Audience</span>
            <Award className="w-5 h-5 text-editorial-cyan" />
          </div>
          <div className="my-6">
            <span className="text-4xl font-black text-white">94.8%</span>
            <p className="text-[11px] text-slate-400 mt-1">Authentic geo-verified viewer retention</p>
          </div>
          <div className="text-[11px] text-slate-400">
            Greater Accra (68%) • Ashanti (22%)
          </div>
        </div>
      </div>

      {/* Interactive Engagement Simulator */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <h3 className="text-base font-bold text-white mb-2 flex items-center gap-2">
          <TrendingUp className="w-4 h-4 text-editorial-blue" />
          Monetization & Viral Simulator
        </h3>
        <p className="text-xs text-slate-400 mb-6">
          Test how audience retention, 5-star ratings, and video completion rates directly impact your Ghana Cedis rewards.
        </p>

        <div className="grid grid-cols-1 md:grid-cols-2 gap-6">
          <div>
            <label className="text-xs text-slate-300 font-semibold flex justify-between">
              <span>Audience Views:</span>
              <span className="text-editorial-blue font-bold">{views.toLocaleString()}</span>
            </label>
            <input
              type="range"
              min="1000"
              max="200000"
              step="1000"
              value={views}
              onChange={(e) => setViews(Number(e.target.value))}
              className="w-full h-1.5 bg-white/10 rounded-lg appearance-none cursor-pointer accent-blue-500 mt-2"
            />
          </div>

          <div>
            <label className="text-xs text-slate-300 font-semibold flex justify-between">
              <span>Completion Rate:</span>
              <span className="text-editorial-purple font-bold">{(completionRate * 100).toFixed(0)}%</span>
            </label>
            <input
              type="range"
              min="0.2"
              max="1.0"
              step="0.05"
              value={completionRate}
              onChange={(e) => setCompletionRate(Number(e.target.value))}
              className="w-full h-1.5 bg-white/10 rounded-lg appearance-none cursor-pointer accent-purple-500 mt-2"
            />
          </div>

          <div>
            <label className="text-xs text-slate-300 font-semibold flex justify-between">
              <span>Community Rating:</span>
              <span className="text-editorial-gold font-bold">⭐ {averageRating.toFixed(1)}</span>
            </label>
            <input
              type="range"
              min="2.0"
              max="5.0"
              step="0.1"
              value={averageRating}
              onChange={(e) => setAverageRating(Number(e.target.value))}
              className="w-full h-1.5 bg-white/10 rounded-lg appearance-none cursor-pointer accent-amber-500 mt-2"
            />
          </div>

          <div>
            <label className="text-xs text-slate-300 font-semibold flex justify-between">
              <span>Social Shares:</span>
              <span className="text-editorial-cyan font-bold">{shares.toLocaleString()}</span>
            </label>
            <input
              type="range"
              min="10"
              max="10000"
              step="50"
              value={shares}
              onChange={(e) => setShares(Number(e.target.value))}
              className="w-full h-1.5 bg-white/10 rounded-lg appearance-none cursor-pointer accent-sky-500 mt-2"
            />
          </div>
        </div>
      </div>
    </div>
  );
}
