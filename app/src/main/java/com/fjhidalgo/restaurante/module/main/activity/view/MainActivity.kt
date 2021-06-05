package com.fjhidalgo.restaurante.module.main.activity.view

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.drawerlayout.widget.DrawerLayout
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.model.Auth
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.module.base.view.BaseFragment
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.view.MainMenuFragment
import com.fjhidalgo.restaurante.module.menu.add_product.view.AddProductActivity
import com.fjhidalgo.restaurante.module.menu.delete_product.view.DeleteProductActivity
import com.fjhidalgo.restaurante.module.menu.update_product.view.UpdateProductActivity
import com.fjhidalgo.restaurante.module.menu.users.view.UsersActivity
import com.fjhidalgo.restaurante.module.ubic.bar.fragment.view.BarFragment
import com.fjhidalgo.restaurante.module.ubic.indoor.fragment.view.IndoorFragment
import com.fjhidalgo.restaurante.module.ubic.outdoor.fragment.view.OutdoorFragment
import com.fjhidalgo.restaurante.ui.navigation.view.NavigationDrawerImpl
import com.fjhidalgo.restaurante.ui.toolbar.CustomAppBarLayout
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson


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

    var userData: UserModel = UserModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setUpMenu()
        setUpAppBarLayout()
        setOnButtonNavigationItemSelectedListener()
        loadDataUser(intent.extras!!)
        displayView(R.id.mnuIndoor)
    }

    private fun loadDataUser(extras: Bundle) {
        userData.email  = extras.getString("email")
        userData.name = extras.getString("name")
        userData.surname = extras.getString("surname")
        userData.isAdmin = extras.getBoolean("isAdmin")
        userData.id = extras.getString("id")
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
                fragment = IndoorFragment()
            }
            R.id.mnuOutdoor -> {
                fragment = OutdoorFragment()
            }
            R.id.mnuBarra -> {
                fragment = BarFragment()
            }
            0 -> {
                if (userData.isAdmin!!){
                    val i = Intent(this, AddProductActivity::class.java)
                    startActivity(i)
                } else {
                    showToastNoAdmin()
                }
            }
            1 -> {
                if (userData.isAdmin!!){
                    val i = Intent(this, DeleteProductActivity::class.java)
                    startActivity(i)
                } else {
                    showToastNoAdmin()
                }

            }
            2 -> {
                if (userData.isAdmin!!){
                    val i = Intent(this, UpdateProductActivity::class.java)
                    startActivity(i)
                } else {
                   showToastNoAdmin()
                }

            }
            3 -> {
                if(userData.isAdmin!!){
                    val i = Intent(this, UsersActivity::class.java)
                    startActivity(i)
                } else {
                    showToastNoAdmin()
                }
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

    private fun showToastNoAdmin() {
        Toast.makeText(this, getString(R.string.no_admin_msg), Toast.LENGTH_LONG).show()
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

    fun getNameSurnameUser(): String {
        return userData.name + " " + userData.surname
    }

    fun isAdminUser(): Boolean {
        return userData.isAdmin!!
    }
}