package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.PostEntity
import com.example.data.model.UserAccount
import com.example.ui.components.PostCard
import com.example.ui.theme.UltraGoldAccent
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    currentUser: UserAccount,
    posts: List<PostEntity>,
    onOpenDrawer: () -> Unit,
    onNavigateToSubscriptions: () -> Unit,
    onOpenComposer: () -> Unit,
    onLikePost: (PostEntity) -> Unit,
    onRetweetPost: (PostEntity) -> Unit,
    onBookmarkPost: (PostEntity) -> Unit,
    onReplyPost: (PostEntity) -> Unit,
    onSharePost: (PostEntity) -> Unit,
    onAuthorClick: (String) -> Unit
) {
    var selectedFeedTab by remember { mutableIntStateOf(0) } // 0: Pour vous, 1: Abonnements

    val displayedPosts = if (selectedFeedTab == 0) {
        posts
    } else {
        posts.filter { it.isCreator || it.tagCategory == "FOLLOWING" || it.authorHandle == currentUser.handle }
    }

    Scaffold(
        topBar = {
            Column(modifier = Modifier.background(XBlack)) {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "𝔸",
                                color = Color.White,
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Black
                            )
                        }
                    },
                    navigationIcon = {
                        Box(
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(34.dp)
                                .clip(CircleShape)
                                .background(if (currentUser.isCreator) Color(0xFF1E1B4B) else Color(0xFF1E293B))
                                .clickable { onOpenDrawer() }
                                .testTag("open_drawer_avatar"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = currentUser.avatarInitial,
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            onClick = onNavigateToSubscriptions,
                            modifier = Modifier.testTag("upgrade_button_top")
                        ) {
                            Icon(
                                imageVector = Icons.Filled.AutoAwesome,
                                contentDescription = "Abonnements",
                                tint = UltraGoldAccent
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = XBlack)
                )

                // Feeds TabRow: "Pour vous" & "Abonnements"
                TabRow(
                    selectedTabIndex = selectedFeedTab,
                    containerColor = XBlack,
                    contentColor = XBlue,
                    indicator = { tabPositions ->
                        TabRowDefaults.SecondaryIndicator(
                            Modifier.tabIndicatorOffset(tabPositions[selectedFeedTab]),
                            color = XBlue
                        )
                    },
                    divider = { HorizontalDivider(color = XDivider, thickness = 0.5.dp) }
                ) {
                    Tab(
                        selected = selectedFeedTab == 0,
                        onClick = { selectedFeedTab = 0 },
                        text = {
                            Text(
                                text = "Pour vous",
                                color = if (selectedFeedTab == 0) XTextPrimary else XTextSecondary,
                                fontWeight = if (selectedFeedTab == 0) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        },
                        modifier = Modifier.testTag("tab_for_you")
                    )
                    Tab(
                        selected = selectedFeedTab == 1,
                        onClick = { selectedFeedTab = 1 },
                        text = {
                            Text(
                                text = "Abonnements",
                                color = if (selectedFeedTab == 1) XTextPrimary else XTextSecondary,
                                fontWeight = if (selectedFeedTab == 1) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 15.sp
                            )
                        },
                        modifier = Modifier.testTag("tab_following")
                    )
                }
            }
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onOpenComposer,
                containerColor = XBlue,
                contentColor = Color.White,
                shape = CircleShape,
                modifier = Modifier
                    .size(56.dp)
                    .testTag("compose_fab")
            ) {
                Icon(
                    imageVector = Icons.Filled.Add,
                    contentDescription = "Nouveau post",
                    modifier = Modifier.size(28.dp)
                )
            }
        },
        containerColor = XBlack
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(displayedPosts, key = { it.id }) { post ->
                PostCard(
                    post = post,
                    onLikeClick = { onLikePost(post) },
                    onRetweetClick = { onRetweetPost(post) },
                    onBookmarkClick = { onBookmarkPost(post) },
                    onReplyClick = { onReplyPost(post) },
                    onShareClick = { onSharePost(post) },
                    onAuthorClick = onAuthorClick
                )
            }
        }
    }
}
