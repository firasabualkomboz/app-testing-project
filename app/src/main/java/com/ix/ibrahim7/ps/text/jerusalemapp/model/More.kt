package com.ix.ibrahim7.ps.text.jerusalemapp.model

import android.app.Activity
import com.ix.ibrahim7.ps.text.jerusalemapp.R

data class More(
    val title:String,
    val image:Int
)


fun Activity.getData() =   arrayListOf(
    More(getString(R.string.imges), R.drawable.ic_camera),
    More(getString(R.string.videos), R.drawable.ic_youtube),
    More(getString(R.string.occasions), R.drawable.ic_video)
)