package com.fjhidalgo.restaurante.module.base.view

internal interface FragmentCallback {

    fun onFragmentAttached()

    fun onFragmentDetached(tag: String)
}