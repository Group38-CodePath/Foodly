import android.content.Context
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

class FavoriteRecipeAdapter(val context: Context, val meals:MutableList<RecipeFavorites>): RecyclerView.Adapter<FavoriteRecipeAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.recipe_details,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val post = meals.get(position)
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