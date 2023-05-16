package com.jindvir.dieta.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.jindvir.dieta.R
import com.jindvir.dieta.modals.Recipe
import com.jindvir.dieta.retrofit.RecipeClickListener
import com.squareup.picasso.Picasso

class RandomRecipeAdapter(private val context: Context, private val categoryItems: ArrayList<Recipe> , private val recipeClickListener: RecipeClickListener) : RecyclerView.Adapter<RandomRecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context)
            .inflate(R.layout.category_items_dashboard_rv ,parent , false )

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = categoryItems[position]

        // sets the image to the imageview from our itemHolder class
        holder.textView1.text = ItemsViewModel.title
        holder.textView2.text = ItemsViewModel.summary
        holder.textView3.apply {
            setOnClickListener {
                Toast.makeText(context , "Not Made Yet" , Toast.LENGTH_SHORT).show()
            }
        }
        holder.textView4.apply {
            setOnClickListener {
//                Toast.makeText(context , "Not Made Yet" , Toast.LENGTH_SHORT).show()
                recipeClickListener.onRecipeClick(ItemsViewModel.id.toString())
            }

        }

        Picasso.get()
            .load(ItemsViewModel.image)
            .into(holder.imageView)

//        holder.cardView.apply {
//            setOnClickListener{
//                recipeClickListener.onRecipeClick(categoryItems[holder.adapterPosition].id.toString())
//            }
//        }
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView1: TextView = ItemView.findViewById(R.id.recycler_tv_category)
        val textView2: TextView = ItemView.findViewById(R.id.recipe_prev_info)
        val textView3: TextView = ItemView.findViewById(R.id.nutrition_tv)
        val textView4: TextView = ItemView.findViewById(R.id.procedure_tv)
        val imageView: ImageView = ItemView.findViewById(R.id.item_img_fetch)
        val cardView : CardView = ItemView.findViewById(R.id.cardview_item_recipe)
    }

    override fun getItemCount(): Int {
        return categoryItems.size
    }
}