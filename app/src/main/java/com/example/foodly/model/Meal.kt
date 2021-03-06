package com.example.foodly.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.json.JSONArray

@Parcelize
data class Meal(
//    val dateModified: Any,
      val idMeal: String,
//    val strArea: String,
      val strCategory: String,
//    val strCreativeCommonsConfirmed: Any,
//    val strDrinkAlternate: Any,
      val strMealThumb: String,
      val strIngredient1: String,
      val strIngredient2: String,
      val strIngredient3: String,
      val strIngredient4: String,
//      val strIngredient5: String,
//      val strIngredient6: String,
//      val strIngredient7: String,
//      val strIngredient8: String,
//      val strIngredient9: String,
//      val strIngredient10: String,
//      val strIngredient11: String,
//    val strIngredient12: String,
//    val strIngredient13: String,
//    val strIngredient14: String,
//    val strIngredient15: String,
//    val strIngredient16: Any,
//    val strIngredient17: Any,
//    val strIngredient18: Any,
//    val strIngredient19: Any,
//    val strIngredient20: Any,
      val strInstructions: String,
      val strMeal: String,
      val strMeasure1: String,
      val strMeasure2: String,
      val strMeasure3: String,
      val strMeasure4: String,
//    val strMeasure5: String,
//    val strMeasure6: String,
//    val strMeasure7: String,
//    val strMeasure8: String,
//    val strMeasure9: String,
//    val strMeasure10: String,
//    val strMeasure11: String,
//    val strMeasure12: String,
//    val strMeasure13: String,
//    val strMeasure14: String,
//    val strMeasure15: String,
//    val strMeasure16: Any,
//    val strMeasure17: Any,
//    val strMeasure18: Any,
//    val strMeasure19: Any,
//    val strMeasure20: Any,
//    val strSource: Any,
//    val strTags: String,
      val strYoutube: String
) : Parcelable {
    companion object {
        fun fromJsonArray(mealJsonArray: JSONArray): MutableList<Meal> {
            val meals = mutableListOf<Meal>()
            for (i in 0 until mealJsonArray.length()) {
                val mealJson = mealJsonArray.getJSONObject(i)
                meals.add(
                    Meal(
                        mealJson.getString("idMeal"),
                        mealJson.getString("strCategory"),
                        mealJson.getString("strMealThumb"),
                        mealJson.getString("strIngredient1"),
                        mealJson.getString("strIngredient2"),
                        mealJson.getString("strIngredient3"),
                        mealJson.getString("strIngredient4"),
                        mealJson.getString("strInstructions"),
                        mealJson.getString("strMeal"),
                        mealJson.getString("strMeasure1"),
                        mealJson.getString("strMeasure2"),
                        mealJson.getString("strMeasure3"),
                        mealJson.getString("strMeasure4"),
                        mealJson.getString("strYoutube")
                        )
                )
            }
            return meals
        }
    }
}