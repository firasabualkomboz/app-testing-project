package com.ix.ibrahim7.ps.text.jerusalemapp.other

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.request.RequestOptions
import com.google.firebase.storage.FirebaseStorage
import com.ix.ibrahim7.ps.text.jerusalemapp.R

object HolderAdapter {

    @JvmStatic
    @BindingAdapter("loadImageRec")
    fun loadImageResource(image:ImageView,img:Int){
        image.setImageResource(img)
    }

    @JvmStatic
    @BindingAdapter("loadImage")
    fun loadImagePath(image:ImageView,path:String){
            loadImage(image, path)
    }

    @JvmStatic
    @BindingAdapter(value = [VALUE, TYPE], requireAll = false)
    fun loadFireImage(image:ImageView, value: String?, type: String){
        when(type){
            IMAGE ->{
                loadImageFromFirebase(image, value!!)
            }
        }
    }

     fun loadImage(view: ImageView,path:String){
        Glide
            .with(view.context)
            .load(path)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .apply(RequestOptions().transform(CenterCrop()))
            .into(view)
    }

    private fun loadImageFromFirebase(view: ImageView,path:String){
        GlideApp
            .with(view.context)
            .load(if (path != ""){
                FirebaseStorage.getInstance().getReference(path)}else path)
            .placeholder(R.drawable.ic_profile_img)
            .error(R.drawable.ic_profile_img)
            .into(view)
    }

}