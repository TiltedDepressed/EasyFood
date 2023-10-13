package com.example.easyfood.activities


import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.pojo.Meal
import com.example.easyfood.viewModel.MealViewModel

class MealActivity : AppCompatActivity() {
    private lateinit var mealId : String
    private lateinit var mealName: String
    private lateinit var mealThumb: String
    private lateinit var linkYouTube: String


    private lateinit var binding: ActivityMealBinding
    private lateinit var mealMvvm: MealViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mealMvvm = ViewModelProvider(this)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onYouTubeImageClick()
    }

    private fun onYouTubeImageClick() {
        binding.imageYouTube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkYouTube))
            startActivity(intent)
        }
    }

    private fun observeMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this,object:Observer<Meal>{
            override fun onChanged(t: Meal?) {
                onResponseCase()
                binding.categoryTextView.text = "Category : ${t!!.strCategory}"
                binding.areaTextView.text = "Area : ${t!!.strArea}"
                binding.instructionsDescriptionTextView.text = t.strInstructions

                linkYouTube = t.strYoutube
            }

        })
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(mealThumb)
            .into(binding.mealDetailImageView)

        binding.collapsingToolBar.title = mealName
        binding.collapsingToolBar.setCollapsedTitleTextColor(resources.getColor(R.color.white))


    }

    private fun getMealInformationFromIntent() {
        val intent = intent
        mealId = intent.getStringExtra(HomeFragment.MEAL_ID)!!
        mealName = intent.getStringExtra(HomeFragment.MEAL_NAME)!!
        mealThumb = intent.getStringExtra(HomeFragment.MEAL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.addToFavoritesButton.visibility = View.INVISIBLE
        binding.instructionTextView.visibility = View.INVISIBLE
        binding.categoryTextView.visibility = View.INVISIBLE
        binding.areaTextView.visibility = View.INVISIBLE
        binding.imageYouTube.visibility = View.INVISIBLE
    }
    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.addToFavoritesButton.visibility = View.VISIBLE
        binding.instructionTextView.visibility = View.VISIBLE
        binding.categoryTextView.visibility = View.VISIBLE
        binding.areaTextView.visibility = View.VISIBLE
        binding.imageYouTube.visibility = View.VISIBLE
    }
}