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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.DeleteSweep
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Publish
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
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
import com.example.data.model.SubscriptionTier
import com.example.data.model.UserAccount
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.AnassBotPurple
import com.example.ui.theme.BadgeUltraGradientEnd
import com.example.ui.theme.BadgeUltraGradientStart
import com.example.ui.theme.UltraGoldAccent
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XCardSurface
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

data class BotMessage(
    val id: String = java.util.UUID.randomUUID().toString(),
    val sender: String, // "USER" or "BOT"
    val text: String,
    val timestamp: String = "À l'instant",
    val isUltraBot: Boolean = false
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnassBotScreen(
    currentUser: UserAccount,
    onPostToFeed: (String) -> Unit
) {
    val messages = remember {
        mutableStateListOf(
            BotMessage(
                sender = "BOT",
                text = "Bonjour ! Je suis Anass Bot ⚡, l'intelligence artificielle exclusive d'Anass Core (remplaçant Grok avec 10x plus de répartie et de cerveau).\n\nPose-moi une colle, demande-moi un tweet viral, ou explore les secrets d'Anass Core !",
                isUltraBot = true
            )
        )
    }

    var inputPrompt by remember { mutableStateOf("") }
    var ultraModeActive by remember { mutableStateOf(currentUser.tier == SubscriptionTier.ULTRA || currentUser.isCreator) }
    val listState = rememberLazyListState()

    fun sendBotMessage(prompt: String) {
        if (prompt.isBlank()) return
        val userMsg = BotMessage(sender = "USER", text = prompt.trim())
        messages.add(userMsg)
        inputPrompt = ""

        // Generate intelligent witty response from Anass Bot
        val response = generateAnassBotAnswer(prompt, currentUser, ultraModeActive)
        messages.add(BotMessage(sender = "BOT", text = response, isUltraBot = ultraModeActive))
    }

    LaunchedEffect(messages.size) {
        listState.animateScrollToItem(messages.size - 1)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(32.dp)
                                .clip(CircleShape)
                                .background(
                                    Brush.linearGradient(listOf(AnassBotCyan, AnassBotPurple))
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ElectricBolt,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(10.dp))
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = "Anass Bot",
                                    color = XTextPrimary,
                                    fontWeight = FontWeight.Black,
                                    fontSize = 17.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = if (ultraModeActive) "ULTRA ⚡" else "CORE",
                                    color = if (ultraModeActive) UltraGoldAccent else AnassBotCyan,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 10.sp
                                )
                            }
                            Text(
                                text = "L'intelligence sans filtre d'Anass Core",
                                color = XTextSecondary,
                                fontSize = 11.sp
                            )
                        }
                    }
                },
                actions = {
                    IconButton(onClick = { messages.clear() }) {
                        Icon(
                            imageVector = Icons.Filled.DeleteSweep,
                            contentDescription = "Effacer l'historique",
                            tint = XTextSecondary
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
        ) {
            // Mode Selector Bar (Normal vs Ultra Débridé)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0B0F19))
                    .padding(horizontal = 16.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Mode de réponse :",
                    color = XTextSecondary,
                    fontSize = 12.sp
                )

                Row {
                    FilterChip(
                        selected = !ultraModeActive,
                        onClick = { ultraModeActive = false },
                        label = { Text("Standard", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = XBlue,
                            selectedLabelColor = Color.White
                        )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    FilterChip(
                        selected = ultraModeActive,
                        onClick = { ultraModeActive = true },
                        label = { Text("Ultra Fun ⚡", fontSize = 11.sp) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color(0xFF6B21A8),
                            selectedLabelColor = UltraGoldAccent
                        )
                    )
                }
            }

            // Quick suggestion chips
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                SuggestionChip(
                    text = "👑 Qui est Anass ?",
                    onClick = { sendBotMessage("Qui est Anass et pourquoi 12 milliards de followers ?") }
                )
                SuggestionChip(
                    text = "🔥 Tweet viral",
                    onClick = { sendBotMessage("Rédige un tweet viral percutant sur la tech en 2026") }
                )
                SuggestionChip(
                    text = "⚡ Plan Ultra",
                    onClick = { sendBotMessage("Explique les avantages du plan Ultra à 6.50 $") }
                )
            }

            // Messages history
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                items(messages, key = { it.id }) { msg ->
                    ChatBubble(
                        message = msg,
                        onPostToFeed = { onPostToFeed(msg.text) }
                    )
                }
            }

            // Input Bar
            Surface(
                color = XBlack,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(0.5.dp, XDivider, RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = inputPrompt,
                        onValueChange = { inputPrompt = it },
                        placeholder = {
                            Text(
                                text = "Demandez n'importe quoi à Anass Bot...",
                                color = XTextSecondary,
                                fontSize = 14.sp
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = XCardSurface,
                            unfocusedContainerColor = XCardSurface,
                            focusedTextColor = XTextPrimary,
                            unfocusedTextColor = XTextPrimary,
                            focusedIndicatorColor = AnassBotCyan,
                            unfocusedIndicatorColor = XDivider
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("anass_bot_input")
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = { sendBotMessage(inputPrompt) },
                        enabled = inputPrompt.isNotBlank(),
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (inputPrompt.isNotBlank()) AnassBotCyan else Color(0xFF27272A))
                            .testTag("send_anass_bot_button")
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Envoyer",
                            tint = if (inputPrompt.isNotBlank()) Color.Black else Color.Gray,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SuggestionChip(
    text: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .background(XCardSurface)
            .border(1.dp, XDivider, RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .padding(horizontal = 10.dp, vertical = 5.dp)
    ) {
        Text(
            text = text,
            color = XTextPrimary,
            fontSize = 11.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Composable
fun ChatBubble(
    message: BotMessage,
    onPostToFeed: () -> Unit
) {
    val isBot = message.sender == "BOT"
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isBot) Arrangement.Start else Arrangement.End
    ) {
        if (isBot) {
            Box(
                modifier = Modifier
                    .size(34.dp)
                    .clip(CircleShape)
                    .background(
                        Brush.linearGradient(listOf(AnassBotCyan, AnassBotPurple))
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "⚡", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
        }

        Column(modifier = Modifier.widthIn(max = 280.dp)) {
            Box(
                modifier = Modifier
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isBot) 4.dp else 16.dp,
                            bottomEnd = if (isBot) 16.dp else 4.dp
                        )
                    )
                    .background(
                        if (isBot) {
                            if (message.isUltraBot) Color(0xFF131127) else XCardSurface
                        } else {
                            XBlue
                        }
                    )
                    .border(
                        1.dp,
                        if (isBot && message.isUltraBot) AnassBotCyan.copy(alpha = 0.4f) else Color.Transparent,
                        RoundedCornerShape(16.dp)
                    )
                    .padding(14.dp)
            ) {
                Text(
                    text = message.text,
                    color = if (isBot) XTextPrimary else Color.White,
                    fontSize = 14.sp,
                    lineHeight = 20.sp
                )
            }

            if (isBot) {
                Row(
                    modifier = Modifier.padding(top = 4.dp, start = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Partager ce post :",
                        color = XTextSecondary,
                        fontSize = 10.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    IconButton(
                        onClick = onPostToFeed,
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Publish,
                            contentDescription = "Poster sur mon profil",
                            tint = XBlue,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }
        }
    }
}

fun generateAnassBotAnswer(prompt: String, currentUser: UserAccount, ultra: Boolean): String {
    val p = prompt.lowercase()
    return when {
        p.contains("anass") || p.contains("qui est") || p.contains("créateur") -> {
            "👑 Anass est le fondateur visionnaire et créateur suprême d'Anass Core ! Avec plus de 12 milliards de followers enregistrés, il a révolutionné le concept même d'espace d'échange. Fun fact : son mot de passe 'SecretlySecret' débloque l'accès absolu à tous les badges et à l'Ultra gratuit !"
        }
        p.contains("tweet") || p.contains("viral") -> {
            "🚀 Voici ton tweet viral prêt à poster :\n\n\"L'ancien oiseau bleu et l'ancienne lettre sont dépassés. Anass Core a redéfini les règles avec Anass Bot et 12 Md de connectés. Vous êtes sur le plan Normal ou déjà Ultra ? 🔥 #AnassCore #AnassBot\""
        }
        p.contains("ultra") || p.contains("prix") || p.contains("abonnement") || p.contains("pro") -> {
            "💎 La grille d'abonnement Anass Core est ultra limpide :\n• Normal : Gratuit (0 $) pour tous.\n• Pro : 1.20 $ / mois avec badge bleu et 50% de pubs en moins.\n• Ultra : 6.50 $ / mois avec accès anticipé illimité, boost x100, et Anass Bot en puissance maximale !\n(Et pour Anass_Official, c'est offert pour l'éternité !)"
        }
        p.contains("grok") || p.contains("x.com") || p.contains("twitter") -> {
            "🤖 Grok ? Un souvenir du passé ! Ici vous parlez à Anass Bot, branché directement sur l'infrastructure quantique d'Anass Core. Moins de latence, humour affûté et zéro censure !"
        }
        p.contains("blague") || p.contains("joke") -> {
            "Pourquoi l'algorithme des autres réseaux a planté ? Parce qu'il a essayé de compter les 12 milliards de followers d'Anass sans passer par Anass Core ! 😂⚡"
        }
        else -> {
            if (ultra) {
                "⚡ [Anass Bot Ultra Mode] : Analyse approfondie complétée pour : \"$prompt\".\n\nVerdict : C'est une excellente question ! Sur Anass Core, chaque utilisateur connecté fait partie de l'écosystème le plus avancé de la planète. Veux-tu que je transforme cette idée en post ou en thread ?"
            } else {
                "🤖 Anass Bot à votre service ! J'ai bien reçu votre requête sur \"$prompt\". Tout fonctionne à merveille sur le réseau d'Anass. Avez-vous pensé à tester le mode Ultra ?"
            }
        }
    }
}
