package com.example.foodly.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.example.foodly.adapters.RecipeAdapter
import com.example.foodly.databinding.FragmentHomeBinding
import com.example.foodly.model.Meal
import okhttp3.Headers
import org.json.JSONException


private const val TAG = "HomeFragment"
class HomeFragment : Fragment() {

    lateinit var rvRecipeHome: RecyclerView
    private val meals = mutableListOf<Meal>()
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recipeAdapter = RecipeAdapter(requireContext(),meals)
        rvRecipeHome = binding.rvRecipeHome

        rvRecipeHome.adapter = recipeAdapter

        rvRecipeHome.layoutManager = LinearLayoutManager(requireContext())


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit( query: String): Boolean {

                val client = AsyncHttpClient()
                val MEAL_URL = "https://www.themealdb.com/api/json/v1/1/search.php?s=${query}"
                client.get(MEAL_URL, object: JsonHttpResponseHandler() {
                    override fun onFailure(
                        statusCode: Int,
                        headers: Headers?,
                        response: String?,
                        throwable: Throwable?
                    ) {
                        Log.e(TAG,"onFailure: $statusCode")
                    }

                    override fun onSuccess(statusCode: Int, headers: Headers?, json: JSON) {
                        Log.i(TAG,"onSuccess: JSON data $json")

                        try {
                            val mealJsonArray = json.jsonObject.getJSONArray("meals")
                            meals.clear()
                            meals.addAll(Meal.fromJsonArray(mealJsonArray))
                            recipeAdapter.notifyDataSetChanged()
                            Log.i(TAG, "Meal list $meals")

                          // TODO() Show toast if api returns empty list from search (ex. injnj)


                        } catch(e: JSONException){
                            Log.e(TAG,"Encountered exception $e")
                        }
                    }
                })
                return true
            }
            override fun onQueryTextChange(s: String?): Boolean {
                return false
            }

        })


    }


}