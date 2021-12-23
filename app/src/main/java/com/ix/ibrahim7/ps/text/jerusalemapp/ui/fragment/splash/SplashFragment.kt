package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentSplashBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.other.ISVERIFIED
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getSharePref
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.SplashViewModel

class SplashFragment : Fragment() {

    private val mBinding by lazy {
        FragmentSplashBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[SplashViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.liveData.observe(requireActivity(), Observer {
            when (it) {
                is SplashViewModel.SplashState.SplashFragment -> {
                    if (requireActivity().getSharePref().getBoolean(ISVERIFIED,false)){
                        findNavController().navigate(R.id.action_splashFragment_to_homeFragment2)
                    }else {
                        findNavController().navigate(R.id.action_splashFragment_to_welcomeFragment)
                    }
                }
            }
        })

        val a: Animation = AnimationUtils.loadAnimation(requireContext(), R.anim.slide_up)
        a.reset()
        a.duration = 500

        mBinding.apply {
            logoContainer.clearAnimation()
            logoContainer.startAnimation(a)
        }

    }

}