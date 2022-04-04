package com.ix.ibrahim7.ps.text.jerusalemapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val userID:String = "",
    val username:String = "",
    val email :String = "",
    val image:String = "",
    val about:String ="",
    val verified:Boolean = true,
    val likes_id:ArrayList<String> = ArrayList(),
    val userToken:String = ""
):Parcelable{

    companion object{
        const val USERID = "uesrID"
        const val USERNAME = "username"
        const val EMAIL = "email"
        const val IMAGE = "image"
        const val ABOUT = "about"
        const val VERIFIED = "verified"
        const val LIKESID = "likes_id"
        const val USERTOKENT = "userToken"
    }
}
