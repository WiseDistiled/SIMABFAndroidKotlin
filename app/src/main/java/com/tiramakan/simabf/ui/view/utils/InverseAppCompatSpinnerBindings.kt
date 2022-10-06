package com.tiramakan.simabf.ui.view.utils

import androidx.appcompat.widget.AppCompatSpinner
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import com.tiramakan.simabf.ui.view.utils.AppCompatSpinnerExtensions.getAppCompatSpinnerValue
import com.tiramakan.simabf.ui.view.utils.AppCompatSpinnerExtensions.setAppCompatSpinnerInverseBindingListener
import com.tiramakan.simabf.ui.view.utils.AppCompatSpinnerExtensions.setAppCompatSpinnerValue

/**
 * Copyright (c) 2017 Fueled. All rights reserved.
 * @author chetansachdeva on 10/04/18
 */
 class InverseAppCompatSpinnerBindings {

    @BindingAdapter("selectedValue")
    fun AppCompatSpinner.setSelectedValue(selectedValue: Any?) {
        if (selectedValue != null) {
            setAppCompatSpinnerValue(selectedValue)
        }
    }

    @BindingAdapter("selectedValueAttrChanged")
    fun AppCompatSpinner.setInverseBindingListener(inverseBindingListener: InverseBindingListener?) {
        if (inverseBindingListener != null) {
            setAppCompatSpinnerInverseBindingListener(inverseBindingListener)
        }
    }

    companion object InverseSpinnerBindings {

        @JvmStatic
        @InverseBindingAdapter(attribute = "selectedValue", event = "selectedValueAttrChanged")
        fun AppCompatSpinner.getSelectedValue(): Any? {
            return getAppCompatSpinnerValue()
        }
    }
}