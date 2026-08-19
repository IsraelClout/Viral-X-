"use client";

import { useState } from "react";
import { User, Shield, Key, Grid, Bookmark, Settings, LogOut, CheckCircle2 } from "lucide-react";

export default function ProfilePage() {
  const [activeTab, setActiveTab] = useState<"posts" | "saved">("posts");
  const [twoFactorEnabled, setTwoFactorEnabled] = useState(true);

  const posts = [
    { id: "p1", url: "https://images.unsplash.com/photo-1516280440614-37939bbacd81?auto=format&fit=crop&q=80&w=400", views: "48.2K" },
    { id: "p2", url: "https://images.unsplash.com/photo-1509631179647-0177331693ae?auto=format&fit=crop&q=80&w=400", views: "34.1K" },
    { id: "p3", url: "https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&q=80&w=400", views: "19.8K" },
  ];

  return (
    <div className="flex flex-col gap-6 pb-16">
      {/* Profile Header */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl flex flex-col md:flex-row items-center gap-6 text-center md:text-left">
        <div className="w-24 h-24 rounded-full p-1 bg-editorial-gradient shadow-xl shadow-purple-600/30">
          <img
            src="https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=400"
            alt="Profile Avatar"
            className="w-full h-full rounded-full object-cover border-2 border-[#050507]"
          />
        </div>

        <div className="flex-1">
          <div className="flex flex-col md:flex-row md:items-center gap-2">
            <h2 className="text-xl font-bold text-white">Israel Ewoenam Gokah</h2>
            <span className="bg-blue-500/20 text-editorial-blue border border-blue-500/30 text-[10px] font-black px-2.5 py-0.5 rounded-full uppercase tracking-wider">
              VERIFIED CREATOR 🇬🇭
            </span>
          </div>
          <p className="text-xs text-slate-400 mt-1">@israel_gokah • Accra, Ghana</p>
          <p className="text-xs text-slate-300 mt-2 max-w-md">
            Digital creator & full-stack architect building the future of African digital media & creator monetization.
          </p>

          <div className="flex justify-center md:justify-start gap-6 mt-4 pt-4 border-t border-white/10 text-xs">
            <div>
              <span className="font-bold text-white">1,284</span> <span className="text-slate-400">Followers</span>
            </div>
            <div>
              <span className="font-bold text-white">312</span> <span className="text-slate-400">Following</span>
            </div>
            <div>
              <span className="font-bold text-editorial-gold">98.2</span> <span className="text-slate-400">Viral Index</span>
            </div>
          </div>
        </div>
      </div>

      {/* Security & 2FA Card */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl flex items-center justify-between">
        <div className="flex items-center gap-3">
          <div className="w-10 h-10 rounded-2xl bg-editorial-gradient flex items-center justify-center text-white">
            <Shield className="w-5 h-5" />
          </div>
          <div>
            <h4 className="text-sm font-bold text-white flex items-center gap-1.5">
              Two-Factor Authentication (2FA)
              {twoFactorEnabled && <CheckCircle2 className="w-4 h-4 text-green-400" />}
            </h4>
            <p className="text-xs text-slate-400">Required for securing Ghana Mobile Money withdrawals.</p>
          </div>
        </div>

        <button
          onClick={() => setTwoFactorEnabled(!twoFactorEnabled)}
          className={`px-4 py-1.5 rounded-full text-xs font-bold transition-all ${
            twoFactorEnabled ? "bg-green-500/20 text-green-400 border border-green-500/30" : "bg-white/10 text-slate-300"
          }`}
        >
          {twoFactorEnabled ? "Enabled" : "Enable"}
        </button>
      </div>

      {/* Profile Content Grid */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <div className="flex justify-center border-b border-white/10 pb-4 mb-6">
          <div className="flex gap-4">
            <button
              onClick={() => setActiveTab("posts")}
              className={`flex items-center gap-1.5 text-xs font-bold pb-1 transition-all ${
                activeTab === "posts" ? "text-editorial-blue border-b-2 border-editorial-blue" : "text-slate-400 hover:text-white"
              }`}
            >
              <Grid className="w-4 h-4" /> My Posts
            </button>
            <button
              onClick={() => setActiveTab("saved")}
              className={`flex items-center gap-1.5 text-xs font-bold pb-1 transition-all ${
                activeTab === "saved" ? "text-editorial-blue border-b-2 border-editorial-blue" : "text-slate-400 hover:text-white"
              }`}
            >
              <Bookmark className="w-4 h-4" /> Bookmarks
            </button>
          </div>
        </div>

        <div className="grid grid-cols-2 md:grid-cols-3 gap-3">
          {posts.map((post) => (
            <div key={post.id} className="aspect-square rounded-2xl overflow-hidden relative group border border-white/10">
              <img src={post.url} alt="Post thumbnail" className="w-full h-full object-cover group-hover:scale-105 transition-transform" />
              <div className="absolute inset-0 bg-black/40 opacity-0 group-hover:opacity-100 transition-opacity flex items-center justify-center text-xs font-bold text-white">
                👁️ {post.views}
              </div>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
