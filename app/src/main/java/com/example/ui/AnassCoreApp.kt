package com.example.ui

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Mail
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.data.model.UserAccount
import com.example.ui.components.AnassDrawerContent
import com.example.ui.components.ComposePostDialog
import com.example.ui.screens.AnassBotScreen
import com.example.ui.screens.AuthScreen
import com.example.ui.screens.ExploreScreen
import com.example.ui.screens.HomeScreen
import com.example.ui.screens.MessagesScreen
import com.example.ui.screens.NotificationsScreen
import com.example.ui.screens.ProfileScreen
import com.example.ui.screens.SubscriptionScreen
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.AnassBotPurple
import com.example.ui.theme.UltraGoldAccent
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary
import kotlinx.coroutines.launch

enum class AppScreen {
    HOME,
    EXPLORE,
    ANASS_BOT,
    NOTIFICATIONS,
    MESSAGES,
    PROFILE,
    AUTH,
    SUBSCRIPTIONS
}

@Composable
fun AnassCoreApp(
    viewModel: AnassCoreViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val snackbarHostState = remember { SnackbarHostState() }

    val currentUser by viewModel.currentUser.collectAsState()
    val posts by viewModel.posts.collectAsState()

    var currentScreen by remember { mutableStateOf(AppScreen.HOME) }
    var showComposer by remember { mutableStateOf(false) }

    // Handle back button for secondary screens
    BackHandler(enabled = currentScreen != AppScreen.HOME) {
        currentScreen = AppScreen.HOME
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                drawerContainerColor = XBlack,
                modifier = Modifier.width(300.dp)
            ) {
                AnassDrawerContent(
                    currentUser = currentUser,
                    onNavigateToProfile = { currentScreen = AppScreen.PROFILE },
                    onNavigateToSubscriptions = { currentScreen = AppScreen.SUBSCRIPTIONS },
                    onNavigateToAuth = { currentScreen = AppScreen.AUTH },
                    onCloseDrawer = {
                        coroutineScope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        Scaffold(
            bottomBar = {
                // Bottom bar is visible on the 5 main tabs
                val showBottomNav = currentScreen in listOf(
                    AppScreen.HOME,
                    AppScreen.EXPLORE,
                    AppScreen.ANASS_BOT,
                    AppScreen.NOTIFICATIONS,
                    AppScreen.MESSAGES
                )

                if (showBottomNav) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(XBlack)
                            .windowInsetsPadding(WindowInsets.navigationBars)
                    ) {
                        HorizontalDivider(color = XDivider, thickness = 0.5.dp)

                        NavigationBar(
                            containerColor = XBlack,
                            contentColor = XTextPrimary,
                            tonalElevation = 0.dp
                        ) {
                            // 1. Home
                            NavigationBarItem(
                                selected = currentScreen == AppScreen.HOME,
                                onClick = { currentScreen = AppScreen.HOME },
                                icon = {
                                    Icon(
                                        imageVector = if (currentScreen == AppScreen.HOME) Icons.Filled.Home else Icons.Outlined.Home,
                                        contentDescription = "Accueil",
                                        tint = if (currentScreen == AppScreen.HOME) Color.White else XTextSecondary,
                                        modifier = Modifier.size(26.dp)
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.testTag("nav_home")
                            )

                            // 2. Search / Explore
                            NavigationBarItem(
                                selected = currentScreen == AppScreen.EXPLORE,
                                onClick = { currentScreen = AppScreen.EXPLORE },
                                icon = {
                                    Icon(
                                        imageVector = if (currentScreen == AppScreen.EXPLORE) Icons.Filled.Search else Icons.Outlined.Search,
                                        contentDescription = "Explorer",
                                        tint = if (currentScreen == AppScreen.EXPLORE) Color.White else XTextSecondary,
                                        modifier = Modifier.size(26.dp)
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.testTag("nav_explore")
                            )

                            // 3. ANASS BOT (The Grok replacement with glowing cyber icon)
                            NavigationBarItem(
                                selected = currentScreen == AppScreen.ANASS_BOT,
                                onClick = { currentScreen = AppScreen.ANASS_BOT },
                                icon = {
                                    Box(
                                        modifier = Modifier
                                            .size(34.dp)
                                            .clip(CircleShape)
                                            .background(
                                                Brush.linearGradient(listOf(AnassBotCyan, AnassBotPurple))
                                            )
                                            .border(
                                                1.dp,
                                                if (currentScreen == AppScreen.ANASS_BOT) Color.White else Color.Transparent,
                                                CircleShape
                                            ),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Icon(
                                            imageVector = Icons.Filled.ElectricBolt,
                                            contentDescription = "Anass Bot",
                                            tint = Color.Black,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.testTag("nav_anass_bot")
                            )

                            // 4. Notifications
                            NavigationBarItem(
                                selected = currentScreen == AppScreen.NOTIFICATIONS,
                                onClick = { currentScreen = AppScreen.NOTIFICATIONS },
                                icon = {
                                    Icon(
                                        imageVector = if (currentScreen == AppScreen.NOTIFICATIONS) Icons.Filled.Notifications else Icons.Outlined.Notifications,
                                        contentDescription = "Notifications",
                                        tint = if (currentScreen == AppScreen.NOTIFICATIONS) Color.White else XTextSecondary,
                                        modifier = Modifier.size(26.dp)
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.testTag("nav_notifications")
                            )

                            // 5. Messages
                            NavigationBarItem(
                                selected = currentScreen == AppScreen.MESSAGES,
                                onClick = { currentScreen = AppScreen.MESSAGES },
                                icon = {
                                    Icon(
                                        imageVector = if (currentScreen == AppScreen.MESSAGES) Icons.Filled.Mail else Icons.Outlined.Mail,
                                        contentDescription = "Messages",
                                        tint = if (currentScreen == AppScreen.MESSAGES) Color.White else XTextSecondary,
                                        modifier = Modifier.size(26.dp)
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    indicatorColor = Color.Transparent
                                ),
                                modifier = Modifier.testTag("nav_messages")
                            )
                        }
                    }
                }
            },
            snackbarHost = { SnackbarHost(snackbarHostState) },
            containerColor = XBlack
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when (currentScreen) {
                    AppScreen.HOME -> {
                        HomeScreen(
                            currentUser = currentUser,
                            posts = posts,
                            onOpenDrawer = {
                                coroutineScope.launch { drawerState.open() }
                            },
                            onNavigateToSubscriptions = { currentScreen = AppScreen.SUBSCRIPTIONS },
                            onOpenComposer = { showComposer = true },
                            onLikePost = { viewModel.likePost(it) },
                            onRetweetPost = {
                                viewModel.retweetPost(it)
                                Toast.makeText(context, "Republié sur votre profil", Toast.LENGTH_SHORT).show()
                            },
                            onBookmarkPost = {
                                viewModel.bookmarkPost(it)
                                val msg = if (!it.isBookmarked) "Ajouté aux signets" else "Retiré des signets"
                                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            },
                            onReplyPost = { showComposer = true },
                            onSharePost = {
                                Toast.makeText(context, "Lien copié dans le presse-papiers !", Toast.LENGTH_SHORT).show()
                            },
                            onAuthorClick = { handle ->
                                if (handle == currentUser.handle) {
                                    currentScreen = AppScreen.PROFILE
                                } else {
                                    Toast.makeText(context, "Profil de $handle", Toast.LENGTH_SHORT).show()
                                }
                            }
                        )
                    }

                    AppScreen.EXPLORE -> {
                        ExploreScreen(
                            posts = posts,
                            onLikePost = { viewModel.likePost(it) },
                            onRetweetPost = { viewModel.retweetPost(it) },
                            onBookmarkPost = { viewModel.bookmarkPost(it) },
                            onReplyPost = { showComposer = true },
                            onSharePost = {
                                Toast.makeText(context, "Lien copié !", Toast.LENGTH_SHORT).show()
                            },
                            onAuthorClick = { handle ->
                                if (handle == currentUser.handle) {
                                    currentScreen = AppScreen.PROFILE
                                }
                            }
                        )
                    }

                    AppScreen.ANASS_BOT -> {
                        AnassBotScreen(
                            currentUser = currentUser,
                            onPostToFeed = { botText ->
                                viewModel.createPost(botText, "ANASS_BOT")
                                Toast.makeText(context, "Posté sur votre fil !", Toast.LENGTH_SHORT).show()
                                currentScreen = AppScreen.HOME
                            }
                        )
                    }

                    AppScreen.NOTIFICATIONS -> {
                        NotificationsScreen()
                    }

                    AppScreen.MESSAGES -> {
                        MessagesScreen(currentUser = currentUser)
                    }

                    AppScreen.PROFILE -> {
                        val userPosts = posts.filter { it.authorHandle == currentUser.handle || (currentUser.isCreator && it.isCreator) }
                        ProfileScreen(
                            user = currentUser,
                            userPosts = userPosts,
                            onLikePost = { viewModel.likePost(it) },
                            onRetweetPost = { viewModel.retweetPost(it) },
                            onBookmarkPost = { viewModel.bookmarkPost(it) },
                            onReplyPost = { showComposer = true },
                            onSharePost = {
                                Toast.makeText(context, "Lien copié !", Toast.LENGTH_SHORT).show()
                            },
                            onNavigateToSubscriptions = { currentScreen = AppScreen.SUBSCRIPTIONS },
                            onBack = { currentScreen = AppScreen.HOME }
                        )
                    }

                    AppScreen.AUTH -> {
                        AuthScreen(
                            currentUser = currentUser,
                            onLoginSuccess = { newUser ->
                                viewModel.loginUser(newUser)
                                coroutineScope.launch {
                                    if (newUser.isCreator) {
                                        snackbarHostState.showSnackbar("👑 Compte Créateur activé ! 12 Milliards de followers & Ultra gratuit.")
                                    } else {
                                        snackbarHostState.showSnackbar("Connecté en tant que ${newUser.displayName}")
                                    }
                                }
                                currentScreen = AppScreen.HOME
                            },
                            onBack = { currentScreen = AppScreen.HOME }
                        )
                    }

                    AppScreen.SUBSCRIPTIONS -> {
                        SubscriptionScreen(
                            currentUser = currentUser,
                            onSelectTier = { tier ->
                                viewModel.selectSubscriptionTier(tier)
                                Toast.makeText(context, "Plan ${tier.title} activé avec succès !", Toast.LENGTH_SHORT).show()
                                currentScreen = AppScreen.HOME
                            },
                            onBack = { currentScreen = AppScreen.HOME }
                        )
                    }
                }

                // Compose Post modal dialog
                if (showComposer) {
                    ComposePostDialog(
                        currentUser = currentUser,
                        onDismiss = { showComposer = false },
                        onPostCreated = { content, mediaType ->
                            viewModel.createPost(content, mediaType)
                            Toast.makeText(context, "Votre post a été publié !", Toast.LENGTH_SHORT).show()
                        }
                    )
                }
            }
        }
    }
}
