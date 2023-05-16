package com.jindvir.dieta.retrofit

import android.content.Context
import com.jindvir.dieta.R
import com.jindvir.dieta.modals.RandomRecipeApiResponse
import com.jindvir.dieta.modals.information.RecipeDetailsResponse
import com.jindvir.dieta.modals.similar.RecipeSimilarResponse
import com.jindvir.dieta.modals.similar.RecipeSimilarResponseItem
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

class RequestManager(private var context: Context?) {

    private val retrofit: Retrofit =
        Retrofit.Builder().baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()


    fun getRandomReciepe(listener: RandomRecipeResponseListener , tags: ArrayList<String>) {
        val callApiInterface: CallRandomRecipes = retrofit.create(CallRandomRecipes::class.java)
        context?.let {
            callApiInterface.getData(it.getString(R.string.apiKey), "10" , tags)
                ?.enqueue(object : Callback<RandomRecipeApiResponse?> {
                    override fun onResponse(
                        call: Call<RandomRecipeApiResponse?>,
                        response: Response<RandomRecipeApiResponse?>
                    ) {
                        if (!response.isSuccessful) {
                            listener.didError(response.message())
                            return
                        }
                        response.body()?.let { listener.didFetch(it, response.message()) }
                    }

                    override fun onFailure(call: Call<RandomRecipeApiResponse?>, t: Throwable) {
                        t.localizedMessage?.let { it1 -> listener.didError(it1) }
                    }
                })
        }
    }

    fun getRecipeDetails(recipeDetailsListener: RecipeDetailsListener ,  id: Int){
        val callRecipeDetails : CallRecipeDetails = retrofit.create<CallRecipeDetails>(CallRecipeDetails::class.java)

            context?.let {
                callRecipeDetails.getInformation(id , it.getString(R.string.apiKey))
                    ?.enqueue(object : Callback<RecipeDetailsResponse?> {
                        override fun onResponse(
                            call: Call<RecipeDetailsResponse?>,
                            response: Response<RecipeDetailsResponse?>
                        ) {
                            if (!response.isSuccessful) {
                                recipeDetailsListener.didError(response.message())
                                return
                            }
                            response.body()?.let { recipeDetailsListener.didFetch(it, response.message()) }
                        }

                        override fun onFailure(call: Call<RecipeDetailsResponse?>, t: Throwable) {
                            t.localizedMessage?.let { it1 -> recipeDetailsListener.didError(it1) }
                        }
                    })
            }

    }

    fun getSimilarRecipe(recipeSimilarListener: RecipeSimilarListener , id : Int) {
        val callSimilarRecipe : CallSimilarRecipe = retrofit.create(CallSimilarRecipe::class.java)
            context?.let { callSimilarRecipe.getInformation(id , "5" , it.getString(R.string.apiKey))
                ?.enqueue(object: Callback<ArrayList<RecipeSimilarResponseItem>>{
                    override fun onResponse(
                        call: Call<ArrayList<RecipeSimilarResponseItem>>,
                        response: Response<ArrayList<RecipeSimilarResponseItem>>
                    ) {
                        if (!response.isSuccessful){
                            recipeSimilarListener.didError(response.message())
                        }
                        response.body()
                            ?.let { it1 -> recipeSimilarListener.didFetch(it1, response.message()) }
                    }

                    override fun onFailure(
                        call: Call<ArrayList<RecipeSimilarResponseItem>>,
                        t: Throwable
                    ) {
                        t.localizedMessage?.let { it1 -> recipeSimilarListener.didError(it1) }
                    }

                })
            }

    }

    interface CallRecipeDetails{
        @GET("recipes/{id}/information")
        fun getInformation(
            @Path("id") id: Int,
            @Query("apiKey") apiKey: String
        ): Call<RecipeDetailsResponse?>?
    }

    interface CallSimilarRecipe{
        @GET("recipes/{id}/similar")
        fun getInformation(
            @Path("id") id: Int,
            @Query("number") number: String,
            @Query("apiKey") apiKey: String
        ): Call<ArrayList<RecipeSimilarResponseItem>>?
    }



}