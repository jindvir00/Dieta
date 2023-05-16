package com.jindvir.dieta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jindvir.dieta.adapters.RandomRecipeAdapter
import com.jindvir.dieta.databinding.ActivityDashboardScreenBinding
import com.jindvir.dieta.modals.RandomRecipeApiResponse
import com.jindvir.dieta.retrofit.RandomRecipeResponseListener
import com.jindvir.dieta.retrofit.RecipeClickListener
import com.jindvir.dieta.retrofit.RequestManager

class DashboardScreen : AppCompatActivity() {
    lateinit var binding: ActivityDashboardScreenBinding
    lateinit var recyclerView: RecyclerView
    lateinit var tags :ArrayList<String>
    lateinit var manager : RequestManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tags = ArrayList()
        val arrayAdapter : ArrayAdapter<CharSequence> = ArrayAdapter.createFromResource(this , R.array.tags  , R.layout.spinner_text)
        arrayAdapter.setDropDownViewResource(R.layout.spinner_inner_text)

        manager = RequestManager(this@DashboardScreen)
        binding.categorySpinner.adapter = arrayAdapter
        binding.categorySpinner.onItemSelectedListener = spinnerSelectedListener
//        manager.getRandomReciepe(listener)

        binding.searchDashboard.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                tags.clear()
                if (query != null) {
                    tags.add(query)
                }
                manager.getRandomReciepe(listener , tags)
                return  true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })

    }

    private var listener  = object : RandomRecipeResponseListener{
        override fun didFetch(response: RandomRecipeApiResponse, message: String) {
            binding.llAnim.visibility = View.GONE

            binding.rvCategoryItems.setHasFixedSize(true)
            binding.rvCategoryItems.layoutManager = GridLayoutManager(this@DashboardScreen , 1)
            val randomRecipeAdapter : RandomRecipeAdapter = RandomRecipeAdapter(this@DashboardScreen , response.recipes , clickListener)
            binding.rvCategoryItems.adapter = randomRecipeAdapter


        }

        override fun didError(message: String) {
            binding.llAnim.visibility = View.GONE

            Toast.makeText(this@DashboardScreen , message , Toast.LENGTH_SHORT)
                .show()
            return


        }

    }

    private var clickListener = object : RecipeClickListener{
        override fun onRecipeClick(id: String) {

                val i = Intent(this@DashboardScreen, ProcedureScreen::class.java)
            i.putExtra("id"  , id)
                startActivity(i)


        }

    }

    private val spinnerSelectedListener = object : AdapterView.OnItemSelectedListener{
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            binding.llAnim.visibility = View.GONE
            tags.clear()
            if (parent != null) {
                tags.add(parent.selectedItem.toString())
            }
            manager.getRandomReciepe(listener , tags)

        }

        override fun onNothingSelected(parent: AdapterView<*>?) {

        }

    }

}