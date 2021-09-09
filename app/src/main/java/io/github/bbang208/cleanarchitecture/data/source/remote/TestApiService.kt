package io.github.bbang208.cleanarchitecture.data.source.remote

import androidx.lifecycle.LiveData
import io.github.bbang208.cleanarchitecture.data.ApiResponse
import io.github.bbang208.cleanarchitecture.data.models.DateModel
import retrofit2.http.GET

interface TestApiService {

    @GET("/")
    fun getTime(): LiveData<ApiResponse<DateModel>>
}