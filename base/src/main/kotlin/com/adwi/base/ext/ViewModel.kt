package com.adwi.base.ext

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

fun ViewModel.onDispatcher(dispatcher: CoroutineDispatcher, body: suspend () -> Unit): Job {
    return viewModelScope.launch(dispatcher) {
        body()
    }
}

val <T> T.exhaustive: T
    get() = this