package com.tiramakan.simabf.ui.view.utils.Binding

import com.tiramakan.simabf.bootstrap.util.Objects

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
open class BindableInteger : BaseObservable() {
    open var mValue: Int = 0

    fun get(): Int {

        return mValue
    }

    fun set(value: Int) {

        if (!Objects.equals(this.mValue, value)) {
            this.mValue = value
            notifyChange()
        }
    }
}