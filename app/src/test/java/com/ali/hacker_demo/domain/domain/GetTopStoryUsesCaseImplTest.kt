package com.ali.hacker_demo.domain.domain

import app.cash.turbine.test
import com.ali.hacker_demo.common.UsesCaseResult
import com.ali.hacker_demo.data_layer.api.IHackerNew
import com.ali.hacker_demo.data_layer.api.model.ResHackerNews
import com.ali.hacker_demo.domain.GetTopStoryUsesCaseImpl
import com.ali.hacker_demo.domain.utils.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import retrofit2.Response


@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineRule::class)
internal class GetTopStoryUsesCaseImplTest {

    private val iHackerNew: IHackerNew = mockk()
    private lateinit var getTopStoryUsesCaseImpl: GetTopStoryUsesCaseImpl

    @BeforeEach
    fun setUp() {
        getTopStoryUsesCaseImpl = GetTopStoryUsesCaseImpl(iHackerNew)
    }

    @Test
    fun `getTopStories, Result emit is called success`() = runTest {
        // Given
        val list: MutableList<Long> = mutableListOf()
        for (i in 0 until 20) {
            list.add(i.toLong())
        }

        // Mock
        coEvery { iHackerNew.getItemsTopStories() } returns Response.success(list)
        coEvery { iHackerNew.getItemById(any()) } returns ResHackerNews(
            time = 1,
            kids = ArrayList()
        )

        // Execute
        val count = getTopStoryUsesCaseImpl.invoke().count()

        // Result
        assertEquals(1, count)
    }

    @Test
    fun `getTopStories, Result emit is called 5 times`() = runTest {
        // Given
        val list: MutableList<Long> = mutableListOf()
        for (i in 0 until 82) {
            list.add(i.toLong())
        }

        // Mock
        coEvery { iHackerNew.getItemsTopStories() } returns Response.success(list)
        coEvery { iHackerNew.getItemById(any()) } returns ResHackerNews(
            time = 1,
            kids = ArrayList()
        )

        // Execute
        val count = getTopStoryUsesCaseImpl.invoke().count()

        // Result
        assertEquals(5, count)
    }


    @Test
    fun `getTopStories, Result emit error is called`() = runTest {
        // Given
        val list: MutableList<Long> = mutableListOf()
        for (i in 0 until 82) {
            list.add(i.toLong())
        }

        // Mock
        coEvery { iHackerNew.getItemsTopStories() } returns Response.error(
            401,
            ResponseBody.create(null, "gagal")
        )
        coEvery { iHackerNew.getItemById(any()) } returns ResHackerNews(
            time = 1,
            kids = ArrayList()
        )

        // Execute && Result
        getTopStoryUsesCaseImpl.invoke().test {
            assert(this.awaitItem() is UsesCaseResult.OnError)
            awaitComplete()
        }
    }
}