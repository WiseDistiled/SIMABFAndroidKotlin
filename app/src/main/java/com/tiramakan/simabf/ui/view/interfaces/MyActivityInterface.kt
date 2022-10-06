package com.tiramakan.simabf.ui.view.interfaces

import androidx.fragment.app.Fragment


/**
 * Created by tiramakan on 28/01/2016.
 */
interface MyActivityInterface {
    fun setTitle(title: String)
    fun showFragment(fragment: Fragment)
    fun addFragment(fragment: Fragment)
    fun getFragment(fragmentTagName: String): Fragment?
    fun showActivity(cls: Class<*>)
}
