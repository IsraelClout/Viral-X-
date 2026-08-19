package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ui.theme.ElectricPurple
import com.example.ui.theme.ElectricViolet
import com.example.ui.theme.GlassBorder
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.GlassSurface
import com.example.ui.theme.MomoGold
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.NeonMint
import com.example.ui.theme.NeonPink
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.ObsidianCardGlass
import com.example.ui.theme.PinkPurpleGradient
import com.example.ui.theme.PurpleCyanGradient
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(22.dp),
    backgroundColor: Color = ObsidianCardGlass,
    borderColor: Color = GlassBorder,
    borderWidth: Dp = 1.dp,
    onClick: (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Surface(
        modifier = modifier
            .clip(shape)
            .border(borderWidth, borderColor, shape)
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier),
        shape = shape,
        color = backgroundColor,
        tonalElevation = 6.dp
    ) {
        content()
    }
}

@Composable
fun GlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    enabled: Boolean = true,
    testTag: String = "glass_button"
) {
    val backgroundBrush = if (isPrimary) PurpleCyanGradient else Brush.linearGradient(listOf(GlassSurface, GlassSurface))
    val textColor = if (isPrimary) Color.White else TextPrimary

    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = modifier
            .testTag(testTag)
            .height(48.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(backgroundBrush)
            .border(1.dp, if (isPrimary) GlassBorderLight else GlassBorder, RoundedCornerShape(24.dp)),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            disabledContainerColor = Color(0x33475569)
        ),
        shape = RoundedCornerShape(24.dp)
    ) {
        Text(
            text = text,
            color = textColor,
            fontSize = 15.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}

@Composable
fun CreatorAvatar(
    avatarUrl: String,
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    hasStoryRing: Boolean = false,
    isLive: Boolean = false
) {
    val context = LocalContext.current
    val drawableRes = remember(avatarUrl) {
        val cleanName = avatarUrl.trim()
        when {
            cleanName.contains("avatar_ghana") -> context.resources.getIdentifier("avatar_ghana_creator_1787123197792", "drawable", context.packageName)
            cleanName.contains("fashion") -> context.resources.getIdentifier("post_accra_fashion_1787123210091", "drawable", context.packageName)
            cleanName.contains("music") -> context.resources.getIdentifier("post_accra_music_1787123223856", "drawable", context.packageName)
            cleanName.contains("logo") -> context.resources.getIdentifier("ic_viralx_logo_1787123186424", "drawable", context.packageName)
            else -> context.resources.getIdentifier("avatar_ghana_creator_1787123197792", "drawable", context.packageName)
        }
    }

    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        if (hasStoryRing) {
            Box(
                modifier = Modifier
                    .size(size)
                    .clip(CircleShape)
                    .background(PinkPurpleGradient)
                    .padding(2.5.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(size - 5.dp)
                        .clip(CircleShape)
                        .background(ObsidianCard)
                )
            }
        }

        val imageSize = if (hasStoryRing) size - 7.dp else size
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(if (drawableRes != 0) drawableRes else avatarUrl)
                .crossfade(true)
                .build(),
            contentDescription = "Creator Avatar",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .border(1.dp, GlassBorderLight, CircleShape)
        )

        if (isLive) {
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .background(NeonPink, RoundedCornerShape(4.dp))
                    .padding(horizontal = 4.dp, vertical = 1.dp)
            ) {
                Text(
                    text = "LIVE",
                    color = Color.White,
                    fontSize = 8.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
fun StoryBubble(
    name: String,
    avatarUrl: String,
    isUnseen: Boolean = true,
    onClick: () -> Unit
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable(onClick = onClick)
            .padding(horizontal = 6.dp)
    ) {
        CreatorAvatar(
            avatarUrl = avatarUrl,
            size = 64.dp,
            hasStoryRing = isUnseen
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = name,
            color = TextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.width(68.dp),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun StarRatingDialog(
    currentRating: Int,
    averageRating: Float,
    ratingsCount: Int,
    onDismiss: () -> Unit,
    onRate: (Int) -> Unit
) {
    var selectedStars by remember { mutableIntStateOf(if (currentRating > 0) currentRating else 5) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ObsidianCard,
        title = {
            Text(
                text = "Rate this Creator's Post ⭐",
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Authentic ratings directly boost the creator's Viral Score and GH₵ earnings.",
                    color = TextSecondary,
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..5).forEach { star ->
                        val isFilled = star <= selectedStars
                        IconButton(
                            onClick = { selectedStars = star },
                            modifier = Modifier.size(44.dp)
                        ) {
                            Icon(
                                imageVector = if (isFilled) Icons.Filled.Star else Icons.Outlined.Star,
                                contentDescription = "Star $star",
                                tint = if (isFilled) MomoGold else TextMuted,
                                modifier = Modifier.size(36.dp)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (selectedStars) {
                        5 -> "⭐⭐⭐⭐⭐ Outstanding / Viral Masterpiece"
                        4 -> "⭐⭐⭐⭐ Great Quality"
                        3 -> "⭐⭐⭐ Good Content"
                        2 -> "⭐⭐ Fair"
                        else -> "⭐ Needs Improvement"
                    },
                    color = MomoGold,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        },
        confirmButton = {
            GlassButton(
                text = "Submit Rating",
                onClick = {
                    onRate(selectedStars)
                    onDismiss()
                },
                modifier = Modifier.width(140.dp)
            )
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary)
            }
        }
    )
}

@Composable
fun ReportDialog(
    targetTitle: String,
    onDismiss: () -> Unit,
    onSubmitReport: (reason: String) -> Unit
) {
    val reasons = listOf(
        "Spam or fake engagement",
        "Inappropriate or adult content",
        "Harassment or hate speech",
        "Misleading / Fraudulent claim",
        "Intellectual property violation"
    )
    var selectedReason by remember { mutableStateOf(reasons.first()) }

    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = ObsidianCard,
        title = {
            Text(
                text = "Report Content",
                color = TextPrimary,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        },
        text = {
            Column {
                Text(
                    text = "Help maintain Ghana's authentic creator ecosystem by reporting policy violations:",
                    color = TextSecondary,
                    fontSize = 13.sp
                )
                Spacer(modifier = Modifier.height(12.dp))
                reasons.forEach { reason ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { selectedReason = reason }
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(18.dp)
                                .clip(CircleShape)
                                .border(2.dp, if (selectedReason == reason) NeonCyan else TextMuted, CircleShape)
                                .background(if (selectedReason == reason) NeonCyan else Color.Transparent)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = reason,
                            color = if (selectedReason == reason) TextPrimary else TextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSubmitReport(selectedReason)
                    onDismiss()
                },
                colors = ButtonDefaults.buttonColors(containerColor = NeonPink)
            ) {
                Text("Submit Report", color = Color.White)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel", color = TextSecondary)
            }
        }
    )
}
