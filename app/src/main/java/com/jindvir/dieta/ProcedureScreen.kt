package com.jindvir.dieta

import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.LinearLayoutManager
import com.jindvir.dieta.adapters.RandomRecipeAdapter
import com.jindvir.dieta.adapters.SimilarRecipeInformationAdapter
import com.jindvir.dieta.databinding.ActivityProcedureScreenBinding
import com.jindvir.dieta.modals.information.RecipeDetailsResponse
import com.jindvir.dieta.modals.similar.RecipeSimilarResponse
import com.jindvir.dieta.modals.similar.RecipeSimilarResponseItem
import com.jindvir.dieta.retrofit.RecipeClickListener
import com.jindvir.dieta.retrofit.RecipeDetailsListener
import com.jindvir.dieta.retrofit.RecipeSimilarListener
import com.jindvir.dieta.retrofit.RequestManager
import com.squareup.picasso.Picasso
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.properties.Delegates


class ProcedureScreen : AppCompatActivity() {

    lateinit var binding: ActivityProcedureScreenBinding
    lateinit var decodedBytes : ByteArray
    var id by Delegates.notNull<Int>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProcedureScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recipeProcedure.isSelected = true
        binding.procedureSummary.movementMethod = ScrollingMovementMethod()

        id  = intent.getStringExtra("id")!!.toInt()
        val manager : RequestManager = RequestManager(this@ProcedureScreen)
        manager.getRecipeDetails(listener , id)
        manager.getSimilarRecipe(recipeSimilarListener , id)
    }

    @OptIn(ExperimentalEncodingApi::class)
    private var listener = object : RecipeDetailsListener{
        override fun didFetch(response: RecipeDetailsResponse, message: String) {
            binding.llAnimProc.visibility = View.GONE


            binding.recipeProcedure.text = response.title
            binding.procedureSummary.text = response.summary

            Picasso.get().load(response.image).into(binding.imgProcedureRecipe)


        }


        override fun didError(message: String) {

            Toast.makeText(this@ProcedureScreen , message , Toast.LENGTH_SHORT)
                .show()
            return
        }

    }

    private var recipeSimilarListener = object : RecipeSimilarListener {
               override fun didFetch(response: ArrayList<RecipeSimilarResponseItem>, message: String) {

                   binding.recipeRv.layoutManager = LinearLayoutManager(this@ProcedureScreen,  LinearLayoutManager.HORIZONTAL , false)
                   val adapter = SimilarRecipeInformationAdapter(this@ProcedureScreen , response , clickListener )
                   binding.recipeRv.adapter = adapter
        }

        override fun didError(message: String) {

            Toast.makeText(this@ProcedureScreen , message , Toast.LENGTH_SHORT)
                .show()
            return
        }

    }

    private var clickListener = object : RecipeClickListener {
        override fun onRecipeClick(id: String) {
            val intent = Intent(this@ProcedureScreen , ProcedureScreen::class.java)
            intent.putExtra("id" , id)
            startActivity(intent)
//            finish()

        }

    }
}