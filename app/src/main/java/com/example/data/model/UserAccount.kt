package com.example.data.model

enum class SubscriptionTier(val title: String, val priceMonthly: String) {
    NORMAL("Normal", "Gratuit (0 $)"),
    PRO("Pro", "1.20 $ / mois"),
    ULTRA("Ultra", "6.50 $ / mois")
}

data class UserAccount(
    val username: String,
    val displayName: String,
    val handle: String,
    val bio: String,
    val location: String = "Paris / Silicon Valley",
    val website: String = "anasscore.x.ai",
    val joinedDate: String = "Rejoint en Janvier 2026",
    val followersCount: Long,
    val followingCount: Long,
    val isCreator: Boolean = false,
    val isVerifiedBlue: Boolean = false,
    val isVerifiedGold: Boolean = false,
    val isVerifiedGrey: Boolean = false,
    val isVerifiedUltra: Boolean = false,
    val tier: SubscriptionTier = SubscriptionTier.NORMAL,
    val isUltraFree: Boolean = false,
    val avatarInitial: String = "A",
    val bannerColor: Long = 0xFF0F172A
) {
    fun formatFollowers(): String {
        return when {
            followersCount >= 1_000_000_000L -> {
                val billions = followersCount.toDouble() / 1_000_000_000.0
                if (billions % 1.0 == 0.0) "${billions.toLong()} Md" else String.format("%.1f Md", billions)
            }
            followersCount >= 1_000_000L -> {
                val millions = followersCount.toDouble() / 1_000_000.0
                if (millions % 1.0 == 0.0) "${millions.toLong()} M" else String.format("%.1f M", millions)
            }
            followersCount >= 1_000L -> {
                val thousands = followersCount.toDouble() / 1_000.0
                if (thousands % 1.0 == 0.0) "${thousands.toLong()} k" else String.format("%.1f k", thousands)
            }
            else -> followersCount.toString()
        }
    }

    fun formatFollowing(): String {
        return when {
            followingCount >= 1_000_000L -> "${followingCount / 1_000_000} M"
            followingCount >= 1_000L -> "${followingCount / 1_000} k"
            else -> followingCount.toString()
        }
    }

    companion object {
        val CREATOR_OFFICIAL = UserAccount(
            username = "Anass_Official",
            displayName = "Anass",
            handle = "@Anass_Official",
            bio = "🚀 Fondateur & Créateur Officiel de Anass Core. Maître de l'algorithme et d'Anass Bot. Accès anticipé illimité activé. ⚡",
            location = "Metaverse & Silicon Core",
            website = "https://anass.core",
            joinedDate = "Rejoint le jour de la création (2026)",
            followersCount = 12_000_000_000L, // 12 Milliards de followers !
            followingCount = 1L,
            isCreator = true,
            isVerifiedBlue = true,
            isVerifiedGold = true,
            isVerifiedGrey = true,
            isVerifiedUltra = true,
            tier = SubscriptionTier.ULTRA,
            isUltraFree = true,
            avatarInitial = "👑",
            bannerColor = 0xFF1E1B4B
        )

        val DEFAULT_GUEST = UserAccount(
            username = "Invité_Core",
            displayName = "Nouvel Utilisateur",
            handle = "@visiteur_core",
            bio = "Bienvenue sur Anass Core ! Connectez-vous avec Anass_Official / SecretlySecret pour le compte créateur.",
            location = "France",
            website = "",
            joinedDate = "Rejoint en Septembre 2026",
            followersCount = 142L,
            followingCount = 89L,
            isCreator = false,
            isVerifiedBlue = false,
            isVerifiedGold = false,
            isVerifiedGrey = false,
            isVerifiedUltra = false,
            tier = SubscriptionTier.NORMAL,
            isUltraFree = false,
            avatarInitial = "U",
            bannerColor = 0xFF1F2937
        )
    }
}
