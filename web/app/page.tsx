"use client";

import { useState, useEffect } from "react";
import Image from "next/image";
import { Heart, MessageCircle, Star, Bookmark, Share2, Play, Volume2, Flame, Eye, Sparkles } from "lucide-react";

interface Post {
  id: string;
  creatorName: string;
  creatorHandle: string;
  location: string;
  avatarUrl: string;
  mediaUrl: string;
  caption: string;
  hashtags: string;
  likesCount: number;
  commentsCount: number;
  viralScore: number;
  averageRating: number;
  duration: string;
}

export default function HomeFeed() {
  const [activeTab, setActiveTab] = useState<"for_you" | "following">("for_you");
  const [likedPosts, setLikedPosts] = useState<Record<string, boolean>>({});
  const [savedPosts, setSavedPosts] = useState<Record<string, boolean>>({});
  const [userRatings, setUserRatings] = useState<Record<string, number>>({});
  const [ratingModalPost, setRatingModalPost] = useState<Post | null>(null);

  const stories = [
    { name: "Your Story", avatar: "https://images.unsplash.com/photo-1534528741775-53994a69daeb?auto=format&fit=crop&q=80&w=200", isUnseen: false },
    { name: "Kofi_Creative", avatar: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&q=80&w=200", isUnseen: true },
    { name: "Ama_Fashion", avatar: "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&q=80&w=200", isUnseen: true },
    { name: "Kwame_Beats", avatar: "https://images.unsplash.com/photo-1500648767791-00dcc994a43e?auto=format&fit=crop&q=80&w=200", isUnseen: true },
    { name: "Adwoa_Accra", avatar: "https://images.unsplash.com/photo-1494790108377-be9c29b29330?auto=format&fit=crop&q=80&w=200", isUnseen: true },
  ];

  const posts: Post[] = [
    {
      id: "post_1",
      creatorName: "Kofi_Creative",
      creatorHandle: "kofi_accra",
      location: "Accra, Ghana",
      avatarUrl: "https://images.unsplash.com/photo-1507003211169-0a1dd7228f2d?auto=format&fit=crop&q=80&w=200",
      mediaUrl: "https://images.unsplash.com/photo-1516280440614-37939bbacd81?auto=format&fit=crop&q=80&w=800",
      caption: "Showcasing the vibrant street culture and energetic Afrobeats sounds straight from Osu, Accra! 🇬🇭🔥 Rate this clip to support our viral creator fund!",
      hashtags: "#ViralX #AccraVibes #GhanaCreators #Afrobeats #MonetizeGhana",
      likesCount: 12400,
      commentsCount: 842,
      viralScore: 98.2,
      averageRating: 4.9,
      duration: "02:45",
    },
    {
      id: "post_2",
      creatorName: "Ama_Fashion",
      creatorHandle: "ama_style",
      location: "Kumasi, Ghana",
      avatarUrl: "https://images.unsplash.com/photo-1517841905240-472988babdf9?auto=format&fit=crop&q=80&w=200",
      mediaUrl: "https://images.unsplash.com/photo-1509631179647-0177331693ae?auto=format&fit=crop&q=80&w=800",
      caption: "Modern Kente Fusion 2026 runway reveal in Accra. Hand-woven authentic fabrics meeting contemporary African luxury design. 👑✨",
      hashtags: "#GhanaFashion #KenteLuxury #ViralXCreators #AccraFashionWeek",
      likesCount: 8900,
      commentsCount: 512,
      viralScore: 94.6,
      averageRating: 4.8,
      duration: "01:30",
    },
  ];

  const toggleLike = (id: string) => {
    setLikedPosts((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  const toggleSave = (id: string) => {
    setSavedPosts((prev) => ({ ...prev, [id]: !prev[id] }));
  };

  const handleRate = (postId: string, stars: number) => {
    setUserRatings((prev) => ({ ...prev, [postId]: stars }));
    setRatingModalPost(null);
  };

  return (
    <div className="flex flex-col gap-6 pb-16">
      {/* Stories Row */}
      <div className="flex items-center gap-3 overflow-x-auto pb-2 scrollbar-none">
        {stories.map((story, i) => (
          <div key={i} className="flex flex-col items-center gap-1 shrink-0 cursor-pointer group">
            <div className={`w-16 h-16 rounded-full p-[2px] ${story.isUnseen ? "bg-editorial-gradient shadow-md shadow-purple-600/30" : "border border-white/20"}`}>
              <div className="w-full h-full rounded-full overflow-hidden border-2 border-[#050507]">
                <img src={story.avatar} alt={story.name} className="w-full h-full object-cover group-hover:scale-110 transition-transform" />
              </div>
            </div>
            <span className="text-[11px] font-medium text-slate-300 w-16 truncate text-center">{story.name}</span>
          </div>
        ))}
      </div>

      {/* Editorial Quick Stats Banner */}
      <div className="grid grid-cols-2 gap-3">
        <div className="bg-white/5 backdrop-blur-xl border border-white/10 rounded-2xl p-4 flex flex-col justify-between hover:border-white/20 transition-all">
          <p className="text-[10px] text-slate-400 uppercase font-bold tracking-widest">Today&apos;s Reach</p>
          <div className="mt-2">
            <p className="text-2xl font-light tracking-tight text-white">4.2M</p>
            <div className="h-1.5 w-full bg-white/10 rounded-full mt-2 overflow-hidden">
              <div className="h-full bg-editorial-blue w-[72%] rounded-full shadow-sm shadow-blue-500/50"></div>
            </div>
          </div>
        </div>

        <div className="bg-gradient-to-br from-blue-600/20 to-purple-600/20 backdrop-blur-xl border border-blue-500/30 rounded-2xl p-4 flex flex-col justify-between hover:border-blue-500/50 transition-all">
          <p className="text-[10px] text-blue-300 uppercase font-bold tracking-widest">Est. Rewards</p>
          <div className="mt-2">
            <p className="text-2xl font-light tracking-tight text-white">GH₵ 142.00</p>
            <p className="text-[10px] text-blue-300/80 mt-1 italic flex items-center gap-1">
              <Sparkles className="w-3 h-3 text-editorial-gold" /> Pending Verification
            </p>
          </div>
        </div>
      </div>

      {/* Feed Toggle */}
      <div className="flex justify-center">
        <div className="flex bg-white/5 border border-white/10 rounded-full p-1">
          <button
            onClick={() => setActiveTab("for_you")}
            className={`px-4 py-1.5 rounded-full text-xs font-bold transition-all ${
              activeTab === "for_you" ? "bg-editorial-gradient text-white shadow-md shadow-blue-500/20" : "text-slate-400 hover:text-white"
            }`}
          >
            For You (Viral 🔥)
          </button>
          <button
            onClick={() => setActiveTab("following")}
            className={`px-4 py-1.5 rounded-full text-xs font-bold transition-all ${
              activeTab === "following" ? "bg-editorial-gradient text-white shadow-md shadow-blue-500/20" : "text-slate-400 hover:text-white"
            }`}
          >
            Following
          </button>
        </div>
      </div>

      {/* Feed Posts */}
      <div className="flex flex-col gap-6">
        {posts.map((post) => {
          const isLiked = likedPosts[post.id];
          const isSaved = savedPosts[post.id];
          const userRating = userRatings[post.id] || post.averageRating;

          return (
            <article
              key={post.id}
              className="bg-white/5 backdrop-blur-xl border border-white/10 rounded-3xl p-4 flex flex-col gap-3 shadow-xl hover:border-white/20 transition-all"
            >
              {/* Post Header */}
              <div className="flex items-center justify-between">
                <div className="flex items-center gap-3">
                  <div className="w-11 h-11 rounded-full p-0.5 ring-2 ring-blue-500/50">
                    <img src={post.avatarUrl} alt={post.creatorName} className="w-full h-full rounded-full object-cover" />
                  </div>
                  <div>
                    <p className="font-bold text-sm text-white flex items-center gap-1">
                      {post.creatorName} <span className="text-[10px] text-blue-400">✓</span>
                    </p>
                    <p className="text-xs text-slate-400 italic">{post.location}</p>
                  </div>
                </div>

                <div className="flex items-center gap-2">
                  <span className="bg-green-500/20 text-green-400 text-[10px] px-2.5 py-0.5 rounded-full border border-green-500/30 font-black tracking-tighter">
                    LIVE VIRAL
                  </span>
                  <button className="text-xs bg-white text-black font-bold px-3 py-1 rounded-full hover:bg-slate-200 transition-all">
                    + Follow
                  </button>
                </div>
              </div>

              {/* Media Player Container */}
              <div className="aspect-video w-full rounded-2xl bg-gradient-to-br from-blue-950/40 to-purple-950/40 border border-white/10 relative flex items-center justify-center overflow-hidden group">
                <img src={post.mediaUrl} alt={post.caption} className="absolute inset-0 w-full h-full object-cover opacity-80 group-hover:scale-105 transition-transform duration-500" />
                <div className="absolute inset-0 bg-gradient-to-t from-black/80 via-transparent to-black/30"></div>

                {/* Play Icon */}
                <div className="z-10 w-12 h-12 rounded-full bg-white/20 backdrop-blur-md flex items-center justify-center border border-white/30 group-hover:scale-110 transition-transform cursor-pointer">
                  <Play className="w-6 h-6 text-white ml-1 fill-white" />
                </div>

                {/* Video Info Overlay */}
                <div className="absolute bottom-3 left-3 flex items-center gap-2">
                  <span className="bg-black/60 backdrop-blur-md text-[10px] px-2 py-1 rounded-lg border border-white/10 font-mono text-white flex items-center gap-1">
                    <Volume2 className="w-3 h-3 text-editorial-cyan" /> {post.duration}
                  </span>
                </div>
              </div>

              {/* Caption & Hashtags */}
              <div className="px-1">
                <p className="text-sm text-slate-200 leading-relaxed">{post.caption}</p>
                <p className="text-xs text-editorial-blue font-semibold mt-1">{post.hashtags}</p>
              </div>

              {/* Action Bar */}
              <div className="flex justify-between items-center px-1 pt-2 border-t border-white/5">
                <div className="flex items-center gap-3">
                  {/* Like Button */}
                  <button
                    onClick={() => toggleLike(post.id)}
                    className={`flex items-center gap-1.5 px-3 py-1 rounded-lg border text-xs font-semibold transition-all ${
                      isLiked
                        ? "bg-rose-500/20 border-rose-500/40 text-rose-400"
                        : "bg-white/5 border-white/10 text-slate-300 hover:bg-white/10"
                    }`}
                  >
                    <Heart className={`w-4 h-4 ${isLiked ? "fill-rose-400" : ""}`} />
                    <span>{((post.likesCount + (isLiked ? 1 : 0)) / 1000).toFixed(1)}K</span>
                  </button>

                  {/* Comment Button */}
                  <button className="flex items-center gap-1.5 px-3 py-1 rounded-lg border border-white/10 bg-white/5 text-slate-300 text-xs font-semibold hover:bg-white/10">
                    <MessageCircle className="w-4 h-4" />
                    <span>{post.commentsCount}</span>
                  </button>

                  {/* Star Rating Button */}
                  <button
                    onClick={() => setRatingModalPost(post)}
                    className="flex items-center gap-1 px-3 py-1 rounded-lg border border-amber-500/30 bg-amber-500/10 text-amber-300 text-xs font-semibold hover:bg-amber-500/20"
                  >
                    <Star className="w-4 h-4 fill-amber-400 text-amber-400" />
                    <span>{userRating.toFixed(1)}</span>
                  </button>

                  {/* Bookmark Button */}
                  <button
                    onClick={() => toggleSave(post.id)}
                    className={`p-1.5 rounded-lg border transition-all ${
                      isSaved ? "bg-blue-500/20 border-blue-500/40 text-blue-400" : "bg-white/5 border-white/10 text-slate-400 hover:text-white"
                    }`}
                  >
                    <Bookmark className={`w-4 h-4 ${isSaved ? "fill-blue-400" : ""}`} />
                  </button>
                </div>

                {/* Viral Score Badge */}
                <div className="flex items-center gap-1.5 bg-blue-500/10 border border-blue-500/20 px-2.5 py-1 rounded-full">
                  <span className="text-[9px] text-slate-400 uppercase font-black tracking-wider">VIRAL SCORE</span>
                  <span className="text-sm font-black text-editorial-blue">{post.viralScore}</span>
                </div>
              </div>
            </article>
          );
        })}
      </div>

      {/* 5-Star Rating Dialog Modal */}
      {ratingModalPost && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-md p-4">
          <div className="bg-[#12101F] border border-white/20 rounded-3xl max-w-sm w-full p-6 text-center shadow-2xl">
            <h3 className="text-lg font-bold text-white mb-1">Rate this Creator&apos;s Post ⭐</h3>
            <p className="text-xs text-slate-400 mb-6">
              Authentic community ratings boost {ratingModalPost.creatorName}&apos;s Viral Score and GH₵ earnings.
            </p>

            <div className="flex justify-center gap-2 mb-6">
              {[1, 2, 3, 4, 5].map((star) => (
                <button
                  key={star}
                  onClick={() => handleRate(ratingModalPost.id, star)}
                  className="p-2 hover:scale-125 transition-transform text-amber-400"
                >
                  <Star className="w-8 h-8 fill-amber-400" />
                </button>
              ))}
            </div>

            <button
              onClick={() => setRatingModalPost(null)}
              className="text-xs text-slate-400 hover:text-white"
            >
              Cancel
            </button>
          </div>
        </div>
      )}
    </div>
  );
}
