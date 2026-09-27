package com.example.ui.screens

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import com.example.data.local.PostEntity
import com.example.ui.components.PostCard
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.UltraGoldAccent
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XCardSurface
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

data class TrendItem(
    val category: String,
    val title: String,
    val postCount: String
)

@Composable
fun ExploreScreen(
    posts: List<PostEntity>,
    onLikePost: (PostEntity) -> Unit,
    onRetweetPost: (PostEntity) -> Unit,
    onBookmarkPost: (PostEntity) -> Unit,
    onReplyPost: (PostEntity) -> Unit,
    onSharePost: (PostEntity) -> Unit,
    onAuthorClick: (String) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var selectedCategoryIndex by remember { mutableIntStateOf(0) }
    val categories = listOf("Pour vous", "Tendances", "Actualités", "Tech", "Divertissement")

    val trends = listOf(
        TrendItem("Technologie · Tendances mondiales", "#AnassCoreLaunch", "14,2 M de posts"),
        TrendItem("Intelligence Artificielle · En vedette", "Anass Bot", "8,9 M de posts"),
        TrendItem("Communauté · Record historique", "12 Milliards de membres", "12,4 M de posts"),
        TrendItem("Abonnements · Tendance", "Pass Ultra 6.50$", "1,8 M de posts"),
        TrendItem("Sécurité & Mystères", "#SecretlySecret", "940 k posts"),
        TrendItem("Culture Web · Populaire", "#AdieuTwitter", "3,5 M de posts")
    )

    val filteredPosts = if (searchQuery.isBlank()) {
        posts
    } else {
        posts.filter {
            it.content.contains(searchQuery, ignoreCase = true) ||
            it.authorName.contains(searchQuery, ignoreCase = true) ||
            it.authorHandle.contains(searchQuery, ignoreCase = true)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(XBlack)
    ) {
        // Search Input Bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
        ) {
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                placeholder = {
                    Text(
                        text = "Rechercher sur Anass Core...",
                        color = XTextSecondary,
                        fontSize = 14.sp
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Search,
                        contentDescription = "Rechercher",
                        tint = XTextSecondary
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = XCardSurface,
                    unfocusedContainerColor = XCardSurface,
                    focusedTextColor = XTextPrimary,
                    unfocusedTextColor = XTextPrimary,
                    focusedIndicatorColor = XBlue,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                shape = RoundedCornerShape(24.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("explore_search_input")
            )
        }

        // Category Tabs
        ScrollableTabRow(
            selectedTabIndex = selectedCategoryIndex,
            containerColor = XBlack,
            contentColor = XBlue,
            edgePadding = 16.dp,
            indicator = { tabPositions ->
                TabRowDefaults.SecondaryIndicator(
                    Modifier.tabIndicatorOffset(tabPositions[selectedCategoryIndex]),
                    color = XBlue
                )
            },
            divider = { HorizontalDivider(color = XDivider, thickness = 0.5.dp) }
        ) {
            categories.forEachIndexed { index, cat ->
                Tab(
                    selected = selectedCategoryIndex == index,
                    onClick = { selectedCategoryIndex = index },
                    text = {
                        Text(
                            text = cat,
                            color = if (selectedCategoryIndex == index) XTextPrimary else XTextSecondary,
                            fontWeight = if (selectedCategoryIndex == index) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 14.sp
                        )
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            // Hero Trending Banner
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFF0F172A))
                        .padding(16.dp)
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.TrendingUp,
                                contentDescription = null,
                                tint = UltraGoldAccent,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "TENDANCE MAJEURE DU JOUR",
                                color = UltraGoldAccent,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Anass Core atteint les 12 Milliards de membres avec Anass Bot Ultra",
                            color = Color.White,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Le créateur @Anass_Official et Anass Bot dominent le web mondial.",
                            color = Color.LightGray,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Trending items section
            item {
                Text(
                    text = "Tendances pour vous",
                    color = XTextPrimary,
                    fontWeight = FontWeight.Black,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                )
            }

            items(trends) { trend ->
                TrendRow(
                    trend = trend,
                    onClick = { searchQuery = trend.title }
                )
            }

            item {
                Spacer(modifier = Modifier.height(12.dp))
                HorizontalDivider(color = XDivider, thickness = 0.5.dp)
                Text(
                    text = "Derniers posts pertinents",
                    color = XTextPrimary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(16.dp)
                )
            }

            items(filteredPosts, key = { it.id }) { post ->
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

@Composable
fun TrendRow(
    trend: TrendItem,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 10.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column {
            Text(
                text = trend.category,
                color = XTextSecondary,
                fontSize = 12.sp
            )
            Text(
                text = trend.title,
                color = XTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
            Text(
                text = trend.postCount,
                color = XTextSecondary,
                fontSize = 12.sp
            )
        }

        Icon(
            imageVector = Icons.Filled.MoreHoriz,
            contentDescription = "Options",
            tint = XTextSecondary,
            modifier = Modifier.size(18.dp)
        )
    }
}
