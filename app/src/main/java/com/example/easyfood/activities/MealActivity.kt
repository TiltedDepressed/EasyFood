package com.example.easyfood.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.easyfood.R
import com.example.easyfood.databinding.ActivityMealBinding
import com.example.easyfood.db.MealDatabase
import com.example.easyfood.fragments.HomeFragment
import com.example.easyfood.model.Meal
import com.example.easyfood.viewModel.MealViewModel
import com.example.easyfood.viewModel.MealViewModelFactory

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


        val mealDatabase = MealDatabase.getInstance(this)
        val viewModelFactory = MealViewModelFactory(mealDatabase)
        mealMvvm = ViewModelProvider(this,viewModelFactory)[MealViewModel::class.java]

        getMealInformationFromIntent()
        setInformationInViews()

        loadingCase()
        mealMvvm.getMealDetail(mealId)
        observeMealDetailsLiveData()
        onYouTubeImageClick()
        onFavoriteClick()


    }

    private fun onFavoriteClick() {
        binding.addToFavoritesButton.setOnClickListener{
            mealToSave?.let {
                mealMvvm.insertMeal(it)
                Toast.makeText(this, "Meal saved", Toast.LENGTH_SHORT).show()
            }
        }
    }


    private fun onYouTubeImageClick() {
        binding.imageYouTube.setOnClickListener{
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(linkYouTube))
            startActivity(intent)
        }
    }
    private var mealToSave:Meal?=null
    private fun observeMealDetailsLiveData() {
        mealMvvm.observerMealDetailsLiveData().observe(this,object:Observer<Meal>{
            override fun onChanged(value: Meal) {
                mealToSave = value
                onResponseCase()
                binding.categoryTextView.text = "Category : ${value!!.strCategory}"
                binding.areaTextView.text = "Area : ${value!!.strArea}"
                binding.instructionsDescriptionTextView.text = value.strInstructions

                linkYouTube = value.strYoutube.toString()
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