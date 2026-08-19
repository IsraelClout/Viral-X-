package com.example.ui.screens

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AdminPanelSettings
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.GridOn
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.data.local.entities.PostEntity
import com.example.data.local.entities.UserEntity
import com.example.ui.components.CreatorAvatar
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
import com.example.ui.theme.PinkPurpleGradient
import com.example.ui.theme.PurpleCyanGradient
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun ProfileScreen(
    currentUser: UserEntity?,
    userPosts: List<PostEntity>,
    savedPosts: List<PostEntity>,
    onUpdateProfile: (name: String, bio: String, momoNumber: String) -> Unit,
    onToggle2FA: (Boolean) -> Unit,
    onNavigateToAdmin: () -> Unit,
    onLogout: () -> Unit
) {
    var isEditingProfile by remember { mutableStateOf(false) }
    var selectedTab by remember { mutableStateOf("POSTS") } // POSTS, SAVED, SECURITY
    var nameInput by remember(currentUser) { mutableStateOf(currentUser?.displayName ?: "Israel Ewoenam Gokah") }
    var bioInput by remember(currentUser) { mutableStateOf(currentUser?.bio ?: "🇬🇭 Digital Creator & Tech Innovator in Accra. Creating next-gen experiences on Viral X.") }
    var phoneInput by remember(currentUser) { mutableStateOf(currentUser?.phone ?: "0244889900") }

    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("profile_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // PROFILE HEADER CARD
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 6.dp),
                shape = RoundedCornerShape(24.dp),
                borderColor = GlassBorder
            ) {
                Column(
                    modifier = Modifier.padding(18.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(86.dp)
                            .clip(CircleShape)
                            .background(PinkPurpleGradient)
                            .padding(3.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CreatorAvatar(avatarUrl = currentUser?.avatarUrl ?: "avatar_ghana_creator", size = 80.dp)
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = currentUser?.displayName ?: "Israel Ewoenam Gokah",
                            color = TextPrimary,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        if (currentUser?.isVerified == true) {
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = "Verified Creator",
                                tint = NeonCyan,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }

                    Text(
                        text = "@${currentUser?.username ?: "israel_creator"}",
                        color = TextMuted,
                        fontSize = 12.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = currentUser?.bio ?: "🇬🇭 Digital Creator in Accra. Building next-gen experiences on Viral X.",
                        color = TextSecondary,
                        fontSize = 12.sp,
                        lineHeight = 16.sp
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // STATS ROW
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        ProfileStat(title = "Posts", value = "${userPosts.size}")
                        ProfileStat(title = "Followers", value = "${String.format("%.1f", (currentUser?.totalFollowers ?: 18450) / 1000.0)}k")
                        ProfileStat(title = "Following", value = "${currentUser?.totalFollowing ?: 214}")
                        ProfileStat(title = "Viral Score", value = "${String.format("%.1f", currentUser?.viralScore ?: 92.4f)}")
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GlassButton(
                            text = if (isEditingProfile) "Done" else "Edit Profile",
                            onClick = {
                                if (isEditingProfile) {
                                    onUpdateProfile(nameInput, bioInput, phoneInput)
                                }
                                isEditingProfile = !isEditingProfile
                            },
                            modifier = Modifier.weight(1f)
                        )

                        if (currentUser?.isAdmin == true) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(14.dp))
                                    .background(Color(0xFF2A1B40))
                                    .border(1.dp, NeonPink, RoundedCornerShape(14.dp))
                                    .clickable(onClick = onNavigateToAdmin)
                                    .padding(horizontal = 12.dp, vertical = 10.dp)
                            ) {
                                Icon(Icons.Default.AdminPanelSettings, contentDescription = "Admin", tint = NeonPink, modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }

        // EDIT PROFILE MODAL / EXPANDED SECTION
        if (isEditingProfile) {
            item {
                GlassCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 6.dp),
                    shape = RoundedCornerShape(20.dp),
                    borderColor = NeonCyan
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Edit Creator Profile", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = nameInput,
                            onValueChange = { nameInput = it },
                            label = { Text("Display Name", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonCyan,
                                unfocusedBorderColor = GlassBorderLight,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = bioInput,
                            onValueChange = { bioInput = it },
                            label = { Text("Bio", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonCyan,
                                unfocusedBorderColor = GlassBorderLight,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            maxLines = 3
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = phoneInput,
                            onValueChange = { phoneInput = it },
                            label = { Text("Mobile Money Phone", fontSize = 12.sp) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = MomoGold,
                                unfocusedBorderColor = GlassBorderLight,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            singleLine = true
                        )
                    }
                }
            }
        }

        // TABS: POSTS, SAVED, SECURITY & 2FA
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TabPill(
                    label = "My Posts",
                    icon = Icons.Default.GridOn,
                    isSelected = selectedTab == "POSTS",
                    onClick = { selectedTab = "POSTS" },
                    modifier = Modifier.weight(1f)
                )
                TabPill(
                    label = "Saved",
                    icon = Icons.Default.Bookmark,
                    isSelected = selectedTab == "SAVED",
                    onClick = { selectedTab = "SAVED" },
                    modifier = Modifier.weight(1f)
                )
                TabPill(
                    label = "2FA & Security",
                    icon = Icons.Default.Security,
                    isSelected = selectedTab == "SECURITY",
                    onClick = { selectedTab = "SECURITY" },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        // TAB CONTENTS
        when (selectedTab) {
            "POSTS" -> {
                if (userPosts.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No posts yet. Tap '+' to create your first viral post!", color = TextMuted, fontSize = 13.sp)
                        }
                    }
                } else {
                    items(userPosts.chunked(3)) { rowPosts ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 3.dp, bottom = 3.dp),
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            rowPosts.forEach { post ->
                                val imageRes = remember(post.mediaUrl) {
                                    when {
                                        post.mediaUrl.contains("fashion") -> context.resources.getIdentifier("post_accra_fashion_1787123210091", "drawable", context.packageName)
                                        post.mediaUrl.contains("music") -> context.resources.getIdentifier("post_accra_music_1787123223856", "drawable", context.packageName)
                                        else -> context.resources.getIdentifier("avatar_ghana_creator_1787123197792", "drawable", context.packageName)
                                    }
                                }

                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .aspectRatio(1f)
                                        .clip(RoundedCornerShape(12.dp))
                                        .border(1.dp, GlassBorderLight, RoundedCornerShape(12.dp))
                                ) {
                                    AsyncImage(
                                        model = ImageRequest.Builder(context)
                                            .data(if (imageRes != 0) imageRes else post.mediaUrl)
                                            .crossfade(true)
                                            .build(),
                                        contentDescription = post.caption,
                                        contentScale = ContentScale.Crop,
                                        modifier = Modifier.fillMaxSize()
                                    )
                                    if (post.type == "VIDEO") {
                                        Icon(
                                            Icons.Default.PlayArrow,
                                            contentDescription = null,
                                            tint = Color.White,
                                            modifier = Modifier
                                                .align(Alignment.TopEnd)
                                                .padding(4.dp)
                                                .size(16.dp)
                                        )
                                    }
                                }
                            }
                            repeat(3 - rowPosts.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }

            "SAVED" -> {
                if (savedPosts.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(30.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "No saved bookmarks yet.", color = TextMuted, fontSize = 13.sp)
                        }
                    }
                } else {
                    items(savedPosts) { post ->
                        GlassCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                            shape = RoundedCornerShape(14.dp),
                            borderColor = GlassBorderLight
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(10.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                CreatorAvatar(avatarUrl = post.creatorAvatarUrl, size = 36.dp)
                                Spacer(modifier = Modifier.width(10.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(text = post.creatorDisplayName, color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                    Text(text = post.caption, color = TextSecondary, fontSize = 11.sp, maxLines = 1)
                                }
                            }
                        }
                    }
                }
            }

            "SECURITY" -> {
                item {
                    GlassCard(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                        shape = RoundedCornerShape(20.dp),
                        borderColor = GlassBorderLight
                    ) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column {
                                    Text(text = "Two-Factor Auth (2FA / TOTP)", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                                    Text(text = "Mandatory for Ghana Mobile Money withdrawals", color = TextSecondary, fontSize = 11.sp)
                                }
                                Switch(
                                    checked = currentUser?.is2FaEnabled ?: true,
                                    onCheckedChange = { onToggle2FA(it) },
                                    colors = SwitchDefaults.colors(
                                        checkedThumbColor = NeonMint,
                                        checkedTrackColor = Color(0xFF10B981).copy(alpha = 0.3f)
                                    )
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))
                            HorizontalDivider(thickness = 0.6.dp, color = GlassBorderLight)
                            Spacer(modifier = Modifier.height(14.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.QrCode, contentDescription = null, tint = NeonCyan, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = "Authenticator Secret Key", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                    Text(text = currentUser?.totpSecret ?: "VIRALX-8921-GH", color = MomoGold, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                }
                            }

                            Spacer(modifier = Modifier.height(12.dp))

                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Lock, contentDescription = null, tint = ElectricPurple, modifier = Modifier.size(18.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Column {
                                    Text(text = "Emergency Recovery Codes", color = TextPrimary, fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                                    Text(text = currentUser?.backupRecoveryCodes ?: "REC-9182-GH, REC-8821-GH", color = TextMuted, fontSize = 11.sp)
                                }
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            GlassButton(
                                text = "Sign Out from Viral X",
                                onClick = onLogout,
                                isPrimary = false,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }
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
fun TabPill(
    label: String,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    isSelected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(
                if (isSelected) PurpleCyanGradient
                else Brush.linearGradient(listOf(Color(0xFF1B162E), Color(0xFF1B162E)))
            )
            .border(1.dp, if (isSelected) NeonCyan else GlassBorderLight, RoundedCornerShape(14.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(icon, contentDescription = null, tint = if (isSelected) Color.White else TextMuted, modifier = Modifier.size(14.dp))
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                color = if (isSelected) Color.White else TextMuted,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun ProfileStat(title: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = value, color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
        Text(text = title, color = TextMuted, fontSize = 10.sp)
    }
}
