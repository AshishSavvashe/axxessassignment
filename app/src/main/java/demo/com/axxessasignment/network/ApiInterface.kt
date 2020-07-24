package demo.com.axxessasignment.network

import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*



interface ApiInterface {

    @GET("search/1")
    fun getImages(
        @Header("Authorization") Authentication: String,
        @Query("q") q: String
    ): Call<JsonObject>
}
