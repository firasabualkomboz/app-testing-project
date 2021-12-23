package com.ix.ibrahim7.ps.text.jerusalemapp.adapter

import android.content.Context
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.ix.ibrahim7.ps.text.jerusalemapp.R
import com.ix.ibrahim7.ps.text.jerusalemapp.model.Post
import com.ix.ibrahim7.ps.text.jerusalemapp.model.news.Article
import com.ix.ibrahim7.ps.text.jerusalemapp.other.HolderAdapter.loadImage
import com.ix.ibrahim7.ps.text.jerusalemapp.other.IMAGE
import com.ix.ibrahim7.ps.text.jerusalemapp.other.LOCATION
import com.ix.ibrahim7.ps.text.jerusalemapp.other.VIDEO
import com.ix.ibrahim7.ps.text.jerusalemapp.other.getMarkerBitmapFromView
import fm.jiecao.jcvideoplayer_lib.JCVideoPlayerStandard
import kotlinx.android.synthetic.main.item_news.view.*
import kotlinx.android.synthetic.main.item_post.view.*
import kotlin.collections.ArrayList

class GenericAdapter<T>(
    @LayoutRes val layoutId: Int,
    var type: Int,
    val itemclick: OnListItemViewClickListener<T>
) :
    RecyclerView.Adapter<GenericAdapter.GenericViewHolder<T>>() {

    private var onAttach = true
    val data = ArrayList<T>()
    private var inflater: LayoutInflater? = null

    fun addItems(items: List<T>) {
        this.data.clear()
        this.data.addAll(items)
        notifyDataSetChanged()
    }

    val diffCallback = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<T>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GenericViewHolder<T> {
        val layoutInflater = inflater ?: LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, layoutId, parent, false)
        return GenericViewHolder(binding)
    }

    override fun getItemCount(): Int = differ.currentList.size

    override fun onBindViewHolder(holder: GenericViewHolder<T>, position: Int) {
        val itemViewModel = differ.currentList[position]
        holder.bind(itemViewModel, type)
        holder.itemView.apply {

            when(itemViewModel){
                is Post ->{
                    when(itemViewModel.post_type) {
                         IMAGE -> {
                             tvPostImage.visibility = View.VISIBLE
                             tvPostVideo.visibility = View.GONE
                        }
                        VIDEO ->{
                        holder.itemView.apply {
                            tvPostImage.visibility = View.GONE
                            tvPostVideo.visibility = View.VISIBLE
                            tvPostVideo.setUp(
                                itemViewModel.postContent
                                , JCVideoPlayerStandard.SCREEN_LAYOUT_NORMAL
                            )
                            tvPostVideo.backButton.drawable.setVisible(false, false)
                        }
                        }
                        LOCATION ->{
                            holder.CustomeHolder()
                            tvPostImage.visibility = View.GONE
                            tvPostVideo.visibility = View.GONE
                            map.visibility = View.VISIBLE
                            val mCustomMarkerView = (context.getSystemService(
                                Context.LAYOUT_INFLATER_SERVICE
                            ) as LayoutInflater).inflate(R.layout.view_custom_marker, null)

                            val location = itemViewModel.postContent.split("|")
                            val marker = LatLng(location[0].toDouble(), location[1].toDouble())
                            map.getMapAsync {
                                it.apply {
                                  val userMarker=  this.addMarker(
                                        MarkerOptions()
                                            .position(marker)
                                            .title("Your Here")
                                    )
                                    this.animateCamera(CameraUpdateFactory.newLatLng(marker))
                                    userMarker!!.setIcon(
                                        BitmapDescriptorFactory.fromBitmap(
                                            context.getMarkerBitmapFromView(
                                                mCustomMarkerView
                                            )!!
                                        )
                                    )

                                    setOnMapClickListener {
                                        itemclick.onClickItem(itemViewModel, 1)
                                    }
                                }

                            }
                        }
                    }
                }
                is Article ->{
                    holder.itemView.apply {
                        txtNewsDescription.text = fixEncodingUnicode(itemViewModel.description!!)
                        if (!itemViewModel.urlToImage.isNullOrEmpty()){
                            loadImage(tvNewsImage,itemViewModel.urlToImage)
                        }
                    }
                }
            }
            setOnClickListener {
                itemclick.onClickItem(itemViewModel,1)
            }
        }
    }


    class GenericViewHolder<T>(private val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) , OnMapReadyCallback {
        var mapCurrent: GoogleMap? = null
        var map: MapView? = null

        fun bind(itemViewModel: T, F: Int) {
            binding.setVariable(F, itemViewModel)
            binding.executePendingBindings()
        }

        override fun onMapReady(p0: GoogleMap) {
            MapsInitializer.initialize(itemView.context);
            mapCurrent = p0
        }

        fun CustomeHolder() {
            map = itemView.findViewById<View>(R.id.map) as MapView
            if (map != null) {
                map!!.onCreate(null)
                map!!.onResume()
                map!!.getMapAsync(this)
            }
        }

    }

     fun fixEncodingUnicode(response:String): String {
        var str = ""
        try {
            // displayed as    desired encoding
            str = String(response.toByteArray(), charset("UTF-8"));
        } catch (e:Exception) {
            e.printStackTrace();
        }
        val decodedStr = Html.fromHtml(str).toString();
        return decodedStr
    }


    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                onAttach = false
                super.onScrollStateChanged(recyclerView, newState)
            }
        })
        super.onAttachedToRecyclerView(recyclerView)
    }

    interface OnListItemViewClickListener<T> {
        fun onClickItem(itemViewModel: T, type: Int)
    }

}