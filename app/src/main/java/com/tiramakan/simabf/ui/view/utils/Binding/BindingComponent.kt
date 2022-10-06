package com.tiramakan.simabf.ui.view.utils.Binding

import androidx.databinding.DataBindingComponent
import com.tiramakan.simabf.ui.view.utils.InverseAppCompatSpinnerBindings
import com.tiramakan.simabf.ui.view.utils.InverseSpinnerBindings

class BindingComponent : DataBindingComponent {
    override fun getInverseAppCompatSpinnerBindings(): InverseAppCompatSpinnerBindings {
        return InverseAppCompatSpinnerBindings()
    }

    override fun getSpinnerBindings(): SpinnerBindings {
        return SpinnerBindings()
    }

    override fun getInverseSpinnerBindings(): InverseSpinnerBindings {
        return InverseSpinnerBindings()
    }
}