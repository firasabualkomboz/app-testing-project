package com.ix.ibrahim7.ps.text.jerusalemapp.other

import android.app.*
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.core.view.isVisible
import com.google.android.material.snackbar.Snackbar
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.ToolbarLayoutBinding
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody

fun Activity.setToolbarView(
    view: ToolbarLayoutBinding,
    title: String,
    main: Boolean,
    hasMenu:Boolean = false,
    menuIcon:Int = 0,
    onComplete: (type:Int) -> Unit
): ToolbarLayoutBinding {

    view.tvTitle.text = title

    when (hasMenu) {
        true -> {
            view.apply {
                btnAdd.isVisible = true
                tvAddImage.setImageResource(menuIcon)
                btnAdd.setOnClickListener {
                    onComplete(TWO)
                }
            }
        }
        else->{
            view.btnAdd.isVisible = false
        }
    }

    when (main) {
        true -> {
            view.btnBack.isVisible = false
        }
        else->{
            view.btnBack.apply {
                isVisible = true
                setOnClickListener {
                    onComplete(ONE)
                }
            }
        }
    }
    return view
}



fun Activity.getSnackBar(@LayoutRes layoutId: Int, view: View, message: String): View {
    val snackbar = Snackbar.make(view, "", Snackbar.LENGTH_SHORT)

    val custom = this.layoutInflater.inflate(layoutId, null)

    snackbar.view.setBackgroundColor(Color.TRANSPARENT)
    val snackbarLayout = snackbar.view as Snackbar.SnackbarLayout
    snackbarLayout.setPadding(0, 0, 0, 16)

    custom.findViewById<com.akexorcist.roundcornerprogressbar.RoundCornerProgressBar>(R.id.item_progress).progress = 100f

    custom.findViewById<TextView>(R.id.txtMessage).text = message

    snackbarLayout.addView(custom)
    snackbar.show()
    return custom
}


fun Activity.getSharePref() =
    this.getSharedPreferences(SHARE, Activity.MODE_PRIVATE)

fun Activity.editor() = getSharePref().edit()


fun Activity.getPermission(
    permissions: ArrayList<String>,
    onComplete: (done:Boolean) -> Unit
) {
    Dexter.withContext(this)
        .withPermissions(
            permissions
        )
        .withListener(object : MultiplePermissionsListener {
            override fun onPermissionsChecked(report: MultiplePermissionsReport?) {
                report?.let {
                    when {
                        report.areAllPermissionsGranted() -> {
                            onComplete(true)
                           Log.v("location Permissions","onComplete")
                        }
                        else -> {
                            onComplete(false)
                            Log.v("location Permissions","onDenied")
                        }
                    }
                }
            }

            override fun onPermissionRationaleShouldBeShown(
                permissions: MutableList<PermissionRequest>?,
                token: PermissionToken?
            ) {

                token?.continuePermissionRequest()
            }
        })
        .withErrorListener {

        }
        .check()
}


fun Context.getMarkerBitmapFromView(view: View): Bitmap? {
    view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
    view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    view.buildDrawingCache()
    val returnedBitmap = Bitmap.createBitmap(
        view.measuredWidth, view.measuredHeight,
        Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(returnedBitmap)
    canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
    val drawable = view.background
    drawable?.draw(canvas)
    view.draw(canvas)
    return returnedBitmap
}
