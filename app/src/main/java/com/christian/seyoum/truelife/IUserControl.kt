package com.christian.seyoum.truelife

interface IUserControl {
    fun create(user: User)
    fun signIn(userName:String, password:String):User?
    fun checkPresent(userName: String):Boolean
    fun change(user: User)
    fun launchCreate()
    fun launchInfo()

    val usertemp:IUserRepo
}