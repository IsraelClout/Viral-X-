/**
 * VIRAL X ALGORITHMIC SCORING ENGINE
 * Lead Architect: Gokah Israel Ewoenam
 * Copyright © 2026 by Gokah Israel Ewoenam.
 */

export interface PostEngagementMetrics {
  viewsCount: number;
  completionRate: number; // 0.0 - 1.0
  watchTimeSeconds: number;
  likesCount: number;
  commentsCount: number;
  sharesCount: number;
  ratingsCount: number;
  averageRating: number; // 1.0 - 5.0
  isGhanaianAudienceVerified: boolean;
}

export function calculateViralScore(metrics: PostEngagementMetrics): number {
  // 1. Completion & Watch Depth Weight: 35%
  const completionScore = Math.min(metrics.completionRate * 100, 100) * 0.35;

  // 2. Rating Quality Weight: 25% (5-star scale normalized)
  const ratingNormalized = (metrics.averageRating / 5.0) * 100;
  const ratingScore = ratingNormalized * 0.25;

  // 3. Social Interaction Velocity: 25% (Shares weighted highest, then comments, then likes)
  const interactions = (metrics.sharesCount * 3) + (metrics.commentsCount * 1.5) + (metrics.likesCount * 0.5);
  const interactionScore = Math.min((interactions / Math.max(metrics.viewsCount, 1)) * 300, 100) * 0.25;

  // 4. Ghanaian Community Authenticity Multiplier: 15%
  const authenticityScore = (metrics.isGhanaianAudienceVerified ? 100 : 70) * 0.15;

  const rawScore = completionScore + ratingScore + interactionScore + authenticityScore;
  return Math.min(Math.max(Number(rawScore.toFixed(1)), 10.0), 99.8);
}

export function estimateEarningsGhc(viralScore: number, viewsCount: number, baseRewardGhc: number = 1.0): number {
  if (viralScore < 50.0) return 0.0;
  const multiplier = Math.pow(viralScore / 50.0, 1.4);
  const earnings = (viewsCount / 1000.0) * baseRewardGhc * multiplier;
  return Number(earnings.toFixed(2));
}
