package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.home

import android.os.Build
import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.adapter.GenericAdapter
import com.ix.ibrahim7.ps.text.jerusalemapp.adapter.ImageAdapter
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentHomeBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentWallpaperBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Wallpaper
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView


class WallpaperFragment : Fragment(),ImageAdapter.OnClickMenuSheet{

    private val mBinding by lazy {
        FragmentWallpaperBinding.inflate(layoutInflater)
    }

    private val imageAdapter by lazy {
        ImageAdapter(this, requireActivity())
    }
    private lateinit var options: ActivityOptionsCompat

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mBinding.rcImageList.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                adapter = imageAdapter
        }

        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.imges), false) {
            findNavController().navigateUp()
        }

        sharedElementReturnTransition =
            TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        options = ActivityOptionsCompat
            .makeSceneTransitionAnimation(requireActivity(), view, "")

        val array = arrayListOf(Wallpaper(height = 5000),Wallpaper(height = 3600),Wallpaper(height = 4600),Wallpaper(height = 5200),Wallpaper(height = 6000),Wallpaper(height = 3600))
        imageAdapter.differ.submitList(array)

    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onClickItemListener(data: Wallpaper, image: ImageView) {

        val action =
            WallpaperFragmentDirections.actionWallpaperFragmentToShowWallpaperFragment()

        findNavController()
            .navigate(
                action,
                FragmentNavigator.Extras.Builder()
                    .addSharedElements(
                        mapOf(image to image.transitionName)
                    ).build()
            )

    }

}