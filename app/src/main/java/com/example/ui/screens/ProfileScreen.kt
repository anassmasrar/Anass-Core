package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PostEntity
import com.example.data.model.UserAccount
import com.example.ui.components.PostCard
import com.example.ui.components.VerificationBadgesRow
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.BadgeUltraGradientEnd
import com.example.ui.theme.BadgeUltraGradientStart
import com.example.ui.theme.UltraGoldAccent
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XCardSurface
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    user: UserAccount,
    userPosts: List<PostEntity>,
    onLikePost: (PostEntity) -> Unit,
    onRetweetPost: (PostEntity) -> Unit,
    onBookmarkPost: (PostEntity) -> Unit,
    onReplyPost: (PostEntity) -> Unit,
    onSharePost: (PostEntity) -> Unit,
    onNavigateToSubscriptions: () -> Unit,
    onBack: () -> Unit
) {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Posts", "Réponses", "Médias", "J'aime")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = user.displayName,
                                color = XTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            VerificationBadgesRow(
                                isVerifiedBlue = user.isVerifiedBlue,
                                isVerifiedGold = user.isVerifiedGold,
                                isVerifiedGrey = user.isVerifiedGrey,
                                isVerifiedUltra = user.isVerifiedUltra,
                                isCreator = user.isCreator,
                                iconSize = 14
                            )
                        }
                        Text(
                            text = "${userPosts.size} posts · ${user.formatFollowers()} abonnés",
                            color = XTextSecondary,
                            fontSize = 11.sp
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "Retour",
                            tint = XTextPrimary
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.Filled.MoreVert,
                            contentDescription = "Options",
                            tint = XTextPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = XBlack)
            )
        },
        containerColor = XBlack
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Profile Header & Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .background(
                            if (user.isCreator) {
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF1E1B4B), Color(0xFF4C1D95), Color(0xFF0F172A))
                                )
                            } else {
                                Brush.horizontalGradient(
                                    listOf(Color(0xFF1F2937), Color(0xFF111827))
                                )
                            }
                        )
                ) {
                    if (user.isCreator) {
                        Box(
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(12.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .background(Color.Black.copy(alpha = 0.5f))
                                .border(1.dp, UltraGoldAccent, RoundedCornerShape(12.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "FONDATEUR OFFICIEL",
                                color = UltraGoldAccent,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    }
                }
            }

            // Avatar & Action Button row
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    // Avatar overlapping banner
                    val avatarBorder = if (user.isCreator || user.isVerifiedUltra) {
                        Modifier.border(
                            3.dp,
                            Brush.sweepGradient(
                                listOf(BadgeUltraGradientStart, AnassBotCyan, UltraGoldAccent, BadgeUltraGradientEnd)
                            ),
                            CircleShape
                        )
                    } else {
                        Modifier.border(3.dp, XBlack, CircleShape)
                    }

                    Box(
                        modifier = Modifier
                            .offset(y = (-40).dp)
                            .size(80.dp)
                            .then(avatarBorder)
                            .clip(CircleShape)
                            .background(if (user.isCreator) Color(0xFF1E1B4B) else Color(0xFF1E293B)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = user.avatarInitial,
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    // Action Button (Modifier or Gérer Ultra)
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(top = 10.dp)
                    ) {
                        if (user.isCreator) {
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(20.dp))
                                    .background(
                                        Brush.horizontalGradient(
                                            listOf(BadgeUltraGradientStart, BadgeUltraGradientEnd)
                                        )
                                    )
                                    .clickable { onNavigateToSubscriptions() }
                                    .padding(horizontal = 14.dp, vertical = 7.dp)
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = Icons.Filled.AutoAwesome,
                                        contentDescription = null,
                                        tint = Color.White,
                                        modifier = Modifier.size(14.dp)
                                    )
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text(
                                        text = "Ultra Illimité Gratuit",
                                        color = Color.White,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 12.sp
                                    )
                                }
                            }
                        } else {
                            OutlinedButton(
                                onClick = onNavigateToSubscriptions,
                                shape = RoundedCornerShape(20.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                            ) {
                                Text(
                                    text = "Passer à Ultra (6.50$)",
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }

            // User Info
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = user.displayName,
                            color = XTextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = 22.sp
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        VerificationBadgesRow(
                            isVerifiedBlue = user.isVerifiedBlue,
                            isVerifiedGold = user.isVerifiedGold,
                            isVerifiedGrey = user.isVerifiedGrey,
                            isVerifiedUltra = user.isVerifiedUltra,
                            isCreator = user.isCreator,
                            iconSize = 18
                        )
                    }

                    Text(
                        text = user.handle,
                        color = XTextSecondary,
                        fontSize = 14.sp
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = user.bio,
                        color = XTextPrimary,
                        fontSize = 14.sp,
                        lineHeight = 20.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Location, Website, Join date
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = null,
                            tint = XTextSecondary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = user.location,
                            color = XTextSecondary,
                            fontSize = 13.sp
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Icon(
                            imageVector = Icons.Filled.CalendarMonth,
                            contentDescription = null,
                            tint = XTextSecondary,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = user.joinedDate,
                            color = XTextSecondary,
                            fontSize = 13.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Follower Counts (12 Milliards de followers pour Anass !)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = user.formatFollowing(),
                            color = XTextPrimary,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "abonnements",
                            color = XTextSecondary,
                            fontSize = 14.sp
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Text(
                            text = if (user.isCreator) "12 000 000 000" else user.formatFollowers(),
                            color = if (user.isCreator) Color(0xFFFFD700) else XTextPrimary,
                            fontWeight = FontWeight.Black,
                            fontSize = if (user.isCreator) 16.sp else 15.sp
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = if (user.isCreator) "abonnés (12 Milliards) 🔥" else "abonnés",
                            color = if (user.isCreator) Color(0xFFFFD700) else XTextSecondary,
                            fontWeight = if (user.isCreator) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Tab bar (Posts, Réponses, Médias, J'aime)
            item {
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    containerColor = XBlack,
                    contentColor = XBlue,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                            color = XBlue
                        )
                    },
                    divider = { HorizontalDivider(color = XDivider, thickness = 0.5.dp) }
                ) {
                    tabs.forEachIndexed { index, title ->
                        Tab(
                            selected = selectedTabIndex == index,
                            onClick = { selectedTabIndex = index },
                            text = {
                                Text(
                                    text = title,
                                    color = if (selectedTabIndex == index) XTextPrimary else XTextSecondary,
                                    fontWeight = if (selectedTabIndex == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        )
                    }
                }
            }

            // User posts or empty state
            if (userPosts.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Aucun post pour le moment.",
                            color = XTextSecondary,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(userPosts, key = { it.id }) { post ->
                    PostCard(
                        post = post,
                        onLikeClick = { onLikePost(post) },
                        onRetweetClick = { onRetweetPost(post) },
                        onBookmarkClick = { onBookmarkPost(post) },
                        onReplyClick = { onReplyPost(post) },
                        onShareClick = { onSharePost(post) },
                        onAuthorClick = { }
                    )
                }
            }
        }
    }
}
