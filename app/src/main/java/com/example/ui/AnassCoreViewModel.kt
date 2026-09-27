package com.example.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.AppDatabase
import com.example.data.local.PostEntity
import com.example.data.model.SubscriptionTier
import com.example.data.model.UserAccount
import com.example.data.repository.AnassCoreRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class AnassCoreViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AnassCoreRepository

    private val _currentUser = MutableStateFlow(UserAccount.CREATOR_OFFICIAL)
    val currentUser: StateFlow<UserAccount> = _currentUser.asStateFlow()

    val posts: StateFlow<List<PostEntity>>

    init {
        val database = AppDatabase.getDatabase(application, viewModelScope)
        repository = AnassCoreRepository(database.postDao())

        posts = repository.allPosts.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

        viewModelScope.launch {
            repository.ensureDatabaseSeeded()
        }
    }

    fun likePost(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleLike(post)
        }
    }

    fun retweetPost(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleRetweet(post)
        }
    }

    fun bookmarkPost(post: PostEntity) {
        viewModelScope.launch {
            repository.toggleBookmark(post)
        }
    }

    fun createPost(content: String, mediaType: String? = null) {
        val user = _currentUser.value
        val newPost = PostEntity(
            authorName = user.displayName,
            authorHandle = user.handle,
            authorAvatarInitial = user.avatarInitial,
            isCreator = user.isCreator,
            isVerifiedBlue = user.isVerifiedBlue,
            isVerifiedGold = user.isVerifiedGold,
            isVerifiedGrey = user.isVerifiedGrey,
            isVerifiedUltra = user.isVerifiedUltra,
            content = content,
            timestamp = "À l'instant",
            likesCount = 0,
            retweetsCount = 0,
            repliesCount = 0,
            viewsCount = if (user.isCreator) 1_500_000L else 12L,
            isLiked = false,
            isRetweeted = false,
            isBookmarked = false,
            tagCategory = "FOR_YOU",
            mediaType = mediaType,
            createdAtMillis = System.currentTimeMillis()
        )

        viewModelScope.launch {
            repository.createPost(newPost)
        }
    }

    fun loginUser(user: UserAccount) {
        _currentUser.value = user
    }

    fun selectSubscriptionTier(tier: SubscriptionTier) {
        val current = _currentUser.value
        if (current.isCreator) {
            // Creator always has Ultra free
            return
        }
        _currentUser.value = current.copy(
            tier = tier,
            isVerifiedBlue = tier == SubscriptionTier.PRO || tier == SubscriptionTier.ULTRA,
            isVerifiedGold = tier == SubscriptionTier.ULTRA,
            isVerifiedUltra = tier == SubscriptionTier.ULTRA
        )
    }
}
