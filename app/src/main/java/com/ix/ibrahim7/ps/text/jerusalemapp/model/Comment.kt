package com.ix.ibrahim7.ps.text.jerusalemapp.model

data class Comment(
    val comment_id:String ="",
    val post_id:String = "",
    val user: User = User(),
    val text:String = ""
)