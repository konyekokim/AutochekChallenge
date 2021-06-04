package com.konyekokim.core.data

import kotlinx.coroutines.CoroutineDispatcher

data class AppCoroutineDispatcher(
    val io: CoroutineDispatcher,
    val computation: CoroutineDispatcher,
    val main: CoroutineDispatcher
)