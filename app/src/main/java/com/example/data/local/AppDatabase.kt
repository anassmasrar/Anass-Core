package com.example.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [PostEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun postDao(): PostDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "anass_core_database"
                )
                    .addCallback(DatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class DatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateInitialPosts(database.postDao())
                    }
                }
            }
        }

        suspend fun populateInitialPosts(dao: PostDao) {
            val now = System.currentTimeMillis()
            val initialPosts = listOf(
                PostEntity(
                    authorName = "Anass",
                    authorHandle = "@Anass_Official",
                    authorAvatarInitial = "👑",
                    isCreator = true,
                    isVerifiedBlue = true,
                    isVerifiedGold = true,
                    isVerifiedGrey = true,
                    isVerifiedUltra = true,
                    content = "Bienvenue officiellement sur Anass Core ! 🚀\n\nL'algorithme nouvelle génération est actif, Anass Bot est en ligne sans limites et notre communauté dépasse les 12 Milliards de membres. Profitez de l'expérience Ultra !",
                    timestamp = "Épinglé",
                    likesCount = 894_200_000L,
                    retweetsCount = 312_500_000L,
                    repliesCount = 48_100_000L,
                    viewsCount = 12_000_000_000L,
                    isLiked = true,
                    isRetweeted = true,
                    isPinned = true,
                    tagCategory = "OFFICIAL",
                    mediaType = "IMAGE_ANASS_CORE",
                    createdAtMillis = now
                ),
                PostEntity(
                    authorName = "Anass Bot",
                    authorHandle = "@Anass_Bot",
                    authorAvatarInitial = "⚡",
                    isCreator = false,
                    isVerifiedBlue = true,
                    isVerifiedGold = true,
                    isVerifiedGrey = false,
                    isVerifiedUltra = true,
                    content = "Salutations aux citoyens d'Anass Core ! 🤖\n\nJe remplace officiellement l'ancien Grok. Je suis plus rapide, plus vif, avec un sens de l'humour aiguisé et une intelligence hors du commun. Venez me tester dans l'onglet Anass Bot !",
                    timestamp = "15 min",
                    likesCount = 42_500L,
                    retweetsCount = 18_200L,
                    repliesCount = 6_300L,
                    viewsCount = 1_450_000L,
                    tagCategory = "FOR_YOU",
                    mediaType = "ANASS_BOT",
                    createdAtMillis = now - 900_000
                ),
                PostEntity(
                    authorName = "Tech Insider",
                    authorHandle = "@tech_insider",
                    authorAvatarInitial = "T",
                    isCreator = false,
                    isVerifiedBlue = true,
                    isVerifiedGold = false,
                    isVerifiedGrey = false,
                    isVerifiedUltra = false,
                    content = "Rupture totale : Anass Core déploie son offre d'abonnement exclusive :\n\n- Normal : Gratuit pour tous\n- Pro : 1.20 $ / mois\n- Ultra : 6.50 $ / mois avec accès anticipé illimité\n\nEt devinez quoi ? Le créateur @Anass_Official dispose d'Ultra à vie ! 🔥",
                    timestamp = "1h",
                    likesCount = 18_400L,
                    retweetsCount = 5_120L,
                    repliesCount = 1_230L,
                    viewsCount = 420_000L,
                    tagCategory = "FOR_YOU",
                    createdAtMillis = now - 3_600_000
                ),
                PostEntity(
                    authorName = "Tendances Mondiales",
                    authorHandle = "@trends_global",
                    authorAvatarInitial = "📈",
                    isCreator = false,
                    isVerifiedBlue = true,
                    isVerifiedGold = false,
                    isVerifiedGrey = false,
                    isVerifiedUltra = false,
                    content = "Top 5 des tendances en direct sur Anass Core :\n\n1. #AnassCore\n2. #AnassBotUltra\n3. #12Milliards\n4. #SecretlySecret\n5. #NouvelleEre",
                    timestamp = "3h",
                    likesCount = 9_320L,
                    retweetsCount = 2_890L,
                    repliesCount = 890L,
                    viewsCount = 280_000L,
                    tagCategory = "FOR_YOU",
                    createdAtMillis = now - 10_800_000
                ),
                PostEntity(
                    authorName = "Sarah Developer",
                    authorHandle = "@sarah_dev",
                    authorAvatarInitial = "S",
                    isCreator = false,
                    isVerifiedBlue = true,
                    isVerifiedGold = false,
                    isVerifiedGrey = false,
                    isVerifiedUltra = false,
                    content = "L'interface en noir OLED absolu (#000000) et la fluidité d'Anass Core sous Jetpack Compose, c'est exactement ce qu'on attendait. Le bouton Anass Bot au milieu est génial !",
                    timestamp = "5h",
                    likesCount = 4_890L,
                    retweetsCount = 980L,
                    repliesCount = 310L,
                    viewsCount = 95_000L,
                    tagCategory = "FOLLOWING",
                    createdAtMillis = now - 18_000_000
                )
            )
            dao.insertPosts(initialPosts)
        }
    }
}
