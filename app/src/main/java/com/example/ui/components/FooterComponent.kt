package com.example.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.EditorialBlue
import com.example.ui.theme.GlassBorderLight
import com.example.ui.theme.NeonCyan
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary

@Composable
fun FooterComponent(
    modifier: Modifier = Modifier
) {
    var showTermsModal by remember { mutableStateOf(false) }
    var showSupportModal by remember { mutableStateOf(false) }
    var showPrivacyModal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .testTag("footer_component")
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            thickness = 0.8.dp,
            color = GlassBorderLight,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Terms of Service",
                color = EditorialBlue,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable { showTermsModal = true }
                    .padding(horizontal = 6.dp, vertical = 4.dp)
                    .testTag("footer_terms_link")
            )
            Text(text = "•", color = TextMuted, fontSize = 11.sp)
            Text(
                text = "Privacy Policy",
                color = EditorialBlue,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable { showPrivacyModal = true }
                    .padding(horizontal = 6.dp, vertical = 4.dp)
                    .testTag("footer_privacy_link")
            )
            Text(text = "•", color = TextMuted, fontSize = 11.sp)
            Text(
                text = "Contact Support",
                color = EditorialBlue,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier
                    .clickable { showSupportModal = true }
                    .padding(horizontal = 6.dp, vertical = 4.dp)
                    .testTag("footer_support_link")
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "© 2026 by Gokah Israel Ewoenam • Ghana Creator Hub",
            color = TextMuted,
            fontSize = 10.sp,
            fontWeight = FontWeight.Medium,
            letterSpacing = 0.5.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier.testTag("footer_copyright_text")
        )
    }

    if (showTermsModal) {
        AlertDialog(
            onDismissRequest = { showTermsModal = false },
            containerColor = ObsidianCard,
            title = {
                Text("Terms of Service", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "1. Introduction to Viral X\n" +
                                "Viral X is a creator platform built for digital creators in Ghana and worldwide. Users can create, share, and monetize authentic engagement.\n\n" +
                                "2. Creator Rewards Policy\n" +
                                "GH₵1.00 serves as the base engagement unit. Payouts are calculated via our server-side Viral Score engine based on authentic watch time, ratings, and genuine viewer retention. Bot farming or self-engagement will result in account forfeiture.\n\n" +
                                "3. Ghana Mobile Money (MoMo)\n" +
                                "Withdrawals are subject to provider confirmation and standard network fees (1%). Minimum withdrawal is GH₵10.00.\n\n" +
                                "© 2026 by Gokah Israel Ewoenam. All rights reserved.",
                        color = TextSecondary,
                        fontSize = 13.sp,
                        lineHeight = 18.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showTermsModal = false }) {
                    Text("Close", color = NeonCyan)
                }
            }
        )
    }

    if (showSupportModal) {
        AlertDialog(
            onDismissRequest = { showSupportModal = false },
            containerColor = ObsidianCard,
            title = {
                Text("Contact Support & Creator Help", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column {
                    Text(
                        text = "Need help with your creator account, Viral Score, or Mobile Money withdrawal?\n",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                    Text(
                        text = "📧 Email: support@viralx.gh\n" +
                                "🇬🇭 Ghana Hotline: +233 24 488 9900\n" +
                                "💬 In-App Support: Available 24/7 in Creator Studio\n" +
                                "📍 Accra Innovation Hub, Greater Accra, Ghana\n\n" +
                                "Lead Architect: Gokah Israel Ewoenam",
                        color = TextPrimary,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 20.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showSupportModal = false }) {
                    Text("Got It", color = NeonCyan)
                }
            }
        )
    }

    if (showPrivacyModal) {
        AlertDialog(
            onDismissRequest = { showPrivacyModal = false },
            containerColor = ObsidianCard,
            title = {
                Text("Privacy & Data Protection", color = TextPrimary, fontWeight = FontWeight.Bold)
            },
            text = {
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    Text(
                        text = "At Viral X, we respect user privacy. We strictly secure personal information, phone numbers used for Ghana Mobile Money payouts, and 2FA authentication tokens with modern encryption standards. We never sell personal data.\n\n" +
                                "© 2026 by Gokah Israel Ewoenam",
                        color = TextSecondary,
                        fontSize = 13.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyModal = false }) {
                    Text("Accept", color = NeonCyan)
                }
            }
        )
    }
}
