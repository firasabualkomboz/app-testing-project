package com.ix.ibrahim7.ps.text.jerusalemapp.model

import android.content.Context
import com.ix.ibrahim7.ps.text.jerusalemapp.R


data class Welcome (var image: Int, var desc: String)


fun getData(context: Context) =
    arrayListOf(
        Welcome(
            R.drawable.ic_welcome_img,
            context.getString(R.string.welcome1_title)
        ),
        Welcome(
            R.drawable.ic_welcome_img,
            context.getString(R.string.welcome2_title)
        ),
        Welcome(
            R.drawable.ic_welcome_img,
            context.getString(R.string.welcome3_title)
        )
    )