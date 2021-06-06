package com.fjhidalgo.restaurante.module.bill_activity.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.bill.BillModel
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.bill_activity.viewHolder.ViewholderBill

class AdapterBill (private val context: Context,
                   private val layoutInflater: LayoutInflater, internal var billList: List<BillModel>, internal var activity: FragmentActivity
) : RecyclerView.Adapter<ViewholderBill>() {

    private var lastPosition = -1

    init {

        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewholderBill {
        val v = layoutInflater.inflate(R.layout.item_bill, parent, false)
        return ViewholderBill(v)
    }

    override fun onBindViewHolder(holder: ViewholderBill, position: Int) {

        holder.bind(context, billList[position], position, activity)
    }

    override fun getItemCount(): Int {
        return billList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}