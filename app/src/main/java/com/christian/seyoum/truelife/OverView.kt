package com.christian.seyoum.truelife

import android.app.Activity
import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonArray
import kotlinx.android.synthetic.main.activity_over_view.*
import kotlinx.android.synthetic.main.food_layout.*

class OverView : AppCompatActivity(), IFoodControl {

    lateinit var json:String
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

    override fun launchAdd() {
        val intent = Intent(this, AddFood::class.java)
        startActivityForResult(intent, ADD_FOOD_REQUEST_CODE)
    }

    override fun add(food: Food) {
        foods.add(food)
    }

    override fun count(): Int {
        return foods.count()
    }

    override fun getFood(): MutableList<Food> {
        return foods.getFoods()
    }

    override fun search(word: String): MutableList<Food> {
        return foods.search(word)
    }

    override fun reset() {
        foods.reset()
    }

    override lateinit var foods: IFoodRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_over_view)
        foods = FoodRepo(this)

        json = intent.getStringExtra("user")

        recycle_view.layoutManager = LinearLayoutManager(this)
        recycle_view.adapter = MainAdapter(this)



    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.overview_bar, menu)

        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search_bar)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchView.clearFocus()
                searchView.setQuery("",false)
                searchItem.collapseActionView()
                Toast.makeText(this@OverView,"looking for $query", Toast.LENGTH_SHORT).show()
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText!!.isNotEmpty()){
                    search(newText)
                    recycle_view.adapter?.notifyDataSetChanged()
                }
                else{
                    reset()
                    recycle_view.adapter?.notifyDataSetChanged()
                }
                return true
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.info -> {
                val intent = Intent(this, UserInfo::class.java)
                intent.putExtra("user",json)
                startActivity(intent)
                return true
            }

            R.id.logout -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                startActivity(intent)
                return true
            }

            R.id.add_bar -> {
                launchAdd()
                return true
            }

            else -> return super.onOptionsItemSelected(item)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_OK -> {
                when(requestCode){
                    ADD_FOOD_REQUEST_CODE -> {
                        val json = data?.getStringExtra(AddFood.FOOD_EXTRA_KEY)
                        if (json != null){
                            val foo = Gson().fromJson<Food>(json,Food::class.java)
                            foods.add(foo)
                            recycle_view.adapter?.notifyItemInserted(foods.count())
                        }

                    }
                    /*VIEW_NOTE_REQUEST_CODE -> {
                        val json = data?.getStringExtra(ViewNote.NOTEE_EXTRA_KEY)
                        val idx = data?.getIntExtra(ViewNote.POSITION_KEY,1)
                        if (json != null && idx != null){
                            val todo = Gson().fromJson<Note>(json,Note::class.java)
                            editNote(todo)
                            recycle_fram.adapter?.notifyItemChanged(idx)
                        }

                    }*/
                }

            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(this,"No change", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object{
        val ADD_FOOD_REQUEST_CODE = 1
    }
}
