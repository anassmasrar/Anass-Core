package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.BadgeUltraGradientEnd
import com.example.ui.theme.BadgeUltraGradientStart
import com.example.ui.theme.BadgeVerifiedBlue
import com.example.ui.theme.BadgeVerifiedGold
import com.example.ui.theme.BadgeVerifiedGrey

@Composable
fun VerificationBadgesRow(
    isVerifiedBlue: Boolean,
    isVerifiedGold: Boolean,
    isVerifiedGrey: Boolean,
    isVerifiedUltra: Boolean,
    isCreator: Boolean = false,
    modifier: Modifier = Modifier,
    iconSize: Int = 16
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if (isVerifiedBlue) {
            Icon(
                imageVector = Icons.Filled.Verified,
                contentDescription = "Vérifié Bleu",
                tint = BadgeVerifiedBlue,
                modifier = Modifier
                    .size(iconSize.dp)
                    .padding(horizontal = 1.dp)
            )
        }
        if (isVerifiedGold) {
            Icon(
                imageVector = Icons.Filled.Verified,
                contentDescription = "Vérifié Or Officiel",
                tint = BadgeVerifiedGold,
                modifier = Modifier
                    .size(iconSize.dp)
                    .padding(horizontal = 1.dp)
            )
        }
        if (isVerifiedGrey) {
            Icon(
                imageVector = Icons.Filled.Shield,
                contentDescription = "Gouvernement / Institution",
                tint = BadgeVerifiedGrey,
                modifier = Modifier
                    .size((iconSize - 2).dp)
                    .padding(horizontal = 1.dp)
            )
        }
        if (isVerifiedUltra) {
            Box(
                modifier = Modifier
                    .padding(start = 2.dp)
                    .clip(RoundedCornerShape(4.dp))
                    .background(
                        Brush.horizontalGradient(
                            listOf(BadgeUltraGradientStart, BadgeUltraGradientEnd)
                        )
                    )
                    .padding(horizontal = 4.dp, vertical = 1.dp),
                contentAlignment = Alignment.Center
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Filled.AutoAwesome,
                        contentDescription = "Ultra",
                        tint = Color.White,
                        modifier = Modifier.size(10.dp)
                    )
                    Spacer(modifier = Modifier.width(2.dp))
                    Text(
                        text = "ULTRA",
                        color = Color.White,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.Black
                    )
                }
            }
        }
        if (isCreator) {
            Box(
                modifier = Modifier
                    .padding(start = 4.dp)
                    .clip(RoundedCornerShape(6.dp))
                    .background(Color(0xFFFFD700).copy(alpha = 0.2f))
                    .padding(horizontal = 5.dp, vertical = 2.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "👑 CRÉATEUR",
                    color = Color(0xFFFFD700),
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
