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

package com.dylanc.activityresult.launcher.sample.kotlin.launcher

import java.io.Serializable

/**
 * @author Dylan Cai
 */


interface OnFilterValueListener : Serializable {
  fun onFilterValue(value: String): Boolean
}

class InputTextRequest(
  val name: String,
  val title: String = name,
  val hint: String? = null,
  val value: String? = null,
  val listener: OnFilterValueListener? = null
)