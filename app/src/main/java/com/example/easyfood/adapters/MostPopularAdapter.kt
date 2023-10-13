package com.example.easyfood.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.easyfood.databinding.PopularItemsBinding
import com.example.easyfood.pojo.CategoryMeals

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.PopularMealViewHolder>() {
    private lateinit var mealsList : ArrayList<CategoryMeals>

    fun setMeals(mealsList: ArrayList<CategoryMeals>) {
        this.mealsList= mealsList
        notifyDataSetChanged()
    }

    class PopularMealViewHolder(private val binding: PopularItemsBinding):RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularMealViewHolder {
        return PopularMealViewHolder(PopularItemsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: PopularMealViewHolder, position: Int) {
        TODO("Not yet implemented")
    }
}