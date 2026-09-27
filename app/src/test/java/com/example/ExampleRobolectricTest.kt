package com.example

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.example.data.model.SubscriptionTier
import com.example.data.model.UserAccount
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Anass Core", appName)
    }

    @Test
    fun `creator account has 12 billion followers and all badges and ultra tier`() {
        val creator = UserAccount.CREATOR_OFFICIAL
        assertEquals("Anass_Official", creator.username)
        assertEquals("Anass", creator.displayName)
        assertEquals(12_000_000_000L, creator.followersCount)
        assertEquals("12 Md", creator.formatFollowers())
        assertTrue(creator.isCreator)
        assertTrue(creator.isVerifiedBlue)
        assertTrue(creator.isVerifiedGold)
        assertTrue(creator.isVerifiedGrey)
        assertTrue(creator.isVerifiedUltra)
        assertEquals(SubscriptionTier.ULTRA, creator.tier)
        assertTrue(creator.isUltraFree)
    }
}
