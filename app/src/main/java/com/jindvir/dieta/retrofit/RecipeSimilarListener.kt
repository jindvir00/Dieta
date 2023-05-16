package com.jindvir.dieta.retrofit

import com.jindvir.dieta.modals.similar.RecipeSimilarResponse
import com.jindvir.dieta.modals.similar.RecipeSimilarResponseItem

interface RecipeSimilarListener {
    fun didFetch(response: ArrayList<RecipeSimilarResponseItem>, message: String)
    fun didError(message: String)
}