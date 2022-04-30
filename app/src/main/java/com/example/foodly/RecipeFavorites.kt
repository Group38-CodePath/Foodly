package com.example.foodly

import com.example.foodly.model.Meal
import com.parse.ParseClassName
import com.parse.ParseObject
import com.parse.ParseUser

@ParseClassName("RecipeFavorites")
class RecipeFavorites : ParseObject() {

    fun setRecipeId(mealId: String?) {
        put(KEY_MEAL_ID, mealId!!)
    }

    fun getRecipeId(): String? {
        return getString(KEY_MEAL_ID)
    }

    fun getImageUrl(): String? {
        return getString(KEY_IMAGE_URL)
    }

    fun setImageUrl(imageUrl: String?) {
        put(KEY_IMAGE_URL, imageUrl!!)
    }

    fun getTitle(): String? {
        return getString(KEY_TITLE)
    }

    fun setTitle(title: String?) {
        put(KEY_TITLE, title!!)
    }

    fun getUser(): ParseUser? {
        return getParseUser(KEY_USER)
    }

    fun setUser(user: ParseUser) {
        put(KEY_USER, user)
    }

    fun setMeal(meal: Meal) {
        KEY_MEAL = meal
    }

    fun getMeal(): Meal {
        return KEY_MEAL
    }

    companion object {
        const val KEY_USER  = "userId"
        const val KEY_IMAGE_URL = "imageUrl"
        const val KEY_TITLE = "title"
        const val KEY_MEAL_ID = "mealId"
        lateinit var KEY_MEAL: Meal
    }
}