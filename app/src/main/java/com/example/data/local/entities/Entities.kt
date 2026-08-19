package com.example.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey val id: String,
    val username: String,
    val displayName: String,
    val email: String,
    val phone: String = "",
    val avatarUrl: String = "",
    val bio: String = "",
    val isCreator: Boolean = true,
    val isVerified: Boolean = false,
    val isAdmin: Boolean = false,
    val totalFollowers: Int = 0,
    val totalFollowing: Int = 0,
    val totalLikes: Int = 0,
    val totalViews: Long = 0L,
    val viralScore: Float = 78.5f,
    val is2FaEnabled: Boolean = false,
    val totpSecret: String = "VIRALX-8921-GH",
    val backupRecoveryCodes: String = "REC-9182-GH,REC-8821-GH,REC-7734-GH",
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey val id: String,
    val creatorId: String,
    val creatorUsername: String,
    val creatorDisplayName: String,
    val creatorAvatarUrl: String = "",
    val isCreatorVerified: Boolean = true,
    val type: String, // "PHOTO" or "VIDEO"
    val mediaUrl: String,
    val thumbnailUrl: String = "",
    val caption: String,
    val hashtags: String = "#Ghana #ViralX #Accra",
    val commentsEnabled: Boolean = true,
    val visibility: String = "PUBLIC", // "PUBLIC", "FOLLOWERS"
    val likesCount: Int = 0,
    val ratingsCount: Int = 0,
    val averageRating: Float = 0f,
    val viewsCount: Long = 0L,
    val uniqueViewersCount: Long = 0L,
    val watchTimeSeconds: Long = 0L,
    val durationSeconds: Int = 60,
    val sharesCount: Int = 0,
    val savesCount: Int = 0,
    val viralScore: Float = 0f,
    val estimatedEarningsGhc: Double = 0.0,
    val isFlagged: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "likes", primaryKeys = ["postId", "userId"])
data class LikeEntity(
    val postId: String,
    val userId: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "ratings", primaryKeys = ["postId", "userId"])
data class RatingEntity(
    val postId: String,
    val userId: String,
    val stars: Int, // 1 to 5
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "view_records", primaryKeys = ["postId", "viewerId"])
data class ViewRecordEntity(
    val postId: String,
    val viewerId: String,
    val watchTimeSeconds: Long = 0L,
    val isAuthentic: Boolean = true,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: String,
    val postId: String,
    val parentCommentId: String? = null,
    val userId: String,
    val username: String,
    val displayName: String,
    val userAvatarUrl: String = "",
    val content: String,
    val likesCount: Int = 0,
    val createdAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "follows", primaryKeys = ["followerId", "followingId"])
data class FollowEntity(
    val followerId: String,
    val followingId: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "saved_posts", primaryKeys = ["userId", "postId"])
data class SavedPostEntity(
    val userId: String,
    val postId: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "wallets")
data class WalletEntity(
    @PrimaryKey val userId: String,
    val availableBalanceGhc: Double = 0.0,
    val pendingBalanceGhc: Double = 0.0,
    val lifetimeEarningsGhc: Double = 0.0,
    val momoNetwork: String = "MTN", // "MTN", "Telecel", "AT"
    val momoNumber: String = "0240000000",
    val momoAccountName: String = "Ghana Creator",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val type: String, // "REWARD_EARNED", "WITHDRAWAL_MOMO", "BONUS"
    val amountGhc: Double,
    val status: String, // "COMPLETED", "PENDING", "PROCESSING", "FAILED"
    val reference: String,
    val description: String,
    val momoNetwork: String? = null,
    val momoNumber: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "withdrawals")
data class WithdrawalEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val amountGhc: Double,
    val feeGhc: Double,
    val netAmountGhc: Double,
    val momoNetwork: String,
    val momoNumber: String,
    val momoAccountName: String,
    val status: String, // "PENDING", "PROCESSING", "APPROVED", "COMPLETED", "FAILED"
    val providerTransactionId: String,
    val failureReason: String? = null,
    val idempotencyKey: String,
    val createdAt: Long = System.currentTimeMillis(),
    val completedAt: Long? = null
)

@Entity(tableName = "notifications")
data class NotificationEntity(
    @PrimaryKey val id: String,
    val userId: String,
    val actorId: String = "",
    val actorUsername: String = "",
    val actorAvatarUrl: String = "",
    val type: String, // "LIKE", "RATING", "COMMENT", "FOLLOW", "EARNING", "WITHDRAWAL", "SYSTEM"
    val title: String,
    val message: String,
    val referenceId: String = "",
    val isRead: Boolean = false,
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "reports")
data class ReportEntity(
    @PrimaryKey val id: String,
    val reporterId: String,
    val targetType: String, // "POST", "USER", "COMMENT"
    val targetId: String,
    val targetTitle: String,
    val reason: String,
    val status: String = "PENDING", // "PENDING", "RESOLVED", "DISMISSED"
    val notes: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

@Entity(tableName = "system_settings")
data class SystemSettingsEntity(
    @PrimaryKey val key: String,
    val value: String,
    val description: String = "",
    val updatedAt: Long = System.currentTimeMillis()
)

@Entity(tableName = "admin_audit_logs")
data class AdminAuditLogEntity(
    @PrimaryKey val id: String,
    val adminUsername: String,
    val action: String,
    val target: String,
    val details: String,
    val timestamp: Long = System.currentTimeMillis()
)
