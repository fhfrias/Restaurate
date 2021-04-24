package com.fjhidalgo.restaurante.module.main.fragment.mainmenu.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.model.MainMenuItem
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.viewholder.MenuItemViewHolder

class MenuItemAdapter(private val context: Context,
                      private val layoutInflater: LayoutInflater,
                      private val menuItemList: List<MainMenuItem>) : RecyclerView.Adapter<MenuItemViewHolder>() {

    init {
        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuItemViewHolder {
        val v = layoutInflater.inflate(R.layout.main_menu_item_view, parent, false)
        return MenuItemViewHolder(v)
    }

    override fun onBindViewHolder(holder: MenuItemViewHolder, position: Int) {
        holder.bind(context, menuItemList[position])
    }

    override fun getItemCount(): Int {
        return menuItemList.size
    }

    override fun getItemId(position: Int): Long {
        return menuItemList[position].id
    }
}
