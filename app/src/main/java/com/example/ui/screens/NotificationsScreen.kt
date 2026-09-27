package com.example.ui.screens

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Repeat
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.BadgeVerifiedGold
import com.example.ui.theme.UltraGoldAccent
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XDivider
import com.example.ui.theme.XLike
import com.example.ui.theme.XRetweet
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

data class NotificationItem(
    val id: String,
    val icon: ImageVector,
    val iconTint: Color,
    val title: String,
    val description: String,
    val time: String,
    val isVerified: Boolean = false,
    val isMention: Boolean = false
)

@Composable
fun NotificationsScreen() {
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("Toutes", "Vérifiées", "Mentions")

    val notifications = listOf(
        NotificationItem(
            id = "1",
            icon = Icons.Filled.AutoAwesome,
            iconTint = UltraGoldAccent,
            title = "Statut Ultra Activé ⚡",
            description = "Bienvenue dans l'expérience Anass Core Ultra : accès anticipé, badges débloqués et 100% de visibilité !",
            time = "5 min",
            isVerified = true
        ),
        NotificationItem(
            id = "2",
            icon = Icons.Filled.ElectricBolt,
            iconTint = AnassBotCyan,
            title = "Anass Bot",
            description = "Anass Bot a répondu à votre question dans l'espace IA : 'L'humour est le meilleur algorithme.'",
            time = "18 min",
            isVerified = true,
            isMention = true
        ),
        NotificationItem(
            id = "3",
            icon = Icons.Filled.Repeat,
            iconTint = XRetweet,
            title = "@Anass_Official et 41 800 autres",
            description = "ont republié votre message sur l'architecture moderne d'Anass Core.",
            time = "1h",
            isVerified = true
        ),
        NotificationItem(
            id = "4",
            icon = Icons.Filled.Favorite,
            iconTint = XLike,
            title = "Plus de 2 500 personnes",
            description = "ont aimé votre réponse à propos de la sortie d'Anass Core.",
            time = "3h",
            isVerified = false
        ),
        NotificationItem(
            id = "5",
            icon = Icons.Filled.Person,
            iconTint = XBlue,
            title = "Nouveaux abonnés",
            description = "Sarah Dev et 320 autres comptes ont commencé à vous suivre.",
            time = "5h",
            isVerified = false
        ),
        NotificationItem(
            id = "6",
            icon = Icons.Filled.Verified,
            iconTint = BadgeVerifiedGold,
            title = "Badge Vérifié Actif",
            description = "Tous les badges (Bleu, Or, Institution, Ultra) sont validés pour le créateur.",
            time = "Hier",
            isVerified = true
        )
    )

    val filteredList = when (selectedTabIndex) {
        1 -> notifications.filter { it.isVerified }
        2 -> notifications.filter { it.isMention }
        else -> notifications
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(XBlack)
    ) {
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
                            fontSize = 15.sp
                        )
                    }
                )
            }
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(filteredList, key = { it.id }) { item ->
                NotificationRow(item = item)
            }
        }
    }
}

@Composable
fun NotificationRow(item: NotificationItem) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(XBlack)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {
            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = item.iconTint,
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 4.dp)
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = item.title,
                        color = XTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Text(
                        text = item.time,
                        color = XTextSecondary,
                        fontSize = 12.sp
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = item.description,
                    color = XTextSecondary,
                    fontSize = 13.sp,
                    lineHeight = 18.sp
                )
            }
        }

        HorizontalDivider(color = XDivider, thickness = 0.5.dp)
    }
}
