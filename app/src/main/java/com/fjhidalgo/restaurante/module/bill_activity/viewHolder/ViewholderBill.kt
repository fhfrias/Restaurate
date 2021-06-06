package com.fjhidalgo.restaurante.module.bill_activity.viewHolder

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.bill.BillModel
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.bill_activity.view.BillActivity

class ViewholderBill (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val nameProduct: TextView by lazy {
        itemView.findViewById<TextView>(R.id.nameProduct)
    }

    private val amount: TextView by lazy {
        itemView.findViewById<TextView>(R.id.amount)
    }
    private val priceUnity: TextView by lazy {
        itemView.findViewById<TextView>(R.id.priceUnity)
    }
    private val priceTotal: TextView by lazy {
        itemView.findViewById<TextView>(R.id.priceTotal)
    }

    fun bind(context: Context, item: BillModel, position: Int, activity: FragmentActivity) {

        nameProduct.setText(item.name)
        amount.setText(item.amount.toString())
        priceUnity.setText(item.price.toString())

        val total = item.amount!!.toDouble() * item.price!!
        priceTotal.setText(total.toString())
    }
}