/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.bbang208.cleanarchitecture.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * A generic class that can provide a resource backed by both the sqlite database and the network.
 *
 *
 * You can read more about it in the [Architecture
 * Guide](https://developer.android.com/arch).
 * @param <ResultType>
 * @param <RequestType>
</RequestType></ResultType> */
abstract class NetworkBoundResource<ResultType, RequestType>
constructor() {

    private val result = MediatorLiveData<Resource<ResultType>>().apply {
        postValue(Resource.loading(null))
    }

    init {
        CoroutineScope(Dispatchers.Main).launch {
            fetchFromNetwork()
        }
    }

    private fun setValue(newValue: Resource<ResultType>) {
        if (result.value != newValue) {
            result.postValue(newValue)
        }
    }

    private suspend fun fetchFromNetwork() {
        val apiResponse =
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) { createCall() }

        result.addSource(apiResponse) { response ->
            when (response) {
                is ApiSuccessResponse -> {
                    CoroutineScope(Dispatchers.IO).launch {
                        setValue(Resource.success(processResponse(response)))
                    }
                }
                is ApiEmptyResponse -> {
                    setValue(Resource.success(null))
                }
                is ApiErrorResponse -> {
                    onFetchFailed()
                    val errorMessage = when (response.errorCode) {
                        -1 -> "서버 접속에 실패했습니다.\n잠시 후 다시 시도해주세요."
                        401 -> "토큰이 만료되었습니다.\n다시 로그인해주세요."
                        else -> response.errorMessage
                    }
                    setValue(Resource.error(errorMessage, null, response.errorCode))
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    fun asLiveData() = result as LiveData<Resource<ResultType>>

    protected abstract suspend fun processResponse(response: ApiSuccessResponse<RequestType>): ResultType

    protected abstract suspend fun createCall(): LiveData<ApiResponse<RequestType>>

    //Room, Paging 안 써서 필요없을 듯..
    /*
    @MainThread
    protected abstract fun shouldFetch(data: ResultType?): Boolean

    @MainThread
    protected abstract fun loadFromDb(): LiveData<ResultType>
     */
}
