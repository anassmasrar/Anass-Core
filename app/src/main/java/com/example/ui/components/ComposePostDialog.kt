package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.SentimentSatisfiedAlt
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.data.model.SubscriptionTier
import com.example.data.model.UserAccount
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XCardSurface
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

@Composable
fun ComposePostDialog(
    currentUser: UserAccount,
    onDismiss: () -> Unit,
    onPostCreated: (content: String, mediaType: String?) -> Unit
) {
    var content by remember { mutableStateOf("") }
    var selectedMedia by remember { mutableStateOf<String?>(null) }
    val maxChars = if (currentUser.tier == SubscriptionTier.ULTRA || currentUser.isCreator) 10_000 else 280
    val isOverLimit = content.length > maxChars
    val progress = (content.length.toFloat() / maxChars).coerceIn(0f, 1f)

    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false)
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .background(XBlack),
            color = XBlack
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                // Top Bar: Cancel button, Drafts, Post button
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = onDismiss,
                        modifier = Modifier.testTag("close_composer_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = "Fermer",
                            tint = XTextPrimary
                        )
                    }

                    Button(
                        onClick = {
                            if (content.isNotBlank() && !isOverLimit) {
                                onPostCreated(content.trim(), selectedMedia)
                                onDismiss()
                            }
                        },
                        enabled = content.isNotBlank() && !isOverLimit,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = XBlue,
                            disabledContainerColor = XBlue.copy(alpha = 0.4f),
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(20.dp),
                        modifier = Modifier.testTag("publish_post_button")
                    ) {
                        Text(
                            text = "Poster",
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // User Info & Audience
                Row(
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (currentUser.isCreator) Color(0xFF1E1B4B) else Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = currentUser.avatarInitial,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.width(12.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = currentUser.displayName,
                                fontWeight = FontWeight.Bold,
                                color = XTextPrimary,
                                fontSize = 15.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            VerificationBadgesRow(
                                isVerifiedBlue = currentUser.isVerifiedBlue,
                                isVerifiedGold = currentUser.isVerifiedGold,
                                isVerifiedGrey = currentUser.isVerifiedGrey,
                                isVerifiedUltra = currentUser.isVerifiedUltra,
                                isCreator = currentUser.isCreator
                            )
                        }

                        // Audience pill
                        Box(
                            modifier = Modifier
                                .padding(top = 4.dp)
                                .clip(RoundedCornerShape(14.dp))
                                .background(XCardSurface)
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    imageVector = Icons.Filled.Public,
                                    contentDescription = null,
                                    tint = XBlue,
                                    modifier = Modifier.size(12.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "Tout le monde peut répondre",
                                    color = XBlue,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Text Area
                        BasicTextField(
                            value = content,
                            onValueChange = { content = it },
                            textStyle = TextStyle(
                                color = XTextPrimary,
                                fontSize = 18.sp,
                                lineHeight = 24.sp
                            ),
                            cursorBrush = SolidColor(XBlue),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(220.dp)
                                .testTag("compose_text_input"),
                            decorationBox = { innerTextField ->
                                if (content.isEmpty()) {
                                    Text(
                                        text = "Quoi de neuf sur Anass Core ?",
                                        color = XTextSecondary,
                                        fontSize = 18.sp
                                    )
                                }
                                innerTextField()
                            }
                        )

                        // Optional attached media preview
                        if (selectedMedia != null) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF0F172A))
                                    .padding(8.dp)
                            ) {
                                Text(
                                    text = "📎 Média attaché : Bannière Officielle Anass Core",
                                    color = Color.White,
                                    fontSize = 12.sp,
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                                IconButton(
                                    onClick = { selectedMedia = null },
                                    modifier = Modifier.align(Alignment.TopEnd)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "Supprimer",
                                        tint = Color.White
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(12.dp))
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                // Bottom toolbar (icons: media, poll, emoji, location, character count ring)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        IconButton(onClick = { selectedMedia = "IMAGE_ANASS_CORE" }) {
                            Icon(
                                imageVector = Icons.Filled.Image,
                                contentDescription = "Ajouter image",
                                tint = if (selectedMedia != null) XBlue else XBlue.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.FormatListBulleted,
                                contentDescription = "Sondage",
                                tint = XBlue.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.SentimentSatisfiedAlt,
                                contentDescription = "Emoji",
                                tint = XBlue.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        IconButton(onClick = { }) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = "Localisation",
                                tint = XBlue.copy(alpha = 0.8f),
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (currentUser.tier == SubscriptionTier.ULTRA || currentUser.isCreator) {
                            Text(
                                text = "Ultra : Illimité",
                                color = Color(0xFFFFD700),
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        } else {
                            Text(
                                text = "${maxChars - content.length}",
                                color = if (isOverLimit) Color.Red else XTextSecondary,
                                fontSize = 12.sp,
                                modifier = Modifier.padding(end = 8.dp)
                            )
                        }

                        CircularProgressIndicator(
                            progress = { progress },
                            modifier = Modifier.size(20.dp),
                            color = if (isOverLimit) Color.Red else XBlue,
                            trackColor = XDivider,
                            strokeWidth = 2.5.dp
                        )
                    }
                }
            }
        }
    }
}
