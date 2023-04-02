package com.qcuncle.teenstudy.service

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request

class HtmlImpl : IHtml {
    override suspend fun getHtmlBody(url: String): String? {
        return withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                OkHttpClient().newCall(request)
                    .execute().body
                    ?.string()
            } catch (e: Exception) {
                null
            }
        }
    }
}