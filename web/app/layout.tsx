import type { Metadata } from "next";
import { Inter } from "next/font/google";
import "./globals.css";
import { Navbar } from "@/components/Navbar";
import { Footer } from "@/components/Footer";

const inter = Inter({ subsets: ["latin"] });

export const metadata: Metadata = {
  title: "Viral X • Ghana Creator & Monetization Platform",
  description: "Ghana's premier social media and creator monetization platform. Watch trending videos, boost your Viral Score, and cash out to MTN MoMo, Telecel Cash, and AT Money.",
  authors: [{ name: "Gokah Israel Ewoenam" }],
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <html lang="en" className="dark">
      <body className={`${inter.className} bg-[#050507] text-slate-100 min-h-screen flex flex-col antialiased selection:bg-blue-600 selection:text-white`}>
        {/* Atmospheric Glows */}
        <div className="fixed inset-0 pointer-events-none overflow-hidden z-0">
          <div className="absolute top-[-10%] left-[-10%] w-[50%] h-[40%] bg-blue-600/15 blur-[120px] rounded-full"></div>
          <div className="absolute bottom-[10%] right-[-10%] w-[60%] h-[40%] bg-purple-600/15 blur-[140px] rounded-full"></div>
        </div>

        <Navbar />
        <main className="relative z-10 flex-1 max-w-4xl w-full mx-auto px-4 py-6">
          {children}
        </main>
        <Footer />
      </body>
    </html>
  );
}
