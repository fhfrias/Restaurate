package com.fjhidalgo.restaurante.ui.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.fjhidalgo.restaurante.R
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.MaterialToolbar

class CustomAppBarLayout : AppBarLayout {

    lateinit var appBarLayout: AppBarLayout

    lateinit var toolbar: MaterialToolbar

    constructor(context: Context) : super(context) {
        initialize(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initialize(context, attrs)
    }

    private fun initialize(context: Context, attrs: AttributeSet?) {
        val viewRoot = View.inflate(context, R.layout.custom_toolbar, this)
        appBarLayout = viewRoot.findViewById(R.id.appBarLayout)
        toolbar = viewRoot.findViewById(R.id.toolbar)
        toolbar.title = null
    }

    fun setScroll(value: Boolean) {

    }
}
