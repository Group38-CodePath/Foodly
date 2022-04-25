package com.example.foodly.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodly.R
import com.example.foodly.RecipeDetailsActivity
import com.example.foodly.RecipeFavorites
import com.example.foodly.RecipeFavorites.Companion.KEY_MEAL_ID
import com.example.foodly.model.Meal
import com.parse.*


const val MEAL_EXTRA = "MEAL_EXTRA"
const val TAG = "RecipeAdapter"

class RecipeAdapter(private val context: Context, private val meal: List<Meal>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recipe_details, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int) {
        val meal = meal[position]
        return holder.bindView(meal)
    }

    override fun getItemCount(): Int {
        return meal.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        private var mealName: TextView = itemView.findViewById(R.id.tvFood)
        private var mealImage: ImageView = itemView.findViewById(R.id.ivFood)
        private var submitFavoriteButton: CheckBox = itemView.findViewById(R.id.checkBox)

        init {
            mealImage.setOnClickListener(this)
        }

        fun bindView(meal: Meal) {
            mealName.text = meal.strMeal

            Glide.with(itemView.context)
                .load(meal.strMealThumb)
                .apply(RequestOptions().override(600, 600))
                .into(mealImage)

            //Is it in the table
            val query = ParseQuery.getQuery<RecipeFavorites>("RecipeFavorites")
            query.whereEqualTo(KEY_MEAL_ID, meal.idMeal)
            query.getFirstInBackground { `object`, e ->
                if (e == null) {
                    //object exists
                    submitFavoriteButton.isChecked = true
                } else {
                    if (e.code == ParseException.OBJECT_NOT_FOUND) {
                        //object doesn't exist
                        submitFavoriteButton.isChecked = false
                    } else {
                        //unknown error, debug
                        Log.e(TAG, "Error: $e")
                    }
                }
            }

            submitFavoriteButton.setOnClickListener {
                if (!submitFavoriteButton.isChecked) {

                    val query = ParseQuery.getQuery<RecipeFavorites>("RecipeFavorites")
                    //query.whereEqualTo(KEY_USER, parseRecipe.getUser())
                    query.whereEqualTo(KEY_MEAL_ID, meal.idMeal)
                    // The query will search for a ParseObject, given its objectId.
                    // When the query finishes running, it will invoke the GetCallback
                    // with either the object, or the exception thrown
                    query.getFirstInBackground { `object`, e ->
                        if (`object` != null) {
                            Log.i(TAG, "Deleting $`object`")
                            val query = ParseQuery.getQuery<ParseObject>("RecipeFavorites")
                            query.getInBackground(
                                `object`.objectId
                            ) { `object`, e ->
                                if (e == null) {
                                    // Otherwise, you can delete the entire ParseObject from the database
                                    `object`?.deleteInBackground()
                                }
                            }
                        } else if (e != null) {
                            Log.e(TAG, "Error: $e")
                        }
                    }

                } else {
                    val user = ParseUser.getCurrentUser()

                    val recipeFavorite = RecipeFavorites()
                    recipeFavorite.setRecipeId(meal.idMeal)
                    recipeFavorite.setUser(user)
                    recipeFavorite.setTitle(meal.strMeal)
                    recipeFavorite.setImageUrl(meal.strMealThumb)

                    recipeFavorite.saveInBackground { exception ->
                        if (exception != null) {
                            // Something has went wrong
                            Log.e(TAG, "Error while saving recipe")
                            Toast.makeText(context, "Description is missing!", Toast.LENGTH_SHORT)
                                .show()
                            exception.printStackTrace()
                        } else {
                            Toast.makeText(context, "Successfully saved recipe", Toast.LENGTH_SHORT)
                                .show()
                            Log.i(TAG, "Successfully saved recipe")
                        }
                    }
                }
            }
        }

        override fun onClick(p0: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val meal: Meal = meal[position]
                Toast.makeText(context, meal.strMeal, Toast.LENGTH_SHORT).show()

                val intent = Intent(context, RecipeDetailsActivity::class.java)
                intent.putExtra(
                    MEAL_EXTRA, meal
                )
                context.startActivity(intent)
            }
        }

    }

}