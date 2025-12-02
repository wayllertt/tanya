package com.example.playlist_maker_android_nadtochievatatyana.data.network

import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.NetworkClient
import com.example.playlist_maker_android_nadtochievatatyana.data.network.BaseResponse
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

class RetrofitNetworkClient : NetworkClient {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://itunes.apple.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(ITunesService::class.java)

    override suspend fun doRequest(dto: Any): BaseResponse {
        return if (dto is TracksSearchRequest) {
            val response = api.searchTracks(dto.expression)
            toBaseResponse(response)
        } else {
            BaseResponse().apply { resultCode = 400 }
        }
    }
    private fun toBaseResponse(response: Response<TracksSearchResponse>): BaseResponse {
        val body = response.body() ?: TracksSearchResponse(emptyList())
        return body.apply { resultCode = response.code() }
    }
}

private interface ITunesService {
    @GET("search")
    suspend fun searchTracks(
        @Query("term") term: String,
        @Query("entity") entity: String = "song",
    ): Response<TracksSearchResponse>
}