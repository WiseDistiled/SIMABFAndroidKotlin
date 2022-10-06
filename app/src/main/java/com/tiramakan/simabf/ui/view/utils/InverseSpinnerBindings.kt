package com.tiramakan.simabf.ui.view.utils

import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import android.widget.Spinner
import com.tiramakan.simabf.ui.view.utils.SpinnerExtensions.getSpinnerValue
import com.tiramakan.simabf.ui.view.utils.SpinnerExtensions.setSpinnerInverseBindingListener
import com.tiramakan.simabf.ui.view.utils.SpinnerExtensions.setSpinnerValue

/**
 * Copyright (c) 2017 Fueled. All rights reserved.
 * @author chetansachdeva on 10/04/18
 */
 class InverseSpinnerBindings {

    @BindingAdapter("selectedValue")
    fun Spinner.setSelectedValue(selectedValue: Any?) {
        if (selectedValue != null) {
            setSpinnerValue(selectedValue)
        }
    }

    @BindingAdapter("selectedValueAttrChanged")
    fun Spinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
        if (inverseBindingListener != null) {
            setSpinnerInverseBindingListener(inverseBindingListener)
        }
    }

    companion object InverseSpinnerBindings {

        @JvmStatic
        @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
        fun Spinner.getSelectedValue(): Any? {
            return getSpinnerValue()
        }
    }
}