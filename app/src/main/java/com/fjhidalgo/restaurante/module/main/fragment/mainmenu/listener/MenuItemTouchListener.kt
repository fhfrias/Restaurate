package com.fjhidalgo.restaurante.module.main.fragment.mainmenu.listener

import android.view.View
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.ui.BaseRecyclerViewItemTouchListener

class MenuItemTouchListener(recyclerView: RecyclerView,
                            @IdRes specialIds: IntArray?,
                            clickListener: MenuItemClickListener)
    : BaseRecyclerViewItemTouchListener<MenuItemTouchListener.MenuItemClickListener>(recyclerView, specialIds, clickListener) {

    companion object {
        private const val SPECIAL_VIEW_CLICK_AREA_EXTENSION = 5
    }

    private var clickPadding: Int

    init {
        clickPadding = (SPECIAL_VIEW_CLICK_AREA_EXTENSION * recyclerView.resources.displayMetrics.density).toInt()
    }

    interface MenuItemClickListener : BaseRecyclerViewItemTouchListener.ClickListener

    override fun onSpecialViewClick(specialChildView: View, listPosition: Int) {
        when (specialChildView.id) {
            else -> {
                clickListener.onClick(specialChildView, listPosition)
            }
        }
    }

    override fun getSpecialViewClickPadding(): Int = clickPadding
}