package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.AutoGraph
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.CommentsBottomSheet
import com.example.ui.screens.AdminScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.CreatePostScreen
import com.example.ui.screens.CreatorStudioScreen
import com.example.ui.screens.DiscoverScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.WalletScreen
import com.example.ui.theme.BluePurpleGradient
import com.example.ui.theme.EditorialBlue
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import com.example.ui.viewmodel.ScreenTab
import com.example.ui.viewmodel.ViralXViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ViralXViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                ViralXApp(viewModel = viewModel)
            }
        }
    }
}

@Composable
fun ViralXApp(viewModel: ViralXViewModel) {
    val currentTab by viewModel.currentTab.collectAsState()
    val currentUser by viewModel.currentUser.collectAsState()
    val allPosts by viewModel.allPosts.collectAsState()
    val creators by viewModel.creators.collectAsState()
    val userPosts by viewModel.userPosts.collectAsState()
    val savedPosts by viewModel.savedPosts.collectAsState()
    val wallet by viewModel.wallet.collectAsState()
    val transactions by viewModel.transactions.collectAsState()
    val notifications by viewModel.notifications.collectAsState()
    val unreadNotifCount by viewModel.unreadNotifCount.collectAsState()
    val reports by viewModel.reports.collectAsState()
    val activeCommentPostId by viewModel.activeCommentPostId.collectAsState()
    val activePostComments by viewModel.activePostComments.collectAsState()

    val showBottomNav = currentTab != ScreenTab.AUTH && currentTab != ScreenTab.ADMIN

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .statusBarsPadding()
    ) {
        // Editorial Atmospheric Background Glows (Blue top-left, Purple bottom-right)
        Box(
            modifier = Modifier
                .size(320.dp)
                .offset(x = (-80).dp, y = (-60).dp)
                .clip(CircleShape)
                .background(Color(0x242563EB))
        )
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(360.dp)
                .offset(x = 100.dp, y = 80.dp)
                .clip(CircleShape)
                .background(Color(0x207C3AED))
        )

        // Main Screen View Switcher
        AnimatedContent(
            targetState = currentTab,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "ScreenTransition"
        ) { tab ->
            when (tab) {
                ScreenTab.AUTH -> AuthScreen(
                    onLoginSuccess = { email, name, phone ->
                        viewModel.login(email, name, phone)
                    },
                    onQuickDemoLogin = {
                        viewModel.login("israel@viralx.gh", "Israel Ewoenam Gokah", "0244889900")
                    }
                )

                ScreenTab.HOME -> HomeScreen(
                    currentUser = currentUser,
                    allPosts = allPosts,
                    creators = creators,
                    wallet = wallet,
                    unreadNotifCount = unreadNotifCount,
                    onNavigateToSearch = { viewModel.setTab(ScreenTab.DISCOVER) },
                    onNavigateToWallet = { viewModel.setTab(ScreenTab.WALLET) },
                    onNavigateToNotifications = { viewModel.setTab(ScreenTab.NOTIFICATIONS) },
                    onLikePost = { viewModel.likePost(it) },
                    onRatePost = { id, stars -> viewModel.ratePost(id, stars) },
                    onOpenComments = { viewModel.openComments(it) },
                    onToggleFollow = { viewModel.toggleFollow(it) },
                    onToggleSave = { viewModel.toggleSave(it) },
                    onSharePost = { viewModel.sharePost(it) },
                    onRecordWatchTime = { id, secs -> viewModel.recordWatchTime(id, secs) },
                    onSubmitReport = { type, id, title, reason ->
                        viewModel.submitReport(type, id, title, reason)
                    }
                )

                ScreenTab.DISCOVER -> DiscoverScreen(
                    posts = allPosts,
                    creators = creators,
                    onSelectPost = { viewModel.setTab(ScreenTab.HOME) },
                    onToggleFollow = { viewModel.toggleFollow(it) }
                )

                ScreenTab.CREATE -> CreatePostScreen(
                    currentUserId = currentUser?.id ?: "usr_israel",
                    onPostCreated = { type, mediaUrl, caption, hashtags, comments, visibility ->
                        viewModel.createPost(type, mediaUrl, caption, hashtags, comments, visibility)
                    }
                )

                ScreenTab.STUDIO -> CreatorStudioScreen(
                    currentUser = currentUser,
                    userPosts = userPosts,
                    wallet = wallet,
                    onNavigateToWallet = { viewModel.setTab(ScreenTab.WALLET) }
                )

                ScreenTab.WALLET -> WalletScreen(
                    wallet = wallet,
                    transactions = transactions,
                    onProcessWithdrawal = { amt, net, phone, name ->
                        viewModel.processWithdrawal(amt, net, phone, name)
                    }
                )

                ScreenTab.NOTIFICATIONS -> NotificationsScreen(
                    notifications = notifications,
                    onMarkAllAsRead = { viewModel.markNotificationsAsRead() }
                )

                ScreenTab.PROFILE -> ProfileScreen(
                    currentUser = currentUser,
                    userPosts = userPosts,
                    savedPosts = savedPosts,
                    onUpdateProfile = { name, bio, phone ->
                        viewModel.updateProfile(name, bio, phone)
                    },
                    onToggle2FA = { viewModel.toggle2FA(it) },
                    onNavigateToAdmin = { viewModel.setTab(ScreenTab.ADMIN) },
                    onLogout = { viewModel.logout() }
                )

                ScreenTab.ADMIN -> AdminScreen(
                    reports = reports,
                    onBack = { viewModel.setTab(ScreenTab.PROFILE) },
                    onModerateReport = { id, resolution ->
                        viewModel.moderateReport(id, resolution)
                    }
                )
            }
        }

        // Floating Glassmorphic Editorial Bottom Navigation Bar
        if (showBottomNav) {
            FloatingBottomNav(
                currentTab = currentTab,
                onTabSelected = { viewModel.setTab(it) },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .navigationBarsPadding()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
        }

        // Comments Bottom Sheet
        if (activeCommentPostId != null) {
            CommentsBottomSheet(
                comments = activePostComments,
                onDismiss = { viewModel.closeComments() },
                onAddComment = { content, parentId ->
                    viewModel.addComment(activeCommentPostId!!, content, parentId)
                }
            )
        }
    }
}

@Composable
fun FloatingBottomNav(
    currentTab: ScreenTab,
    onTabSelected: (ScreenTab) -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(32.dp))
            .border(1.dp, GlassBorder, RoundedCornerShape(32.dp))
            .testTag("floating_bottom_nav"),
        shape = RoundedCornerShape(32.dp),
        color = Color(0xF2070512),
        tonalElevation = 10.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            NavTabItem(
                label = "HOME",
                icon = if (currentTab == ScreenTab.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                isSelected = currentTab == ScreenTab.HOME,
                onClick = { onTabSelected(ScreenTab.HOME) },
                testTag = "nav_tab_home"
            )

            NavTabItem(
                label = "DISCOVER",
                icon = if (currentTab == ScreenTab.DISCOVER) Icons.Filled.Explore else Icons.Outlined.Explore,
                isSelected = currentTab == ScreenTab.DISCOVER,
                onClick = { onTabSelected(ScreenTab.DISCOVER) },
                testTag = "nav_tab_discover"
            )

            // Elevated Center Editorial Action Button (+)
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(BluePurpleGradient)
                    .border(3.dp, ObsidianBg, CircleShape)
                    .clickable { onTabSelected(ScreenTab.CREATE) }
                    .testTag("nav_tab_create"),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Create Post",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )
            }

            NavTabItem(
                label = "STUDIO",
                icon = if (currentTab == ScreenTab.STUDIO) Icons.Filled.AutoGraph else Icons.Outlined.AutoGraph,
                isSelected = currentTab == ScreenTab.STUDIO,
                onClick = { onTabSelected(ScreenTab.STUDIO) },
                testTag = "nav_tab_studio"
            )

            NavTabItem(
                label = "PROFILE",
                icon = if (currentTab == ScreenTab.PROFILE) Icons.Filled.Person else Icons.Outlined.Person,
                isSelected = currentTab == ScreenTab.PROFILE,
                onClick = { onTabSelected(ScreenTab.PROFILE) },
                testTag = "nav_tab_profile"
            )
        }
    }
}

@Composable
fun NavTabItem(
    label: String,
    icon: ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    testTag: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .clip(CircleShape)
            .clickable(onClick = onClick)
            .padding(horizontal = 10.dp, vertical = 6.dp)
            .testTag(testTag)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (isSelected) EditorialBlue else TextMuted,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = label,
            color = if (isSelected) EditorialBlue else TextMuted,
            fontSize = 9.sp,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
            letterSpacing = 0.5.sp
        )
    }
}
