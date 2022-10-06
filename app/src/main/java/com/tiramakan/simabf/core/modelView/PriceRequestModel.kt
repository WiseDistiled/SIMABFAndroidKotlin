package com.tiramakan.simabf.core.modelView

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import org.parceler.Parcel


/**
 * Created by tiramakan on 18/02/2016.
 */
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@Parcel(value = Parcel.Serialization.BEAN)
class PriceRequestModel (
                         var produitCode: String = "",
                         var mesureCode: String = "",
                         var marcheCode: String = "") : Parcelable {
    override fun writeToParcel(dest: android.os.Parcel?, p1: Int) {
        dest?.writeString(this.produitCode)
        dest?.writeString(this.mesureCode)
        dest?.writeString(this.marcheCode)
    }

     val request: String
        get() {
            val messageSMS: String
            val tokenSeparator = " "
            if ("" == marcheCode) {
                if ("" == mesureCode) {
                    messageSMS = "getprix$tokenSeparator#$produitCode"
                } else
                    messageSMS = "getprix$tokenSeparator#$produitCode#$mesureCode"
            } else {
                if ("" == mesureCode) {
                    messageSMS = "getprix$tokenSeparator#$produitCode#$marcheCode"
                } else
                    messageSMS = "getprix$tokenSeparator#$produitCode#$marcheCode#$mesureCode"
            }
            return messageSMS
        }

    constructor(parcel: android.os.Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString()
            ) {
    }


    enum class Type {
        read_price,
        write_price
    }


    fun stringify(): String {
        return ""
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PriceRequestModel> {
        override fun createFromParcel(parcel: android.os.Parcel): PriceRequestModel {
            return PriceRequestModel(parcel)
        }

        override fun newArray(size: Int): Array<PriceRequestModel?> {
            return arrayOfNulls(size)
        }
    }


}
