package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.home

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentShowWallpaperBinding
import kotlinx.android.synthetic.main.dialog_wallpaper_options.*

class ShowWallpaperFragment : Fragment() {

    private val mBinding by lazy {
        FragmentShowWallpaperBinding.inflate(layoutInflater)
    }

    private val argImage by lazy {
        requireArguments().getInt("Image")
    }

    lateinit var bottom:BottomSheetBehavior<RelativeLayout>


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        mBinding.apply {

            tvWallpaperImage.setImageResource(R.drawable.ic_test_image)

            bottom = BottomSheetBehavior.from(bottom_sheet)

            bottom.state = BottomSheetBehavior.STATE_HIDDEN

            tvWallpaperImage.setOnClickListener {
                if (bottom.state == BottomSheetBehavior.STATE_EXPANDED){
                    bottom.state = BottomSheetBehavior.STATE_HIDDEN
                }
            }
            bottomAppBar.setNavigationOnClickListener {
                bottom.state = BottomSheetBehavior.STATE_EXPANDED
            }


            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }

        }


    }



}
