package com.christian.seyoum.truelife

interface IFoodControl {
    fun add(food: Food)
    fun count():Int
    fun getFood():MutableList<Food>
    fun search(word:String):MutableList<Food>
    fun reset()
    fun launchAdd()

    val foods:IFoodRepo
}