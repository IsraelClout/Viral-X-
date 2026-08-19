package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.local.entities.PostEntity
import com.example.data.local.entities.UserEntity
import com.example.data.local.entities.WalletEntity
import com.example.ui.components.CreatorAvatar
import com.example.ui.components.FooterComponent
import com.example.ui.components.GlassCard
import com.example.ui.components.ReportDialog
import com.example.ui.components.StarRatingDialog
import com.example.ui.components.StoryBubble
import com.example.ui.theme.BluePurpleGradient
import com.example.ui.theme.EditorialBlue
import com.example.ui.theme.EditorialCardGradient
import com.example.ui.theme.EditorialPurple
import com.example.ui.theme.EditorialRoyalBlue
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.MomoGold
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonMint
import com.example.ui.theme.NeonPink
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardGlass
import com.example.ui.theme.PurpleCyanGradient
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay

@Composable
fun HomeScreen(
    currentUser: UserEntity?,
    allPosts: List<PostEntity>,
    creators: List<UserEntity>,
    wallet: WalletEntity?,
    unreadNotifCount: Int,
    onNavigateToSearch: () -> Unit,
    onNavigateToWallet: () -> Unit,
    onNavigateToNotifications: () -> Unit,
    onLikePost: (postId: String) -> Unit,
    onRatePost: (postId: String, stars: Int) -> Unit,
    onOpenComments: (postId: String) -> Unit,
    onToggleFollow: (creatorId: String) -> Unit,
    onToggleSave: (postId: String) -> Unit,
    onSharePost: (postId: String) -> Unit,
    onRecordWatchTime: (postId: String, seconds: Long) -> Unit,
    onSubmitReport: (targetType: String, targetId: String, targetTitle: String, reason: String) -> Unit
) {
    var selectedFeedTab by remember { mutableStateOf("FOR_YOU") }
    var reportingPost by remember { mutableStateOf<PostEntity?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("home_screen_feed"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // EDITORIAL TOP HEADER BAR (Viral X Brand, Search, Pill Wallet, Notifications)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 4.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Editorial Logo & Brand
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(BluePurpleGradient),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "X",
                            color = Color.White,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "Viral X",
                            color = TextPrimary,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = (-0.3).sp
                        )
                        Text(
                            text = "Ghana Creator Hub",
                            color = EditorialBlue,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.SemiBold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // Action buttons (Editorial Glass Wallet Pill, Search, Notifications)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Editorial Frosted Glass Wallet Pill
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0x1AFFFFFF))
                            .border(1.dp, GlassBorderLight, RoundedCornerShape(20.dp))
                            .clickable(onClick = onNavigateToWallet)
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                            .testTag("home_wallet_chip"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "WALLET",
                            color = EditorialBlue,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Black,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "GH₵ ${String.format("%.2f", wallet?.availableBalanceGhc ?: 1240.50)}",
                            color = TextPrimary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.width(6.dp))

                    IconButton(
                        onClick = onNavigateToSearch,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Search",
                            tint = TextSecondary,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    BadgedBox(
                        badge = {
                            if (unreadNotifCount > 0) {
                                Badge(containerColor = NeonPink) {
                                    Text(text = unreadNotifCount.toString(), color = Color.White, fontSize = 9.sp)
                                }
                            }
                        }
                    ) {
                        IconButton(
                            onClick = onNavigateToNotifications,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notifications",
                                tint = TextSecondary,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }

        // STORIES CAROUSEL (Top Creator Bubbles with Editorial Rings)
        item {
            Column(modifier = Modifier.padding(vertical = 8.dp)) {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    item {
                        StoryBubble(
                            name = "Your Story",
                            avatarUrl = currentUser?.avatarUrl ?: "avatar_ghana_creator",
                            isUnseen = false,
                            onClick = {}
                        )
                    }
                    items(creators, key = { it.id }) { creator ->
                        StoryBubble(
                            name = creator.displayName.split(" ").firstOrNull() ?: creator.username,
                            avatarUrl = creator.avatarUrl,
                            isUnseen = true,
                            onClick = {}
                        )
                    }
                }
            }
        }

        // EDITORIAL STATS GRID (Today's Reach & Est. Rewards)
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Today's Reach Card
                GlassCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = Color(0x0FFFFFFF),
                    borderColor = GlassBorderLight
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "TODAY'S REACH",
                            color = TextMuted,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "4.2M",
                            color = TextPrimary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Light,
                            letterSpacing = (-0.5).sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = { 0.72f },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(4.dp)
                                .clip(RoundedCornerShape(2.dp)),
                            color = EditorialBlue,
                            trackColor = Color(0x22FFFFFF)
                        )
                    }
                }

                // Est. Rewards Card
                GlassCard(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(20.dp),
                    backgroundColor = Color(0x1F2563EB),
                    borderColor = Color(0x333B82F6)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "EST. REWARDS",
                            color = Color(0xFF93C5FD),
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 1.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "GH₵ 142",
                            color = TextPrimary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Light,
                            letterSpacing = (-0.5).sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Pending Verification",
                            color = Color(0x9993C5FD),
                            fontSize = 10.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }
            }
        }

        // FEED TOGGLE ("For You (Viral 🔥)" / "Following")
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0x12FFFFFF))
                        .border(1.dp, GlassBorderLight, RoundedCornerShape(20.dp))
                        .padding(3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selectedFeedTab == "FOR_YOU") BluePurpleGradient else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
                            .clickable { selectedFeedTab = "FOR_YOU" }
                            .padding(horizontal = 18.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "For You (Viral 🔥)",
                            color = if (selectedFeedTab == "FOR_YOU") Color.White else TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (selectedFeedTab == "FOLLOWING") BluePurpleGradient else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
                            .clickable { selectedFeedTab = "FOLLOWING" }
                            .padding(horizontal = 18.dp, vertical = 6.dp)
                    ) {
                        Text(
                            text = "Following",
                            color = if (selectedFeedTab == "FOLLOWING") Color.White else TextSecondary,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // POSTS LIST (Editorial Cards)
        items(allPosts, key = { it.id }) { post ->
            FeedPostCard(
                post = post,
                currentUserId = currentUser?.id ?: "",
                onLike = { onLikePost(post.id) },
                onRate = { stars -> onRatePost(post.id, stars) },
                onOpenComments = { onOpenComments(post.id) },
                onToggleFollow = { onToggleFollow(post.creatorId) },
                onToggleSave = { onToggleSave(post.id) },
                onShare = { onSharePost(post.id) },
                onRecordWatch = { seconds -> onRecordWatchTime(post.id, seconds) },
                onReport = { reportingPost = post }
            )
            Spacer(modifier = Modifier.height(14.dp))
        }

        // FOOTER COMPONENT
        item {
            FooterComponent()
        }
    }

    if (reportingPost != null) {
        ReportDialog(
            targetTitle = reportingPost?.caption ?: "Post",
            onDismiss = { reportingPost = null },
            onSubmitReport = { reason ->
                reportingPost?.let { p ->
                    onSubmitReport("POST", p.id, p.caption, reason)
                }
            }
        )
    }
}

@Composable
fun FeedPostCard(
    post: PostEntity,
    currentUserId: String,
    onLike: () -> Unit,
    onRate: (Int) -> Unit,
    onOpenComments: () -> Unit,
    onToggleFollow: () -> Unit,
    onToggleSave: () -> Unit,
    onShare: () -> Unit,
    onRecordWatch: (Long) -> Unit,
    onReport: () -> Unit
) {
    val context = LocalContext.current
    var isPlaying by remember { mutableStateOf(true) }
    var showRatingDialog by remember { mutableStateOf(false) }
    var isLiked by remember { mutableStateOf(false) }
    var isSaved by remember { mutableStateOf(false) }
    var isFollowing by remember { mutableStateOf(false) }
    var showMenu by remember { mutableStateOf(false) }

    var videoProgress by remember { mutableFloatStateOf(0.35f) }
    LaunchedEffect(isPlaying, post.id) {
        if (isPlaying && post.type == "VIDEO") {
            while (true) {
                delay(1000)
                videoProgress = (videoProgress + 0.03f) % 1.0f
                onRecordWatch(1L)
            }
        }
    }

    val imageRes = remember(post.mediaUrl) {
        when {
            post.mediaUrl.contains("fashion") -> context.resources.getIdentifier("post_accra_fashion_1787123210091", "drawable", context.packageName)
            post.mediaUrl.contains("music") -> context.resources.getIdentifier("post_accra_music_1787123223856", "drawable", context.packageName)
            else -> context.resources.getIdentifier("avatar_ghana_creator_1787123197792", "drawable", context.packageName)
        }
    }

    GlassCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 14.dp)
            .testTag("post_card_${post.id}"),
        shape = RoundedCornerShape(26.dp),
        backgroundColor = Color(0x0DFFFFFF),
        borderColor = GlassBorder
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            // Top Creator Row & LIVE VIRAL Badge
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, Color(0x663B82F6), CircleShape)
                            .padding(2.dp)
                    ) {
                        CreatorAvatar(
                            avatarUrl = post.creatorAvatarUrl,
                            size = 38.dp
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = post.creatorDisplayName,
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Accra, Ghana",
                            color = TextSecondary,
                            fontSize = 11.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // LIVE VIRAL Badge
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0x2E22C55E))
                            .border(1.dp, Color(0x4D22C55E), RoundedCornerShape(12.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "LIVE VIRAL",
                            color = NeonMint,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )
                    }

                    IconButton(
                        onClick = { showMenu = true },
                        modifier = Modifier.size(30.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More",
                            tint = TextMuted,
                            modifier = Modifier.size(18.dp)
                        )
                    }

                    DropdownMenu(
                        expanded = showMenu,
                        onDismissRequest = { showMenu = false },
                        modifier = Modifier.background(ObsidianCard)
                    ) {
                        DropdownMenuItem(
                            text = { Text("Report Content", color = NeonPink) },
                            onClick = {
                                showMenu = false
                                onReport()
                            }
                        )
                    }
                }
            }

            // Media Preview Container (Aspect ratio & Play Overlay)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.25f)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0x1F1E1B4B))
                    .border(1.dp, GlassBorderLight, RoundedCornerShape(20.dp))
                    .clickable { isPlaying = !isPlaying }
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(if (imageRes != 0) imageRes else post.mediaUrl)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Post Media",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Dark Vignette Overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    Color(0x88000000)
                                )
                            )
                        )
                )

                // Center Play Button Circle (Editorial Glass)
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(CircleShape)
                        .background(Color(0x44FFFFFF))
                        .border(1.dp, Color(0x66FFFFFF), CircleShape)
                        .align(Alignment.Center),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isPlaying) Icons.Default.VolumeUp else Icons.Default.PlayArrow,
                        contentDescription = "Play",
                        tint = Color.White,
                        modifier = Modifier.size(22.dp)
                    )
                }

                // Bottom Left Timestamp Badge (02:45)
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(8.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(Color(0x99000000))
                        .border(0.5.dp, Color(0x33FFFFFF), RoundedCornerShape(6.dp))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "02:45",
                        color = Color.White,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // Caption
            Text(
                text = post.caption,
                color = TextPrimary,
                fontSize = 13.sp,
                lineHeight = 18.sp,
                modifier = Modifier.padding(top = 10.dp)
            )

            // Hashtags
            Text(
                text = post.hashtags,
                color = EditorialBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
            )

            // EDITORIAL BOTTOM ACTION BAR
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Like, Comment, Rate, Share cluster
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    // Like Button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(if (isLiked) Color(0x33F43F5E) else Color(0x14FFFFFF))
                            .border(1.dp, if (isLiked) NeonPink else Color(0x1FFFFFFF), RoundedCornerShape(10.dp))
                            .clickable {
                                isLiked = !isLiked
                                onLike()
                            }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("post_like_button_${post.id}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "❤️", fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${String.format("%.1f", (post.likesCount + if (isLiked) 1 else 0) / 100.0)}K",
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Comments Button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x14FFFFFF))
                            .border(1.dp, Color(0x1FFFFFFF), RoundedCornerShape(10.dp))
                            .clickable(onClick = onOpenComments)
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("post_comments_button_${post.id}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "💬", fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "842",
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Rate Button
                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0x14FFFFFF))
                            .border(1.dp, Color(0x1FFFFFFF), RoundedCornerShape(10.dp))
                            .clickable { showRatingDialog = true }
                            .padding(horizontal = 8.dp, vertical = 5.dp)
                            .testTag("post_rating_button_${post.id}"),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = "⭐", fontSize = 11.sp)
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (post.averageRating > 0) String.format("%.1f", post.averageRating) else "4.9",
                            color = TextPrimary,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Save / Bookmark Icon
                    IconButton(
                        onClick = {
                            isSaved = !isSaved
                            onToggleSave()
                        },
                        modifier = Modifier.size(28.dp)
                    ) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = "Save",
                            tint = if (isSaved) EditorialBlue else TextMuted,
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }

                // VIRAL SCORE Badge
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "VIRAL SCORE",
                        color = TextMuted,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.5.sp
                    )
                    Text(
                        text = String.format("%.1f", post.viralScore),
                        color = EditorialBlue,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
    }

    if (showRatingDialog) {
        StarRatingDialog(
            currentRating = 5,
            averageRating = post.averageRating,
            ratingsCount = post.ratingsCount,
            onDismiss = { showRatingDialog = false },
            onRate = { stars -> onRate(stars) }
        )
    }
}
