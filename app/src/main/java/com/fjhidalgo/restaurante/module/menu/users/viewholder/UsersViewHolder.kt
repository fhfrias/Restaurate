package com.fjhidalgo.restaurante.module.menu.users.viewholder

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.menu.users.view.UsersActivity


class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val tvName: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tv_name)
    }
    private val tvEmail: TextView by lazy {
        itemView.findViewById<TextView>(R.id.tv_email)
    }
    private val checkBoxAdmin: CheckBox by lazy {
        itemView.findViewById<CheckBox>(R.id.check_admin)
    }


    fun bind(context: Context, item: UserModel, position: Int, activity: AppCompatActivity) {

        tvName.setText(item.name + " " + item.surname)
        tvEmail.setText(item.email)

        if (item.isAdmin == true){
            checkBoxAdmin.isChecked = true
        } else {
            checkBoxAdmin.isChecked = false
        }

        checkBoxAdmin.setOnClickListener {
            (activity as UsersActivity).setAdmin(item.id!!, checkBoxAdmin.isChecked)
        }
    }
}