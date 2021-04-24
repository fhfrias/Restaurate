package com.fjhidalgo.restaurante.ui.navigation.view

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.ui.navigation.interactor.NavigationDrawerInteractor
import com.fjhidalgo.restaurante.ui.navigation.interactor.NavigationDrawerInteractorImpl
import com.fjhidalgo.restaurante.ui.navigation.presenter.NavigationDrawerPresenter
import com.fjhidalgo.restaurante.ui.navigation.presenter.NavigationDrawerPresenterImpl
import com.google.android.material.navigation.NavigationView


class NavigationDrawerImpl : NavigationView, NavigationDrawerView {

    private val presenter: NavigationDrawerPresenter<NavigationDrawerView, NavigationDrawerInteractor> by lazy {
        NavigationDrawerPresenterImpl<NavigationDrawerView, NavigationDrawerInteractor>(
            NavigationDrawerInteractorImpl(PreferenceHelperImpl(context, AppConstants.PREF_NAME), ApiHelperImpl())
        )
    }

    interface OnDrawerToggleChangeListener {
        fun onDrawerOpened()
        fun onDrawerClosed()
    }

    var onDrawerToggleChangeListener: OnDrawerToggleChangeListener? = null

    private var drawerLayout: DrawerLayout? = null

    var drawerToggle: DrawerToggle? = null

    val isOpen: Boolean
        get() = drawerToggle?.isOpen!!

    val isClosed: Boolean
        get() = drawerToggle?.isClosed!!

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    fun setup(activity: Activity, drawerLayout: DrawerLayout, onDrawerToggleChangeListener: OnDrawerToggleChangeListener) {
        presenter.onAttach(this)

        this.drawerLayout = drawerLayout
        this.onDrawerToggleChangeListener = onDrawerToggleChangeListener

        drawerToggle = DrawerToggle(activity, drawerLayout, R.string.drawer_open, R.string.drawer_close)

        drawerToggle?.also {
            drawerLayout.addDrawerListener(it)
            it.syncState()
        }
    }

    inner class DrawerToggle : ActionBarDrawerToggle {

        private var activity: Activity
        private var navigationDrawer: NavigationDrawerView? = null

        var isOpen: Boolean = false
        var isClosed: Boolean = false

        constructor(activity: Activity, drawerLayout: DrawerLayout, toolbar: Toolbar, openDrawerContentDescRes: Int, closeDrawerContentDescRes: Int) : super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes) {
            this.activity = activity
        }

        constructor(activity: Activity, drawerLayout: DrawerLayout, openDrawerContentDescRes: Int, closeDrawerContentDescRes: Int) : super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes) {
            this.activity = activity
        }

        override fun onDrawerOpened(drawerView: View) {
            super.onDrawerOpened(drawerView)

            this.isOpen = true
            this.isClosed = false

            this.navigationDrawer = drawerView as NavigationDrawerView?

            onDrawerToggleChangeListener?.onDrawerOpened()
        }

        override fun onDrawerClosed(drawerView: View) {
            super.onDrawerClosed(drawerView)

            this.isOpen = false
            this.isClosed = true

            this.navigationDrawer = drawerView as NavigationDrawerView?

            onDrawerToggleChangeListener?.onDrawerClosed()
        }
    }
}