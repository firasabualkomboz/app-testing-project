package com.ix.ibrahim7.ps.text.jerusalemapp.model

data class Post (
    val post_id:String = "",
    val comment_id:String = "",
    val post_description:String = "",
    val like_id:ArrayList<String> = ArrayList(),
    val post_type:String = "",
    val postContent:String = "",
    val status:Boolean = true,
    val user: User = User()
)