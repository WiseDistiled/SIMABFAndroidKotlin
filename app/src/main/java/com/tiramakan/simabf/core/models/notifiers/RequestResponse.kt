package com.tiramakan.simabf.core.models.notifiers

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


/**
 * Created by tiramakan on 09/02/2016.
 */
@Parcelize
class RequestResponse (var message: String="" , var title: String, var result: Boolean?):Parcelable {
      val prettyMessage: String
      get() = message.replace("[", "").replace("]", "")
}
