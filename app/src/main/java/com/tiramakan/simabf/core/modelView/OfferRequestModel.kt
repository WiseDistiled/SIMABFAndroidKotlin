package com.tiramakan.simabf.core.modelView

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import kotlinx.parcelize.Parceler


/**
 * Created by tiramakan on 18/02/2016.
 */
@Parcelize
class OfferRequestModel (
                          var produitCode: String = "",
                          var typeOffreCode: String = "Vente"):Parcelable  {

    val shortTypeOffre: String
        get() = if (typeOffreCode == "Achat")
            "A"
        else
            "V"
     val request: String
        get() {
            val tokenSeparator = " "
            val messageSMS = "getoffre$tokenSeparator#$shortTypeOffre#$produitCode"
            return messageSMS
        }


    fun stringify(): String {
        return ""
    }

    companion object : Parceler<OfferRequestModel> {
        override fun OfferRequestModel.write(parcel: android.os.Parcel, flags: Int) {

            parcel.writeString(produitCode)
            parcel.writeString(typeOffreCode)
        }

        override fun create(parcel: android.os.Parcel): OfferRequestModel = TODO()
    }


}
