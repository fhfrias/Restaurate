package com.fjhidalgo.restaurante.module.ubic.viewholder

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.table.TableModel

class ViewholderBar (itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvTitle: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tvTitle)
    }

    fun bind(context: Context, item: TableModel, position: Int, activity: FragmentActivity) {

        tvTitle.setText(item.name)

    }
}