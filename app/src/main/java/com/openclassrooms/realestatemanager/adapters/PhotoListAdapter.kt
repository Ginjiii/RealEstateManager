package com.openclassrooms.realestatemanager.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.openclassrooms.realestatemanager.databinding.ListPhotosAddedItemBinding
import com.openclassrooms.realestatemanager.models.PropertyPhoto
import com.openclassrooms.realestatemanager.ui.addProperties.PhotoListViewHolder

class PhotoListAdapter(var pictures: List<PropertyPhoto>, private val glide: RequestManager, private val callback: Listener) : RecyclerView.Adapter<PhotoListViewHolder>() {

    private val listViewHolder = mutableListOf<PhotoListViewHolder>()


    interface Listener{
        fun onClickDeleteButton(propertyPhoto: PropertyPhoto)
        fun onDragItemRV(viewHolder: PhotoListViewHolder)
        fun onPictureDescriptionEntered(position: Int, description: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        val binding = ListPhotosAddedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoListViewHolder(binding.addedPicturesRecyclerView)
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        holder.updateWithPicture(pictures[position], glide, callback)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    fun update(pictures: List<PropertyPhoto>){
        this.pictures = pictures.toMutableList()
        notifyDataSetChanged()
    }

    fun updateForegroundViewHolder(){
        listViewHolder.forEach { it.updateForeground() }
    }

    fun showErrorViewHolder(message: String){
        listViewHolder.forEach {
            it.showError(message)
        }
    }
}