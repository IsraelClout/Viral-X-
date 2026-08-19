package com.example.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.data.local.entities.AdminAuditLogEntity
import com.example.data.local.entities.CommentEntity
import com.example.data.local.entities.FollowEntity
import com.example.data.local.entities.LikeEntity
import com.example.data.local.entities.NotificationEntity
import com.example.data.local.entities.PostEntity
import com.example.data.local.entities.RatingEntity
import com.example.data.local.entities.ReportEntity
import com.example.data.local.entities.SavedPostEntity
import com.example.data.local.entities.SystemSettingsEntity
import com.example.data.local.entities.TransactionEntity
import com.example.data.local.entities.UserEntity
import com.example.data.local.entities.ViewRecordEntity
import com.example.data.local.entities.WalletEntity
import com.example.data.local.entities.WithdrawalEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ViralXDao {

    // USERS
    @Query("SELECT * FROM users WHERE id = :userId")
    fun getUserByIdFlow(userId: String): Flow<UserEntity?>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getUserById(userId: String): UserEntity?

    @Query("SELECT * FROM users WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): UserEntity?

    @Query("SELECT * FROM users WHERE phone = :phone LIMIT 1")
    suspend fun getUserByPhone(phone: String): UserEntity?

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1")
    suspend fun getUserByUsername(username: String): UserEntity?

    @Query("SELECT * FROM users ORDER BY totalFollowers DESC")
    fun getAllUsers(): Flow<List<UserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Update
    suspend fun updateUser(user: UserEntity)

    // POSTS
    @Query("SELECT * FROM posts WHERE isFlagged = 0 ORDER BY createdAt DESC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE id = :postId")
    fun getPostByIdFlow(postId: String): Flow<PostEntity?>

    @Query("SELECT * FROM posts WHERE id = :postId")
    suspend fun getPostById(postId: String): PostEntity?

    @Query("SELECT * FROM posts WHERE creatorId = :creatorId ORDER BY createdAt DESC")
    fun getPostsByCreator(creatorId: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE type = 'VIDEO' AND isFlagged = 0 ORDER BY viralScore DESC")
    fun getTrendingVideos(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE isFlagged = 0 ORDER BY viralScore DESC")
    fun getTrendingPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE (caption LIKE '%' || :query || '%' OR hashtags LIKE '%' || :query || '%' OR creatorUsername LIKE '%' || :query || '%') AND isFlagged = 0 ORDER BY createdAt DESC")
    fun searchPosts(query: String): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity)

    @Update
    suspend fun updatePost(post: PostEntity)

    @Query("DELETE FROM posts WHERE id = :postId")
    suspend fun deletePost(postId: String)

    // LIKES
    @Query("SELECT EXISTS(SELECT 1 FROM likes WHERE postId = :postId AND userId = :userId)")
    fun isPostLikedByUserFlow(postId: String, userId: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM likes WHERE postId = :postId AND userId = :userId)")
    suspend fun isPostLikedByUser(postId: String, userId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertLike(like: LikeEntity)

    @Query("DELETE FROM likes WHERE postId = :postId AND userId = :userId")
    suspend fun deleteLike(postId: String, userId: String)

    // RATINGS
    @Query("SELECT stars FROM ratings WHERE postId = :postId AND userId = :userId LIMIT 1")
    fun getUserRatingForPostFlow(postId: String, userId: String): Flow<Int?>

    @Query("SELECT stars FROM ratings WHERE postId = :postId AND userId = :userId LIMIT 1")
    suspend fun getUserRatingForPost(postId: String, userId: String): Int?

    @Query("SELECT AVG(stars) FROM ratings WHERE postId = :postId")
    suspend fun getAverageRatingForPost(postId: String): Float?

    @Query("SELECT COUNT(*) FROM ratings WHERE postId = :postId")
    suspend fun getRatingCountForPost(postId: String): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRating(rating: RatingEntity)

    // VIEWS
    @Query("SELECT EXISTS(SELECT 1 FROM view_records WHERE postId = :postId AND viewerId = :viewerId)")
    suspend fun hasUserViewedPost(postId: String, viewerId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertViewRecord(viewRecord: ViewRecordEntity)

    // COMMENTS
    @Query("SELECT * FROM comments WHERE postId = :postId ORDER BY createdAt ASC")
    fun getCommentsForPost(postId: String): Flow<List<CommentEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertComment(comment: CommentEntity)

    @Query("DELETE FROM comments WHERE id = :commentId")
    suspend fun deleteComment(commentId: String)

    // FOLLOWS
    @Query("SELECT EXISTS(SELECT 1 FROM follows WHERE followerId = :followerId AND followingId = :followingId)")
    fun isFollowingFlow(followerId: String, followingId: String): Flow<Boolean>

    @Query("SELECT EXISTS(SELECT 1 FROM follows WHERE followerId = :followerId AND followingId = :followingId)")
    suspend fun isFollowing(followerId: String, followingId: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFollow(follow: FollowEntity)

    @Query("DELETE FROM follows WHERE followerId = :followerId AND followingId = :followingId")
    suspend fun deleteFollow(followerId: String, followingId: String)

    // SAVES
    @Query("SELECT EXISTS(SELECT 1 FROM saved_posts WHERE userId = :userId AND postId = :postId)")
    fun isPostSavedByUserFlow(userId: String, postId: String): Flow<Boolean>

    @Query("SELECT posts.* FROM posts INNER JOIN saved_posts ON posts.id = saved_posts.postId WHERE saved_posts.userId = :userId ORDER BY saved_posts.timestamp DESC")
    fun getSavedPostsForUser(userId: String): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSavedPost(save: SavedPostEntity)

    @Query("DELETE FROM saved_posts WHERE userId = :userId AND postId = :postId")
    suspend fun deleteSavedPost(userId: String, postId: String)

    // WALLET
    @Query("SELECT * FROM wallets WHERE userId = :userId")
    fun getWalletByUserIdFlow(userId: String): Flow<WalletEntity?>

    @Query("SELECT * FROM wallets WHERE userId = :userId")
    suspend fun getWalletByUserId(userId: String): WalletEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallet(wallet: WalletEntity)

    @Update
    suspend fun updateWallet(wallet: WalletEntity)

    // TRANSACTIONS
    @Query("SELECT * FROM transactions WHERE userId = :userId ORDER BY timestamp DESC")
    fun getTransactionsForUser(userId: String): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    fun getAllTransactions(): Flow<List<TransactionEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTransaction(transaction: TransactionEntity)

    // WITHDRAWALS
    @Query("SELECT * FROM withdrawals WHERE userId = :userId ORDER BY createdAt DESC")
    fun getWithdrawalsForUser(userId: String): Flow<List<WithdrawalEntity>>

    @Query("SELECT * FROM withdrawals ORDER BY createdAt DESC")
    fun getAllWithdrawals(): Flow<List<WithdrawalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWithdrawal(withdrawal: WithdrawalEntity)

    @Update
    suspend fun updateWithdrawal(withdrawal: WithdrawalEntity)

    // NOTIFICATIONS
    @Query("SELECT * FROM notifications WHERE userId = :userId ORDER BY timestamp DESC")
    fun getNotificationsForUser(userId: String): Flow<List<NotificationEntity>>

    @Query("SELECT COUNT(*) FROM notifications WHERE userId = :userId AND isRead = 0")
    fun getUnreadNotificationsCount(userId: String): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotification(notification: NotificationEntity)

    @Query("UPDATE notifications SET isRead = 1 WHERE userId = :userId")
    suspend fun markAllNotificationsAsRead(userId: String)

    // REPORTS & MODERATION
    @Query("SELECT * FROM reports ORDER BY timestamp DESC")
    fun getAllReports(): Flow<List<ReportEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReport(report: ReportEntity)

    @Update
    suspend fun updateReport(report: ReportEntity)

    // SYSTEM SETTINGS
    @Query("SELECT * FROM system_settings")
    fun getAllSystemSettings(): Flow<List<SystemSettingsEntity>>

    @Query("SELECT value FROM system_settings WHERE `key` = :key LIMIT 1")
    suspend fun getSettingValue(key: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSetting(setting: SystemSettingsEntity)

    // ADMIN AUDIT LOGS
    @Query("SELECT * FROM admin_audit_logs ORDER BY timestamp DESC")
    fun getAllAuditLogs(): Flow<List<AdminAuditLogEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAuditLog(log: AdminAuditLogEntity)
}
