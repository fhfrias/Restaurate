package com.fjhidalgo.restaurante.module.menu.users.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.menu.update_product.viewholder.UpdateProductViewHolder
import com.fjhidalgo.restaurante.module.menu.users.viewholder.UsersViewHolder

class UsersAdapter(private val context: Context,
                   private val layoutInflater: LayoutInflater, internal var usersList: List<UserModel>, internal var activity: AppCompatActivity) : RecyclerView.Adapter<UsersViewHolder>() {

    private var lastPosition = -1

    init {

        setHasStableIds(true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val v = layoutInflater.inflate(R.layout.item_users, parent, false)
        return UsersViewHolder(v)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {

        holder.bind(context, usersList[position], position, activity)
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
}