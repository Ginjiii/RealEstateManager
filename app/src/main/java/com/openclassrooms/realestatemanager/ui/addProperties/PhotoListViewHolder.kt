package com.openclassrooms.realestatemanager.ui.addProperties

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.MotionEvent
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.openclassrooms.realestatemanager.adapters.PhotoListAdapter
import com.openclassrooms.realestatemanager.databinding.ListPhotosAddedItemBinding
import com.openclassrooms.realestatemanager.models.PropertyPhoto
import java.lang.ref.WeakReference

class PhotoListViewHolder(view: View) : RecyclerView.ViewHolder(view){

    private lateinit var binding: ListPhotosAddedItemBinding
    private lateinit var callbackWeakRef: WeakReference<PhotoListAdapter.Listener>
    lateinit var picture: PropertyPhoto

    fun updateWithPicture(picture: PropertyPhoto, glide: RequestManager, callback: PhotoListAdapter.Listener){
        this.picture = picture
        callbackWeakRef = WeakReference(callback)
        val pictureToDisplay = picture.thumbnailUrl ?: picture.url
        glide.load(pictureToDisplay).apply(RequestOptions.centerCropTransform()).into(binding.addedPicturesRecyclerView)
        binding.picturesAddedRvDescription.setText(picture.description)

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
        binding.picturesAddedRvDelete.setOnClickListener {
            val callback = callbackWeakRef.get()
            callback?.let{ callback.onClickDeleteButton(picture)}
        }
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