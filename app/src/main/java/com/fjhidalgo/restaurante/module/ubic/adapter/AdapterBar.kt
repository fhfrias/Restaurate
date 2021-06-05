package com.fjhidalgo.restaurante.module.ubic.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.module.ubic.viewholder.ViewholderBar

class AdapterBar (private val context: Context,
                  private val layoutInflater: LayoutInflater, internal var tableList: List<TableModel>, internal var activity: FragmentActivity
) : RecyclerView.Adapter<ViewholderBar>() {

    private var lastPosition = -1

    init {

        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewholderBar {
        val v = layoutInflater.inflate(R.layout.item_table, parent, false)
        return ViewholderBar(v)
    }

    override fun onBindViewHolder(holder: ViewholderBar, position: Int) {

        holder.bind(context, tableList[position], position, activity)
    }

    override fun getItemCount(): Int {
        return tableList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}