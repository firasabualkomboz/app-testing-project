package com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.DialogPostInformationBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.other.EnumConstant
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import kotlinx.android.synthetic.main.item_post.view.*

class PostInformationDialog(
    val postDetails: Post,
    val postType: EnumConstant
) : BottomSheetDialogFragment() {

    private val mBinding by lazy {
        DialogPostInformationBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        isCancelable = false

        mBinding.apply {
            post = postDetails

            when(postType){
                EnumConstant.IMAGE ->
                {
                    tvPostImage.visibility = View.VISIBLE
                    tvPostContentVideo.visibility = View.GONE
                }
                EnumConstant.VIDEO->
                {
                tvPostImage.visibility = View.GONE
                tvPostContentVideo.visibility = View.VISIBLE
                tvPostContentVideo.setUp(
                postDetails.postContent,JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL
                )
                tvPostContentVideo.backButton.drawable.setVisible(false, false)
                }
            }

            btnCancel.setOnClickListener {
                dismiss()
            }
        }

    }

    interface OnListItemViewClickListener {
        fun onAnswerSend(ticketID : String , reply : String)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }
}