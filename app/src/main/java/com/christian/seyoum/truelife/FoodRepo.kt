package com.christian.seyoum.truelife

import android.content.Context
import android.widget.Toast

class FoodRepo(context: Context):IFoodRepo {

    val bloodFoodList:MutableList<String> =
        mutableListOf(
            "apple",
            "apricots",
            "bananas",
            "beet greens",
            "broccoli",
            "carrots",
            "collards",
            "green beans",
            "dates",
            "grapes",
            "green peas",
            "kale",
            "lima beans",
            "mangoes",
            "melons",
            "oranges",
            "peaches",
            "pineapples",
            "potatoes",
            "raisins",
            "spinach",
            "squash",
            "strawberries",
            "sweet potatoes",
            "tangerines",
            "tomatoes",
            "tuna",
            "yogurt (fat-free)")
    val goutFoodList:MutableList<String> =
        mutableListOf(
            "yogurt",
            "skim milk",
            "Fresh fruits",
            "vegetables",
            "Nuts",
            "peanut butter",
            "grains",
            "Fat",
            "oil",
            "Potatoes",
            "rice",
            "bread",
            "pasta",
            "Eggs",
            "fish",
            "chicken",
            "red meat",
            "spinach",
            "asparagus"

        )

    private var foodTemp:MutableList<Food> = mutableListOf()
    private var foods:MutableList<Food> = mutableListOf()
    private val db: IFoodDataBase

    init {
        db = FoodDataBase(context)
        var count = 0

        if (db.getFoods().size != 0){
            foodTemp.addAll(db.getFoods())
            foods.addAll(db.getFoods())
            //Toast.makeText(context,"all is in the data", Toast.LENGTH_SHORT).show()
        }
        else {

            for (n in bloodFoodList) {
                db.addFood(Food(count, n, "Blood Pressure", ""))
                foodTemp.add(Food(count, n, "Blood Pressure", ""))
                count++
            }
            for (m in goutFoodList) {
                db.addFood(Food(count, m, "Gout", ""))
                foodTemp.add(Food(count, m, "Gout", ""))
                count++
            }

            foods.addAll(foodTemp)
        }
    }

    override fun add(food: Food) {
        db.addFood(food)
        foodTemp.add(food)
        foods.clear()
        foods.addAll(foodTemp)
    }

    override fun count(): Int {
        return foods.size
    }

    override fun getFoods(): MutableList<Food> {
        return foods
    }

    override fun search(word: String): MutableList<Food> {
        foods.clear()
        val search = word.toLowerCase()
        foodTemp.forEach {
            if (it.food.toLowerCase().contains(search) || it.catagory.toLowerCase().contains(search)
                        || it.notes.toLowerCase().contains(search)){
                foods.add(it)

            }
        }
        return foods
    }

    override fun reset() {
        foods.clear()
        foods.addAll(foodTemp)
    }
}