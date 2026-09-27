package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.SubscriptionTier
import com.example.data.model.UserAccount
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.BadgeUltraGradientEnd
import com.example.ui.theme.BadgeUltraGradientStart
import com.example.ui.theme.BadgeVerifiedBlue
import com.example.ui.theme.BadgeVerifiedGold
import com.example.ui.theme.UltraGoldAccent
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XCardSurface
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SubscriptionScreen(
    currentUser: UserAccount,
    onSelectTier: (SubscriptionTier) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Abonnements Anass Core",
                        color = XTextPrimary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
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
                colors = TopAppBarDefaults.topAppBarColors(containerColor = XBlack)
            )
        },
        containerColor = XBlack
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 10.dp)
        ) {
            // Header Hero
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(
                        Brush.linearGradient(
                            listOf(Color(0xFF1E1B4B), Color(0xFF3B0764), Color(0xFF0F172A))
                        )
                    )
                    .border(1.dp, Color(0xFF6366F1).copy(alpha = 0.4f), RoundedCornerShape(16.dp))
                    .padding(20.dp)
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = UltraGoldAccent,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Passez à la vitesse supérieure",
                            color = Color.White,
                            fontWeight = FontWeight.Black,
                            fontSize = 18.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Choisissez l'abonnement qui vous correspond. Boost de visibilité, badges exclusifs et puissance Anass Bot.",
                        color = Color.LightGray,
                        fontSize = 13.sp,
                        textAlign = TextAlign.Center
                    )

                    if (currentUser.isCreator) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(Color(0xFFFFD700).copy(alpha = 0.2f))
                                .border(1.dp, Color(0xFFFFD700), RoundedCornerShape(20.dp))
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = "👑 Statut Créateur : Version ULTRA Gratuite à vie !",
                                color = Color(0xFFFFD700),
                                fontWeight = FontWeight.Bold,
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // PLAN 1: NORMAL (0 $ / mois)
            TierCard(
                title = "Normal",
                price = "Gratuit (0 $)",
                period = "Pour toujours",
                badgeIcon = null,
                accentColor = Color.LightGray,
                isCurrent = currentUser.tier == SubscriptionTier.NORMAL && !currentUser.isCreator,
                features = listOf(
                    "Fil d'actualité Pour vous & Abonnements",
                    "Publication de posts (limite 280 caractères)",
                    "Anass Bot basique",
                    "Publicités standards"
                ),
                onSelect = { onSelectTier(SubscriptionTier.NORMAL) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // PLAN 2: PRO (1.20 $ / mois)
            TierCard(
                title = "Pro",
                price = "1.20 $",
                period = "par mois",
                badgeIcon = Icons.Filled.Verified,
                accentColor = BadgeVerifiedBlue,
                isCurrent = currentUser.tier == SubscriptionTier.PRO && !currentUser.isCreator,
                features = listOf(
                    "Badge bleu vérifié officiel",
                    "50% de publicités en moins",
                    "Modification des posts pendant 1 heure",
                    "Posts plus longs jusqu'à 4 000 caractères",
                    "Dossiers de signets & favoris prioritaires",
                    "Priorité accrue dans les réponses"
                ),
                onSelect = { onSelectTier(SubscriptionTier.PRO) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // PLAN 3: ULTRA (6.50 $ / mois)
            TierCard(
                title = "Ultra",
                price = "6.50 $",
                period = if (currentUser.isCreator) "Offert Gratuitement au Créateur" else "par mois",
                badgeIcon = Icons.Filled.AutoAwesome,
                accentColor = UltraGoldAccent,
                isUltra = true,
                isCurrent = currentUser.tier == SubscriptionTier.ULTRA || currentUser.isCreator,
                features = listOf(
                    "Tous les avantages du plan Pro",
                    "Badges dorés & Badge ULTRA exclusif",
                    "ACCÈS ANTICIPÉ ILLIMITÉ aux nouvelles fonctionnalités",
                    "Anass Bot Ultra illimité avec réflexion profonde & sans censure",
                    "Boost d'algorithme maximal x100 dans les tendances",
                    "Partage des revenus créateurs activé",
                    "Zéro publicité dans tout le fil"
                ),
                onSelect = { onSelectTier(SubscriptionTier.ULTRA) }
            )

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Composable
fun TierCard(
    title: String,
    price: String,
    period: String,
    badgeIcon: androidx.compose.ui.graphics.vector.ImageVector?,
    accentColor: Color,
    isUltra: Boolean = false,
    isCurrent: Boolean,
    features: List<String>,
    onSelect: () -> Unit
) {
    val cardBorder = if (isUltra) {
        Modifier.border(
            2.dp,
            Brush.horizontalGradient(listOf(BadgeUltraGradientStart, UltraGoldAccent)),
            RoundedCornerShape(16.dp)
        )
    } else if (isCurrent) {
        Modifier.border(1.5.dp, accentColor, RoundedCornerShape(16.dp))
    } else {
        Modifier.border(1.dp, XDivider, RoundedCornerShape(16.dp))
    }

    Card(
        colors = CardDefaults.cardColors(containerColor = XCardSurface),
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .then(cardBorder)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = title,
                        color = if (isUltra) UltraGoldAccent else XTextPrimary,
                        fontWeight = FontWeight.Black,
                        fontSize = 20.sp
                    )
                    if (badgeIcon != null) {
                        Spacer(modifier = Modifier.width(6.dp))
                        Icon(
                            imageVector = badgeIcon,
                            contentDescription = null,
                            tint = accentColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }

                if (isCurrent) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .background(accentColor.copy(alpha = 0.2f))
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "PLAN ACTUEL",
                            color = accentColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 11.sp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = price,
                    color = Color.White,
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 26.sp
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = period,
                    color = XTextSecondary,
                    fontSize = 13.sp,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            features.forEach { feature ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(vertical = 3.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        tint = if (isUltra) UltraGoldAccent else XBlue,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = feature,
                        color = if (feature.contains("ACCÈS ANTICIPÉ") || feature.contains("Badge ULTRA")) Color.White else XTextPrimary,
                        fontSize = 13.sp,
                        fontWeight = if (feature.contains("ACCÈS ANTICIPÉ")) FontWeight.Bold else FontWeight.Normal
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Button(
                onClick = onSelect,
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isCurrent) Color(0xFF27272A) else if (isUltra) Color.White else XBlue,
                    contentColor = if (isCurrent) Color.LightGray else if (isUltra) Color.Black else Color.White
                ),
                shape = RoundedCornerShape(20.dp),
                enabled = !isCurrent,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("select_tier_${title.lowercase()}")
            ) {
                Text(
                    text = if (isCurrent) "Actif" else "Choisir le plan $title",
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }
        }
    }
}
