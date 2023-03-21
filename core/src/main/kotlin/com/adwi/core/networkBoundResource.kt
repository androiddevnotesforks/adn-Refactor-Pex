package com.adwi.core

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
inline fun <ResultType, RequestType> networkBoundResource(
    crossinline query: () -> Flow<ResultType>,
    crossinline fetch: suspend () -> RequestType,
    crossinline saveFetchResult: suspend (RequestType) -> Unit,
    crossinline shouldFetch: (ResultType) -> Boolean = { true },
    crossinline onFetchSuccess: () -> Unit = { },
    crossinline onFetchFailed: (Throwable) -> Unit = { }
) = channelFlow {
    val data = query().first()

    if (shouldFetch(data)) {
        val loading = launch {
            query().collect { send(DataState.Loading(it)) }
        }
        try {
            delay(2000)
            saveFetchResult(fetch())
            onFetchSuccess()
            loading.cancel()
            query().collect { send(DataState.Success(it)) }
        } catch (t: Throwable) {
            onFetchFailed(t)
            loading.cancel()
            query().collect { send(DataState.Error(t, it)) }
        }
    } else {
        query().collect { send(DataState.Success(it)) }
    }
}