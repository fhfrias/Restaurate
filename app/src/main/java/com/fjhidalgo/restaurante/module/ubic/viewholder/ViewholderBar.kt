package com.fjhidalgo.restaurante.module.ubic.viewholder

import android.content.Context
import android.content.Intent
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.bill_activity.view.BillActivity
import com.fjhidalgo.restaurante.module.main.activity.view.MainActivity

class ViewholderBar (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tvTitle)
    }

    private val materialCardView: CardView by lazy {
        itemView.findViewById<CardView>(R.id.materialCardView)
    }

    fun bind(context: Context, item: TableModel, position: Int, activity: FragmentActivity, type: String) {

        tvTitle.setText(item.name)


        materialCardView.setOnClickListener {
            val intentBill = Intent(activity, BillActivity::class.java)
            intentBill.putExtra("nameTable", item.name)
            intentBill.putExtra("idTable", item.id)
            intentBill.putExtra("typeTable", type)
            intentBill.putExtra("userName", (activity as MainActivity).userData.name)
            activity.startActivity(intentBill)
        }
    }
}