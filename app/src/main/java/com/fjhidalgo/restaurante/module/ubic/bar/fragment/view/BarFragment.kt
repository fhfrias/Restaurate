package com.fjhidalgo.restaurante.module.ubic.bar.fragment.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fjhidalgo.restaurante.R
import com.fjhidalgo.restaurante.data.AppConstants
import com.fjhidalgo.restaurante.data.model.table.TableModel
import com.fjhidalgo.restaurante.data.network.ApiHelperImpl
import com.fjhidalgo.restaurante.data.preferences.PreferenceHelperImpl
import com.fjhidalgo.restaurante.module.base.view.BaseFragment
import com.fjhidalgo.restaurante.module.ubic.adapter.AdapterBar
import com.fjhidalgo.restaurante.module.ubic.bar.fragment.interactor.BarInteractor
import com.fjhidalgo.restaurante.module.ubic.bar.fragment.interactor.BarInteractorImpl
import com.fjhidalgo.restaurante.module.ubic.bar.fragment.presenter.BarPresenter
import com.fjhidalgo.restaurante.module.ubic.bar.fragment.presenter.BarPresenterImpl
import com.fjhidalgo.restaurante.module.ubic.indoor.fragment.interactor.IndoorInteractor
import com.fjhidalgo.restaurante.module.ubic.indoor.fragment.interactor.IndoorInteractorImpl
import com.fjhidalgo.restaurante.module.ubic.indoor.fragment.presenter.IndoorPresenter
import com.fjhidalgo.restaurante.module.ubic.indoor.fragment.presenter.IndoorPresenterImpl
import com.fjhidalgo.restaurante.module.ubic.indoor.fragment.view.IndoorView

class BarFragment: BaseFragment(), BarView {

    val presenter: BarPresenter<BarView, BarInteractor> by lazy {
        BarPresenterImpl(BarInteractorImpl(PreferenceHelperImpl(requireContext(), AppConstants.PREF_NAME), ApiHelperImpl()))
    }

    private var recyclerView: RecyclerView? = null
    private var adapterBar: AdapterBar? = null
    private var tableList: ArrayList<TableModel> = ArrayList()

    private var addTable: CardView? = null
    private var quitTable: CardView? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val viewRoot = inflater.inflate(R.layout.indoor_fragment, container, false)


        return viewRoot
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.onAttach(this)
        initView(view)


        setupRecyclerView()
        presenter.getTablesIndoor()
    }

    override fun setUp() {
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun initView(view: View){

        recyclerView = view.findViewById(R.id.recyclerView)
        addTable = view.findViewById(R.id.btn_card_add)
        quitTable = view.findViewById(R.id.btn_card_quit)

        addTable!!.setOnClickListener {

            if (tableList.size >= 30){
                Toast.makeText(requireContext(), getString(R.string.can_not_add_table), Toast.LENGTH_LONG).show()
            } else {
                val newTable = TableModel("Barra " + tableList.size, tableList.size.toString())
                presenter!!.addTableIndoor(newTable, AppConstants.BAR)
            }
        }

        quitTable!!.setOnClickListener {

            if(tableList.size >= 1){
                presenter.deleteTableIndoor((tableList.size - 1).toString(), AppConstants.BAR)
            } else {
                Toast.makeText(requireContext(), getString(R.string.nothing_delete_table), Toast.LENGTH_LONG).show()
            }

        }
    }

    private fun setupRecyclerView() {

        var layoutInflaterDouble = GridLayoutManager(requireContext(), 2, RecyclerView.VERTICAL, false)

        recyclerView?.also {
            it.layoutManager = layoutInflaterDouble
            it.setHasFixedSize(false)
            it.itemAnimator = DefaultItemAnimator()

            setAdapter()

        }

    }

    private fun setAdapter() {


        adapterBar = AdapterBar(requireContext(), layoutInflater, tableList!!, requireActivity())
        recyclerView?.adapter = adapterBar
        adapterBar?.notifyDataSetChanged()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDetach()
    }

    override fun okAddTable() {
        Toast.makeText(requireContext(), getString(R.string.add_table_ok), Toast.LENGTH_LONG).show()
        presenter.getTablesIndoor()
    }

    override fun errorAddTable() {
        Toast.makeText(requireContext(), getString(R.string.add_table_error), Toast.LENGTH_LONG).show()
    }

    override fun okDeleteTable() {
        Toast.makeText(requireContext(), getString(R.string.ok_delete_table), Toast.LENGTH_LONG).show()
        presenter.getTablesIndoor()
    }

    override fun errorDeleteTable() {
        Toast.makeText(requireContext(), getString(R.string.no_delete_table), Toast.LENGTH_LONG).show()
    }

    override fun setTables(tableList: List<TableModel>?) {
        this.tableList = ArrayList(tableList)
        adapterBar!!.tableList = ArrayList(tableList)
        adapterBar!!.notifyDataSetChanged()
    }
}