import type { Config } from "tailwindcss";

const config: Config = {
  content: [
    "./pages/**/*.{js,ts,jsx,tsx,mdx}",
    "./components/**/*.{js,ts,jsx,tsx,mdx}",
    "./app/**/*.{js,ts,jsx,tsx,mdx}",
  ],
  theme: {
    extend: {
      colors: {
        background: "#050507",
        surface: "#0C0A14",
        card: "#12101F",
        editorial: {
          blue: "#3B82F6",
          royal: "#2563EB",
          purple: "#7C3AED",
          violet: "#9333EA",
          green: "#22C55E",
          gold: "#FBBF24",
          yellow: "#F59E0B",
          pink: "#F43F5E",
          cyan: "#38BDF8",
          slate: "#94A3B8",
          muted: "#64748B",
        },
      },
      backgroundImage: {
        "editorial-gradient": "linear-gradient(135deg, #2563EB 0%, #7C3AED 50%, #A855F7 100%)",
        "glass-gradient": "linear-gradient(180deg, rgba(255, 255, 255, 0.08) 0%, rgba(255, 255, 255, 0.02) 100%)",
        "momo-gradient": "linear-gradient(90deg, #F59E0B 0%, #FBBF24 100%)",
      },
    },
  },
  plugins: [],
};
export default config;
