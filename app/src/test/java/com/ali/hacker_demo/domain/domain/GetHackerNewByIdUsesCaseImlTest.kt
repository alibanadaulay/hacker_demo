package com.ali.hacker_demo.domain.domain

import app.cash.turbine.test
import com.ali.hacker_demo.common.UsesCaseResult
import com.ali.hacker_demo.data_layer.api.IHackerNew
import com.ali.hacker_demo.data_layer.api.model.ResHackerNews
import com.ali.hacker_demo.domain.GetHackerNewByIdUsesCaseIml
import com.ali.hacker_demo.domain.GetTopStoryUsesCaseImpl
import com.ali.hacker_demo.domain.utils.TestCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.*

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineRule::class)
internal class GetHackerNewByIdUsesCaseImlTest {


    private val iHackerNew: IHackerNew = mockk()
    private lateinit var getHackerNewByIdUsesCaseIml: GetHackerNewByIdUsesCaseIml

    @BeforeEach
    fun setUp() {
        getHackerNewByIdUsesCaseIml = GetHackerNewByIdUsesCaseIml(iHackerNew)
    }

    @Test
    fun `getHackerById, Given value kids is empty, Result emit called is one`() = runTest {
        // Mock
        coEvery { iHackerNew.getItemById(any()) } returns ResHackerNews(
            time = 1,
            kids = ArrayList()
        )

        // Execute
        val count = getHackerNewByIdUsesCaseIml.invoke(10).count()

        // Result
        assertEquals(1, count)
    }

    @Test
    fun `getHackerById, Given value kids is not empty, Result emit called is two`() = runTest {
        // Given
        val comments: MutableList<Long> = mutableListOf()
        for (i in 0 until 20) {
            comments.add(i.toLong())
        }

        // Mock
        coEvery { iHackerNew.getItemById(any()) } returns ResHackerNews(
            time = 1,
            kids = comments
        )

        // Execute
        val count = getHackerNewByIdUsesCaseIml.invoke(10).count()

        // Result
        assertEquals(2, count)
    }

    @Test
    fun `getHackerById, Given value kids is not empty, Result emit called is two time and comments less than kids size`() =
        runTest {
            // Given
            val comments: MutableList<Long> = mutableListOf()
            for (i in 0 until 20) {
                comments.add(i.toLong())
            }
            comments.add(21)

            // Mock
            coEvery { iHackerNew.getItemById(any()) } returns ResHackerNews(
                time = 1,
                kids = comments
            )
            coEvery { iHackerNew.getItemById(21) } returns ResHackerNews(
                time = 1,
                kids = mutableListOf(),
                text = null
            )

            // Execute
            val count = getHackerNewByIdUsesCaseIml.invoke(10).test {
                val item = (awaitItem() as UsesCaseResult.OnSuccess)
                if (item.value.isCommentsIsLoaded) {
                    assertEquals(20, item.value.comments.size)
                }
                awaitEvent()
                awaitComplete()
            }

//             Result
//            assertEquals(2, count)
        }
}