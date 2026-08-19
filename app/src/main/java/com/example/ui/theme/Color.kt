package com.example.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Editorial Aesthetic: Deep Obsidian & Atmospheric Dark Canvas (#050507)
val ObsidianBg = Color(0xFF050507)
val ObsidianSurface = Color(0xFF0C0A14)
val ObsidianCard = Color(0xFF12101F)
val ObsidianCardGlass = Color(0x14FFFFFF) // Frosted white/5-10% glassmorphic fill
val GlassSurface = Color(0x14FFFFFF)
val GlassBorder = Color(0x26FFFFFF)       // Crisp white/15% border
val GlassBorderLight = Color(0x1AFFFFFF)  // Subtle white/10% border

// Editorial Accents (Editorial Blue, Electric Purple, Live Green, MoMo Gold)
val EditorialBlue = Color(0xFF3B82F6)
val EditorialRoyalBlue = Color(0xFF2563EB)
val EditorialPurple = Color(0xFF7C3AED)
val EditorialViolet = Color(0xFF9333EA)
val ElectricPurple = Color(0xFFA855F7)
val ElectricViolet = Color(0xFF8B5CF6)
val DeepPurple = Color(0xFF6D28D9)
val NeonCyan = Color(0xFF38BDF8)          // Editorial Sky Cyan
val NeonMint = Color(0xFF22C55E)          // Editorial Live Emerald Green
val NeonPink = Color(0xFFF43F5E)          // Editorial Rose Red
val VividRose = Color(0xFFFB7185)
val MomoGold = Color(0xFFFBBF24)          // Ghana MoMo Gold
val MomoYellow = Color(0xFFF59E0B)

// Typography & Neutrals
val TextPrimary = Color(0xFFF8FAFC)       // High-contrast slate-50
val TextSecondary = Color(0xFF94A3B8)     // Slate-400
val TextMuted = Color(0xFF64748B)         // Slate-500
val TextDark = Color(0xFF050507)

// Editorial Gradients
val BluePurpleGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF2563EB), Color(0xFF7C3AED), Color(0xFFA855F7))
)

val PurpleCyanGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF2563EB), Color(0xFF8B5CF6), Color(0xFF38BDF8))
)

val PinkPurpleGradient = Brush.linearGradient(
    colors = listOf(Color(0xFF2563EB), Color(0xFF7C3AED), Color(0xFFA855F7))
)

val GlassGradient = Brush.verticalGradient(
    colors = listOf(Color(0x1AFFFFFF), Color(0x0AFFFFFF))
)

val MomoGradient = Brush.horizontalGradient(
    colors = listOf(MomoYellow, MomoGold)
)

val EditorialCardGradient = Brush.linearGradient(
    colors = listOf(Color(0x1F2563EB), Color(0x1F7C3AED))
)
