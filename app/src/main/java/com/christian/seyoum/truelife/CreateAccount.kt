package com.christian.seyoum.truelife

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_create_account.*
import java.text.SimpleDateFormat
import java.util.*

class CreateAccount : AppCompatActivity(), View.OnClickListener {

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.createnew_btn -> {

                if (firstname_cr.text.isEmpty() || secondname_cr.text.isEmpty()
                    || username_cr.text.isEmpty() || password_cr.text.isEmpty() || date_cr.text.isEmpty()){
                    Toast.makeText(this,"Every information needs to be filled", Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent()
                    val first = firstname_cr.text.toString()
                    val last = secondname_cr.text.toString()
                    val username = username_cr.text.toString()
                    val pass = password_cr.text.toString()
                    val date = date_cr.text.toString()
                    val remember = sigincr_check.isChecked
                    val user = User(-1,first, last, date, username, pass, remember)
                    val json = Gson().toJson(user)
                    intent.putExtra(USER_EXTRA_KEY, json)
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createnew_btn.setOnClickListener(this)
    }

    companion object{
        val USER_EXTRA_KEY = "User"
    }


}
