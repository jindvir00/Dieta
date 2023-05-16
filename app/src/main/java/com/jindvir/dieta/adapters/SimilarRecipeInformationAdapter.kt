package com.jindvir.dieta.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.jindvir.dieta.R
import com.jindvir.dieta.modals.Recipe
import com.jindvir.dieta.modals.similar.RecipeSimilarResponse
import com.jindvir.dieta.modals.similar.RecipeSimilarResponseItem
import com.jindvir.dieta.retrofit.RecipeClickListener
import com.squareup.picasso.Picasso

class SimilarRecipeInformationAdapter(private val context: Context, private val categoryItems: ArrayList<RecipeSimilarResponseItem> , val listener: RecipeClickListener) : RecyclerView.Adapter<SimilarRecipeInformationAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.similar_recipe_procedure_screen ,parent , false )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = categoryItems[position]

        // sets the image to the imageview from our itemHolder class
        holder.textView1.text = ItemsViewModel.title

        holder.mv.apply {
            setOnClickListener {
                listener.onRecipeClick(ItemsViewModel.id.toString())
            }
        }

        Picasso.get()
            .load("https://spoonacular.com/recipeImages/" + ItemsViewModel.id + "-556x370." + ItemsViewModel.imageType)
            .into(holder.imageView);
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView1: TextView = ItemView.findViewById(R.id.similar_procedure_tv_category)
        val imageView: ImageView = ItemView.findViewById(R.id.similar_item_img_fetch)
        val mv : ImageView = ItemView.findViewById(R.id.similar_item_img_fetch)
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }
}