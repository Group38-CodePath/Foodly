package com.example.foodly.fragment
import FavoriteRecipeAdapter
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.foodly.R
import com.example.foodly.RecipeFavorites
import com.parse.ParseQuery


class FavoriteFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var adapter: FavoriteRecipeAdapter
    var allMeals:MutableList<RecipeFavorites> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.rvRecipeFavorite)
        adapter = FavoriteRecipeAdapter(requireContext(), allMeals)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        queryMeals()
    }


    fun queryMeals(){
        val query: ParseQuery<RecipeFavorites> = ParseQuery.getQuery(RecipeFavorites::class.java)
        query.include(RecipeFavorites.KEY_USER)
        //query.limit = 20
        query.findInBackground { recipes, e ->
            if (e != null) {
                // Something has went wrong
                Log.e(TAG, "Error fetching posts")
            } else {
                if (recipes != null) {
                    for (recipe in recipes) {
                        Log.i(
                            TAG,
                            "username: " + recipe.getUser()?.username
                        )
                    }
                    allMeals.addAll(recipes)
                    adapter.notifyDataSetChanged()

                }
            }
        }
    }
    companion object{
        const val TAG = "com.example.foodly.fragment.FavoriteFragment"
    }
}