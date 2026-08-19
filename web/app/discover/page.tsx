"use client";

import { useState } from "react";
import { Search, Compass, Flame, TrendingUp, Users } from "lucide-react";

export default function DiscoverPage() {
  const [searchQuery, setSearchQuery] = useState("");

  const trendingTags = [
    { tag: "#ViralXGhana", posts: "24.2K posts" },
    { tag: "#AccraVibes", posts: "18.5K posts" },
    { tag: "#Afrobeats2026", posts: "14.1K posts" },
    { tag: "#KenteFashion", posts: "9.8K posts" },
    { tag: "#GhanaComedy", posts: "8.4K posts" },
  ];

  const featuredCreators = [
    { name: "Kofi_Creative", role: "Cinematographer • Accra", avatar: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&q=80&w=200", followers: "42.8K" },
    { name: "Ama_Fashion", role: "High-Fashion Stylist • Kumasi", avatar: "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&q=80&w=200", followers: "31.2K" },
    { name: "Kwame_Beats", role: "Music Producer • Tema", avatar: "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&q=80&w=200", followers: "28.5K" },
  ];

  return (
    <div className="flex flex-col gap-6 pb-16">
      {/* Search Input Bar */}
      <div className="relative">
        <Search className="w-5 h-5 text-slate-400 absolute left-4 top-1/2 -translate-y-1/2" />
        <input
          type="text"
          value={searchQuery}
          onChange={(e) => setSearchQuery(e.target.value)}
          placeholder="Search Ghanaian creators, #hashtags, and trending music..."
          className="w-full bg-white/5 border border-white/10 rounded-2xl pl-12 pr-4 py-3.5 text-sm text-white placeholder-slate-400 focus:outline-none focus:border-editorial-blue transition-colors"
        />
      </div>

      {/* Trending Hashtags */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <h3 className="text-base font-bold text-white mb-4 flex items-center gap-2">
          <Flame className="w-5 h-5 text-editorial-gold" />
          Trending in Ghana
        </h3>
        <div className="flex flex-wrap gap-2">
          {trendingTags.map((item) => (
            <button
              key={item.tag}
              className="flex items-center gap-2 px-4 py-2 bg-white/5 hover:bg-editorial-gradient hover:text-white border border-white/10 rounded-2xl text-xs font-semibold text-slate-200 transition-all"
            >
              <span>{item.tag}</span>
              <span className="text-[10px] text-slate-400 font-normal">{item.posts}</span>
            </button>
          ))}
        </div>
      </div>

      {/* Featured Creators Carousel / Grid */}
      <div className="bg-white/5 border border-white/10 rounded-3xl p-6 backdrop-blur-xl">
        <h3 className="text-base font-bold text-white mb-4 flex items-center gap-2">
          <Users className="w-5 h-5 text-editorial-blue" />
          Top Monetizing Creators
        </h3>
        <div className="space-y-3">
          {featuredCreators.map((creator) => (
            <div
              key={creator.name}
              className="flex items-center justify-between p-3 bg-white/5 rounded-2xl border border-white/10 hover:border-white/20 transition-all"
            >
              <div className="flex items-center gap-3">
                <img src={creator.avatar} alt={creator.name} className="w-12 h-12 rounded-full object-cover ring-2 ring-blue-500/30" />
                <div>
                  <p className="text-sm font-bold text-white">{creator.name}</p>
                  <p className="text-xs text-slate-400">{creator.role}</p>
                </div>
              </div>
              <button className="px-4 py-1.5 bg-white text-black text-xs font-bold rounded-full hover:bg-slate-200 transition-colors">
                Follow
              </button>
            </div>
          ))}
        </div>
      </div>
    </div>
  );
}
