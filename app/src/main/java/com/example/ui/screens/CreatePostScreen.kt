package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Movie
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.ui.components.FooterComponent
import com.example.ui.components.GlassButton
import com.example.ui.components.GlassCard
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.GlassSurface
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonMint
import com.example.ui.theme.NeonPink
import com.example.ui.theme.ObsidianBg
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.PinkPurpleGradient
import com.example.ui.theme.PurpleCyanGradient
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun CreatePostScreen(
    currentUserId: String,
    onPostCreated: (type: String, mediaUrl: String, caption: String, hashtags: String, commentsEnabled: Boolean, visibility: String) -> Unit
) {
    val scope = rememberCoroutineScope()
    var postType by remember { mutableStateOf("VIDEO") } // "PHOTO" or "VIDEO"
    var selectedMediaAsset by remember { mutableStateOf("post_accra_fashion") }
    var caption by remember { mutableStateOf("") }
    var hashtags by remember { mutableStateOf("#ViralX #GhanaCreators #Accra") }
    var commentsEnabled by remember { mutableStateOf(true) }
    var visibility by remember { mutableStateOf("PUBLIC") }
    var isPublishing by remember { mutableStateOf(false) }
    var publishSuccess by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    val presetAssets = listOf(
        "post_accra_fashion" to "High Fashion Accra (Urban)",
        "post_accra_music" to "Dance & Music Festival (Vibrant)",
        "avatar_ghana_creator" to "Studio Portrait (Creative)"
    )

    val sampleHashtagList = listOf("#AccraFashion", "#GhanaMusic", "#ChaleWote", "#ViralX", "#GhanaTech", "#AfroDance")
    val context = LocalContext.current

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("create_post_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Header
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = "Create New Content ✨",
                    color = TextPrimary,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Upload authentic photo or video reels to earn rewards on Viral X",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(14.dp))

                // Post Type Selector
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF171329))
                        .border(1.dp, GlassBorderLight, RoundedCornerShape(20.dp))
                        .padding(4.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (postType == "VIDEO") PurpleCyanGradient else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
                            .clickable { postType = "VIDEO" }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.Videocam, contentDescription = null, tint = if (postType == "VIDEO") Color.White else TextMuted, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Video Post (Reels)",
                                color = if (postType == "VIDEO") Color.White else TextSecondary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(16.dp))
                            .background(if (postType == "PHOTO") PurpleCyanGradient else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
                            .clickable { postType = "PHOTO" }
                            .padding(vertical = 10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.PhotoCamera, contentDescription = null, tint = if (postType == "PHOTO") Color.White else TextMuted, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "Photo Post",
                                color = if (postType == "PHOTO") Color.White else TextSecondary,
                                fontSize = 13.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }

        // Media Asset Picker
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Text(
                    text = "Select Media Asset / Camera Upload",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    presetAssets.forEach { (assetName, label) ->
                        val isSelected = selectedMediaAsset == assetName
                        val imageRes = when {
                            assetName.contains("fashion") -> context.resources.getIdentifier("post_accra_fashion_1787123210091", "drawable", context.packageName)
                            assetName.contains("music") -> context.resources.getIdentifier("post_accra_music_1787123223856", "drawable", context.packageName)
                            else -> context.resources.getIdentifier("avatar_ghana_creator_1787123197792", "drawable", context.packageName)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .aspectRatio(1f)
                                .clip(RoundedCornerShape(16.dp))
                                .border(2.dp, if (isSelected) NeonCyan else GlassBorderLight, RoundedCornerShape(16.dp))
                                .clickable { selectedMediaAsset = assetName }
                        ) {
                            AsyncImage(
                                model = ImageRequest.Builder(context)
                                    .data(imageRes)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = label,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            if (isSelected) {
                                Box(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(6.dp)
                                        .size(22.dp)
                                        .clip(CircleShape)
                                        .background(NeonCyan),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(Icons.Default.Check, contentDescription = null, tint = Color.Black, modifier = Modifier.size(14.dp))
                                }
                            }
                        }
                    }
                }
            }
        }

        // Caption Field
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
                Text(
                    text = "Caption",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = caption,
                    onValueChange = { caption = it },
                    placeholder = { Text("What's on your mind? Share your story, dance, or style...", color = TextMuted, fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(110.dp)
                        .clip(RoundedCornerShape(18.dp))
                        .testTag("create_post_caption_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = ObsidianCard,
                        unfocusedContainerColor = ObsidianCard,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = GlassBorderLight,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    )
                )
            }
        }

        // Hashtags Field & Suggestions
        item {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 6.dp)) {
                Text(
                    text = "Hashtags",
                    color = TextPrimary,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = hashtags,
                    onValueChange = { hashtags = it },
                    placeholder = { Text("#ViralX #Ghana", color = TextMuted, fontSize = 14.sp) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .testTag("create_post_hashtags_input"),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = ObsidianCard,
                        unfocusedContainerColor = ObsidianCard,
                        focusedBorderColor = NeonCyan,
                        unfocusedBorderColor = GlassBorderLight,
                        focusedTextColor = TextPrimary,
                        unfocusedTextColor = TextPrimary
                    ),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                LazyRow(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    items(sampleHashtagList) { tag ->
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color(0xFF1E1933))
                                .clickable {
                                    if (!hashtags.contains(tag)) {
                                        hashtags = "$hashtags $tag".trim()
                                    }
                                }
                                .padding(horizontal = 10.dp, vertical = 6.dp)
                        ) {
                            Text(text = tag, color = NeonCyan, fontSize = 11.sp)
                        }
                    }
                }
            }
        }

        // Controls: Comments & Visibility
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                shape = RoundedCornerShape(18.dp),
                borderColor = GlassBorderLight
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Allow Comments", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = "Let viewers interact and boost your viral score", color = TextSecondary, fontSize = 11.sp)
                        }
                        Switch(
                            checked = commentsEnabled,
                            onCheckedChange = { commentsEnabled = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = NeonCyan
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(text = "Visibility", color = TextPrimary, fontSize = 14.sp, fontWeight = FontWeight.SemiBold)
                            Text(text = if (visibility == "PUBLIC") "Public to all Ghana & world" else "Followers only", color = TextSecondary, fontSize = 11.sp)
                        }
                        Row {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (visibility == "PUBLIC") NeonCyan else Color(0x33FFFFFF))
                                    .clickable { visibility = "PUBLIC" }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Public",
                                    color = if (visibility == "PUBLIC") Color.Black else TextSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(if (visibility == "FOLLOWERS") NeonCyan else Color(0x33FFFFFF))
                                    .clickable { visibility = "FOLLOWERS" }
                                    .padding(horizontal = 10.dp, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "Followers",
                                    color = if (visibility == "FOLLOWERS") Color.Black else TextSecondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }
        }

        // Publish Button & Status Feedback
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (errorMessage != null) {
                    Text(
                        text = errorMessage ?: "",
                        color = NeonPink,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                if (publishSuccess) {
                    Text(
                        text = "🎉 Post published successfully to Viral X!",
                        color = NeonMint,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                if (isPublishing) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                            .clip(RoundedCornerShape(24.dp))
                            .background(Color(0xFF221A3B)),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        CircularProgressIndicator(
                            color = NeonCyan,
                            modifier = Modifier.size(22.dp),
                            strokeWidth = 2.5.dp
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "Processing & Publishing to Viral X...",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                } else {
                    GlassButton(
                        text = "Publish to Viral X 🚀",
                        onClick = {
                            if (caption.isBlank()) {
                                errorMessage = "Please enter a caption for your post."
                                return@GlassButton
                            }
                            errorMessage = null
                            isPublishing = true
                            scope.launch {
                                delay(1000)
                                onPostCreated(
                                    postType,
                                    selectedMediaAsset,
                                    caption,
                                    hashtags,
                                    commentsEnabled,
                                    visibility
                                )
                                isPublishing = false
                                publishSuccess = true
                                caption = ""
                                delay(2000)
                                publishSuccess = false
                            }
                        },
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "publish_post_button"
                    )
                }
            }
        }

        // Footer
        item {
            FooterComponent()
        }
    }
}
