# Viral X • Ghana Creator & Monetization Platform (Web App)

> **Lead Architect & Copyright**: &copy; 2026 by Gokah Israel Ewoenam. All rights reserved.

Viral X is a full-stack, Ghana-focused creator and social media platform built with Next.js 14 (App Router), TypeScript, Tailwind CSS (Editorial Aesthetic), Supabase PostgreSQL, and Ghana Mobile Money disbursement logic.

---

## 🚀 Features

- **Editorial Aesthetic Design**: Deep `#050507` obsidian theme with atmospheric ambient glows, frosted glassmorphic cards, live viral badges, and mobile-friendly navigation.
- **Viral Score Algorithm**: Algorithmic calculation (0–100) factoring in watch depth, completion rate, 5-star community ratings, and Ghana audience authenticity.
- **Ghana Mobile Money (MoMo) Engine**: Real payout validation for MTN MoMo, Telecel Cash, and AT Money with automated 1% fee calculation and transaction history ledgers.
- **Creator Studio**: Live metrics, viral multiplier gauges, audience retention breakdown, and reward simulator.
- **Security & Moderation**: Two-Factor Authentication (2FA) for withdrawals, automated report moderation queue, and copyright notices on all pages.

---

## 🛠️ Tech Stack

- **Framework**: Next.js 14 (App Router)
- **Language**: TypeScript
- **Styling**: Tailwind CSS
- **Icons**: Lucide-React
- **Database**: Supabase (PostgreSQL with Row Level Security)
- **Deployment**: Vercel-ready

---

## 📦 How to Deploy to GitHub & Vercel

### 1. Initialize & Push to GitHub

Navigate into the `web` folder (or use the root if deploying the web app directly):

```bash
cd web
git init
git add .
git commit -m "Initial commit: Viral X Full-Stack Web Platform"
git branch -M main
git remote add origin https://github.com/your-username/viral-x-web.git
git push -u origin main
```

### 2. Deploy to Vercel (1-Click)

1. Go to [Vercel.com](https://vercel.com) and click **"Add New Project"**.
2. Import your `viral-x-web` GitHub repository.
3. Configure Environment Variables (found in `.env.example`):
   - `NEXT_PUBLIC_SUPABASE_URL`
   - `NEXT_PUBLIC_SUPABASE_ANON_KEY`
   - `NEXT_PUBLIC_APP_NAME`
4. Click **Deploy**. Vercel will build and assign you a global production URL (e.g., `https://viral-x-web.vercel.app`).

### 3. Supabase Database Setup

1. Create a free project on [Supabase](https://supabase.com).
2. Open the **SQL Editor** in your Supabase dashboard.
3. Copy and paste the contents of `web/supabase/schema.sql` and run it.
4. Add your Supabase project URL and Anon key to Vercel's Environment Variables.

---

## ⚖️ Legal & Copyright

Designed and developed by **Gokah Israel Ewoenam**  
Copyright &copy; 2026 by Gokah Israel Ewoenam. All rights reserved.
