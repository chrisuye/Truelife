package com.christian.seyoum.truelife

interface IFoodRepo {
    fun add(food: Food)
    fun count():Int
    fun getFoods():MutableList<Food>
    fun search(word:String):MutableList<Food>
    fun reset()
}