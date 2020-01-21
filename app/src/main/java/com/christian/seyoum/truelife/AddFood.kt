package com.christian.seyoum.truelife

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_food.*
import java.text.SimpleDateFormat
import java.util.*

class AddFood : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.add_btn -> {
                val food = food_add.text.toString()
                val category = catagory_add.text.toString()
                val note = note_add.text.toString()
                if (food.isEmpty() || category.isEmpty()){
                    Toast.makeText(this,"Missing category or food", Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent()


                    val foo = Food(-1,food,category,note)
                    val json = Gson().toJson(foo)
                    intent.putExtra(FOOD_EXTRA_KEY, json)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_food)

        add_btn.setOnClickListener(this)
    }

    companion object{
        val FOOD_EXTRA_KEY = "Todo"
    }

}
