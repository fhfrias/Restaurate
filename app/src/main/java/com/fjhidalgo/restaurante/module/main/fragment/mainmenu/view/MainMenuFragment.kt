package com.fjhidalgo.restaurante.module.main.fragment.mainmenu.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.BuildConfig
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.core.app.view.App
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.user.UserModel
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.base.view.BaseFragment
import com.fjhidalgo.restaurante.module.login.view.LoginActivity
import com.fjhidalgo.restaurante.module.main.activity.view.MainActivity
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.adapter.MenuItemAdapter
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.interactor.MainMenuFragmentInteractor
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.interactor.MainMenuFragmentInteractorImpl
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.listener.MenuItemTouchListener
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.model.MainMenuItem
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.presenter.MainMenuFragmentPresenter
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.presenter.MainMenuFragmentPresenterImpl
import kotlin.collections.ArrayList

class MainMenuFragment : BaseFragment(), MainMenuFragmentView {

    companion object {
        fun newInstance(menuClickListener: MenuClickListener, type: Int): MainMenuFragment {
            val fragment = MainMenuFragment()
            fragment.type = type
            fragment.menuClickListener = menuClickListener
            return fragment
        }
    }

    private val presenter: MainMenuFragmentPresenter<MainMenuFragmentView, MainMenuFragmentInteractor> by lazy {
        MainMenuFragmentPresenterImpl<MainMenuFragmentView, MainMenuFragmentInteractor>(MainMenuFragmentInteractorImpl(PreferenceHelperImpl(context, AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    interface MenuClickListener {
        fun onMenuItemClicked(position: Int)
    }

    private var menuClickListener: MenuClickListener? = null
    private var type: Int = 1

    private var tvUsername: TextView? = null
    private var linearLayoutLogout: LinearLayout? = null

    private var recyclerViewProduct: RecyclerView? = null

    private val menuItemListProduct = ArrayList<MainMenuItem>()
    private var menuItemAdapterProduct: MenuItemAdapter? = null

    private var tvInfoVersion: TextView? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewRoot = inflater.inflate(R.layout.main_menu_fragment, container, false)

        initMenu(viewRoot)

        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        presenter.onAttach(this)
        super.onViewCreated(view, savedInstanceState)
    }

    override fun setUp() {

        menuItemListProduct.add(MainMenuItem(0, getString(R.string.add_product), R.drawable.ic_add))
        menuItemListProduct.add(MainMenuItem(1, getString(R.string.delete_product), R.drawable.ic_eliminar))
        menuItemListProduct.add(MainMenuItem(2, getString(R.string.update_product), R.drawable.ic_update))
        menuItemListProduct.add(MainMenuItem(3, getString(R.string.menu_user), R.drawable.ic_people))

        setupRecyclerView()

    }


    private fun setupRecyclerView() {

        val layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)

        recyclerViewProduct?.also {
            it.layoutManager = layoutManager
            it.setHasFixedSize(false)
            it.itemAnimator = DefaultItemAnimator()

            setAdapter()

            val menuItemClickListener = object : MenuItemTouchListener.MenuItemClickListener {
                override fun onClick(view: View, position: Int) {
                    menuClickListener?.onMenuItemClicked(position)
                }

                override fun onLongClick(viewHolder: RecyclerView.ViewHolder, view: View, position: Int) {

                }
            }

            it.addOnItemTouchListener(MenuItemTouchListener(it, null, menuItemClickListener))
        }

    }


    private fun setAdapter() {
        menuItemAdapterProduct = MenuItemAdapter(requireContext(), layoutInflater, menuItemListProduct)
        recyclerViewProduct?.adapter = menuItemAdapterProduct
        menuItemAdapterProduct?.notifyDataSetChanged()

    }



    override fun onStart() {
        super.onStart()
    }

    private fun initMenu(viewRoot: View){
        tvUsername = viewRoot.findViewById(R.id.tvUsername)
        linearLayoutLogout = viewRoot.findViewById(R.id.llLogout)
        recyclerViewProduct = viewRoot.findViewById(R.id.recyclerViewProduct)

        val versionName: String = BuildConfig.VERSION_NAME
        tvInfoVersion = viewRoot.findViewById(R.id.tv_info_version)
        tvInfoVersion?.text = "v.$versionName"

        linearLayoutLogout!!.setOnClickListener {
            Toast.makeText(requireContext(), getString(R.string.close_session), Toast.LENGTH_LONG).show()
            App.instance.firebaseAuth!!.signOut()
            val intentLogin = Intent(requireContext(), LoginActivity::class.java)
            startActivity(intentLogin)
            requireActivity().finish()
        }

        tvUsername!!.setText((requireActivity() as MainActivity).getNameSurnameUser())
    }

}