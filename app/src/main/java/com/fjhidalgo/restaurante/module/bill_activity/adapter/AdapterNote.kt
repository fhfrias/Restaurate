package com.fjhidalgo.restaurante.module.bill_activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.module.bill_activity.viewHolder.ViewholderNote

class AdapterNote (private val context: Context,
                   private val layoutInflater: LayoutInflater, internal var productList: List<ProductModel>, internal var activity: FragmentActivity,internal var type: String
) : RecyclerView.Adapter<ViewholderNote>() {

    private var lastPosition = -1

    init {

        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewholderNote {
        val v = layoutInflater.inflate(R.layout.item_add_note, parent, false)
        return ViewholderNote(v)
    }

    override fun onBindViewHolder(holder: ViewholderNote, position: Int) {

        holder.bind(context, productList[position], position, activity, type)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}