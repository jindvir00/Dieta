package com.jindvir.dieta.retrofit

import com.jindvir.dieta.modals.information.RecipeDetailsResponse

interface RecipeDetailsListener {
    fun didFetch(response: RecipeDetailsResponse, message: String)
    fun didError(message: String)
}