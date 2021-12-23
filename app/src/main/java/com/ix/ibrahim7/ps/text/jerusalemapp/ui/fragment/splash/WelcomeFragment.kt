package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.BR
import com.ix.ibrahim7.ps.text.jerusalemapp.adapter.GenericAdapter
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentWelcomeBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Welcome
import com.ix.ibrahim7.ps.text.jerusalemapp.model.getData

class WelcomeFragment : Fragment(), GenericAdapter.OnListItemViewClickListener<Welcome> {

    private val mBinding by lazy {
        FragmentWelcomeBinding.inflate(layoutInflater)
    }

    private val mAdapter by lazy {
        GenericAdapter(R.layout.item_welcome, BR.welcome, this)
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ) = mBinding.root

    private var count = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mAdapter.submitList(getData(requireContext()))

        mBinding.vpWelcome.apply {
            adapter = mAdapter
            mBinding.dotsIndicator.setViewPager2(this)
        }

        mBinding.btnSkip.setOnClickListener {
            mBinding.vpWelcome.currentItem = 2
            gone(false)
        }

        mBinding.vpWelcome.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                gone(position != 2)
                count = position

            }
        })

        mBinding.btnNext.setOnClickListener {
            count++
            mBinding.vpWelcome.currentItem = count
            gone(count != 2)

        }


        mBinding.btnGetStarted.setOnClickListener {
            findNavController().navigate(R.id.action_welcomeFragment_to_loginFragment)
        }

    }

    private fun gone(isShow: Boolean) {
        if (!isShow) {
            mBinding.btnGetStarted.visibility = View.VISIBLE
            mBinding.btnNext.visibility = View.INVISIBLE
            mBinding.btnSkip.visibility = View.INVISIBLE
        } else {
            mBinding.btnGetStarted.visibility = View.INVISIBLE
            mBinding.btnNext.visibility = View.VISIBLE
            mBinding.btnSkip.visibility = View.VISIBLE
        }
    }

    override fun onClickItem(itemViewModel: Welcome, type: Int) {

    }
}