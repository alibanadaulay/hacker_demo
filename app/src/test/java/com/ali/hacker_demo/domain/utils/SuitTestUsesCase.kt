package com.ali.hacker_demo.domain.utils

import com.ali.hacker_demo.domain.domain.GetHackerNewByIdUsesCaseImlTest
import com.ali.hacker_demo.domain.domain.GetTopStoryUsesCaseImplTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.platform.suite.api.SelectClasses
import org.junit.platform.suite.api.Suite


@Suite
@SelectClasses(
    GetHackerNewByIdUsesCaseImlTest::class,
    GetTopStoryUsesCaseImplTest::class
)
@ExperimentalCoroutinesApi
class SuiteTest