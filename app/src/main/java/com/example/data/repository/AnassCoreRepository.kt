package com.example.data.repository

import com.example.data.local.AppDatabase
import com.example.data.local.PostDao
import com.example.data.local.PostEntity
import kotlinx.coroutines.flow.Flow

class AnassCoreRepository(
    private val postDao: PostDao
) {
    val allPosts: Flow<List<PostEntity>> = postDao.getAllPosts()
    val followingPosts: Flow<List<PostEntity>> = postDao.getFollowingPosts()

    fun getPostsByAuthor(handle: String): Flow<List<PostEntity>> =
        postDao.getPostsByAuthor(handle)

    fun getBookmarkedPosts(): Flow<List<PostEntity>> =
        postDao.getBookmarkedPosts()

    fun getLikedPosts(): Flow<List<PostEntity>> =
        postDao.getLikedPosts()

    suspend fun toggleLike(post: PostEntity) {
        val newLiked = !post.isLiked
        val newCount = if (newLiked) post.likesCount + 1 else (post.likesCount - 1).coerceAtLeast(0)
        postDao.updateLike(post.id, newLiked, newCount)
    }

    suspend fun toggleRetweet(post: PostEntity) {
        val newRetweeted = !post.isRetweeted
        val newCount = if (newRetweeted) post.retweetsCount + 1 else (post.retweetsCount - 1).coerceAtLeast(0)
        postDao.updateRetweet(post.id, newRetweeted, newCount)
    }

    suspend fun toggleBookmark(post: PostEntity) {
        postDao.updateBookmark(post.id, !post.isBookmarked)
    }

    suspend fun createPost(post: PostEntity): Long {
        return postDao.insertPost(post)
    }

    suspend fun deletePost(id: Long) {
        postDao.deletePost(id)
    }

    suspend fun ensureDatabaseSeeded() {
        if (postDao.getPostCount() == 0) {
            AppDatabase.populateInitialPosts(postDao)
        }
    }
}
