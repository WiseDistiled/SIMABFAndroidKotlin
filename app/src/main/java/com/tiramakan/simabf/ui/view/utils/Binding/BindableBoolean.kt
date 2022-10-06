package com.tiramakan.simabf.ui.view.utils.Binding

import android.os.Parcelable
import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
open class BindableBoolean() : BaseObservable(), Parcelable {
    var mValue: Boolean = false

    constructor(parcel: android.os.Parcel) : this() {
        mValue = parcel.readByte() != 0.toByte()
    }

    fun get(): Boolean {
        return mValue
    }

    fun set(value: Boolean) {
        if (mValue != value) {
            this.mValue = value
            notifyChange()
        }
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeByte(if (mValue) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BindableBoolean> {
        override fun createFromParcel(parcel: android.os.Parcel): BindableBoolean {
            return BindableBoolean(parcel)
        }

        override fun newArray(size: Int): Array<BindableBoolean?> {
            return arrayOfNulls(size)
        }
    }
}