package com.tiramakan.simabf.ui.view.utils.Binding

import com.tiramakan.simabf.bootstrap.util.Objects

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
open class BindableLong : BaseObservable() {
    open var mValue: Long = 0

    fun get(): Long {

        return mValue
    }

    fun set(value: Long) {

        if (!Objects.equals(this.mValue, value)) {
            this.mValue = value
            notifyChange()
        }
    }
}