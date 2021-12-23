
package com.ix.ibrahim7.ps.text.jerusalemapp.adapter


import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.ItemPhotoBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Wallpaper


class ImageAdapter(val onClick: OnClickMenuSheet, val activity: Activity) :
    RecyclerView.Adapter<ImageAdapter.ImageHolder>() {

    inner class ImageHolder(val binding: ItemPhotoBinding) : RecyclerView.ViewHolder(binding.root)


    private val differCallback = object
        : DiffUtil.ItemCallback<Wallpaper>() {
        override fun areItemsTheSame(
            oldItem: Wallpaper,
            newItem: Wallpaper
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Wallpaper,
            newItem: Wallpaper
        ): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    fun getImageItemAt(position: Int): Wallpaper {
        return differ.currentList[position]
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageHolder {
        return ImageHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.item_photo, parent, false
            )
        )
    }

    override fun getItemCount() = differ.currentList.size

    override fun onBindViewHolder(holder: ImageHolder, position: Int) {

        val photo = getImageItemAt(position)

        val h = photo.height!!.toFloat()

        holder.binding.apply {

            val layoutParams = this.containerPhoto.layoutParams
            layoutParams.height = convertDpToPixels(h / 17, activity)
            this.containerPhoto.layoutParams = layoutParams

           /* loadImage(
                activity,
                photo.urls!!.regular!!,
                Color.parseColor(photo.color)
                , this.containerPhoto
            )*/

            this.containerPhoto.setOnClickListener {
                onClick.onClickItemListener(photo, image)

            }

        }


    }

    interface OnClickMenuSheet {
        fun onClickItemListener(data: Wallpaper, image: ImageView)
    }

    private fun convertDpToPixels(dp: Float, context: Context): Int {
        val resources: Resources = context.resources
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dp,
            resources.displayMetrics
        ).toInt()
    }
    }

