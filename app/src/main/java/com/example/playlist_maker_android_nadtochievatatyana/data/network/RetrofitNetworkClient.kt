package com.example.playlist_maker_android_nadtochievatatyana.data.network

import com.example.playlist_maker_android_nadtochievatatyana.creator.Storage
import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchRequest
import com.example.playlist_maker_android_nadtochievatatyana.data.dto.TracksSearchResponse
import com.example.playlist_maker_android_nadtochievatatyana.domain.api.NetworkClient

class RetrofitNetworkClient(private val storage: Storage) : NetworkClient {

    override fun doRequest(dto: Any): BaseResponse {
        val request = dto as TracksSearchRequest
        val searchList = storage.search(request.expression)
        return TracksSearchResponse(searchList).apply { resultCode = 200 }
    }
}