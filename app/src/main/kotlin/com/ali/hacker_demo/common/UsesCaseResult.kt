package com.ali.hacker_demo.common

sealed class UsesCaseResult<out T : Any> {
    class OnSuccess<out T : Any>(val value: T) : UsesCaseResult<T>()
    data class OnError(val err: Throwable) : UsesCaseResult<Nothing>()
}
