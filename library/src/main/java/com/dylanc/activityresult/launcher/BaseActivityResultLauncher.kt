/*
 * Copyright (c) 2021. Dylan Cai
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

@file:Suppress("unused")

package com.dylanc.activityresult.launcher

import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

/**
 * @author Dylan Cai
 */

suspend fun <I, O> BaseActivityResultLauncher<I, O>.launchForResult(input: I?) =
  suspendCancellableCoroutine<O> { continuation ->
    launch(input) {
      if (it != null) {
        continuation.resume(it)
      } else {
        continuation.cancel()
      }
    }
  }

suspend fun <I, T> BaseActivityResultLauncher<I, List<T>>.launchForNonEmptyResult(input: I?) =
  suspendCancellableCoroutine<List<T>> { continuation ->
    launch(input) {
      if (it != null && it.isNotEmpty()) {
        continuation.resume(it)
      } else {
        continuation.cancel()
      }
    }
  }

suspend fun <I, O> BaseActivityResultLauncher<I, O>.launchForNullableResult(input: I?) =
  suspendCoroutine<O?> { continuation ->
    launch(input) { continuation.resume(it) }
  }

fun <I, O> BaseActivityResultLauncher<I, O>.launchForFlow(input: I?) =
  flow { emit(launchForResult(input)) }

fun <I, T> BaseActivityResultLauncher<I, List<T>>.launchForNonEmptyFlow(input: I?) =
  flow { emit(launchForNonEmptyResult(input)) }

fun <I, O> BaseActivityResultLauncher<I, O>.launchForNullableFlow(input: I?) =
  flow { emit(launchForNullableResult(input)) }