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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ElectricBolt
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import com.example.data.model.UserAccount
import com.example.ui.theme.AnassBotCyan
import com.example.ui.theme.BadgeVerifiedGold
import com.example.ui.theme.XBlack
import com.example.ui.theme.XBlue
import com.example.ui.theme.XCardSurface
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

data class DirectMessageConversation(
    val id: String,
    val name: String,
    val handle: String,
    val avatarInitial: String,
    val lastMessage: String,
    val time: String,
    val isVerified: Boolean = true,
    val isBot: Boolean = false
)

data class SingleDM(
    val isMe: Boolean,
    val text: String,
    val time: String
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    currentUser: UserAccount
) {
    var activeConversation by remember { mutableStateOf<DirectMessageConversation?>(null) }
    var chatText by remember { mutableStateOf("") }
    val currentDMs = remember {
        mutableStateListOf(
            SingleDM(false, "Bienvenue dans vos messages directs Anass Core !", "10:30"),
            SingleDM(true, "Merci, Anass Core est ultra rapide.", "10:31"),
            SingleDM(false, "Les 12 milliards de comptes sont synchronisés avec succès.", "10:32")
        )
    }

    val conversations = listOf(
        DirectMessageConversation(
            id = "bot",
            name = "Anass Bot",
            handle = "@Anass_Bot",
            avatarInitial = "⚡",
            lastMessage = "Je suis connecté et prêt pour vos questions 24/7.",
            time = "10 min",
            isBot = true
        ),
        DirectMessageConversation(
            id = "support",
            name = "Équipe Anass Core",
            handle = "@anass_support",
            avatarInitial = "🛡️",
            lastMessage = "Votre statut Ultra avec accès anticipé est actif.",
            time = "2h"
        ),
        DirectMessageConversation(
            id = "elon",
            name = "Tech Pioneer",
            handle = "@pioneer_x",
            avatarInitial = "🚀",
            lastMessage = "Impressionné par la migration des 12 milliards vers Anass Core !",
            time = "Hier"
        )
    )

    if (activeConversation != null) {
        val conv = activeConversation!!
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = conv.name,
                                color = XTextPrimary,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            if (conv.isVerified) {
                                Icon(
                                    imageVector = Icons.Filled.Verified,
                                    contentDescription = null,
                                    tint = if (conv.isBot) AnassBotCyan else BadgeVerifiedGold,
                                    modifier = Modifier.size(15.dp)
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = { activeConversation = null }) {
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
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 14.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(currentDMs) { msg ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = if (msg.isMe) Arrangement.End else Arrangement.Start
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(
                                        RoundedCornerShape(
                                            topStart = 16.dp,
                                            topEnd = 16.dp,
                                            bottomStart = if (msg.isMe) 16.dp else 4.dp,
                                            bottomEnd = if (msg.isMe) 4.dp else 16.dp
                                        )
                                    )
                                    .background(if (msg.isMe) XBlue else XCardSurface)
                                    .padding(horizontal = 14.dp, vertical = 10.dp)
                            ) {
                                Text(
                                    text = msg.text,
                                    color = if (msg.isMe) Color.White else XTextPrimary,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }

                // Input DM bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(XBlack)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = chatText,
                        onValueChange = { chatText = it },
                        placeholder = { Text("Écrire un message...", color = XTextSecondary, fontSize = 14.sp) },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = XCardSurface,
                            unfocusedContainerColor = XCardSurface,
                            focusedTextColor = XTextPrimary,
                            unfocusedTextColor = XTextPrimary,
                            focusedIndicatorColor = XBlue,
                            unfocusedIndicatorColor = XDivider
                        ),
                        shape = RoundedCornerShape(24.dp),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("dm_input_field")
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (chatText.isNotBlank()) {
                                currentDMs.add(SingleDM(true, chatText.trim(), "À l'instant"))
                                val reply = if (conv.isBot) {
                                    "⚡ [Anass Bot DM] : Bien reçu ! Anass Core traite votre message avec priorité Ultra."
                                } else {
                                    "Merci pour votre message ! Transmis à la direction."
                                }
                                currentDMs.add(SingleDM(false, reply, "À l'instant"))
                                chatText = ""
                            }
                        },
                        enabled = chatText.isNotBlank(),
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(if (chatText.isNotBlank()) XBlue else Color(0xFF27272A))
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = "Envoyer",
                            tint = Color.White,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(XBlack)
        ) {
            Text(
                text = "Messages",
                color = XTextPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 20.sp,
                modifier = Modifier.padding(16.dp)
            )

            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(conversations, key = { it.id }) { conv ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { activeConversation = conv }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(46.dp)
                                .clip(CircleShape)
                                .background(if (conv.isBot) Color(0xFF03221C) else Color(0xFF1E293B)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = conv.avatarInitial,
                                color = Color.White,
                                fontSize = 20.sp
                            )
                        }

                        Spacer(modifier = Modifier.width(12.dp))

                        Column(modifier = Modifier.weight(1f)) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = conv.name,
                                    color = XTextPrimary,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                if (conv.isVerified) {
                                    Icon(
                                        imageVector = Icons.Filled.Verified,
                                        contentDescription = null,
                                        tint = if (conv.isBot) AnassBotCyan else BadgeVerifiedGold,
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = conv.handle,
                                    color = XTextSecondary,
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.weight(1f))
                                Text(
                                    text = conv.time,
                                    color = XTextSecondary,
                                    fontSize = 11.sp
                                )
                            }

                            Spacer(modifier = Modifier.height(3.dp))

                            Text(
                                text = conv.lastMessage,
                                color = XTextSecondary,
                                fontSize = 13.sp,
                                maxLines = 1
                            )
                        }
                    }

                    HorizontalDivider(color = XDivider, thickness = 0.5.dp)
                }
            }
        }
    }
}
