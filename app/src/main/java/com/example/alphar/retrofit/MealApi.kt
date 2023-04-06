package com.example.alphar.retrofit

import com.example.alphar.pojo.CategoryList
import com.example.alphar.pojo.MealsByCategoryList
import com.example.alphar.pojo.MealList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MealApi {

    @GET("random.php")
    fun getRandomMeal() : Call<MealList>

    // ? should be removed
    @GET("lookup.php?")
    fun getMealDetails(@Query("i") id :String) : Call<MealList>

    // ? should be removed
    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName : String) : Call<MealsByCategoryList>

    @GET("categories.php")
    fun getCategories() : Call<CategoryList>

    @GET("filter.php")
    fun getMealsByCategory(@Query("c") categoryName: String) : Call<MealsByCategoryList>
}