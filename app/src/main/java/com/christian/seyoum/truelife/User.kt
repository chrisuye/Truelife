package com.christian.seyoum.truelife

data class User(
    val id:Int,
    val firstName:String,
    val lastName:String,
    val dateBirth: String,
    val userName:String,
    val password:String,
    val remember:Boolean
)