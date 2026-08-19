"use client";

import { useState } from "react";

export function Footer() {
  const [modalType, setModalType] = useState<"terms" | "privacy" | "support" | null>(null);

  return (
    <footer className="w-full bg-black/40 backdrop-blur-2xl border-t border-white/10 py-10 px-4 mt-16 text-center">
      <div className="max-w-4xl mx-auto flex flex-col items-center gap-4">
        {/* Navigation Links */}
        <div className="flex flex-wrap items-center justify-center gap-3 text-xs font-semibold text-editorial-blue">
          <button
            onClick={() => setModalType("terms")}
            className="hover:underline transition-all"
          >
            Terms of Service
          </button>
          <span className="text-slate-600">•</span>
          <button
            onClick={() => setModalType("privacy")}
            className="hover:underline transition-all"
          >
            Privacy Policy
          </button>
          <span className="text-slate-600">•</span>
          <button
            onClick={() => setModalType("support")}
            className="hover:underline transition-all"
          >
            Contact Support
          </button>
        </div>

        {/* Mandatory Copyright Notice */}
        <p className="text-xs text-slate-400 font-medium tracking-wide uppercase">
          &copy; 2026 by Gokah Israel Ewoenam • Ghana Creator Hub
        </p>

        <p className="text-[10px] text-slate-500 max-w-md">
          Viral X Ghana • Empowering African digital creators with algorithmic viral recognition and instant Ghana Mobile Money payouts.
        </p>
      </div>

      {/* Modal Dialog */}
      {modalType && (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black/70 backdrop-blur-md p-4">
          <div className="bg-[#12101F] border border-white/20 rounded-3xl max-w-lg w-full p-6 text-left shadow-2xl">
            <h3 className="text-lg font-bold text-white mb-3">
              {modalType === "terms" && "Terms of Service"}
              {modalType === "privacy" && "Privacy & Security Policy"}
              {modalType === "support" && "Ghana Creator Support & Help"}
            </h3>

            <div className="text-xs text-slate-300 space-y-3 max-h-80 overflow-y-auto pr-2">
              {modalType === "terms" && (
                <>
                  <p><strong>1. Monetization & Base Unit:</strong> GH₵1.00 serves as the base engagement unit on Viral X. Payouts are computed via our server-side Viral Score algorithm based on authentic Ghanaian watch time, completion rates, and genuine viewer retention.</p>
                  <p><strong>2. Ghana Mobile Money:</strong> Withdrawals to MTN MoMo, Telecel Cash, and AT Money are subject to 1% network processing fees. Minimum withdrawal is GH₵ 10.00.</p>
                  <p><strong>3. Content Policy:</strong> Fraudulent engagement, bot farming, and prohibited content will result in immediate creator account suspension.</p>
                </>
              )}

              {modalType === "privacy" && (
                <>
                  <p>We treat Ghanaian creator data with bank-grade encryption. Phone numbers registered for Mobile Money withdrawals and two-factor authentication tokens are never sold or shared with unauthorized third parties.</p>
                </>
              )}

              {modalType === "support" && (
                <>
                  <p>Need assistance with your Creator Studio, Viral Score calculation, or Ghana Mobile Money withdrawal?</p>
                  <div className="bg-white/5 p-3 rounded-xl border border-white/10 space-y-1 text-slate-200">
                    <p>📧 Email: <span className="text-editorial-blue">support@viralx.gh</span></p>
                    <p>🇬🇭 Ghana Hotline: <span className="text-white">+233 24 488 9900</span></p>
                    <p>📍 Location: Accra Innovation Center, Greater Accra, Ghana</p>
                    <p>👤 Lead Architect: <span className="text-editorial-gold">Gokah Israel Ewoenam</span></p>
                  </div>
                </>
              )}
            </div>

            <div className="mt-6 flex justify-end">
              <button
                onClick={() => setModalType(null)}
                className="px-4 py-2 bg-editorial-gradient text-white text-xs font-bold rounded-full hover:opacity-90"
              >
                Close
              </button>
            </div>
          </div>
        </div>
      )}
    </footer>
  );
}
