package com.ix.ibrahim7.ps.text.jerusalemapp.ui.fragment.home

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.FragmentAddPostBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.model.User
import com.ix.ibrahim7.ps.text.jerusalemapp.other.*
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.AddLocationDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog.LoadingDialog
import com.ix.ibrahim7.ps.text.jerusalemapp.ui.viewmodel.AddPostViewModel
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Provider.getPath
import com.ix.ibrahim7.ps.text.jerusalemapp.util.Resource
import com.vansuita.pickimage.bean.PickResult
import com.vansuita.pickimage.bundle.PickSetup
import com.vansuita.pickimage.dialog.PickImageDialog
import com.vansuita.pickimage.enums.EPickType
import com.vansuita.pickimage.listeners.IPickResult

class AddPostFragment : Fragment(),
    IPickResult,
    OnMapReadyCallback,
    AddLocationDialog.OnListItemViewClickListener{

    private val mBinding by lazy {
        FragmentAddPostBinding.inflate(layoutInflater)
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[AddPostViewModel::class.java]
    }

    private val user by lazy {
    requireArguments().getParcelable<User>(USERS)
    }

    private val dataSourceFactory: DataSource.Factory by lazy {
        DefaultDataSourceFactory(requireContext(), USER_AGENT)
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mCustomMarkerView: View
    private var simpleExoplayer: SimpleExoPlayer? = null
    private var playbackPosition: Long = 0
    private var contentUrl: Uri? = null
    private val REQUEST_TAKE_GALLERY_VIDEO = 200
    private lateinit var postType:EnumConstant
    private lateinit var dialog: LoadingDialog
    private var isShown = false
    private var path = ""


    override fun onResume() {
        super.onResume()
        if (contentUrl != null)
            initializePlayer(contentUrl?.toString()!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog = LoadingDialog()
        requireActivity().setToolbarView(mBinding.toolbarView, getString(R.string.add_post), false) {
            findNavController().navigateUp()
        }

        mBinding.apply {

            val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment!!.getMapAsync(this@AddPostFragment)

            mCustomMarkerView = (requireActivity().getSystemService(
                Context.LAYOUT_INFLATER_SERVICE
            ) as LayoutInflater).inflate(R.layout.view_custom_marker, null)

            btnShow.setOnClickListener {
                when {
                    isShown -> {
                        tvShowImage.setImageResource(R.drawable.ic_show)
                        addContainer.visibility = View.GONE
                        isShown = false
                    }
                    else -> {
                        tvShowImage.setImageResource(R.drawable.ic_hide)
                        addContainer.visibility = View.VISIBLE
                        isShown = true
                    }
                }
            }


            btnAddLocation.setOnClickListener {
                AddLocationDialog(this@AddPostFragment).show(childFragmentManager,"")
            }

            btnAddImage.setOnClickListener {
                requireActivity().getPermission(
                    arrayListOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    )
                ) {
                    openChooseImage()
                }
            }

            btnAddVideo.setOnClickListener {
                requireActivity().getPermission(
                    arrayListOf(
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE
                    )
                ) {
                    openVideoChooser("video/*",REQUEST_TAKE_GALLERY_VIDEO)
                }
            }

            btnPublishPost.setOnClickListener {
                when {
                    TextUtils.isEmpty(txtPostDescription.text!!.toString()) -> {
                        txtPostDescription.apply {
                            error = getString(R.string.required_field)
                            requestFocus()
                        }
                        return@setOnClickListener
                    }
                    /*path == "" -> {
                       requireActivity().getSnackBar(R.layout.item_custom_snackbar,mBinding.root,getString(
                                                  R.string.you_must_add_post_contect))
                        return@setOnClickListener
                    }*/
                    else -> {
                        dialog.show(childFragmentManager,"")
                        when (postType) {
                            EnumConstant.VIDEO -> {
                                viewModel.uploadVideo(
                                    Uri.parse(
                                        getPath(
                                            requireContext(),
                                            contentUrl!!
                                        )
                                    )
                                ) { videoPath ->
                                    path = videoPath
                                    viewModel.addPost(postDescription = txtPostDescription.text.toString(),user = user!!,postType = VIDEO,postContent = path)
                                }
                            }
                            EnumConstant.IMAGE -> {
                                viewModel.ZipImage(contentUrl!!,requireContext()) { image ->
                                    viewModel.uploadImage(image) { imagePath ->
                                        path = imagePath
                                        viewModel.addPost(postDescription = txtPostDescription.text.toString(),user = user!!,postType = IMAGE,postContent = path)
                                    }
                                }
                            }
                            else->{
                                viewModel.addPost(postDescription = txtPostDescription.text.toString(),user = user!!,postType = LOCATION,postContent = "${mMap.cameraPosition.target.latitude}|${mMap.cameraPosition.target.longitude}")
                            }
                        }
                    }
                }
            }

        }

        subscribeToObserver()
    }

    private fun subscribeToObserver() {

        lifecycleScope.launchWhenStarted {
            viewModel.getAddPostLiveData().observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading ->{
                        dialog.show(childFragmentManager, "")
                    }
                    is Resource.Success  -> {
                        if (dialog.isAdded)
                            dialog.dismiss()

                        requireActivity().getSnackBar(
                            R.layout.item_custom_snackbar,
                            mBinding.root,
                            "تم إضافة البوست بنجاح"
                        )
                        findNavController().navigateUp()
                    }
                    is Resource.Error -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                        Log.e("eee addPost Error",response.message.toString())
                    }
                }
            })
        }

        lifecycleScope.launchWhenStarted {
            viewModel.postContentLiveData().observe(viewLifecycleOwner, Observer { response ->
                when (response) {
                    is Resource.Loading ->{
                        if (!dialog.isAdded)
                        dialog.show(childFragmentManager, "")
                    }
                    is Resource.Success  -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                    }
                    is Resource.Error -> {
                        if (dialog.isAdded)
                            dialog.dismiss()
                        Log.e("eee postContent Error",response.message.toString())
                    }
                }
            })
        }
    }


    private fun openChooseImage() {
        PickImageDialog.build(
            PickSetup().setTitle("Select Image").setSystemDialog(false).setVideo(false)
                .setPickTypes(EPickType.GALLERY)
        )
            .setOnPickResult { onPickResult(it) }.setOnPickCancel {}.show(activity)
    }


    private fun openVideoChooser(intentType:String,requestCode: Int) {
        val intent = Intent().apply {
            type = intentType
            action = Intent.ACTION_GET_CONTENT
        }
        startActivityForResult(
            Intent.createChooser(intent, "Select Video"),
            requestCode
        )
    }


    private fun initializePlayer(uri: String) {
        simpleExoplayer = SimpleExoPlayer.Builder(requireContext()).build()
        preparePlayer(uri)
        mBinding.tvPostContentVideo.player = simpleExoplayer
        simpleExoplayer?.seekTo(playbackPosition)
        simpleExoplayer?.playWhenReady = true
    }

    private fun buildMediaSource(uri: Uri): MediaSource {
        return ProgressiveMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri)
    }

    private fun preparePlayer(videoUrl: String) {
        val uri = Uri.parse(videoUrl)
        val mediaSource = buildMediaSource(uri)
        simpleExoplayer?.prepare(mediaSource)
    }

    private fun releasePlayer() {
        playbackPosition = simpleExoplayer?.currentPosition ?: 0
        simpleExoplayer?.release()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode == RESULT_OK) {
            when(requestCode){
                REQUEST_TAKE_GALLERY_VIDEO->{
                    val selectedImageUri = data!!.data
                    postType = EnumConstant.VIDEO
                    contentUrl = selectedImageUri
                    initializePlayer(contentUrl!!.toString())
                    mBinding.tvPostContent.visibility = View.VISIBLE
                    mBinding.tvPostContentVideo.visibility = View.VISIBLE
                    mBinding.tvPostContentImage.visibility = View.GONE
                    mBinding.addContainer.visibility = View.GONE
                    mBinding.btnShow.visibility = View.GONE
                }

            }

        }
        super.onActivityResult(requestCode, resultCode, data)
    }



    override fun onPickResult(r: PickResult?) {
        if (r!!.error == null) {
            postType = EnumConstant.IMAGE
            contentUrl = r.uri
            mBinding.tvPostContentImage.setImageURI(contentUrl)
            mBinding.tvPostContent.visibility = View.VISIBLE
            mBinding.tvPostContentImage.visibility = View.VISIBLE
            mBinding.tvPostContentVideo.visibility = View.GONE
            mBinding.addContainer.visibility = View.GONE
            mBinding.btnShow.visibility = View.GONE

        }
    }

    override fun onStop() {
        super.onStop()
        releasePlayer()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setPadding(16, 24, 16, 16)
        mMap.uiSettings.isZoomControlsEnabled = true
    }

    override fun onPickedLocaiton(latLng: LatLng, address: String?) {
        mBinding.tvPostContent.visibility = View.VISIBLE
        mBinding.tvPostContentImage.visibility = View.GONE
        mBinding.tvPostContentVideo.visibility = View.GONE
        mBinding.mapContainer.visibility = View.VISIBLE
        mBinding.addContainer.visibility = View.GONE
        mBinding.btnShow.visibility = View.GONE
        postType = EnumConstant.LOCATION
        val userMarker = mMap.addMarker(
            MarkerOptions()
                .position(LatLng(latLng.latitude,latLng.longitude))
                .title("Your Here")
        )
        mMap.animateCamera(CameraUpdateFactory.newLatLng(latLng))
        userMarker!!.setIcon(
            BitmapDescriptorFactory.fromBitmap(
                requireActivity().getMarkerBitmapFromView(
                    mCustomMarkerView
                )!!
            )
        )
    }


}