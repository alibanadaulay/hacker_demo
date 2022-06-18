package com.ali.hacker_demo.di

import com.ali.hacker_demo.domain.GetHackerNewByIdUsesCaseIml
import com.ali.hacker_demo.domain.GetHackerNewsByIdUsesCase
import com.ali.hacker_demo.domain.GetTopStoryUsesCase
import com.ali.hacker_demo.domain.GetTopStoryUsesCaseImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainBinds {

    @Binds
    abstract fun bindGetTopStoryUsesCase(
        getTopStoryUsesCaseImpl: GetTopStoryUsesCaseImpl
    ): GetTopStoryUsesCase

    @Binds
    abstract fun bindGetHackerNewByIdUsesCase(
        getHackerNewByIdUsesCaseIml: GetHackerNewByIdUsesCaseIml
    ): GetHackerNewsByIdUsesCase
}