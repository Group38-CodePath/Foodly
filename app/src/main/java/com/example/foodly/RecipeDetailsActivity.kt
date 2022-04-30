package com.example.foodly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.foodly.adapters.MEAL_EXTRA
import com.example.foodly.databinding.ActivityRecipeDetailsBinding
import com.example.foodly.model.Meal
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class RecipeDetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecipeDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRecipeDetailsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        val meal = intent.getParcelableExtra<Meal>(MEAL_EXTRA) as Meal

        binding.tvMealTitle.text = meal.strMeal
        binding.tvInstructions.text = meal.strInstructions
        initializeYoutube()
    }

    private fun initializeYoutube() {
        binding.player.addYouTubePlayerListener(object :
            AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
               val meal = intent.getParcelableExtra<Meal>(MEAL_EXTRA) as Meal
                val videoId = meal.strYoutube.substring(32,meal.strYoutube.length)
                youTubePlayer.loadVideo(videoId,0f)
                Toast.makeText(applicationContext,videoId,Toast.LENGTH_LONG).show()
            }
        })
    }
}