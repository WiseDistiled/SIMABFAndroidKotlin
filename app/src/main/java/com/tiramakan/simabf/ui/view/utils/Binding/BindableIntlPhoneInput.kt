package com.tiramakan.simabf.ui.view.utils.Binding

import com.tiramakan.simabf.bootstrap.util.Objects

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
open class BindableIntlPhoneInput : BaseObservable() {
    open   var value: String = ""

    fun get(): String {
        return value
    }

    fun set(value: String) {
        if (!Objects.equals(this.value, value)) {
            this.value = value
            notifyChange()
        }
    }
}