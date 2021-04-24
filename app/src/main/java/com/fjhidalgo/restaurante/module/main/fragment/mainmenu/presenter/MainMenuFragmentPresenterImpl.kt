package com.fjhidalgo.restaurante.module.main.fragment.mainmenu.presenter


import com.fjhidalgo.restaurante.module.base.presenter.BasePresenterImpl
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.interactor.MainMenuFragmentInteractor
import com.fjhidalgo.restaurante.module.main.fragment.mainmenu.view.MainMenuFragmentView


class MainMenuFragmentPresenterImpl<V : MainMenuFragmentView, I : MainMenuFragmentInteractor>
constructor(interactor: I)
    : BasePresenterImpl<V, I>(interactor), MainMenuFragmentPresenter<V, I> {

}
