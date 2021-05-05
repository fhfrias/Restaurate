package com.fjhidalgo.restaurante.module.menu.delete_product.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.module.menu.delete_product.viewholder.DeleteProductViewHolder

class DeleteProductAdapter(private val context: Context,
                   private val layoutInflater: LayoutInflater, internal var productList: List<ProductModel>, internal var activity: AppCompatActivity) : RecyclerView.Adapter<DeleteProductViewHolder>() {

    private var lastPosition = -1

    init {

        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeleteProductViewHolder {
        val v = layoutInflater.inflate(R.layout.item_delete_product, parent, false)
        return DeleteProductViewHolder(v)
    }

    override fun onBindViewHolder(holder: DeleteProductViewHolder, position: Int) {

        holder.bind(context, productList[position], position, activity)
    }

    override fun getItemCount(): Int {
        return productList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}