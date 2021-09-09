package io.github.bbang208.cleanarchitecture.data.source

import androidx.lifecycle.LiveData
import io.github.bbang208.cleanarchitecture.data.ApiResponse
import io.github.bbang208.cleanarchitecture.data.ApiSuccessResponse
import io.github.bbang208.cleanarchitecture.data.NetworkBoundResource
import io.github.bbang208.cleanarchitecture.data.Resource
import io.github.bbang208.cleanarchitecture.data.models.DateModel
import io.github.bbang208.cleanarchitecture.data.source.remote.TestApiService
import javax.inject.Inject

class TestRepository @Inject constructor(
    private val testApiService: TestApiService
) {

    fun getTime(): LiveData<Resource<DateModel>> {
        return object : NetworkBoundResource<DateModel, DateModel>() {
            override suspend fun processResponse(response: ApiSuccessResponse<DateModel>): DateModel {
                return response.body
            }

            override suspend fun createCall(): LiveData<ApiResponse<DateModel>> {
                return testApiService.getTime()
            }

        }.asLiveData()
    }
}