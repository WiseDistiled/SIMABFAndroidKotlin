package com.tiramakan.simabf.core.modelView

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by tiramakan on 18/02/2016.
 */
@Parcelize
class GPSRequestModel ( var depotCode: String = "",
                        var depot: String = "", var request: String = "") : Parcelable {


    fun stringify(): String? {
        return depot
    }


    override fun describeContents(): Int {
        return 0
    }

}
