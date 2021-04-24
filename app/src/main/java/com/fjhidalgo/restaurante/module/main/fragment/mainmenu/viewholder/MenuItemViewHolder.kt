package com.fjhidalgo.restaurante.module.main.fragment.mainmenu.viewholder

import android.content.Context
import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.model.MainMenuItem


class MenuItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var linearLayout: LinearLayout? = null
    var ivIcon: ImageView? = null
    var tvTitle: TextView? = null

    init {
        linearLayout = itemView.findViewById(R.id.content)
        ivIcon = itemView.findViewById(R.id.ivIcon)
        tvTitle = itemView.findViewById(R.id.tvTitle)
    }

    fun bind(context: Context, item: MainMenuItem) {
        ivIcon?.setImageDrawable(ContextCompat.getDrawable(context, item.icon))
        tvTitle?.text = item.title

        val color = if (item.isSelected) {
            "#CCCCCC"
        } else {
            "#FFFFFF"
        }

        linearLayout?.setBackgroundColor(Color.parseColor(color))
        linearLayout?.invalidate()
    }
}