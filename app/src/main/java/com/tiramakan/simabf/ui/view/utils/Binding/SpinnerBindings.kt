package com.tiramakan.simabf.ui.view.utils.Binding

import android.widget.Spinner
import androidx.databinding.BindingAdapter
import com.tiramakan.simabf.ui.view.utils.SpinnerExtensions
import com.tiramakan.simabf.ui.view.utils.SpinnerExtensions.setSpinnerEntries
import com.tiramakan.simabf.ui.view.utils.SpinnerExtensions.setSpinnerItemSelectedListener
import com.tiramakan.simabf.ui.view.utils.SpinnerExtensions.setSpinnerValue


/**
 * Copyright (c) 2017 Fueled. All rights reserved.
 * @author chetansachdeva on 09/04/18
 */
class SpinnerBindings {

    @BindingAdapter("entries")
    fun Spinner.setEntries(entries: List<Any>?) {
        setSpinnerEntries(entries)
    }

    @BindingAdapter("onItemSelected")
    fun Spinner.setOnItemSelectedListener(itemSelectedListener: SpinnerExtensions.ItemSelectedListener?) {
        if (itemSelectedListener != null) {
            setSpinnerItemSelectedListener(itemSelectedListener)
        }
    }

    @BindingAdapter("newValue")
    fun Spinner.setNewValue(newValue: Any?) {
        if (newValue != null) {
            setSpinnerValue(newValue)
        }
    }
}
