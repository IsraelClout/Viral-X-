"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { Home, Compass, PlusCircle, BarChart3, User, Wallet, Bell } from "lucide-react";

export function Navbar() {
  const pathname = usePathname();

  const navItems = [
    { name: "HOME", href: "/", icon: Home },
    { name: "DISCOVER", href: "/discover", icon: Compass },
    { name: "STUDIO", href: "/studio", icon: BarChart3 },
    { name: "WALLET", href: "/wallet", icon: Wallet },
    { name: "PROFILE", href: "/profile", icon: User },
  ];

  return (
    <header className="sticky top-0 z-50 w-full backdrop-blur-xl bg-[#050507]/80 border-b border-white/10">
      <div className="max-w-4xl mx-auto px-4 h-16 flex items-center justify-between">
        {/* Brand */}
        <Link href="/" className="flex items-center gap-2.5 group">
          <div className="w-8 h-8 rounded-lg bg-editorial-gradient flex items-center justify-center font-black text-white shadow-lg shadow-blue-600/20 group-hover:scale-105 transition-transform">
            X
          </div>
          <div>
            <h1 className="text-lg font-bold tracking-tight text-white flex items-center gap-1">
              Viral X <span className="text-[10px] uppercase font-bold text-editorial-blue bg-blue-500/10 px-1.5 py-0.5 rounded-full border border-blue-500/20">GH 🇬🇭</span>
            </h1>
          </div>
        </Link>

        {/* Desktop Nav */}
        <nav className="hidden md:flex items-center gap-1 bg-white/5 border border-white/10 rounded-full px-3 py-1">
          {navItems.map((item) => {
            const Icon = item.icon;
            const isActive = pathname === item.href;
            return (
              <Link
                key={item.name}
                href={item.href}
                className={`flex items-center gap-1.5 px-3 py-1.5 rounded-full text-xs font-semibold tracking-wider transition-all ${
                  isActive
                    ? "bg-editorial-gradient text-white shadow-md shadow-blue-500/20"
                    : "text-slate-400 hover:text-white hover:bg-white/5"
                }`}
              >
                <Icon className="w-3.5 h-3.5" />
                {item.name}
              </Link>
            );
          })}
        </nav>

        {/* Header Right (Wallet Pill & Create) */}
        <div className="flex items-center gap-3">
          <Link
            href="/wallet"
            className="flex items-center gap-2 bg-white/10 hover:bg-white/15 backdrop-blur-md border border-white/10 rounded-full px-3 py-1.5 transition-all"
          >
            <span className="text-[9px] uppercase tracking-widest text-editorial-blue font-black">WALLET</span>
            <span className="text-xs font-bold text-white">GH₵ 1,240.50</span>
          </Link>

          <Link
            href="/studio"
            className="w-8 h-8 rounded-full bg-editorial-gradient flex items-center justify-center text-white shadow-lg shadow-blue-600/30 hover:scale-105 transition-transform"
          >
            <PlusCircle className="w-5 h-5" />
          </Link>
        </div>
      </div>

      {/* Mobile Bottom Navigation */}
      <div className="md:hidden fixed bottom-4 left-4 right-4 z-50">
        <nav className="flex justify-around items-center h-16 bg-[#0C0A14]/90 backdrop-blur-2xl border border-white/15 rounded-full shadow-2xl px-3">
          {navItems.slice(0, 2).map((item) => {
            const Icon = item.icon;
            const isActive = pathname === item.href;
            return (
              <Link
                key={item.name}
                href={item.href}
                className={`flex flex-col items-center justify-center text-[9px] font-bold tracking-wider ${
                  isActive ? "text-editorial-blue" : "text-slate-400 hover:text-white"
                }`}
              >
                <Icon className="w-5 h-5 mb-0.5" />
                {item.name}
              </Link>
            );
          })}

          {/* Center Floating Plus */}
          <Link
            href="/studio"
            className="w-12 h-12 -mt-5 bg-editorial-gradient rounded-full flex items-center justify-center shadow-xl shadow-blue-600/40 border-2 border-[#050507] hover:scale-105 transition-transform text-white"
          >
            <PlusCircle className="w-6 h-6" />
          </Link>

          {navItems.slice(2).map((item) => {
            const Icon = item.icon;
            const isActive = pathname === item.href;
            return (
              <Link
                key={item.name}
                href={item.href}
                className={`flex flex-col items-center justify-center text-[9px] font-bold tracking-wider ${
                  isActive ? "text-editorial-blue" : "text-slate-400 hover:text-white"
                }`}
              >
                <Icon className="w-5 h-5 mb-0.5" />
                {item.name}
              </Link>
            );
          })}
        </nav>
      </div>
    </header>
  );
}
