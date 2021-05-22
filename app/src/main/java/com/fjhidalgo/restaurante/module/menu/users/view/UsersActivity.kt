package com.fjhidalgo.restaurante.module.menu.users.view

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.product.ProductModel
import com.fjhidalgo.restaurante.data.model.product.ProductResponse
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.menu.update_product.adapter.UpdateProductAdapter
import com.fjhidalgo.restaurante.module.menu.users.adapter.UsersAdapter
import com.fjhidalgo.restaurante.module.menu.users.interactor.UsersInteractor
import com.fjhidalgo.restaurante.module.menu.users.interactor.UsersInteractorImpl
import com.fjhidalgo.restaurante.module.menu.users.presenter.UsersPresenter
import com.fjhidalgo.restaurante.module.menu.users.presenter.UsersPresenterImpl
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout


class UsersActivity: AppCompatActivity(), UsersView {

    val presenter: UsersPresenter<UsersView, UsersInteractor> by lazy {
        UsersPresenterImpl(UsersInteractorImpl(PreferenceHelperImpl(this, AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    private var recyclerView: RecyclerView? = null
    private var appBarLayout: CustomAppBarLayout? = null

    private var adapterUsers: UsersAdapter? = null
    private var listUsers: List<UserModel>? = null

    private var countAdmin: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.users_activity)
        presenter.onAttach(this)

        initView()
        setUpAppBarLayout()
        presenter.getUsers()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    private fun initView(){
        recyclerView = findViewById(R.id.recyclerView)
        appBarLayout = findViewById(R.id.appPartnerBarLayout)
    }

    private fun setUpAppBarLayout() {
        setSupportActionBar(appBarLayout?.toolbar)
        supportActionBar?.also {
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
            it.title = getString(R.string.list_of_users)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        recyclerView?.also {
            it.layoutManager = layoutManager
            it.setHasFixedSize(false)
            it.itemAnimator = DefaultItemAnimator()

            setAdapter()

        }

    }

    private fun setAdapter() {

        adapterUsers = UsersAdapter(this, layoutInflater, listUsers!!, this)
        recyclerView?.adapter = adapterUsers
        adapterUsers?.notifyDataSetChanged()

    }

    override fun setUsers(listUserResponse: List<UserModel>) {

        countAdmin = 0
        if (listUserResponse != null){
            if (listUsers == null ){
                listUsers = listUserResponse
                for(user in listUsers!!){
                    if (user.isAdmin == true){
                        countAdmin++
                    }
                }
                setupRecyclerView()
            } else {
                for(user in listUserResponse){
                    if (user.isAdmin == true){
                        countAdmin++
                    }
                }
                adapterUsers!!.usersList = listUserResponse
                adapterUsers!!.notifyDataSetChanged()
            }

        } else {
            Toast.makeText(this, getString(R.string.error_load_users), Toast.LENGTH_LONG).show()
        }

    }

    override fun setErrorUsers() {
        Toast.makeText(this, getString(R.string.error_load_users), Toast.LENGTH_LONG).show()
    }

    fun setAdmin(id: String, isAdmin: Boolean){

        if(isAdmin == false && countAdmin <= 1){
            Toast.makeText(this, getString(R.string.no_update_admin), Toast.LENGTH_LONG).show()
            presenter.getUsers()
        } else {
            presenter.updateIsAdmin(id, isAdmin)
        }
    }

    override fun updateAdminOK() {
        presenter.getUsers()
        Toast.makeText(this, getString(R.string.user_update_ok), Toast.LENGTH_LONG).show()
    }

    override fun updateAdminError() {
        Toast.makeText(this, getString(R.string.user_update_error), Toast.LENGTH_LONG).show()
        finish()
    }
}