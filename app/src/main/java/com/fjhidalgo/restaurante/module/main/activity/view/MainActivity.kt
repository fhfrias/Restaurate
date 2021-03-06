package com.fjhidalgo.restaurante.module.main.activity.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.module.base.view.BaseFragment
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.view.MainMenuFragment
import com.fjhidalgo.restaurante.module.menu.add_product.view.AddProductActivity
import com.fjhidalgo.restaurante.ui.navigation.view.NavigationDrawerImpl
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private val customAppBarLayout: CustomAppBarLayout by lazy {
        findViewById<CustomAppBarLayout>(R.id.appBarLayout)
    }
    private val drawerLayout: DrawerLayout by lazy {
        findViewById<DrawerLayout>(R.id.drawerLayout)
    }
    private val navigationDrawer: NavigationDrawerImpl by lazy {
        findViewById<NavigationDrawerImpl>(R.id.navigationView)
    }
    private val bottomNavigationView: BottomNavigationView by lazy {
        findViewById<BottomNavigationView>(R.id.bottomNavigationView)
    }
    private val contentScreen: CoordinatorLayout by lazy{
        findViewById<CoordinatorLayout>(R.id.contentScreen)
    }

    private var currentPosition = R.id.mnuIndoor
    private var mainMenuFragment: MainMenuFragment? = null
    private var openDrawerLayout: Boolean = false

    private var fragment: BaseFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//
        setUpMenu()
        setUpAppBarLayout()
        setOnButtonNavigationItemSelectedListener()

    }

    private fun setUpMenu() {

        val menuClickListener = object : MainMenuFragment.MenuClickListener {
            override fun onMenuItemClicked(position: Int) {
                displayView(position)
            }

        }

        mainMenuFragment = MainMenuFragment.newInstance(menuClickListener, 0)

        supportFragmentManager.beginTransaction()
            .replace(R.id.flMainMenu, mainMenuFragment!!, "")
            .commit()

        setupNavigation()
    }

    private fun setupNavigation() {

        navigationDrawer.setup(
            this,
            drawerLayout,
            object : NavigationDrawerImpl.OnDrawerToggleChangeListener {
                override fun onDrawerOpened() {
                    openDrawerLayout = true
                }

                override fun onDrawerClosed() {
                    openDrawerLayout = false
                }
            })

        drawerLayout.setScrimColor(Color.TRANSPARENT)

        val actionBarDrawerToggle: ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.drawer_open,
            R.string.drawer_close
        ) {
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                val slideX: Float = drawerView.width * slideOffset
                customAppBarLayout.translationX = slideX
                bottomNavigationView.translationX = slideX
                contentScreen.translationX = slideX
            }
        }

        drawerLayout.addDrawerListener(actionBarDrawerToggle)
    }

    fun displayView(position: Int) {

        if (currentPosition != position) {
            currentPosition = position

            bottomNavigationView.setOnNavigationItemSelectedListener(null)
            bottomNavigationView.selectedItemId = position
            setOnButtonNavigationItemSelectedListener()
        }

        when (position) {

            R.id.mnuIndoor -> {

            }
            R.id.mnuOutdoor -> {

            }
            R.id.mnuBarra -> {

            }
            0 -> {
                val i = Intent(this, AddProductActivity::class.java)
                startActivity(i)
            }
            1 -> {
                Log.e("Estamos", " En productos Delete")
            }
            2 -> {
                Log.e("Estamos", " En productos Actualizar")
            }
            3 -> {
                Log.e("Estamos", " En user")
            }
            4 -> {

            }
            5 -> {
//                /*val i = Intent(this, LogoutActivity::class.java)
//                startActivity(i)*/
//
//                val logOutDialog = LogOutDialog.newInstance(object : LogOutDialog.OnLogOutDialogButtonClicked {
//
//                    override fun onAcceptButtonClicked() {
//
//                        App.instance.also {
//                            it.preferenceHelper.resetUserPref()
//                            it.requestQueue?.cache?.clear()
//
//                            // Firebase User
//                            it.firebaseAuth?.signOut()
//
//                            val intent = packageManager?.getLaunchIntentForPackage(packageName)
//                            intent?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
//                            startActivity(intent)
//                        }
//                    }
//
//                    override fun onCancelButtonClicked(dialog: DialogFragment) {
//                        dialog.dismiss()
//                    }
//                })
//                logOutDialog.show(supportFragmentManager, "LogOut")
            }
            else -> {
            }
        }

        if (fragment != null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.frameLayout, fragment!!)
                .commitNow()
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            android.R.id.home -> {
                if (openDrawerLayout) {
                    drawerLayout.closeDrawer(navigationDrawer)
                    openDrawerLayout = false
                } else {
                    drawerLayout.openDrawer(navigationDrawer)
                    openDrawerLayout = true
                }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    private fun setOnButtonNavigationItemSelectedListener() {
        bottomNavigationView.setOnNavigationItemSelectedListener {
            displayView(it.itemId)
            true
        }
    }

    private fun setUpAppBarLayout() {
        setSupportActionBar(customAppBarLayout.toolbar)

        supportActionBar?.also {
            it.setHomeAsUpIndicator(R.drawable.ic_menu_white)
            it.setDisplayHomeAsUpEnabled(true)
            it.setDisplayShowHomeEnabled(true)
        }
    }

}