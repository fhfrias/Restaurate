package com.fjhidalgo.restaurante.module.bill_activity.viewHolder

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.bill.BillModel
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.bill_activity.view.BillActivity

class ViewholderNote (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tv_name: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tv_name)
    }
    private val tv_prize: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tv_prize)
    }
    private val amount_product: TextView by lazy {
        itemView.findViewById<TextView>(R.id.amount_product)
    }
    private val btn_add: ImageButton by lazy {
        itemView.findViewById<ImageButton>(R.id.btn_add)
    }
    private val btn_rest: ImageButton by lazy {
        itemView.findViewById<ImageButton>(R.id.btn_rest)
    }
    private val btn_addNote: ImageButton by lazy {
        itemView.findViewById<ImageButton>(R.id.btn_addNote)
    }

    private val imgProduct: ImageView by lazy {
        itemView.findViewById<ImageView>(R.id.imgProduct)
    }


    fun bind(context: Context, item: ProductModel, position: Int, activity: FragmentActivity, type: String) {

        tv_name.setText(item.name)
        tv_prize.setText("Precio : " + item.price + " €")

        btn_add.setOnClickListener{
            val value = amount_product.text.toString()
            var add = value.toInt() + 1
            amount_product.setText(add.toString())
        }

        btn_rest.setOnClickListener {
            val value = amount_product.text.toString()
            if ( value.toInt() > 0){
                var add = value.toInt() - 1
                amount_product.setText(add.toString())
            }

        }

        btn_addNote.setOnClickListener {
            (activity as BillActivity).addNoteToBill(item, amount_product.text.toString().toInt(), type)
            amount_product.setText("0")
        }

        if ( !item.linkImage.equals("null")){
            Glide.with(activity).load(item.linkImage).into(imgProduct)
        }
    }
}