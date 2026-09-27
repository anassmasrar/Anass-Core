package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
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
import com.example.ui.theme.XCardSurface
import com.example.ui.theme.XDivider
import com.example.ui.theme.XTextPrimary
import com.example.ui.theme.XTextSecondary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthScreen(
    currentUser: UserAccount,
    onLoginSuccess: (UserAccount) -> Unit,
    onBack: () -> Unit
) {
    var selectedTab by remember { mutableIntStateOf(0) } // 0: Connexion, 1: Inscription
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var successCreatorBanner by remember { mutableStateOf(false) }

    fun performAuth() {
        val trimmedUser = username.trim()
        val trimmedPass = password.trim()

        if (trimmedUser.isEmpty()) {
            errorMessage = "Veuillez entrer un nom d'utilisateur."
            return
        }
        if (trimmedPass.isEmpty()) {
            errorMessage = "Veuillez entrer un mot de passe."
            return
        }

        // Exact Easter Egg / Creator Account requirement:
        // Nom d'utilisateur: Anass_Official
        // Mot de passe: SecretlySecret
        if (trimmedUser.equals("Anass_Official", ignoreCase = true) && trimmedPass == "SecretlySecret") {
            successCreatorBanner = true
            errorMessage = null
            onLoginSuccess(UserAccount.CREATOR_OFFICIAL)
        } else {
            // Normal User login / registration
            errorMessage = null
            val normalUser = UserAccount(
                username = trimmedUser,
                displayName = trimmedUser,
                handle = "@$trimmedUser",
                bio = "Membre de la communauté Anass Core.",
                followersCount = 142L,
                followingCount = 45L,
                isCreator = false,
                isVerifiedBlue = false,
                isVerifiedGold = false,
                isVerifiedGrey = false,
                isVerifiedUltra = false,
                tier = SubscriptionTier.NORMAL,
                isUltraFree = false,
                avatarInitial = trimmedUser.take(1).uppercase()
            )
            onLoginSuccess(normalUser)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Authentification Anass Core",
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
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(10.dp))

            // Logo Header
            Box(
                modifier = Modifier
                    .size(68.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF0F172A))
                    .border(2.dp, XBlue, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "𝔸",
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = "Rejoignez Anass Core",
                color = XTextPrimary,
                fontWeight = FontWeight.Black,
                fontSize = 22.sp
            )

            Text(
                text = "Le réseau social nouvelle génération",
                color = XTextSecondary,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Tabs: Connexion / Inscription
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = XBlack,
                contentColor = XBlue,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = XBlue
                    )
                },
                divider = { HorizontalDivider(color = XDivider) }
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = {
                        Text(
                            text = "Connexion",
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTab == 0) XBlue else XTextSecondary
                        )
                    }
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = {
                        Text(
                            text = "Inscription",
                            fontWeight = FontWeight.Bold,
                            color = if (selectedTab == 1) XBlue else XTextSecondary
                        )
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Input: Nom d'utilisateur
            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                    errorMessage = null
                },
                label = { Text("Nom d'utilisateur") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Person,
                        contentDescription = null,
                        tint = XTextSecondary
                    )
                },
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = XCardSurface,
                    unfocusedContainerColor = XCardSurface,
                    focusedTextColor = XTextPrimary,
                    unfocusedTextColor = XTextPrimary,
                    focusedIndicatorColor = XBlue,
                    unfocusedIndicatorColor = XDivider,
                    focusedLabelColor = XBlue,
                    unfocusedLabelColor = XTextSecondary
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("username_input")
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input: Mot de passe
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    errorMessage = null
                },
                label = { Text("Mot de passe") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = null,
                        tint = XTextSecondary
                    )
                },
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        Icon(
                            imageVector = if (passwordVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = "Afficher mot de passe",
                            tint = XTextSecondary
                        )
                    }
                },
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = { performAuth() }),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = XCardSurface,
                    unfocusedContainerColor = XCardSurface,
                    focusedTextColor = XTextPrimary,
                    unfocusedTextColor = XTextPrimary,
                    focusedIndicatorColor = XBlue,
                    unfocusedIndicatorColor = XDivider,
                    focusedLabelColor = XBlue,
                    unfocusedLabelColor = XTextSecondary
                ),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("password_input")
            )

            if (errorMessage != null) {
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = errorMessage ?: "",
                    color = Color(0xFFEF4444),
                    fontSize = 13.sp,
                    textAlign = TextAlign.Center
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Submit Button
            Button(
                onClick = { performAuth() },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                ),
                shape = RoundedCornerShape(24.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .testTag("auth_submit_button")
            ) {
                Text(
                    text = if (selectedTab == 0) "Se connecter" else "Créer un compte",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Creator Easter Egg Fast-Autofill Card
            Card(
                colors = CardDefaults.cardColors(containerColor = Color(0xFF0F172A)),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .border(
                        1.dp,
                        Brush.horizontalGradient(listOf(BadgeUltraGradientStart, UltraGoldAccent)),
                        RoundedCornerShape(16.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Filled.AutoAwesome,
                            contentDescription = null,
                            tint = UltraGoldAccent,
                            modifier = Modifier.size(20.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Accès Secret Créateur Officiel",
                            color = UltraGoldAccent,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Entrez :\n• Nom d'utilisateur : Anass_Official\n• Mot de passe : SecretlySecret",
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            username = "Anass_Official"
                            password = "SecretlySecret"
                            performAuth()
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1E1B4B),
                            contentColor = AnassBotCyan
                        ),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .border(1.dp, AnassBotCyan.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
                            .testTag("autofill_creator_button")
                    ) {
                        Text(
                            text = "⚡ Connexion Instantanée Anass Créateur",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            // Success creator alert
            AnimatedVisibility(visible = successCreatorBanner) {
                Card(
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF064E3B)),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Filled.CheckCircle,
                            contentDescription = null,
                            tint = Color(0xFF34D399),
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Bienvenue Créateur Suprême !",
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "12 Milliards de followers & Ultra gratuit activés.",
                                color = Color(0xFFA7F3D0),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}
