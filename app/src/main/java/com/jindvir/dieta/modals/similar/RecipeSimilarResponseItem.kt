package com.jindvir.dieta.modals.similar

data class RecipeSimilarResponseItem(
    val id: Int,
    val imageType: String,
    val readyInMinutes: Int,
    val servings: Int,
    val sourceUrl: String,
    val title: String
)