package com.tiramakan.simabf.core.modelView

import android.graphics.Bitmap
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

import org.parceler.Parcel
import org.parceler.ParcelConstructor

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(value = Parcel.Serialization.BEAN)
class PartnerForUI ( var commentaire:String = "",
                     var role:String = "",
                     var photo:Int = -1,
                     var id:Int =0) {
    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }

        return Bitmap.createScaledBitmap(image, width, height, true)
    }
    constructor(strphoto: Int, strcommentaire: String, strRole: String):this() {
        photo = strphoto
        commentaire = strcommentaire
        role = strRole

    }


}
