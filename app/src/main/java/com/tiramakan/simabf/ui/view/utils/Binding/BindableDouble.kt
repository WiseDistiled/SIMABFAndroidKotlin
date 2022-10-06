package com.tiramakan.simabf.ui.view.utils.Binding

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.parceler.Parcel

//@Parcel(Parcel.Serialization.BEAN)
@Parcelize
open class BindableDouble  : BaseObservable(), Parcelable {
    var mValue: Double = 0.0

    fun get(): Double {

        return mValue
    }

    fun set(value: Double) {

        if (!com.tiramakan.simabf.bootstrap.util.Objects.equals(this.mValue, value)) {
            this.mValue = value
            notifyChange()
        }
    }
}