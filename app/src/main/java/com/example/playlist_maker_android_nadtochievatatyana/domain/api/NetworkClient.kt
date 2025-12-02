package com.example.playlist_maker_android_nadtochievatatyana.domain.api

import com.example.playlist_maker_android_nadtochievatatyana.data.network.BaseResponse

interface NetworkClient {
    suspend fun doRequest(dto: Any): BaseResponse
}