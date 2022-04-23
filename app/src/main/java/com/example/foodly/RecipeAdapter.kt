package com.example.foodly

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodly.model.MealSearch


class RecipeAdapter(private val meal: MealSearch): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recipe_details, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int) {
        return holder.bindView(meal)
    }

    override fun getItemCount(): Int {
       return meal.meals.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        private var mealName: TextView = itemView.findViewById(R.id.tvFood)
        private var mealImage: ImageView = itemView.findViewById(R.id.ivFood)

        fun bindView(meal: MealSearch) {
            mealName.text = meal.meals[bindingAdapterPosition].strMeal

           Glide.with(itemView.context)
               .load(meal.meals[bindingAdapterPosition].strImageSource).into(mealImage)

        }
    }
}