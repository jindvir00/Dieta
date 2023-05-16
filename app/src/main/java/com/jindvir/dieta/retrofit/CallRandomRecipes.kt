package com.jindvir.dieta.retrofit

import com.jindvir.dieta.modals.RandomRecipeApiResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CallRandomRecipes {

    @GET("recipes/random")
    fun getData(
        @Query("apiKey") apiKey: String,
        @Query("number") number: String,
        @Query("tags") tags: ArrayList<String>
    ): Call<RandomRecipeApiResponse?>?

}