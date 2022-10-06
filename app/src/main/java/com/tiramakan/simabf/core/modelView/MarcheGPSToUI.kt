package com.tiramakan.simabf.core.modelView

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


import java.util.Date

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcelize
class MarcheGPSToUI (var date:Date = Date(),
                     var longitude: Double =0.0,
                     var latitude : Double =0.0, var marche: String=""): Parcelable {


    fun validate(): Boolean {
        return latitude != 0.0 && longitude != 0.0 && marche != ""
    }


}
