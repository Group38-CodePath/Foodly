import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.foodly.R
import com.example.foodly.RecipeFavorites
import com.example.foodly.adapters.TAG
import com.parse.ParseObject
import com.parse.ParseQuery

class FavoriteRecipeAdapter(val context: Context, val meals:MutableList<RecipeFavorites>): RecyclerView.Adapter<FavoriteRecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recipe_details,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = meals.get(position)
        holder.submitFavoriteButton.setOnClickListener {
            val query = ParseQuery.getQuery<RecipeFavorites>("RecipeFavorites")
            //query.whereEqualTo(KEY_USER, parseRecipe.getUser())
            query.whereEqualTo(RecipeFavorites.KEY_MEAL_ID, meals[position].getRecipeId())
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

            meals.removeAt(position)
            notifyDataSetChanged()
        }
        holder.bind(post)
    }

    override fun getItemCount(): Int {
        return meals.size
    }

    fun clear() {
        meals.clear()
        notifyDataSetChanged()
    }

    fun addAll(list:List<RecipeFavorites>) {
        meals.addAll(list)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val mealName: TextView

        val mealImage: ImageView

        val submitFavoriteButton: CheckBox

        init {
            mealName = itemView.findViewById(R.id.tvFood)

            mealImage = itemView.findViewById(R.id.ivFood)

            submitFavoriteButton = itemView.findViewById(R.id.checkBox)

        }

        fun bind(meal:RecipeFavorites) {
            mealName.text = meal.getTitle()
            Glide.with(itemView.context)
                .load(meal.getImageUrl())
                .apply(RequestOptions().override(600, 600))
                .into(mealImage)
            submitFavoriteButton.isChecked = true
        }
    }

}