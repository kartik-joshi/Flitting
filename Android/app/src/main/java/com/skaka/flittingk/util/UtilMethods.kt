package com.skaka.flittingk.util

import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.android.UI

//
//public class UtilMethods {
val BaseURL: String = "http://10.0.2.2:8000/flitting/"
val BaseURLImage: String = "http://10.0.2.2:8000/flitting"

fun launchAsync(block: suspend CoroutineScope.() -> Unit): Job {
    return launch(UI) {
        try {
            block()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

suspend fun <T> async(block: suspend CoroutineScope.() -> T): Deferred<T> {
    return async(CommonPool) { block() }
}

suspend fun <T> asyncAwait(block: suspend CoroutineScope.() -> T): T {
    return async(block).await()
}

fun <E> java.util.ArrayList<E>.clearAndAddAll(res: List<E>) {
    if (res.isEmpty()) return
    clear()
    addAll(res)
}