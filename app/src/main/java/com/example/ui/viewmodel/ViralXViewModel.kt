package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.ViralXDatabase
import com.example.data.local.entities.CommentEntity
import com.example.data.local.entities.NotificationEntity
import com.example.data.local.entities.PostEntity
import com.example.data.local.entities.ReportEntity
import com.example.data.local.entities.TransactionEntity
import com.example.data.local.entities.UserEntity
import com.example.data.local.entities.WalletEntity
import com.example.data.repository.ViralXRepository
import com.example.domain.MoMoNetwork
import com.example.domain.PayoutResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class ScreenTab {
    HOME,
    DISCOVER,
    CREATE,
    STUDIO,
    WALLET,
    NOTIFICATIONS,
    PROFILE,
    ADMIN,
    AUTH
}

class ViralXViewModel(application: Application) : AndroidViewModel(application) {
    private val database = ViralXDatabase.getInstance(application)
    private val repository = ViralXRepository(database.viralXDao())

    private val _currentTab = MutableStateFlow(ScreenTab.HOME)
    val currentTab: StateFlow<ScreenTab> = _currentTab.asStateFlow()

    private val _currentUser = MutableStateFlow<UserEntity?>(null)
    val currentUser: StateFlow<UserEntity?> = _currentUser.asStateFlow()

    val allPosts: StateFlow<List<PostEntity>> = repository.allPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val creators: StateFlow<List<UserEntity>> = repository.allUsers
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val userPosts: StateFlow<List<PostEntity>> = repository.allPosts
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val savedPosts: StateFlow<List<PostEntity>> = repository.getSavedPostsFlow("usr_israel")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val wallet: StateFlow<WalletEntity?> = repository.getWalletFlow("usr_israel")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    val transactions: StateFlow<List<TransactionEntity>> = repository.getTransactionsFlow("usr_israel")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val notifications: StateFlow<List<NotificationEntity>> = repository.getNotificationsFlow("usr_israel")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val unreadNotifCount: StateFlow<Int> = repository.getUnreadNotificationsCountFlow("usr_israel")
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), 0)

    val reports: StateFlow<List<ReportEntity>> = repository.allReports
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    // Active Comments Modal
    private val _activeCommentPostId = MutableStateFlow<String?>(null)
    val activeCommentPostId: StateFlow<String?> = _activeCommentPostId.asStateFlow()

    private val _activePostComments = MutableStateFlow<List<CommentEntity>>(emptyList())
    val activePostComments: StateFlow<List<CommentEntity>> = _activePostComments.asStateFlow()

    init {
        viewModelScope.launch {
            repository.seedInitialDataIfEmpty()
            val user = repository.getUserById("usr_israel")
            _currentUser.value = user
        }
    }

    fun setTab(tab: ScreenTab) {
        _currentTab.value = tab
    }

    fun login(email: String, name: String, phone: String) {
        viewModelScope.launch {
            val user = repository.getUserById("usr_israel") ?: UserEntity(
                id = "usr_israel",
                username = "israel_creator",
                displayName = name.ifBlank { "Israel Ewoenam Gokah" },
                email = email,
                phone = phone,
                bio = "🇬🇭 Digital Creator & Tech Innovator in Accra. Creating next-gen experiences on Viral X.",
                avatarUrl = "avatar_ghana_creator",
                viralScore = 92.4f,
                isAdmin = true,
                isCreator = true,
                isVerified = true,
                is2FaEnabled = true
            )
            _currentUser.value = user
            _currentTab.value = ScreenTab.HOME
        }
    }

    fun logout() {
        _currentUser.value = null
        _currentTab.value = ScreenTab.AUTH
    }

    fun openComments(postId: String) {
        _activeCommentPostId.value = postId
        viewModelScope.launch {
            repository.getCommentsFlow(postId).collect { comments ->
                _activePostComments.value = comments
            }
        }
    }

    fun closeComments() {
        _activeCommentPostId.value = null
        _activePostComments.value = emptyList()
    }

    fun addComment(postId: String, content: String, parentId: String?) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.addComment(
                postId = postId,
                parentCommentId = parentId,
                userId = user.id,
                content = content
            )
        }
    }

    fun likePost(postId: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.toggleLike(postId, user.id)
        }
    }

    fun ratePost(postId: String, stars: Int) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.ratePost(postId, user.id, stars)
        }
    }

    fun toggleFollow(creatorId: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.toggleFollow(user.id, creatorId)
        }
    }

    fun toggleSave(postId: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.toggleSave(user.id, postId)
        }
    }

    fun sharePost(postId: String) {
        viewModelScope.launch {
            repository.sharePost(postId)
        }
    }

    fun recordWatchTime(postId: String, seconds: Long) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.recordWatchTimeAndAuthenticView(postId, user.id, seconds)
        }
    }

    fun markNotificationsAsRead() {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.markNotificationsAsRead(user.id)
        }
    }

    fun updateProfile(displayName: String, bio: String, momoNumber: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val updated = user.copy(displayName = displayName, bio = bio, phone = momoNumber)
            repository.updateUser(updated)
            _currentUser.value = updated
        }
    }

    fun toggle2FA(enabled: Boolean) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            val updated = user.copy(is2FaEnabled = enabled)
            repository.updateUser(updated)
            _currentUser.value = updated
        }
    }

    fun createPost(type: String, mediaUrl: String, caption: String, hashtags: String, commentsEnabled: Boolean, visibility: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.createPost(
                creatorId = user.id,
                type = type,
                mediaAsset = mediaUrl,
                caption = caption,
                hashtags = hashtags,
                commentsEnabled = commentsEnabled,
                visibility = visibility
            )
            _currentTab.value = ScreenTab.HOME
        }
    }

    suspend fun processWithdrawal(
        amount: Double,
        network: MoMoNetwork,
        phone: String,
        accountName: String
    ): PayoutResult {
        val user = _currentUser.value ?: return PayoutResult(
            success = false,
            transactionId = "",
            reference = "",
            feeGhc = 0.0,
            netAmountGhc = 0.0,
            network = network.displayName,
            phone = phone,
            accountName = accountName,
            errorMessage = "User not authenticated"
        )
        return repository.processMoMoWithdrawal(user.id, amount, network, phone, accountName)
    }

    fun submitReport(targetType: String, targetId: String, targetTitle: String, reason: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.submitReport(user.id, targetType, targetId, targetTitle, reason)
        }
    }

    fun moderateReport(reportId: String, resolution: String) {
        val user = _currentUser.value ?: return
        viewModelScope.launch {
            repository.resolveReport(reportId, resolution, "Moderated via Admin Screen", user.username)
        }
    }
}
