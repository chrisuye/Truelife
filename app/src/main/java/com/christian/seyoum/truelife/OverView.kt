package com.christian.seyoum.truelife

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import com.google.gson.Gson
import com.google.gson.JsonArray

class OverView : AppCompatActivity() {

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_over_view)

        json = intent.getStringExtra("user")



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
                Toast.makeText(this@OverView,"looking for $newText", Toast.LENGTH_SHORT).show()
                return false
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

            else -> return super.onOptionsItemSelected(item)
        }

    }
}
