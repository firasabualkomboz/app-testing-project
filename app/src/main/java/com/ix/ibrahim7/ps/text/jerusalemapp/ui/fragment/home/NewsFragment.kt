package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.home

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.ix.ibrahim7.ps.text.jerusalemapp.BR
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.adapter.GenericAdapter
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentNewsBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.news.Article
import com.ix.ibrahim7.ps.text.jerusalemapp.model.news.News
import com.ix.ibrahim7.ps.text.jerusalemapp.other.setToolbarView
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.LoadingDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.NewsViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.util.ResultRequest
import okhttp3.RequestBody

class NewsFragment : Fragment(),GenericAdapter.OnListItemViewClickListener<Article>{

    private val mBinding by lazy {
        FragmentNewsBinding.inflate(layoutInflater)
    }

    private val newsAdapter by lazy {
        GenericAdapter(R.layout.item_news, BR.News,this)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[NewsViewModel::class.java]
    }
    private lateinit var dialog: LoadingDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog = LoadingDialog()

        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.last_news), true) {
            findNavController().navigateUp()
        }

        viewModel.getNews(apiKey = "09271e5877f64c73bc2a25faf90b52ff",q = "%D8%A7%D9%84%D9%82%D8%AF%D8%B3")
        mBinding.apply {


        }

        subscribeToObserver()
    }

    private fun subscribeToObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.getNewsLiveData().observe(viewLifecycleOwner, Observer {resultResponse->
                when (resultResponse.status) {
                    ResultRequest.Status.LOADING -> {
                        dialog.show(childFragmentManager, "")
                    }
                    ResultRequest.Status.SUCCESS -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                        newsAdapter.submitList((resultResponse.data as News).articles!!)
                        mBinding.rcNews.adapter = newsAdapter
                        Log.e("eee data",resultResponse.data.toString())
                    }
                    ResultRequest.Status.ERROR -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                        Log.e("eee getNewsData",resultResponse.message!!)
                    }
                }
            })
        }
    }


    override fun onClickItem(itemViewModel: Article, type: Int) {

    }

}