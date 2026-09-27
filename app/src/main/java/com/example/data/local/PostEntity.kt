package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "posts")
data class PostEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val authorName: String,
    val authorHandle: String,
    val authorAvatarInitial: String = "A",
    val isCreator: Boolean = false,
    val isVerifiedBlue: Boolean = false,
    val isVerifiedGold: Boolean = false,
    val isVerifiedGrey: Boolean = false,
    val isVerifiedUltra: Boolean = false,
    val content: String,
    val timestamp: String,
    val likesCount: Long = 0,
    val retweetsCount: Long = 0,
    val repliesCount: Long = 0,
    val viewsCount: Long = 0,
    val isLiked: Boolean = false,
    val isRetweeted: Boolean = false,
    val isBookmarked: Boolean = false,
    val isPinned: Boolean = false,
    val tagCategory: String = "FOR_YOU",
    val mediaType: String? = null, // "IMAGE_ANASS_CORE", "STATS", "ANASS_BOT"
    val createdAtMillis: Long = System.currentTimeMillis()
)
