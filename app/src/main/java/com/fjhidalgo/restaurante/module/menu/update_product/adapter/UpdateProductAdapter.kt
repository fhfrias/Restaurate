package com.fjhidalgo.restaurante.module.menu.update_product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.module.menu.delete_product.viewholder.DeleteProductViewHolder
import com.fjhidalgo.restaurante.module.menu.update_product.viewholder.UpdateProductViewHolder

class UpdateProductAdapter(private val context: Context,
                           private val layoutInflater: LayoutInflater, internal var productList: List<ProductModel>, internal var activity: AppCompatActivity) : RecyclerView.Adapter<UpdateProductViewHolder>() {

    private var lastPosition = -1

    init {

        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpdateProductViewHolder {
        val v = layoutInflater.inflate(R.layout.item_update_product, parent, false)
        return UpdateProductViewHolder(v)
    }

    override fun onBindViewHolder(holder: UpdateProductViewHolder, position: Int) {

        holder.bind(context, productList[position], position, activity)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}