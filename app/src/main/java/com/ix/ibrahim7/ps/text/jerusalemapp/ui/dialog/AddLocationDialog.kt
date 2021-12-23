package com.ix.ibrahim7.ps.text.jerusalemapp.ui.dialog

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.DialogAddLocationBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.databinding.DialogEditAboutmeBinding
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getMarkerBitmapFromView
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getSnackBar

class AddLocationDialog(val itemClick :OnListItemViewClickListener) : BottomSheetDialogFragment(),
    OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private val mBinding by lazy {
        DialogAddLocationBinding.inflate(layoutInflater)
    }

    private lateinit var mMap: GoogleMap
    private lateinit var mCustomMarkerView: View
    private var latLng: LatLng ?= null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = mBinding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)

        mCustomMarkerView = (requireActivity().getSystemService(
            Context.LAYOUT_INFLATER_SERVICE
        ) as LayoutInflater).inflate(R.layout.view_custom_marker, null)

        mBinding.apply {
        isCancelable = false
            btnCancel.setOnClickListener {
                dismiss()
            }

            btnSave.setOnClickListener {
                when{
                    latLng == null ->{
                        requireActivity().getSnackBar(R.layout.item_custom_snackbar,mBinding.view.rootView,"Please Pick up your location")
                    }
                    else ->{
                        itemClick.onPickedLocaiton(latLng!!,"")
                        dismiss()
                    }
                }
            }
        }

    }


    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setPadding(16, 24, 16, 16)
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.setOnMarkerClickListener(this)
        mMap.setOnMapClickListener {
            latLng = it
            val userMarker = mMap.addMarker(
                MarkerOptions()
                    .position(it)
                    .title("Your Here")
            )

            userMarker!!.setIcon(
                BitmapDescriptorFactory.fromBitmap(
                    requireActivity().getMarkerBitmapFromView(
                        mCustomMarkerView
                    )!!
                )
            )
        }
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }


    interface OnListItemViewClickListener {
        fun onPickedLocaiton(latLng: LatLng,address:String?)
    }




}