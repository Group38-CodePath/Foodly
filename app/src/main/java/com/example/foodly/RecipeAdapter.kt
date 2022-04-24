package com.example.foodly

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.foodly.model.Meal

const val MEAL_EXTRA = "MEAL_EXTRA"
class RecipeAdapter(private val context: Context,  private val meal: List<Meal>): RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.recipe_details, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: RecipeAdapter.ViewHolder, position: Int) {
        val meal= meal[position]
        return holder.bindView(meal)
    }

    override fun getItemCount(): Int {
       return meal.size

    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        // Your holder should contain and initialize a member variable
        // for any view that will be set as you render a row
        private var mealName: TextView = itemView.findViewById(R.id.tvFood)
        private var mealImage: ImageView = itemView.findViewById(R.id.ivFood)

        init{
            itemView.setOnClickListener(this)
        }

        fun bindView(meal: Meal) {
            mealName.text = meal.strMeal

           Glide.with(itemView.context)
               .load(meal.strMealThumb).into(mealImage)

        }

        override fun onClick(p0: View?) {
            val position = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                val meal: Meal = meal[position]
                Toast.makeText(context,meal.strMeal, Toast.LENGTH_SHORT).show()

                val intent = Intent(context, RecipeDetailsActivity::class.java)
                intent.putExtra(
                    MEAL_EXTRA, meal
                )
//                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
//                    (context as Activity), ivPoster, "poster"
//                )
                context.startActivity(intent)
            }
        }
    }
}