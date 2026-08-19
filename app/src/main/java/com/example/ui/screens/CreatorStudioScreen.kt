package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.AutoGraph
import androidx.compose.material.icons.filled.HourglassBottom
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.PostEntity
import com.example.data.local.entities.UserEntity
import com.example.data.local.entities.WalletEntity
import com.example.ui.components.FooterComponent
import com.example.ui.components.GlassButton
import com.example.ui.components.GlassCard
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.MomoGold
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonMint
import com.example.ui.theme.NeonPink
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun CreatorStudioScreen(
    currentUser: UserEntity?,
    userPosts: List<PostEntity>,
    wallet: WalletEntity?,
    onNavigateToWallet: () -> Unit
) {
    var selectedTimeframe by remember { mutableStateOf("WEEKLY") }

    val weeklyViewsData = listOf(
        "Mon" to 14.5f,
        "Tue" to 22.0f,
        "Wed" to 38.4f,
        "Thu" to 29.1f,
        "Fri" to 54.0f,
        "Sat" to 68.2f,
        "Sun" to 45.0f
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("creator_studio_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Studio Header
        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "Creator Studio ⚡",
                            color = TextPrimary,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Authentic engagement analytics & GH₵ creator rewards",
                            color = TextSecondary,
                            fontSize = 13.sp
                        )
                    }
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF1E1736))
                            .border(1.dp, GlassBorder, RoundedCornerShape(12.dp))
                            .clickable(onClick = onNavigateToWallet)
                            .padding(horizontal = 10.dp, vertical = 6.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AccountBalanceWallet, contentDescription = null, tint = MomoGold, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(text = "Wallet", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }
        }

        // VIRAL SCORE GAUGE CARD (Hero)
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
                shape = RoundedCornerShape(24.dp),
                borderColor = GlassBorder
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "Viral Score Engine",
                                color = TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Tier: Viral Masterpiece (2.5x Multiplier)",
                                color = NeonMint,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(14.dp))
                                .background(Color(0x3310B981))
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Active 🇬🇭",
                                color = NeonMint,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Circular Arc Gauge
                        Box(
                            modifier = Modifier.size(100.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Canvas(modifier = Modifier.size(90.dp)) {
                                val strokeWidth = 8.dp.toPx()
                                drawArc(
                                    color = Color(0x33475569),
                                    startAngle = 135f,
                                    sweepAngle = 270f,
                                    useCenter = false,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )
                                drawArc(
                                    brush = Brush.sweepGradient(listOf(NeonCyan, ElectricPurple, NeonPink)),
                                    startAngle = 135f,
                                    sweepAngle = 249f,
                                    useCenter = false,
                                    style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
                                )
                            }
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = String.format("%.1f", currentUser?.viralScore ?: 92.4f),
                                    color = TextPrimary,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "/ 100",
                                    color = TextMuted,
                                    fontSize = 10.sp
                                )
                            }
                        }

                        Spacer(modifier = Modifier.width(16.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "Base Reward Unit: GH₵1.00",
                                color = MomoGold,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Calculated from authentic completion time, 5-star ratings, unique Ghana viewers, and fraud-free social shares.",
                                color = TextSecondary,
                                fontSize = 11.sp,
                                lineHeight = 15.sp
                            )
                        }
                    }
                }
            }
        }

        // METRICS GRID
        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = "Authentic Views",
                        value = "384.0k",
                        delta = "+18.4%",
                        icon = Icons.Default.Visibility,
                        accentColor = NeonCyan,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Watch Time",
                        value = "1,240 hrs",
                        delta = "+24.1%",
                        icon = Icons.Default.HourglassBottom,
                        accentColor = ElectricPurple,
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    MetricCard(
                        title = "Followers Gained",
                        value = "18,450",
                        delta = "+1,420",
                        icon = Icons.Default.People,
                        accentColor = NeonMint,
                        modifier = Modifier.weight(1f)
                    )
                    MetricCard(
                        title = "Engagement Rate",
                        value = "14.8%",
                        delta = "High",
                        icon = Icons.Default.AutoGraph,
                        accentColor = MomoGold,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }

        // EARNINGS BREAKDOWN (Ghana Cedis)
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
                shape = RoundedCornerShape(20.dp),
                borderColor = GlassBorderLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.MonetizationOn, contentDescription = null, tint = MomoGold, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Creator Earnings Overview",
                                color = TextPrimary,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Text(
                            text = "GH₵ Currency",
                            color = MomoGold,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column {
                            Text(text = "Available Balance", color = TextMuted, fontSize = 11.sp)
                            Text(
                                text = "GH₵ ${String.format("%.2f", wallet?.availableBalanceGhc ?: 485.50)}",
                                color = TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text(text = "Pending Rewards", color = TextMuted, fontSize = 11.sp)
                            Text(
                                text = "GH₵ ${String.format("%.2f", wallet?.pendingBalanceGhc ?: 120.00)}",
                                color = MomoGold,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Column {
                            Text(text = "Lifetime Earned", color = TextMuted, fontSize = 11.sp)
                            Text(
                                text = "GH₵ ${String.format("%.2f", wallet?.lifetimeEarningsGhc ?: 1840.00)}",
                                color = NeonMint,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    GlassButton(
                        text = "Withdraw to Ghana Mobile Money 📱",
                        onClick = onNavigateToWallet,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "studio_withdraw_button"
                    )
                }
            }
        }

        // ENGAGEMENT CHART
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
                shape = RoundedCornerShape(20.dp),
                borderColor = GlassBorderLight
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Weekly Views (Thousands)",
                            color = TextPrimary,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Row {
                            listOf("DAILY", "WEEKLY", "MONTHLY").forEach { tf ->
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(if (selectedTimeframe == tf) NeonCyan else Color.Transparent)
                                        .clickable { selectedTimeframe = tf }
                                        .padding(horizontal = 6.dp, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = tf.take(1) + tf.drop(1).lowercase(),
                                        color = if (selectedTimeframe == tf) Color.Black else TextMuted,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val maxVal = 75f
                            val barWidth = 24.dp.toPx()
                            val spacing = (size.width - (weeklyViewsData.size * barWidth)) / (weeklyViewsData.size + 1)

                            weeklyViewsData.forEachIndexed { index, pair ->
                                val x = spacing + index * (barWidth + spacing)
                                val barHeight = (pair.second / maxVal) * (size.height - 24.dp.toPx())
                                val y = size.height - barHeight - 20.dp.toPx()

                                drawRoundRect(
                                    brush = Brush.verticalGradient(
                                        colors = listOf(NeonCyan, ElectricPurple)
                                    ),
                                    topLeft = Offset(x, y),
                                    size = Size(barWidth, barHeight),
                                    cornerRadius = CornerRadius(6.dp.toPx(), 6.dp.toPx())
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceAround
                        ) {
                            weeklyViewsData.forEach { pair ->
                                Text(
                                    text = pair.first,
                                    color = TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }
                    }
                }
            }
        }

        // TOP PERFORMING POSTS
        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 4.dp)) {
                Text(
                    text = "Your Top Performing Content",
                    color = TextPrimary,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        items(userPosts, key = { it.id }) { post ->
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                shape = RoundedCornerShape(16.dp),
                borderColor = GlassBorderLight
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = post.caption,
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${post.viewsCount} views", color = TextMuted, fontSize = 11.sp)
                            Text(text = " • ", color = TextMuted, fontSize = 11.sp)
                            Text(text = "${post.likesCount} likes", color = TextMuted, fontSize = 11.sp)
                            Text(text = " • ", color = TextMuted, fontSize = 11.sp)
                            Text(text = "Score: ${String.format("%.0f", post.viralScore)}", color = NeonCyan, fontSize = 11.sp, fontWeight = FontWeight.Bold)
                        }
                    }
                    Column(horizontalAlignment = Alignment.End) {
                        Text(
                            text = "GH₵ ${String.format("%.2f", post.estimatedEarningsGhc)}",
                            color = MomoGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(text = "Estimated", color = TextMuted, fontSize = 10.sp)
                    }
                }
            }
        }

        // Footer
        item {
            FooterComponent()
        }
    }
}

@Composable
fun MetricCard(
    title: String,
    value: String,
    delta: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    GlassCard(
        modifier = modifier,
        shape = RoundedCornerShape(18.dp),
        borderColor = GlassBorderLight
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = title, color = TextMuted, fontSize = 11.sp)
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(accentColor.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = accentColor, modifier = Modifier.size(16.dp))
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = value, color = TextPrimary, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = delta, color = accentColor, fontSize = 11.sp, fontWeight = FontWeight.SemiBold)
        }
    }
}
