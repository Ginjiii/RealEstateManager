package com.openclassrooms.realestatemanager.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.openclassrooms.realestatemanager.R
import com.openclassrooms.realestatemanager.databinding.ItemPropertyBinding
import com.openclassrooms.realestatemanager.models.Property
import com.openclassrooms.realestatemanager.utils.Currency
import com.openclassrooms.realestatemanager.utils.Utils

class PropertyListAdapter(var properties: List<Property>,
                          var currency: Currency?,
                          val glide: RequestManager, private var isDoubleScreen: Boolean)
    : RecyclerView.Adapter<PropertyViewHolder>() {

    private lateinit var adapter: PhotoListAdapter
    private lateinit var context: Context
    private val viewHolders = mutableListOf<PropertyViewHolder>()
    var itemSelected: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PropertyViewHolder {
        context = parent.context
        val propertyItemBinding = ItemPropertyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PropertyViewHolder(propertyItemBinding)
    }

    override fun getItemCount(): Int {
        return properties.size
    }

    override fun onBindViewHolder(holder: PropertyViewHolder, position: Int) {
        val isSelected = position == itemSelected
        holder.updateWithProperty(properties[position], glide, currency, context, isDoubleScreen, isSelected)
        Log.d("TAG", "WHERE IS THE ADAPTER??")
    }

    fun update(properties: List<Property>) {
        this.properties = properties
        notifyDataSetChanged()
    }

    fun getProperty(position: Int): Property {
        return properties[position]
    }

    fun updateCurrency(newCurrency: Currency?) {
        currency = newCurrency
        notifyDataSetChanged()
    }

    fun updateSelection(itemSelected: Int?) {
        this.itemSelected = itemSelected
        viewHolders.forEach { it.updateSelection(itemSelected) }
    }
}

class PropertyViewHolder(private val binding: ItemPropertyBinding) : RecyclerView.ViewHolder(binding.root) {

    private var backgroundColor: Int? = null
    private var priceColor: Int? = null
    private var typeColor: Int? = null

    private var isSelected = false
    private var isSold = false
    private lateinit var context: Context

    init {

        priceColor = binding.price.currentTextColor
        backgroundColor = binding.property.cardBackgroundColor.defaultColor
        typeColor = binding.type.currentTextColor
    }

    fun updateWithProperty(
            property: Property, glide: RequestManager,
            currency: Currency?, context: Context, isDoubleScreen: Boolean, isSelected: Boolean
    ) {
        this.isSelected = isSelected
        this.context = context
        isSold = true
Log.d("TAG", "call?")
        binding.image.setImageResource(R.drawable.home)

        binding.type.text = property.type.typeName
        binding.city.text = property.street[0].toString()
        binding.price.text = when (currency) {
            Currency.EURO -> "${Utils.convertEuroToDollar(property.priceInDollars)}€"
            Currency.DOLLAR -> "$${Utils.convertDollarToEuro(property.priceInDollars)}"
            else -> ""
        }

        if (!isDoubleScreen || !isSelected) {
            configureCardToNormalState()
        }

        if (isSelected) configureCardToSelectedState()

    }

    fun updateSelection(positionSelected: Int?) {
        if (positionSelected != null) {
            if (this.adapterPosition == positionSelected) {
                isSelected = true
                configureCardToSelectedState()
            } else {
                isSelected = false
                configureCardToNormalState()
            }
        } else {
            configureCardToNormalState()
        }

    }

    private fun configureCardToNormalState() {
        if (isSold) {
            binding.property.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimaryUltraLight))
            binding.price.setTextColor(ContextCompat.getColor(context, R.color.colorAccentLight))
            binding.type.setTextColor(ContextCompat.getColor(context, R.color.colorTextAccent))
        } else {
            binding.property.setCardBackgroundColor(backgroundColor!!)
             binding.price.setTextColor(priceColor!!)
             binding.type.setTextColor(typeColor!!)
        }
    }

    private fun configureCardToSelectedState() {
        binding.property.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorAccent))
        binding.price.setTextColor(ContextCompat.getColor(context, R.color.colorTextAccent))
    }
}