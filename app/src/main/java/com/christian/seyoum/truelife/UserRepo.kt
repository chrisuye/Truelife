package com.christian.seyoum.truelife

import android.content.Context

class UserRepo (context: Context):IUserRepo{

    private var userstemp: MutableList<User> = mutableListOf()
    var location: Int
    private val db: IUserDataBase

    init {
        location = 0
        db = UserDataBase(context)
        userstemp.addAll(db.getUsers())
    }
    override fun add(user: User) {
        db.addUser(user)
    }

    override fun check(userName: String, password: String): User? {
        for (n in userstemp){
            if (userName == n.userName && password == n.password){
                return n
            }
        }
        return null
    }

    override fun checkAdd(userName: String): Boolean {
        for (n in userstemp){
            if (userName == n.userName){
                return true
            }
        }
        return false
    }

    override fun update(user: User) {
        db.updateUser(user)
    }

}