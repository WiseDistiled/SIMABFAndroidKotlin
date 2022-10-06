package com.tiramakan.simabf.ui.view.utils.Binding

import android.os.Parcelable
import com.tiramakan.simabf.bootstrap.util.Objects
import com.tiramakan.simabf.bootstrap.util.UIUtils

import org.parceler.Parcel

import java.text.DateFormat
import java.text.ParseException
import java.util.Date

@Parcel(Parcel.Serialization.BEAN)
open class BindableDate() : BaseObservable(), Parcelable {
    open var value: Date = Date()

    constructor(parcel: android.os.Parcel) : this() {

    }

    fun get(): Date {
        return value
    }

    fun set(value: String) {
        val format = UIUtils.dateFormat
        var dateSaisie = Date()
        try {
            dateSaisie = format.parse(value)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        if (!Objects.equals(this.value, dateSaisie)) {
            this.value = dateSaisie
            notifyChange()
        }
    }

    fun set(value: Date) {
        if (!Objects.equals(this.value, value)) {
            this.value = value
            notifyChange()
        }
    }

    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BindableDate> {
        override fun createFromParcel(parcel: android.os.Parcel): BindableDate {
            return BindableDate(parcel)
        }

        override fun newArray(size: Int): Array<BindableDate?> {
            return arrayOfNulls(size)
        }
    }


}