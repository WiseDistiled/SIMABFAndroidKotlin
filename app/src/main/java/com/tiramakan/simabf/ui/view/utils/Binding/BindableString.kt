package com.tiramakan.simabf.ui.view.utils.Binding

import android.os.Parcelable
import com.tiramakan.simabf.bootstrap.util.Objects

import org.parceler.Parcel

@Parcel(Parcel.Serialization.BEAN)
open class BindableString() : BaseObservable(), Parcelable {
    var value: String = ""

    constructor(parcel: android.os.Parcel) : this() {
        value = parcel.readString().toString()
    }


    fun get(): String {
        return this.value
    }

    fun set(value: String) {
        if (!Objects.equals(this.value, value)) {
            this.value = value
            notifyChange()
        }
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
        parcel.writeString(value)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BindableString> {
        override fun createFromParcel(parcel: android.os.Parcel): BindableString {
            return BindableString(parcel)
        }

        override fun newArray(size: Int): Array<BindableString?> {
            return arrayOfNulls(size)
        }
    }


}