package com.example.data.repository

import com.example.data.local.dao.ViralXDao
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
import com.example.domain.MoMoNetwork
import com.example.domain.PaymentService
import com.example.domain.PayoutResult
import com.example.domain.RewardEngine
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.util.UUID

class ViralXRepository(private val dao: ViralXDao) {

    val allPosts: Flow<List<PostEntity>> = dao.getAllPosts()
    val trendingPosts: Flow<List<PostEntity>> = dao.getTrendingPosts()
    val trendingVideos: Flow<List<PostEntity>> = dao.getTrendingVideos()
    val allUsers: Flow<List<UserEntity>> = dao.getAllUsers()
    val allReports: Flow<List<ReportEntity>> = dao.getAllReports()
    val allAuditLogs: Flow<List<AdminAuditLogEntity>> = dao.getAllAuditLogs()
    val allSystemSettings: Flow<List<SystemSettingsEntity>> = dao.getAllSystemSettings()

    suspend fun seedInitialDataIfEmpty() {
        val existingUser = dao.getUserById("usr_israel")
        if (existingUser != null) return

        // 1. Current Active User / Creator (Israel Ewoenam Gokah)
        val currentUser = UserEntity(
            id = "usr_israel",
            username = "israel_creator",
            displayName = "Israel Ewoenam Gokah",
            email = "gokahisraelewoenam@gmail.com",
            phone = "0244889900",
            avatarUrl = "avatar_ghana_creator",
            bio = "🇬🇭 Digital Creator & Tech Innovator in Accra. Creating next-gen experiences on Viral X.",
            isCreator = true,
            isVerified = true,
            isAdmin = true,
            totalFollowers = 18450,
            totalFollowing = 214,
            totalLikes = 94200,
            totalViews = 384000L,
            viralScore = 92.4f,
            is2FaEnabled = true
        )
        dao.insertUser(currentUser)

        // Other Featured Ghanaian Creators
        val creator2 = UserEntity(
            id = "usr_kofi",
            username = "kofi_accra",
            displayName = "Kofi Mensah",
            email = "kofi@viralx.gh",
            phone = "0541234567",
            avatarUrl = "post_accra_fashion",
            bio = "Accra Streetwear & High Fashion Enthusiast 🕶️ Osu / Labone vibes.",
            isCreator = true,
            isVerified = true,
            totalFollowers = 42100,
            totalFollowing = 180,
            totalLikes = 182000,
            totalViews = 780000L,
            viralScore = 96.8f
        )
        val creator3 = UserEntity(
            id = "usr_ama",
            username = "ama_vibes",
            displayName = "Ama Serwaa",
            email = "ama@viralx.gh",
            phone = "0209876543",
            avatarUrl = "post_accra_music",
            bio = "AfroBeats choreographer & dynamic lifestyle creator in Ghana 🎶✨",
            isCreator = true,
            isVerified = true,
            totalFollowers = 68900,
            totalFollowing = 310,
            totalLikes = 340000,
            totalViews = 1250000L,
            viralScore = 98.2f
        )
        val creator4 = UserEntity(
            id = "usr_kwame",
            username = "kwame_tech",
            displayName = "Kwame Boateng",
            email = "kwame@viralx.gh",
            phone = "0271122334",
            avatarUrl = "avatar_ghana_creator",
            bio = "Tech reviewer, gadget unboxer and startup explorer in West Africa 💻⚡",
            isCreator = true,
            isVerified = true,
            totalFollowers = 29500,
            totalFollowing = 145,
            totalLikes = 87400,
            totalViews = 410000L,
            viralScore = 88.0f
        )
        dao.insertUser(creator2)
        dao.insertUser(creator3)
        dao.insertUser(creator4)

        // Seed Posts
        val post1 = PostEntity(
            id = "post_1",
            creatorId = "usr_kofi",
            creatorUsername = "kofi_accra",
            creatorDisplayName = "Kofi Mensah",
            creatorAvatarUrl = "post_accra_fashion",
            isCreatorVerified = true,
            type = "VIDEO",
            mediaUrl = "post_accra_fashion",
            thumbnailUrl = "post_accra_fashion",
            caption = "Neon aesthetics taking over Accra Street Fashion Week! 🕶️🇬🇭 Real style is about wearing bold colors with absolute confidence.",
            hashtags = "#AccraFashion #ViralX #StreetwearGH #ChaleWote",
            likesCount = 1642,
            ratingsCount = 284,
            averageRating = 4.85f,
            viewsCount = 18450L,
            uniqueViewersCount = 14200L,
            watchTimeSeconds = 64200L,
            durationSeconds = 45,
            sharesCount = 312,
            savesCount = 490,
            viralScore = 94.6f,
            estimatedEarningsGhc = 285.50
        )

        val post2 = PostEntity(
            id = "post_2",
            creatorId = "usr_ama",
            creatorUsername = "ama_vibes",
            creatorDisplayName = "Ama Serwaa",
            creatorAvatarUrl = "post_accra_music",
            isCreatorVerified = true,
            type = "VIDEO",
            mediaUrl = "post_accra_music",
            thumbnailUrl = "post_accra_music",
            caption = "New dance routine to the hottest tune in Ghana right now! 🔥 Try this step and tag me! #ViralXChallenge",
            hashtags = "#AfroDance #GhanaMusic #ViralX #DettyDecember",
            likesCount = 3280,
            ratingsCount = 512,
            averageRating = 4.92f,
            viewsCount = 42600L,
            uniqueViewersCount = 38100L,
            watchTimeSeconds = 158000L,
            durationSeconds = 30,
            sharesCount = 890,
            savesCount = 1120,
            viralScore = 98.4f,
            estimatedEarningsGhc = 540.00
        )

        val post3 = PostEntity(
            id = "post_3",
            creatorId = "usr_israel",
            creatorUsername = "israel_creator",
            creatorDisplayName = "Israel Ewoenam Gokah",
            creatorAvatarUrl = "avatar_ghana_creator",
            isCreatorVerified = true,
            type = "PHOTO",
            mediaUrl = "avatar_ghana_creator",
            thumbnailUrl = "avatar_ghana_creator",
            caption = "Building the future of Ghana's digital economy on Viral X! Authentic engagement, direct creator earnings, instant Mobile Money payouts 🇬🇭⚡",
            hashtags = "#GhanaTech #CreatorEconomy #ViralX #AccraInnovation",
            likesCount = 892,
            ratingsCount = 145,
            averageRating = 4.90f,
            viewsCount = 9800L,
            uniqueViewersCount = 8400L,
            watchTimeSeconds = 24000L,
            durationSeconds = 15,
            sharesCount = 145,
            savesCount = 320,
            viralScore = 91.2f,
            estimatedEarningsGhc = 168.00
        )
        dao.insertPost(post1)
        dao.insertPost(post2)
        dao.insertPost(post3)

        // Seed Initial Comments matching screenshot style
        val comment1 = CommentEntity(
            id = "c_1",
            postId = "post_1",
            userId = "usr_ama",
            username = "ama_vibes",
            displayName = "Ama Serwaa",
            userAvatarUrl = "post_accra_music",
            content = "Real style is all about wearing pink and neon with confidence! 🔥",
            likesCount = 164
        )
        val comment2 = CommentEntity(
            id = "c_2",
            postId = "post_1",
            parentCommentId = "c_1",
            userId = "usr_kofi",
            username = "kofi_accra",
            displayName = "Kofi Mensah",
            userAvatarUrl = "post_accra_fashion",
            content = "Thanks Queen! Class and confidence always 💯",
            likesCount = 42
        )
        val comment3 = CommentEntity(
            id = "c_3",
            postId = "post_1",
            userId = "usr_kwame",
            username = "kwame_tech",
            displayName = "Kwame Boateng",
            userAvatarUrl = "avatar_ghana_creator",
            content = "This is what modern African creative excellence looks like. Stunning visual craft! ✨",
            likesCount = 88
        )
        dao.insertComment(comment1)
        dao.insertComment(comment2)
        dao.insertComment(comment3)

        // Seed Wallet
        val initialWallet = WalletEntity(
            userId = "usr_israel",
            availableBalanceGhc = 485.50,
            pendingBalanceGhc = 120.00,
            lifetimeEarningsGhc = 1840.00,
            momoNetwork = "MTN",
            momoNumber = "0244889900",
            momoAccountName = "Israel Ewoenam Gokah"
        )
        dao.insertWallet(initialWallet)

        // Seed Transactions
        val tx1 = TransactionEntity(
            id = "tx_1",
            userId = "usr_israel",
            type = "REWARD_EARNED",
            amountGhc = 168.00,
            status = "COMPLETED",
            reference = "VX-REW-88912",
            description = "Creator authentic engagement reward for post: Building the future"
        )
        val tx2 = TransactionEntity(
            id = "tx_2",
            userId = "usr_israel",
            type = "WITHDRAWAL_MOMO",
            amountGhc = 250.00,
            status = "COMPLETED",
            reference = "MOMO-GH-991823",
            description = "Payout to MTN Mobile Money 0244889900",
            momoNetwork = "MTN",
            momoNumber = "0244889900"
        )
        dao.insertTransaction(tx1)
        dao.insertTransaction(tx2)

        // Seed Notifications
        val notif1 = NotificationEntity(
            id = "n_1",
            userId = "usr_israel",
            actorId = "usr_ama",
            actorUsername = "ama_vibes",
            actorAvatarUrl = "post_accra_music",
            type = "RATING",
            title = "5-Star Rating Received ⭐",
            message = "Ama Serwaa rated your post 5 stars on Viral X!"
        )
        val notif2 = NotificationEntity(
            id = "n_2",
            userId = "usr_israel",
            actorId = "system",
            actorUsername = "Viral X Rewards",
            actorAvatarUrl = "ic_viralx_logo",
            type = "EARNING",
            title = "Reward Credited: GH₵168.00 💰",
            message = "Your authentic engagement score earned you GH₵168.00 available in your wallet."
        )
        val notif3 = NotificationEntity(
            id = "n_3",
            userId = "usr_israel",
            actorId = "usr_kofi",
            actorUsername = "kofi_accra",
            actorAvatarUrl = "post_accra_fashion",
            type = "FOLLOW",
            title = "New Creator Follower 👥",
            message = "Kofi Mensah started following you."
        )
        dao.insertNotification(notif1)
        dao.insertNotification(notif2)
        dao.insertNotification(notif3)

        // Seed System Settings
        dao.insertSetting(SystemSettingsEntity("base_engagement_reward_ghc", "1.00", "Base engagement value per reward unit in Ghana Cedis"))
        dao.insertSetting(SystemSettingsEntity("min_withdrawal_ghc", "10.00", "Minimum Ghana Mobile Money withdrawal amount in GH₵"))
        dao.insertSetting(SystemSettingsEntity("reward_multiplier", "1.0", "Global reward multiplier applied to authentic creator payouts"))
        dao.insertSetting(SystemSettingsEntity("fraud_detection_strictness", "HIGH", "Strictness level for detecting unnatural bot/self engagement"))

        // Seed Sample Audit Log
        dao.insertAuditLog(
            AdminAuditLogEntity(
                id = "log_1",
                adminUsername = "israel_creator",
                action = "SYSTEM_INITIALIZED",
                target = "VIRAL_X_GH",
                details = "Viral X creator platform initialized with GH₵1.00 base reward unit and MoMo sandbox gateway."
            )
        )
    }

    // AUTH & USER
    fun getUserByIdFlow(userId: String): Flow<UserEntity?> = dao.getUserByIdFlow(userId)
    suspend fun getUserById(userId: String): UserEntity? = dao.getUserById(userId)
    suspend fun updateUser(user: UserEntity) = dao.updateUser(user)

    // POST INTERACTION
    fun isPostLikedFlow(postId: String, userId: String): Flow<Boolean> = dao.isPostLikedByUserFlow(postId, userId)
    fun getUserRatingFlow(postId: String, userId: String): Flow<Int?> = dao.getUserRatingForPostFlow(postId, userId)
    fun isFollowingFlow(followerId: String, followingId: String): Flow<Boolean> = dao.isFollowingFlow(followerId, followingId)
    fun isPostSavedFlow(userId: String, postId: String): Flow<Boolean> = dao.isPostSavedByUserFlow(userId, postId)
    fun getSavedPostsFlow(userId: String): Flow<List<PostEntity>> = dao.getSavedPostsForUser(userId)
    fun getCommentsFlow(postId: String): Flow<List<CommentEntity>> = dao.getCommentsForPost(postId)

    suspend fun toggleLike(postId: String, userId: String) {
        val post = dao.getPostById(postId) ?: return
        val isLiked = dao.isPostLikedByUser(postId, userId)
        if (isLiked) {
            dao.deleteLike(postId, userId)
            val updatedLikes = (post.likesCount - 1).coerceAtLeast(0)
            val updatedPost = post.copy(likesCount = updatedLikes)
            dao.updatePost(recalculatePostMetrics(updatedPost))
        } else {
            dao.insertLike(LikeEntity(postId, userId))
            val updatedLikes = post.likesCount + 1
            val updatedPost = post.copy(likesCount = updatedLikes)
            dao.updatePost(recalculatePostMetrics(updatedPost))

            // Notification for creator
            if (post.creatorId != userId) {
                dao.insertNotification(
                    NotificationEntity(
                        id = UUID.randomUUID().toString(),
                        userId = post.creatorId,
                        actorId = userId,
                        actorUsername = "israel_creator",
                        type = "LIKE",
                        title = "New Post Like ❤️",
                        message = "Your post was liked on Viral X."
                    )
                )
            }
        }
    }

    suspend fun ratePost(postId: String, userId: String, stars: Int) {
        val post = dao.getPostById(postId) ?: return
        val validStars = stars.coerceIn(1, 5)
        dao.insertRating(RatingEntity(postId, userId, validStars))

        val avg = dao.getAverageRatingForPost(postId) ?: validStars.toFloat()
        val count = dao.getRatingCountForPost(postId)

        val updatedPost = post.copy(
            ratingsCount = count,
            averageRating = avg
        )
        dao.updatePost(recalculatePostMetrics(updatedPost))

        if (post.creatorId != userId) {
            dao.insertNotification(
                NotificationEntity(
                    id = UUID.randomUUID().toString(),
                    userId = post.creatorId,
                    actorId = userId,
                    actorUsername = "israel_creator",
                    type = "RATING",
                    title = "$stars-Star Rating ⭐",
                    message = "Your post received a $stars-star rating on Viral X."
                )
            )
        }
    }

    suspend fun recordWatchTimeAndAuthenticView(postId: String, viewerId: String, additionalWatchSeconds: Long) {
        val post = dao.getPostById(postId) ?: return
        val isAuthentic = RewardEngine.evaluateFraudRisk(post.creatorId, viewerId, additionalWatchSeconds, post.durationSeconds)

        val hasViewed = dao.hasUserViewedPost(postId, viewerId)
        val newUnique = if (!hasViewed && isAuthentic) post.uniqueViewersCount + 1 else post.uniqueViewersCount
        val newViews = post.viewsCount + 1
        val newWatchTime = post.watchTimeSeconds + additionalWatchSeconds

        dao.insertViewRecord(ViewRecordEntity(postId, viewerId, newWatchTime, isAuthentic))

        val updatedPost = post.copy(
            viewsCount = newViews,
            uniqueViewersCount = newUnique,
            watchTimeSeconds = newWatchTime
        )
        dao.updatePost(recalculatePostMetrics(updatedPost))
    }

    suspend fun addComment(postId: String, parentCommentId: String?, userId: String, content: String) {
        val user = dao.getUserById(userId) ?: return
        val post = dao.getPostById(postId) ?: return

        val comment = CommentEntity(
            id = UUID.randomUUID().toString(),
            postId = postId,
            parentCommentId = parentCommentId,
            userId = userId,
            username = user.username,
            displayName = user.displayName,
            userAvatarUrl = user.avatarUrl,
            content = content
        )
        dao.insertComment(comment)

        if (post.creatorId != userId) {
            dao.insertNotification(
                NotificationEntity(
                    id = UUID.randomUUID().toString(),
                    userId = post.creatorId,
                    actorId = userId,
                    actorUsername = user.username,
                    actorAvatarUrl = user.avatarUrl,
                    type = "COMMENT",
                    title = "New Comment 💬",
                    message = "${user.displayName}: $content"
                )
            )
        }
    }

    suspend fun toggleFollow(followerId: String, followingId: String) {
        val isFollow = dao.isFollowing(followerId, followingId)
        val followingUser = dao.getUserById(followingId)
        val followerUser = dao.getUserById(followerId)

        if (isFollow) {
            dao.deleteFollow(followerId, followingId)
            if (followingUser != null) {
                dao.updateUser(followingUser.copy(totalFollowers = (followingUser.totalFollowers - 1).coerceAtLeast(0)))
            }
            if (followerUser != null) {
                dao.updateUser(followerUser.copy(totalFollowing = (followerUser.totalFollowing - 1).coerceAtLeast(0)))
            }
        } else {
            dao.insertFollow(FollowEntity(followerId, followingId))
            if (followingUser != null) {
                dao.updateUser(followingUser.copy(totalFollowers = followingUser.totalFollowers + 1))
                dao.insertNotification(
                    NotificationEntity(
                        id = UUID.randomUUID().toString(),
                        userId = followingId,
                        actorId = followerId,
                        actorUsername = followerUser?.username ?: "User",
                        actorAvatarUrl = followerUser?.avatarUrl ?: "",
                        type = "FOLLOW",
                        title = "New Follower 👥",
                        message = "${followerUser?.displayName ?: "A creator"} started following you."
                    )
                )
            }
            if (followerUser != null) {
                dao.updateUser(followerUser.copy(totalFollowing = followerUser.totalFollowing + 1))
            }
        }
    }

    suspend fun toggleSave(userId: String, postId: String) {
        val post = dao.getPostById(postId) ?: return
        val saved = dao.isPostSavedByUserFlow(userId, postId).firstOrNull() ?: false
        if (saved) {
            dao.deleteSavedPost(userId, postId)
            val updatedPost = post.copy(savesCount = (post.savesCount - 1).coerceAtLeast(0))
            dao.updatePost(recalculatePostMetrics(updatedPost))
        } else {
            dao.insertSavedPost(SavedPostEntity(userId, postId))
            val updatedPost = post.copy(savesCount = post.savesCount + 1)
            dao.updatePost(recalculatePostMetrics(updatedPost))
        }
    }

    suspend fun sharePost(postId: String) {
        val post = dao.getPostById(postId) ?: return
        val updatedPost = post.copy(sharesCount = post.sharesCount + 1)
        dao.updatePost(recalculatePostMetrics(updatedPost))
    }

    suspend fun createPost(
        creatorId: String,
        type: String,
        mediaAsset: String,
        caption: String,
        hashtags: String,
        commentsEnabled: Boolean,
        visibility: String
    ): PostEntity {
        val user = dao.getUserById(creatorId) ?: throw IllegalStateException("User not found")
        val newPost = PostEntity(
            id = "post_" + UUID.randomUUID().toString().take(8),
            creatorId = creatorId,
            creatorUsername = user.username,
            creatorDisplayName = user.displayName,
            creatorAvatarUrl = user.avatarUrl,
            isCreatorVerified = user.isVerified,
            type = type,
            mediaUrl = mediaAsset,
            thumbnailUrl = mediaAsset,
            caption = caption,
            hashtags = hashtags,
            commentsEnabled = commentsEnabled,
            visibility = visibility,
            likesCount = 0,
            ratingsCount = 0,
            averageRating = 0f,
            viewsCount = 1L,
            uniqueViewersCount = 1L,
            watchTimeSeconds = 5L,
            durationSeconds = if (type == "VIDEO") 45 else 10,
            viralScore = 45.0f,
            estimatedEarningsGhc = 2.50
        )
        dao.insertPost(newPost)
        return newPost
    }

    private fun recalculatePostMetrics(post: PostEntity): PostEntity {
        val viralScore = RewardEngine.calculateViralScore(
            viewsCount = post.viewsCount,
            uniqueViewers = post.uniqueViewersCount,
            totalWatchSeconds = post.watchTimeSeconds,
            durationSeconds = post.durationSeconds,
            likesCount = post.likesCount,
            averageRating = post.averageRating,
            commentsCount = 10,
            sharesCount = post.sharesCount,
            savesCount = post.savesCount,
            isFlagged = post.isFlagged
        )

        val earnings = RewardEngine.calculateEarningsGhc(
            viralScore = viralScore,
            uniqueViewers = post.uniqueViewersCount,
            likesCount = post.likesCount,
            sharesCount = post.sharesCount,
            savesCount = post.savesCount,
            averageRating = post.averageRating
        )

        return post.copy(
            viralScore = viralScore,
            estimatedEarningsGhc = earnings
        )
    }

    // WALLET & GHANA MOBILE MONEY
    fun getWalletFlow(userId: String): Flow<WalletEntity?> = dao.getWalletByUserIdFlow(userId)
    fun getTransactionsFlow(userId: String): Flow<List<TransactionEntity>> = dao.getTransactionsForUser(userId)
    fun getWithdrawalsFlow(userId: String): Flow<List<WithdrawalEntity>> = dao.getWithdrawalsForUser(userId)

    suspend fun processMoMoWithdrawal(
        userId: String,
        amountGhc: Double,
        network: MoMoNetwork,
        phone: String,
        accountName: String
    ): PayoutResult {
        val wallet = dao.getWalletByUserId(userId) ?: return PayoutResult(
            success = false,
            transactionId = "",
            reference = "",
            feeGhc = 0.0,
            netAmountGhc = 0.0,
            network = network.displayName,
            phone = phone,
            accountName = accountName,
            errorMessage = "Wallet not found"
        )

        if (amountGhc > wallet.availableBalanceGhc) {
            return PayoutResult(
                success = false,
                transactionId = "",
                reference = "",
                feeGhc = 0.0,
                netAmountGhc = 0.0,
                network = network.displayName,
                phone = phone,
                accountName = accountName,
                errorMessage = "Insufficient available balance. You have GH₵${String.format("%.2f", wallet.availableBalanceGhc)}"
            )
        }

        val result = PaymentService.processMoMoWithdrawal(amountGhc, network, phone, accountName)
        if (result.success) {
            // Deduct available balance
            val updatedBalance = (wallet.availableBalanceGhc - amountGhc).coerceAtLeast(0.0)
            dao.updateWallet(
                wallet.copy(
                    availableBalanceGhc = updatedBalance,
                    momoNetwork = network.name,
                    momoNumber = phone,
                    momoAccountName = result.accountName,
                    updatedAt = System.currentTimeMillis()
                )
            )

            // Record transaction
            val tx = TransactionEntity(
                id = "tx_" + UUID.randomUUID().toString().take(8),
                userId = userId,
                type = "WITHDRAWAL_MOMO",
                amountGhc = amountGhc,
                status = "COMPLETED",
                reference = result.reference,
                description = "Payout of GH₵${String.format("%.2f", result.netAmountGhc)} (Fee GH₵${String.format("%.2f", result.feeGhc)}) to ${result.network} $phone",
                momoNetwork = network.name,
                momoNumber = phone
            )
            dao.insertTransaction(tx)

            // Record withdrawal record
            val withdrawal = WithdrawalEntity(
                id = "wd_" + UUID.randomUUID().toString().take(8),
                userId = userId,
                amountGhc = amountGhc,
                feeGhc = result.feeGhc,
                netAmountGhc = result.netAmountGhc,
                momoNetwork = network.displayName,
                momoNumber = phone,
                momoAccountName = result.accountName,
                status = "COMPLETED",
                providerTransactionId = result.transactionId,
                idempotencyKey = UUID.randomUUID().toString(),
                completedAt = System.currentTimeMillis()
            )
            dao.insertWithdrawal(withdrawal)

            // Notification
            dao.insertNotification(
                NotificationEntity(
                    id = UUID.randomUUID().toString(),
                    userId = userId,
                    type = "WITHDRAWAL",
                    title = "MoMo Payout Successful 🇬🇭📱",
                    message = "GH₵${String.format("%.2f", result.netAmountGhc)} sent to your ${result.network} ($phone). Ref: ${result.reference}"
                )
            )
        }

        return result
    }

    // NOTIFICATIONS
    fun getNotificationsFlow(userId: String): Flow<List<NotificationEntity>> = dao.getNotificationsForUser(userId)
    fun getUnreadNotificationsCountFlow(userId: String): Flow<Int> = dao.getUnreadNotificationsCount(userId)
    suspend fun markNotificationsAsRead(userId: String) = dao.markAllNotificationsAsRead(userId)

    // REPORTS & MODERATION
    suspend fun submitReport(reporterId: String, targetType: String, targetId: String, targetTitle: String, reason: String) {
        val report = ReportEntity(
            id = "rep_" + UUID.randomUUID().toString().take(8),
            reporterId = reporterId,
            targetType = targetType,
            targetId = targetId,
            targetTitle = targetTitle,
            reason = reason
        )
        dao.insertReport(report)
    }

    suspend fun resolveReport(reportId: String, status: String, adminNotes: String, adminUser: String) {
        val reports = dao.getAllReports().firstOrNull() ?: emptyList()
        val report = reports.find { it.id == reportId } ?: return
        dao.updateReport(report.copy(status = status, notes = adminNotes))

        dao.insertAuditLog(
            AdminAuditLogEntity(
                id = UUID.randomUUID().toString(),
                adminUsername = adminUser,
                action = "RESOLVE_REPORT",
                target = "${report.targetType}:${report.targetId}",
                details = "Report ${report.id} marked as $status. Notes: $adminNotes"
            )
        )
    }

    suspend fun adminUpdateSetting(key: String, value: String, adminUser: String) {
        dao.insertSetting(SystemSettingsEntity(key, value, updatedAt = System.currentTimeMillis()))
        if (key == "base_engagement_reward_ghc") {
            RewardEngine.baseRewardUnitGhc = value.toDoubleOrNull() ?: 1.00
        } else if (key == "reward_multiplier") {
            RewardEngine.rewardMultiplier = value.toDoubleOrNull() ?: 1.00
        }

        dao.insertAuditLog(
            AdminAuditLogEntity(
                id = UUID.randomUUID().toString(),
                adminUsername = adminUser,
                action = "UPDATE_SETTING",
                target = key,
                details = "Setting $key updated to $value"
            )
        )
    }
}
