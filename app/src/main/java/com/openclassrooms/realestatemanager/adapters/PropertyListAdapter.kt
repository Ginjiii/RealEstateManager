package com.openclassrooms.realestatemanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.utils.Utils
import com.openclassrooms.realestatemanager.databinding.ItemPropertyBinding
import com.openclassrooms.realestatemanager.models.PropertyWithAllData
import com.openclassrooms.realestatemanager.ui.fragments.PropertyListFragment

class PropertyListAdapter(var properties: List<PropertyWithAllData>, val glide: RequestManager, private var isDoubleScreen: Boolean)
    : RecyclerView.Adapter<PropertyListAdapter.PropertyViewHolder>() {

    private lateinit var context: Context
    private val viewHolders = mutableListOf<PropertyViewHolder>()
    var itemSelected: Int? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        val binding = ItemPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val isSelected = position == itemSelected
                holder.updateWithProperty(properties[position], glide, context, isDoubleScreen, isSelected)
    }

    class PropertyViewHolder(private val binding: ItemPropertyBinding) : RecyclerView.ViewHolder(binding.root) {

        private var isSelected = false
        private var isSold = false
        private lateinit var context: Context

        fun updateWithProperty(
                property: PropertyWithAllData, glide: RequestManager, context: Context, isDoubleScreen: Boolean, isSelected: Boolean) {
            this.isSelected = isSelected
            this.context = context
            isSold = property.property.isSold


            binding.type.text = property.property.type
            binding.city.text = property.property.street
            binding.price.text = property.property.priceInDollars.toString()
            }

        fun bind(propertyWithAllData: PropertyWithAllData, onItemClickListener: (PropertyWithAllData) -> Unit) {
            binding.apply {

                Glide.with(itemView)
                        .load(propertyWithAllData.property.coverPhoto)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .error(R.drawable.for_sale)
                        .into(image)

                type.text = propertyWithAllData.property.type
                city.text = propertyWithAllData.property.city

                room.text = if (propertyWithAllData.property.nbrRoom >= 10) {
                    "${propertyWithAllData.property.nbrRoom}+"
                } else {
                    propertyWithAllData.property.nbrRoom.toString()
                }
                bedroom.text = if (propertyWithAllData.property.nbrBedroom >= 10) {
                    "${propertyWithAllData.property.nbrBedroom}+"
                } else {
                    propertyWithAllData.property.nbrBedroom.toString()
                }
                bathroom.text = if (propertyWithAllData.property.nbrBathroom >= 10) {
                    "${propertyWithAllData.property.nbrBathroom}+"
                } else {
                    propertyWithAllData.property.nbrBathroom.toString()
                }

                if (propertyWithAllData.property.isSold) soldDiagonal.visibility = View.VISIBLE else soldDiagonal.visibility = View.INVISIBLE

                when (PropertyListFragment.isDollar) {
                    null -> price.text = Utils.formatInDollar(propertyWithAllData.property.priceInDollars, 0)
                    true -> {
                        val pPrice = Utils.convertDollarToEuro(propertyWithAllData.property.priceInDollars)
                        price.text = Utils.formatInEuro(pPrice, 0)
                    }
                    false -> price.text = Utils.formatInDollar(propertyWithAllData.property.priceInDollars, 0)
                }

                root.setOnClickListener {
                    onItemClickListener.invoke(propertyWithAllData)
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<PropertyWithAllData>() {
        override fun areItemsTheSame(oldItem: PropertyWithAllData, newItem: PropertyWithAllData) =
                oldItem.property.id == newItem.property.id

        override fun areContentsTheSame(oldItem: PropertyWithAllData, newItem: PropertyWithAllData) =
                oldItem.property.hashCode() == newItem.property.hashCode()
    }

    override fun getItemCount(): Int {
        return properties.size
    }
}