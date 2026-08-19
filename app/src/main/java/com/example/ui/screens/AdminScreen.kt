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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.entities.ReportEntity
import com.example.ui.components.FooterComponent
import com.example.ui.components.GlassButton
import com.example.ui.components.GlassCard
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
fun AdminScreen(
    reports: List<ReportEntity>,
    onBack: () -> Unit,
    onModerateReport: (reportId: String, resolution: String) -> Unit
) {
    var baseRewardUnitGhc by remember { mutableFloatStateOf(1.00f) }
    var globalMultiplier by remember { mutableFloatStateOf(1.5f) }
    var fraudStrictness by remember { mutableStateOf("HIGH") }
    var saveMessage by remember { mutableStateOf<String?>(null) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("admin_screen"),
        contentPadding = PaddingValues(bottom = 90.dp)
    ) {
        // Header
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = TextPrimary)
                }
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                    Text(
                        text = "Admin Command Center 🛡️",
                        color = TextPrimary,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Ghana Creator Platform Governance & Fraud Rules",
                        color = TextSecondary,
                        fontSize = 12.sp
                    )
                }
            }
        }

        // PLATFORM REWARD SETTINGS
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 6.dp, bottom = 6.dp),
                shape = RoundedCornerShape(20.dp),
                borderColor = GlassBorder
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Settings, contentDescription = null, tint = MomoGold, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "Creator Reward Engine Settings", color = TextPrimary, fontSize = 15.sp, fontWeight = FontWeight.Bold)
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Base Engagement Unit Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Base Engagement Reward Unit:", color = TextSecondary, fontSize = 13.sp)
                        Text(
                            text = "GH₵ ${String.format("%.2f", baseRewardUnitGhc)}",
                            color = MomoGold,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Slider(
                        value = baseRewardUnitGhc,
                        onValueChange = { baseRewardUnitGhc = it },
                        valueRange = 0.50f..5.00f,
                        steps = 8,
                        colors = SliderDefaults.colors(thumbColor = MomoGold, activeTrackColor = MomoGold),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Global Multiplier Slider
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(text = "Viral Score Multiplier:", color = TextSecondary, fontSize = 13.sp)
                        Text(
                            text = "${String.format("%.1f", globalMultiplier)}x",
                            color = NeonCyan,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Slider(
                        value = globalMultiplier,
                        onValueChange = { globalMultiplier = it },
                        valueRange = 1.0f..3.0f,
                        colors = SliderDefaults.colors(thumbColor = NeonCyan, activeTrackColor = NeonCyan),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Fraud Strictness
                    Text(text = "Anti-Fraud Strictness Level:", color = TextSecondary, fontSize = 13.sp)
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf("STANDARD", "HIGH", "MAXIMUM").forEach { level ->
                            val isSel = fraudStrictness == level
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(if (isSel) NeonPink else Color(0xFF1E1736))
                                    .border(1.dp, if (isSel) NeonPink else GlassBorderLight, RoundedCornerShape(10.dp))
                                    .clickable { fraudStrictness = level }
                                    .padding(vertical = 8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = level,
                                    color = if (isSel) Color.White else TextMuted,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    GlassButton(
                        text = "Save Platform Parameters",
                        onClick = {
                            saveMessage = "Platform settings updated! Base unit set to GH₵${String.format("%.2f", baseRewardUnitGhc)}"
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (saveMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = saveMessage ?: "", color = NeonMint, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                    }
                }
            }
        }

        // MODERATION & REPORTS QUEUE
        item {
            Column(modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 6.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.ReportProblem, contentDescription = null, tint = NeonPink, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Moderation Queue (${reports.count { it.status == "PENDING" }} Pending)",
                        color = TextPrimary,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        if (reports.isEmpty()) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No active user reports. Platform is clean! ✨", color = TextMuted, fontSize = 13.sp)
                }
            }
        }

        items(reports, key = { it.id }) { report ->
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 4.dp, bottom = 4.dp),
                shape = RoundedCornerShape(16.dp),
                borderColor = if (report.status == "PENDING") NeonPink.copy(alpha = 0.5f) else GlassBorderLight
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${report.targetType}: ${report.targetTitle}",
                            color = TextPrimary,
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(if (report.status == "PENDING") Color(0x33F43F5E) else Color(0x3310B981))
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = report.status,
                                color = if (report.status == "PENDING") NeonPink else NeonMint,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = "Reason: ${report.reason}", color = Color(0xFFFCA5A5), fontSize = 12.sp)

                    if (report.status == "PENDING") {
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            Button(
                                onClick = { onModerateReport(report.id, "DISMISSED") },
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0x33FFFFFF)),
                                modifier = Modifier.height(34.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp)
                            ) {
                                Text("Dismiss", color = TextSecondary, fontSize = 11.sp)
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = { onModerateReport(report.id, "CONTENT_HIDDEN") },
                                colors = ButtonDefaults.buttonColors(containerColor = NeonPink),
                                modifier = Modifier.height(34.dp),
                                contentPadding = PaddingValues(horizontal = 12.dp)
                            ) {
                                Text("Hide & Warn", color = Color.White, fontSize = 11.sp)
                            }
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
