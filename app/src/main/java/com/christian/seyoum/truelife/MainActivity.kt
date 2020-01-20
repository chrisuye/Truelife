package com.christian.seyoum.truelife

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), IUserControl {

    override fun launchInfo() {
        val intent = Intent(this,UserInfo::class.java)
        startActivity(intent)
    }

    override fun launchCreate() {
        val intent = Intent(this, CreateAccount::class.java)
        startActivityForResult(intent, ADD_USER_REQUEST_CODE)
    }

    override fun create(user: User) {
        usertemp.add(user)
    }

    override fun signIn(userName: String, password: String): User? {
        return usertemp.check(userName,password)
    }

    override fun checkPresent(userName: String): Boolean {
        return usertemp.checkAdd(userName)
    }

    override fun change(user: User) {
        usertemp.update(user)
    }

    override lateinit var usertemp: IUserRepo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usertemp = UserRepo(this)

        create_btn.setOnClickListener {
            launchCreate()
        }
        login_btn.setOnClickListener {
            val user = signIn(user_in.text.toString(), password_in.text.toString())
            if (user != null){
                val intent = Intent(this, OverView::class.java)
                val use = Gson().toJson(user)
                intent.putExtra("user",use)
                startActivity(intent)
            }
            else Toast.makeText(this,"Username or Password do not exist",Toast.LENGTH_SHORT).show()

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(resultCode){
            Activity.RESULT_OK -> {
                when(requestCode){
                    ADD_USER_REQUEST_CODE -> {
                        val json = data?.getStringExtra(CreateAccount.USER_EXTRA_KEY)
                        if (json != null){
                            val user = Gson().fromJson<User>(json,User::class.java)
                            usertemp.add(user)

                            val i = Intent(this, OverView::class.java)
                            val use = Gson().toJson(user)
                            i.putExtra("user",use)
                            startActivity(i)

                        }

                    }
                }

            }
            Activity.RESULT_CANCELED -> {
                Toast.makeText(this,"User not created",Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object{
        val ADD_USER_REQUEST_CODE = 1
    }


}
