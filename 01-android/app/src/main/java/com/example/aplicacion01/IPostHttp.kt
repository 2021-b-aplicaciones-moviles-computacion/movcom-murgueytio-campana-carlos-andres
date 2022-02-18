package com.example.aplicacion01

class IPostHttp(
    val id: Int,
    var userID: Any,
    val title: String,
    val body: String
) {
    init {
        if(userID is String){
            userID = (userID as String).toInt()
        }
        if(userID is Int){
            userID = userID
        }
    }

}