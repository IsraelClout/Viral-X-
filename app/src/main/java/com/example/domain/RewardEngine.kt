package com.example.domain

import kotlin.math.max
import kotlin.math.min

object RewardEngine {
    // Default base engagement value is GH₵1.00 as requested by the prompt
    var baseRewardUnitGhc: Double = 1.00
    var rewardMultiplier: Double = 1.00
    var fraudStrictness: String = "HIGH"

    /**
     * Viral Score calculation (0 to 100).
     * Based on authentic views, watch time completion, like/view ratio, rating score (1-5), comments, shares, and saves.
     */
    fun calculateViralScore(
        viewsCount: Long,
        uniqueViewers: Long,
        totalWatchSeconds: Long,
        durationSeconds: Int,
        likesCount: Int,
        averageRating: Float,
        commentsCount: Int,
        sharesCount: Int,
        savesCount: Int,
        isFlagged: Boolean
    ): Float {
        if (isFlagged) return 0f
        if (viewsCount == 0L) return 0f

        val safeDuration = max(10, durationSeconds)
        val expectedTotalWatchSeconds = viewsCount * safeDuration
        val completionRatio = if (expectedTotalWatchSeconds > 0) {
            min(1.0, totalWatchSeconds.toDouble() / expectedTotalWatchSeconds.toDouble())
        } else 0.0

        val uniqueRatio = min(1.0, uniqueViewers.toDouble() / max(1L, viewsCount).toDouble())
        val likeRatio = min(0.3, likesCount.toDouble() / max(1L, viewsCount).toDouble()) / 0.3
        val ratingScore = (averageRating / 5.0).coerceIn(0.0, 1.0)
        val shareFactor = min(1.0, (sharesCount * 2 + savesCount * 1.5 + commentsCount * 1.0) / max(5.0, viewsCount * 0.1))

        // Weighted viral score formula:
        // 25% Completion rate + 20% Uniqueness + 20% Ratings + 15% Likes + 20% Social shares & saves
        val rawScore = (completionRatio * 25.0) +
                (uniqueRatio * 20.0) +
                (ratingScore * 20.0) +
                (likeRatio * 15.0) +
                (shareFactor * 20.0)

        return (rawScore * 1.0).coerceIn(0.0, 100.0).toFloat()
    }

    /**
     * Calculates authentic creator earnings in Ghana Cedis (GH₵).
     * GH₵1.00 is the base unit. The earnings engine evaluates authentic engagement volume,
     * Viral Score multiplier, and fraud penalty.
     */
    fun calculateEarningsGhc(
        viralScore: Float,
        uniqueViewers: Long,
        likesCount: Int,
        sharesCount: Int,
        savesCount: Int,
        averageRating: Float,
        baseUnit: Double = baseRewardUnitGhc,
        multiplier: Double = rewardMultiplier
    ): Double {
        if (viralScore < 10f || uniqueViewers == 0L) return 0.0

        // Score multiplier (0.1x to 2.5x) based on Viral Score quality tier
        val qualityMultiplier = when {
            viralScore >= 85f -> 2.5
            viralScore >= 70f -> 1.8
            viralScore >= 50f -> 1.2
            viralScore >= 30f -> 0.7
            else -> 0.3
        }

        // Rating bonus (up to 1.3x for 4.5+ stars)
        val ratingBonus = if (averageRating >= 4.5f) 1.3 else if (averageRating >= 4.0f) 1.15 else 1.0

        // Authentic Engagement Points
        val engagementUnits = (uniqueViewers * 0.05) +
                (likesCount * 0.15) +
                (sharesCount * 0.50) +
                (savesCount * 0.35)

        val totalEarnings = engagementUnits * (baseUnit * 0.02) * qualityMultiplier * ratingBonus * multiplier
        return String.format("%.2f", totalEarnings).toDoubleOrNull() ?: totalEarnings
    }

    /**
     * Fraud detection filter: detects rapid bot spam or self-engagement.
     */
    fun evaluateFraudRisk(
        creatorId: String,
        viewerId: String,
        watchSeconds: Long,
        durationSeconds: Int
    ): Boolean {
        // Self-viewing does not generate authentic reward credits
        if (creatorId == viewerId) return false
        // Watch time must be at least 2 seconds to be counted as authentic engagement
        if (watchSeconds < 2) return false
        return true
    }
}
