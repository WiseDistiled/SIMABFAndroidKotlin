package com.tiramakan.simabf.core.modelView

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tiramakan on 18/02/2016.
 */

@org.parceler.Parcel(value = org.parceler.Parcel.Serialization.BEAN)
class StockRequestModel (
                          var produitCode: String? = "",
                          var depotCode: String? = ""):Parcelable {

     val request: String
        get() {
            val tokenSeparator = " "
            val messageSMS = "getstock$tokenSeparator#$depotCode#$produitCode"
            return messageSMS

        }

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString())
    {
    }


    fun stringify(): String {
        return ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(produitCode)
        parcel.writeString(depotCode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StockRequestModel> {
        override fun createFromParcel(parcel: Parcel): StockRequestModel {
            return StockRequestModel(parcel)
        }

        override fun newArray(size: Int): Array<StockRequestModel?> {
            return arrayOfNulls(size)
        }
    }


}
