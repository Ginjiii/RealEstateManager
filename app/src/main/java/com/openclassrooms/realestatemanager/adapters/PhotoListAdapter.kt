package com.openclassrooms.realestatemanager.adapters

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.databinding.ListPhotosAddedItemBinding
import com.openclassrooms.realestatemanager.models.PropertyPhoto
import java.lang.ref.WeakReference

class PhotoListAdapter(var pictures: List<PropertyPhoto>, private val glide: RequestManager, private val callback: Listener
) : RecyclerView.Adapter<PhotoListViewHolder>() {

    private val listViewHolder = mutableListOf<PhotoListViewHolder>()
    private lateinit var context: Context

    interface Listener{
        fun onClickDeleteButton(photo: PropertyPhoto)
        fun onDragItemRV(viewHolder: PhotoListViewHolder)
        fun onPictureDescriptionEntered(position: Int, description: String)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoListViewHolder {
        context = parent.context
        val photosAddedItemBinding = ListPhotosAddedItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PhotoListViewHolder(photosAddedItemBinding)
    }

    override fun getItemCount(): Int {
        return pictures.size
    }

    override fun onBindViewHolder(holder: PhotoListViewHolder, position: Int) {
        holder.updateWithPicture(pictures[position], glide, callback)
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

class PhotoListViewHolder (private val binding: ListPhotosAddedItemBinding) : RecyclerView.ViewHolder(binding.root){

    private lateinit var callbackWeakRef: WeakReference<PhotoListAdapter.Listener>
    lateinit var photo: PropertyPhoto

    fun updateWithPicture(photo: PropertyPhoto, glide: RequestManager, callback: PhotoListAdapter.Listener){
        this.photo = photo
        callbackWeakRef = WeakReference(callback)
        val pictureToDisplay = photo.thumbnailUrl ?: photo.url
        glide.load(pictureToDisplay).apply(RequestOptions.centerCropTransform()).into(binding.addedPicturesRecyclerView)
        binding.picturesAddedRvDescription.setText(photo.description)

        updateForeground()
        setDragButtonListener()

        binding.picturesAddedRvDescription.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                fetchDescription()
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        })
    }

    fun updateForeground(){
        if(adapterPosition == 0){
            binding.picturesAddedRvForeground.visibility = View.VISIBLE
        } else {
            binding.picturesAddedRvForeground.visibility = View.INVISIBLE
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    fun setDragButtonListener() {
        binding.picturesAddedRvDrag.setOnTouchListener { _, event ->
            if (event.actionMasked == MotionEvent.ACTION_DOWN) {
                val callback = callbackWeakRef.get()
                callback?.let { callback.onDragItemRV(this) }
            }
            return@setOnTouchListener true
        }
    }

    fun onClickDeleteButton(){
        val callback = callbackWeakRef.get()
        callback?.let{ callback.onClickDeleteButton(photo)}
    }

    fun showError(message: String){
        if(binding.picturesAddedRvDescription.text.isNullOrBlank()){
            binding.picturesAddedRvInputLayout.error = message
        } else{
            binding.picturesAddedRvInputLayout.isErrorEnabled = false
        }
    }

    private fun fetchDescription(){
        val callback = callbackWeakRef.get()
        callback?.let{ callback.onPictureDescriptionEntered(this.adapterPosition, binding.picturesAddedRvDescription.text.toString())}
    }
}