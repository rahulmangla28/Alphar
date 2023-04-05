package com.example.alphar.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.alphar.R
import com.example.alphar.databinding.FragmentHomeBinding
import com.example.alphar.pojo.Meal
import com.example.alphar.pojo.MealList
import com.example.alphar.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.bumptech.glide.Glide
import com.example.alphar.activities.MealActivity
import com.example.alphar.adapters.MostPopularAdapter
import com.example.alphar.pojo.CategoryMeals
import com.example.alphar.viewModel.HomeViewModel
import java.lang.System.load

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm : HomeViewModel
    private lateinit var randomMeal : Meal
    private lateinit var popularItemsAdapter : MostPopularAdapter

    companion object{
        const val MEAL_ID = "com.example.alphar.fragments.idMeal"
        const val MEAL_NAME = "com.example.alphar.fragments.nameMeal"
        const val MEAL_THUMB = "com.example.alphar.fragments.thumbMeal"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homeMvvm = ViewModelProviders.of(this).get(HomeViewModel::class.java)

        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        homeMvvm.getRandomMeal()
        observerRandomMeal()
        // executes when random meal card is clicked
        onRandomMealClick()

        homeMvvm.getPopularItems()
        observePopularItemsLiveData()

        onPopularItemClick()
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClicked = { meal ->
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra("Meal_ID",meal.idMeal)
            intent.putExtra("Meal_NAME",meal.strMeal)
            intent.putExtra("Meal_THUMB",meal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewMealsPopular.apply {
            layoutManager = LinearLayoutManager(activity,LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { mealList ->
            popularItemsAdapter.setMeals(mealsList = mealList as ArrayList)
        }
    }

    private fun onRandomMealClick() {
        binding.randomMealCard.setOnClickListener {
            val intent = Intent(activity,MealActivity::class.java)
            intent.putExtra("MEAL_ID",randomMeal.idMeal)
            intent.putExtra("MEAL_NAME",randomMeal.strMeal)
            intent.putExtra("MEAL_THUMB",randomMeal.strMealThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomMeal() {
        homeMvvm.observeRandomMealLivedata().observe(viewLifecycleOwner
        ) { meal ->
            Glide.with(this@HomeFragment)
                .load(meal!!.strMealThumb)
                .into(binding.imgRandomMeal)

            this.randomMeal = meal
        }
    }
}