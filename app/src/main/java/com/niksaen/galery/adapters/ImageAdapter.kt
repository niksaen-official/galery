package com.niksaen.galery.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.AdapterView.OnItemClickListener
import androidx.recyclerview.widget.RecyclerView
import com.niksaen.galery.databinding.ItemRecyclerViewBinding
import com.squareup.picasso.Picasso
import java.io.File

class ImageAdapter(val context:Context,val imagePathList: ArrayList<String>): RecyclerView.Adapter<ImageViewHolder>() {
    var onItemClickListener:OnItemClickListener? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val vh = ImageViewHolder(ItemRecyclerViewBinding.inflate(LayoutInflater.from(context)))
        vh.onItemClickListener = onItemClickListener
        return vh;
    }

    override fun getItemCount(): Int = imagePathList.size

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) { holder.bind(imagePathList[position]) }
}
class ImageViewHolder(val irvb: ItemRecyclerViewBinding): RecyclerView.ViewHolder(irvb.root) {
    var onItemClickListener:OnItemClickListener? = null
    fun bind(path:String){
        Picasso.get().load(File(path)).into(irvb.image)
        irvb.root.setOnClickListener {
            onItemClickListener?.onItemClick(null, irvb.root, adapterPosition, 1)
        }
    }
}