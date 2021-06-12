package com.fjhidalgo.restaurante.module.menu.update_product.viewholder

import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.module.menu.delete_product.view.DeleteProductActivity
import com.fjhidalgo.restaurante.module.menu.update_product.view.UpdateProductActivity
import com.mikhaellopez.circularimageview.CircularImageView
import java.text.FieldPosition


class UpdateProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tv_name)
    }
    private val tvPrice: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tv_prize)
    }
    private val cirImgProduct: CircularImageView by lazy {
        itemView.findViewById<CircularImageView>(R.id.cirImgProduct)
    }
    private val imgButton: ImageButton by lazy {
        itemView.findViewById<ImageButton>(R.id.btn_delete)
    }

    fun bind(context: Context, item: ProductModel, position: Int, activity: AppCompatActivity) {

        tvName.setText(item.name)
        tvPrice.setText(context.getString(R.string.item_delete_name_price) + item.price + " €")
        imgButton.setOnClickListener {
            (activity as UpdateProductActivity).updateProduct(item, position)
        }
        if ( !item.linkImage.equals("null")){
            Glide.with(activity).load(item.linkImage).into(cirImgProduct)
        } else {
            Glide.with(activity).load(activity.resources.getDrawable(R.drawable.ic_logo_food_drink)).into(cirImgProduct)
        }
    }


}