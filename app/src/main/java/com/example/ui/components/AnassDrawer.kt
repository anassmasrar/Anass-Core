package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SubscriptionTier
import com.example.data.model.UserAccount
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.BadgeUltraGradientEnd
import com.example.ui.theme.BadgeUltraGradientStart
import com.example.ui.theme.UltraGoldAccent
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

@Composable
fun AnassDrawerContent(
    currentUser: UserAccount,
    onNavigateToProfile: () -> Unit,
    onNavigateToSubscriptions: () -> Unit,
    onNavigateToAuth: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxHeight()
            .width(300.dp)
            .background(XBlack)
            .verticalScroll(rememberScrollState())
            .padding(20.dp)
            .testTag("anass_drawer")
    ) {
        // Avatar
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(if (currentUser.isCreator) Color(0xFF1E1B4B) else Color(0xFF1E293B))
                .clickable {
                    onNavigateToProfile()
                    onCloseDrawer()
                },
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = currentUser.avatarInitial,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Display Name & Badges
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable {
                onNavigateToProfile()
                onCloseDrawer()
            }
        ) {
            Text(
                text = currentUser.displayName,
                color = XTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 17.sp
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

        Text(
            text = currentUser.handle,
            color = XTextSecondary,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Followers & Following stats
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = currentUser.formatFollowing(),
                color = XTextPrimary,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "abonnements",
                color = XTextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = currentUser.formatFollowers(),
                color = if (currentUser.isCreator) Color(0xFFFFD700) else XTextPrimary,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "abonnés",
                color = XTextSecondary,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Subscription Tier Badge Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(
                    if (currentUser.tier == SubscriptionTier.ULTRA) {
                        Brush.horizontalGradient(listOf(Color(0xFF3B0764), Color(0xFF1E1B4B)))
                    } else if (currentUser.tier == SubscriptionTier.PRO) {
                        Brush.horizontalGradient(listOf(Color(0xFF0369A1), Color(0xFF0C4A6E)))
                    } else {
                        Brush.horizontalGradient(listOf(Color(0xFF1F2937), Color(0xFF111827)))
                    }
                )
                .clickable {
                    onNavigateToSubscriptions()
                    onCloseDrawer()
                }
                .padding(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Filled.AutoAwesome,
                    contentDescription = null,
                    tint = if (currentUser.tier == SubscriptionTier.ULTRA) UltraGoldAccent else XBlue,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Text(
                        text = if (currentUser.isCreator) "Abonnement : ULTRA GRATUIT" else "Abonnement : ${currentUser.tier.title}",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp
                    )
                    Text(
                        text = if (currentUser.isCreator) "Offert à vie au créateur officiel" else "Gérer les offres (Normal, Pro, Ultra)",
                        color = Color.LightGray,
                        fontSize = 11.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
        HorizontalDivider(color = XDivider, thickness = 0.5.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // Navigation Menu items
        DrawerMenuItem(
            icon = Icons.Filled.Person,
            label = "Profil",
            onClick = {
                onNavigateToProfile()
                onCloseDrawer()
            }
        )
        DrawerMenuItem(
            icon = Icons.Filled.AutoAwesome,
            label = "Abonnements Core (Pro & Ultra)",
            highlight = true,
            onClick = {
                onNavigateToSubscriptions()
                onCloseDrawer()
            }
        )
        DrawerMenuItem(
            icon = Icons.Filled.Bookmark,
            label = "Signets",
            onClick = {
                onCloseDrawer()
            }
        )
        DrawerMenuItem(
            icon = Icons.Filled.FormatListBulleted,
            label = "Listes",
            onClick = {
                onCloseDrawer()
            }
        )
        DrawerMenuItem(
            icon = Icons.Filled.MonetizationOn,
            label = "Monétisation",
            onClick = {
                onNavigateToSubscriptions()
                onCloseDrawer()
            }
        )

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider(color = XDivider, thickness = 0.5.dp)
        Spacer(modifier = Modifier.height(16.dp))

        DrawerMenuItem(
            icon = Icons.Filled.SwapHoriz,
            label = "Connexion / Compte Créateur",
            tag = "Anass_Official",
            onClick = {
                onNavigateToAuth()
                onCloseDrawer()
            }
        )
        DrawerMenuItem(
            icon = Icons.Filled.Settings,
            label = "Paramètres & Support",
            onClick = {
                onCloseDrawer()
            }
        )
    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    label: String,
    highlight: Boolean = false,
    tag: String? = null,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(vertical = 12.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = if (highlight) UltraGoldAccent else XTextPrimary,
            modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            color = if (highlight) UltraGoldAccent else XTextPrimary,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp
        )
        if (tag != null) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFF1E1B4B))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
                Text(
                    text = tag,
                    color = AnassBotCyan,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
