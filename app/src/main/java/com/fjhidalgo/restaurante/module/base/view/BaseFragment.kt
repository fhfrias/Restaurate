package com.fjhidalgo.restaurante.module.base.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.fjhidalgo.restaurante.core.app.view.App

abstract class BaseFragment : Fragment(), BaseView {

    companion object {
        val TAG = BaseFragment::class.java.simpleName
    }

    internal val app: App = App.instance

    var parentActivity: BaseActivity? = null
        private set

    internal var autoSetTitle: Boolean = true

    internal var title: String? = null
        set(value) {
            field = value
            if (autoSetTitle) {
                setToolbarTitle(title)
            }
        }

    private fun setToolbarTitle(title: String?) {

        try {
            val activity = activity as BaseActivity?
            activity?.supportActionBar?.title = title
        } catch (ex: Exception) {
            ex.printStackTrace()
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity) {
            val activity = context as BaseActivity?
            this.parentActivity = activity
            activity!!.onFragmentAttached()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (title != null) {
            if (autoSetTitle) {
                setToolbarTitle(title)
            }
        }

        setUp()
    }

    protected abstract fun setUp()
}