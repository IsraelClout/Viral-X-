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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PhoneAndroid
import androidx.compose.material.icons.filled.Security
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
import com.example.ui.theme.ObsidianCard
import com.example.ui.theme.PinkPurpleGradient
import com.example.ui.theme.PurpleCyanGradient
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextPrimary
import com.example.ui.theme.TextSecondary
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AuthScreen(
    onLoginSuccess: (email: String, name: String, phone: String) -> Unit,
    onQuickDemoLogin: () -> Unit
) {
    val scope = rememberCoroutineScope()
    var isRegisterMode by remember { mutableStateOf(false) }
    var email by remember { mutableStateOf("israel@viralx.gh") }
    var password by remember { mutableStateOf("viralx2026") }
    var displayName by remember { mutableStateOf("Israel Ewoenam Gokah") }
    var phone by remember { mutableStateOf("0244889900") }

    // 2FA / OTP Simulation
    var requiresOtp by remember { mutableStateOf(false) }
    var otpCode by remember { mutableStateOf("") }
    var otpCooldown by remember { mutableIntStateOf(0) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(otpCooldown) {
        if (otpCooldown > 0) {
            delay(1000)
            otpCooldown -= 1
        }
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ObsidianBg)
            .testTag("auth_screen"),
        contentPadding = PaddingValues(bottom = 60.dp)
    ) {
        // Logo & Hero
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(PinkPurpleGradient)
                        .padding(3.dp)
                ) {
                    CreatorAvatar(avatarUrl = "ic_viralx_logo", size = 66.dp)
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Viral X",
                    color = TextPrimary,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Black,
                    letterSpacing = 1.sp
                )
                Text(
                    text = "Ghana's Premier Creator & Rewards Platform",
                    color = NeonCyan,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        // AUTH FORM CARD
        item {
            GlassCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                shape = RoundedCornerShape(26.dp),
                borderColor = GlassBorder
            ) {
                Column(modifier = Modifier.padding(20.dp)) {
                    // Auth Mode Switch
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(16.dp))
                            .background(Color(0xFF171329))
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (!isRegisterMode) PurpleCyanGradient else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
                                .clickable {
                                    isRegisterMode = false
                                    requiresOtp = false
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Sign In", color = if (!isRegisterMode) Color.White else TextSecondary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(RoundedCornerShape(12.dp))
                                .background(if (isRegisterMode) PurpleCyanGradient else Brush.linearGradient(listOf(Color.Transparent, Color.Transparent)))
                                .clickable {
                                    isRegisterMode = true
                                    requiresOtp = false
                                }
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(text = "Create Account", color = if (isRegisterMode) Color.White else TextSecondary, fontSize = 13.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    if (!requiresOtp) {
                        if (isRegisterMode) {
                            OutlinedTextField(
                                value = displayName,
                                onValueChange = { displayName = it },
                                label = { Text("Full Name", fontSize = 12.sp) },
                                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, tint = NeonCyan) },
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NeonCyan,
                                    unfocusedBorderColor = GlassBorderLight,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                ),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(10.dp))

                            OutlinedTextField(
                                value = phone,
                                onValueChange = { phone = it },
                                label = { Text("Ghana MoMo Phone (02x / 05x)", fontSize = 12.sp) },
                                leadingIcon = { Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = MomoGold) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                                modifier = Modifier.fillMaxWidth(),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = MomoGold,
                                    unfocusedBorderColor = GlassBorderLight,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                ),
                                singleLine = true
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                        }

                        OutlinedTextField(
                            value = email,
                            onValueChange = { email = it },
                            label = { Text("Email Address", fontSize = 12.sp) },
                            leadingIcon = { Icon(Icons.Default.Email, contentDescription = null, tint = ElectricPurple) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_email_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = ElectricPurple,
                                unfocusedBorderColor = GlassBorderLight,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            singleLine = true
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        OutlinedTextField(
                            value = password,
                            onValueChange = { password = it },
                            label = { Text("Password", fontSize = 12.sp) },
                            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null, tint = NeonPink) },
                            visualTransformation = PasswordVisualTransformation(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("auth_password_input"),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = NeonPink,
                                unfocusedBorderColor = GlassBorderLight,
                                focusedTextColor = TextPrimary,
                                unfocusedTextColor = TextPrimary
                            ),
                            singleLine = true
                        )
                    } else {
                        // 2FA / TOTP Input
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(48.dp)
                                    .clip(CircleShape)
                                    .background(Color(0x3310B981)),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Default.Security, contentDescription = null, tint = NeonMint, modifier = Modifier.size(24.dp))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Two-Factor Verification",
                                color = TextPrimary,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = "Enter 6-digit TOTP code or SMS code sent to $phone",
                                color = TextSecondary,
                                fontSize = 12.sp,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(14.dp))

                            OutlinedTextField(
                                value = otpCode,
                                onValueChange = { if (it.length <= 6) otpCode = it },
                                label = { Text("6-Digit Code (e.g. 123456)", fontSize = 12.sp) },
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .testTag("auth_otp_input"),
                                colors = OutlinedTextFieldDefaults.colors(
                                    focusedBorderColor = NeonMint,
                                    unfocusedBorderColor = GlassBorderLight,
                                    focusedTextColor = TextPrimary,
                                    unfocusedTextColor = TextPrimary
                                ),
                                singleLine = true
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = if (otpCooldown > 0) "Resend SMS code in ${otpCooldown}s" else "Resend code",
                                color = if (otpCooldown > 0) TextMuted else NeonCyan,
                                fontSize = 11.sp,
                                modifier = Modifier.clickable(enabled = otpCooldown == 0) {
                                    otpCooldown = 60
                                }
                            )
                        }
                    }

                    if (errorMessage != null) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = errorMessage ?: "", color = NeonPink, fontSize = 12.sp)
                    }

                    Spacer(modifier = Modifier.height(18.dp))

                    if (isLoading) {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(color = NeonCyan, modifier = Modifier.size(28.dp))
                        }
                    } else {
                        GlassButton(
                            text = if (requiresOtp) "Verify & Access Studio 🔐" else if (isRegisterMode) "Register Account 🚀" else "Sign In to Viral X ✨",
                            onClick = {
                                if (!requiresOtp) {
                                    if (email.isBlank() || password.isBlank()) {
                                        errorMessage = "Please fill in all fields."
                                        return@GlassButton
                                    }
                                    errorMessage = null
                                    requiresOtp = true
                                    otpCooldown = 60
                                    otpCode = "774921" // auto-fill sample code for seamless flow
                                } else {
                                    if (otpCode.length < 4) {
                                        errorMessage = "Please enter valid verification code."
                                        return@GlassButton
                                    }
                                    isLoading = true
                                    scope.launch {
                                        delay(800)
                                        isLoading = false
                                        onLoginSuccess(email, displayName, phone)
                                    }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            testTag = "auth_submit_button"
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                    HorizontalDivider(thickness = 0.8.dp, color = GlassBorderLight)
                    Spacer(modifier = Modifier.height(14.dp))

                    // Quick Demo Creator Mode
                    GlassButton(
                        text = "Demo: Enter as Israel Ewoenam Gokah 🇬🇭",
                        onClick = onQuickDemoLogin,
                        isPrimary = false,
                        modifier = Modifier.fillMaxWidth(),
                        testTag = "auth_demo_button"
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
