package com.jindvir.dieta.retrofit

import com.jindvir.dieta.modals.RandomRecipeApiResponse

interface RandomRecipeResponseListener {

    fun didFetch(response : RandomRecipeApiResponse, message: String)
    fun didError(message: String)

}