package com.vama.example.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface VamaApi {
    @GET("public/images/paginate")
    suspend fun getImages(
        @Query("cursorIndex") cursorIndex: Int,
        @Query("limit") limit: Int
    ) : ImageList
}

object Network {

    val baseUrl = "https://api-staging-ypidedhdqa-uc.a.run.app/"

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            // we need to add converter factory to
            // convert JSON object to Java object
            .build()
    }
}

data class ImageList (
    val imageURLs: MutableList<String>
)