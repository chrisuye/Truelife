package com.christian.seyoum.truelife

interface IUserRepo {
    fun add(user: User)
    fun check(userName:String, password:String):User?
    fun checkAdd(userName: String):Boolean
    fun update(user: User)
}