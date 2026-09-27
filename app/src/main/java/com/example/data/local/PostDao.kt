package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface PostDao {
    @Query("SELECT * FROM posts ORDER BY isPinned DESC, createdAtMillis DESC")
    fun getAllPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE isCreator = 1 OR tagCategory = 'FOLLOWING' ORDER BY isPinned DESC, createdAtMillis DESC")
    fun getFollowingPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE authorHandle = :handle ORDER BY createdAtMillis DESC")
    fun getPostsByAuthor(handle: String): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE isBookmarked = 1 ORDER BY createdAtMillis DESC")
    fun getBookmarkedPosts(): Flow<List<PostEntity>>

    @Query("SELECT * FROM posts WHERE isLiked = 1 ORDER BY createdAtMillis DESC")
    fun getLikedPosts(): Flow<List<PostEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPost(post: PostEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPosts(posts: List<PostEntity>)

    @Update
    suspend fun updatePost(post: PostEntity)

    @Query("UPDATE posts SET isLiked = :isLiked, likesCount = :likesCount WHERE id = :id")
    suspend fun updateLike(id: Long, isLiked: Boolean, likesCount: Long)

    @Query("UPDATE posts SET isRetweeted = :isRetweeted, retweetsCount = :retweetsCount WHERE id = :id")
    suspend fun updateRetweet(id: Long, isRetweeted: Boolean, retweetsCount: Long)

    @Query("UPDATE posts SET isBookmarked = :isBookmarked WHERE id = :id")
    suspend fun updateBookmark(id: Long, isBookmarked: Boolean)

    @Query("DELETE FROM posts WHERE id = :id")
    suspend fun deletePost(id: Long)

    @Query("SELECT COUNT(*) FROM posts")
    suspend fun getPostCount(): Int
}
