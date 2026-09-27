package com.example.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.ChatBubbleOutline
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PostEntity
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.BadgeUltraGradientEnd
import com.example.ui.theme.BadgeUltraGradientStart
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XDivider
import com.example.ui.theme.XLike
import com.example.ui.theme.XRetweet
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

fun formatMetric(count: Long): String {
    return when {
        count >= 1_000_000_000L -> {
            val billions = count.toDouble() / 1_000_000_000.0
            if (billions % 1.0 == 0.0) "${billions.toLong()} Md" else String.format("%.1f Md", billions)
        }
        count >= 1_000_000L -> {
            val millions = count.toDouble() / 1_000_000.0
            if (millions % 1.0 == 0.0) "${millions.toLong()} M" else String.format("%.1f M", millions)
        }
        count >= 1_000L -> {
            val thousands = count.toDouble() / 1_000.0
            if (thousands % 1.0 == 0.0) "${thousands.toLong()} k" else String.format("%.1f k", thousands)
        }
        count > 0 -> count.toString()
        else -> ""
    }
}

@Composable
fun PostCard(
    post: PostEntity,
    onLikeClick: () -> Unit,
    onRetweetClick: () -> Unit,
    onBookmarkClick: () -> Unit,
    onReplyClick: () -> Unit,
    onShareClick: () -> Unit,
    onAuthorClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(XBlack)
            .testTag("post_card_${post.id}")
    ) {
        if (post.isPinned) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 36.dp, top = 8.dp, bottom = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.PushPin,
                    contentDescription = "Post épinglé",
                    tint = XTextSecondary,
                    modifier = Modifier.size(12.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Post épinglé par le Créateur",
                    fontSize = 11.sp,
                    color = XTextSecondary,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp)
        ) {
            // Avatar
            val avatarBorderModifier = if (post.isVerifiedUltra || post.isCreator) {
                Modifier.border(
                    2.dp,
                    Brush.sweepGradient(listOf(BadgeUltraGradientStart, AnassBotCyan, BadgeUltraGradientEnd, BadgeUltraGradientStart)),
                    CircleShape
                )
            } else {
                Modifier
            }

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .then(avatarBorderModifier)
                    .clip(CircleShape)
                    .background(
                        if (post.isCreator) Color(0xFF1E1B4B)
                        else if (post.authorHandle == "@Anass_Bot") Color(0xFF064E3B)
                        else Color(0xFF1E293B)
                    )
                    .clickable { onAuthorClick(post.authorHandle) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = post.authorAvatarInitial,
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = if (post.isCreator) 18.sp else 16.sp
                )
            }

            Spacer(modifier = Modifier.width(12.dp))

            // Post content & info
            Column(modifier = Modifier.weight(1f)) {
                // Header (Name, badges, handle, time)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = post.authorName,
                        fontWeight = FontWeight.Bold,
                        color = XTextPrimary,
                        fontSize = 15.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.clickable { onAuthorClick(post.authorHandle) }
                    )

                    Spacer(modifier = Modifier.width(3.dp))

                    VerificationBadgesRow(
                        isVerifiedBlue = post.isVerifiedBlue,
                        isVerifiedGold = post.isVerifiedGold,
                        isVerifiedGrey = post.isVerifiedGrey,
                        isVerifiedUltra = post.isVerifiedUltra,
                        isCreator = post.isCreator,
                        iconSize = 14
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    Text(
                        text = post.authorHandle,
                        color = XTextSecondary,
                        fontSize = 13.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Text(
                        text = " · ${post.timestamp}",
                        color = XTextSecondary,
                        fontSize = 13.sp
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Icon(
                        imageVector = Icons.Filled.MoreHoriz,
                        contentDescription = "Options",
                        tint = XTextSecondary,
                        modifier = Modifier.size(16.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                // Post body with formatted hashtags and mentions
                val annotatedContent = buildAnnotatedString {
                    val words = post.content.split(" ", "\n")
                    var fullText = post.content
                    val tokens = post.content.split(Regex("(?<=\\s)|(?=\\s)"))
                    for (token in tokens) {
                        if (token.startsWith("#") || token.startsWith("@")) {
                            pushStyle(SpanStyle(color = XBlue, fontWeight = FontWeight.SemiBold))
                            append(token)
                            pop()
                        } else {
                            append(token)
                        }
                    }
                }

                Text(
                    text = annotatedContent,
                    color = XTextPrimary,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )

                // Optional Rich media banners
                if (post.mediaType == "IMAGE_ANASS_CORE") {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(160.dp)
                            .clip(RoundedCornerShape(14.dp))
                            .background(
                                Brush.linearGradient(
                                    listOf(Color(0xFF0F172A), Color(0xFF1E1B4B), Color(0xFF000000))
                                )
                            )
                            .border(1.dp, Color(0xFF312E81), RoundedCornerShape(14.dp))
                            .padding(16.dp)
                    ) {
                        Column(
                            verticalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = "𝔸",
                                        color = Color.White,
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Black
                                    )
                                    Spacer(modifier = Modifier.width(8.dp))
                                    Text(
                                        text = "ANASS CORE",
                                        color = Color.White,
                                        fontWeight = FontWeight.ExtraBold,
                                        letterSpacing = 2.sp,
                                        fontSize = 14.sp
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(12.dp))
                                        .background(
                                            Brush.horizontalGradient(
                                                listOf(BadgeUltraGradientStart, BadgeUltraGradientEnd)
                                            )
                                        )
                                        .padding(horizontal = 8.dp, vertical = 3.dp)
                                ) {
                                    Text(
                                        text = "VERSION ULTRA",
                                        color = Color.White,
                                        fontSize = 10.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                }
                            }

                            Column {
                                Text(
                                    text = "12 000 000 000 D'ABONNÉS",
                                    color = Color(0xFFFFD700),
                                    fontSize = 18.sp,
                                    fontWeight = FontWeight.Black
                                )
                                Text(
                                    text = "Le Réseau Social Mondial & Anass Bot Intégré",
                                    color = Color.LightGray,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                } else if (post.mediaType == "ANASS_BOT") {
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(14.dp))
                            .background(Color(0xFF03221C))
                            .border(1.dp, AnassBotCyan.copy(alpha = 0.5f), RoundedCornerShape(14.dp))
                            .padding(12.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(AnassBotCyan),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(text = "⚡", fontSize = 18.sp)
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Column {
                                Text(
                                    text = "Anass Bot IA 2026",
                                    color = Color.White,
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "Remplaçant officiel de Grok · Réponses witties et sans filtre",
                                    color = AnassBotCyan,
                                    fontSize = 11.sp
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Action metrics row (Reply, Retweet, Like, Views, Bookmark/Share)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Reply
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onReplyClick() }
                            .padding(vertical = 4.dp)
                            .testTag("reply_button_${post.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ChatBubbleOutline,
                            contentDescription = "Répondre",
                            tint = XTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        if (post.repliesCount > 0) {
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = formatMetric(post.repliesCount),
                                color = XTextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Retweet
                    val retweetColor by animateColorAsState(
                        targetValue = if (post.isRetweeted) XRetweet else XTextSecondary,
                        animationSpec = spring(),
                        label = "retweet_color"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onRetweetClick() }
                            .padding(vertical = 4.dp)
                            .testTag("retweet_button_${post.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Repeat,
                            contentDescription = "Republier",
                            tint = retweetColor,
                            modifier = Modifier.size(17.dp)
                        )
                        if (post.retweetsCount > 0) {
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = formatMetric(post.retweetsCount),
                                color = retweetColor,
                                fontSize = 12.sp,
                                fontWeight = if (post.isRetweeted) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    // Like
                    val likeColor by animateColorAsState(
                        targetValue = if (post.isLiked) XLike else XTextSecondary,
                        animationSpec = spring(),
                        label = "like_color"
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clickable { onLikeClick() }
                            .padding(vertical = 4.dp)
                            .testTag("like_button_${post.id}")
                    ) {
                        Icon(
                            imageVector = if (post.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "J'aime",
                            tint = likeColor,
                            modifier = Modifier.size(16.dp)
                        )
                        if (post.likesCount > 0) {
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = formatMetric(post.likesCount),
                                color = likeColor,
                                fontSize = 12.sp,
                                fontWeight = if (post.isLiked) FontWeight.Bold else FontWeight.Normal
                            )
                        }
                    }

                    // Views
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(vertical = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.BarChart,
                            contentDescription = "Vues",
                            tint = XTextSecondary,
                            modifier = Modifier.size(16.dp)
                        )
                        if (post.viewsCount > 0) {
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = formatMetric(post.viewsCount),
                                color = XTextSecondary,
                                fontSize = 12.sp
                            )
                        }
                    }

                    // Bookmark & Share
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (post.isBookmarked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder,
                            contentDescription = "Signet",
                            tint = if (post.isBookmarked) XBlue else XTextSecondary,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { onBookmarkClick() }
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = "Partager",
                            tint = XTextSecondary,
                            modifier = Modifier
                                .size(16.dp)
                                .clickable { onShareClick() }
                        )
                    }
                }
            }
        }

        HorizontalDivider(color = XDivider, thickness = 0.6.dp)
    }
}
