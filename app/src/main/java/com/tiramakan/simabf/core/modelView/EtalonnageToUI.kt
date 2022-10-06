package com.tiramakan.simabf.core.modelView
import android.os.Parcelable
import com.tiramakan.simabf.core.models.realm.Etalonnage
import org.parceler.Parcel
import java.util.Date

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(Parcel.Serialization.FIELD)
class EtalonnageToUI(var uml: String = "",
                     var ul: String = "",
                     var valeur: String = "",
                     var marche: String = "",
                     var date: Date = Date(),
                     var id: Int? = null
):Parcelable  {
    override fun writeToParcel(dest: android.os.Parcel?, flags: Int) {
        dest?.writeString(this.uml)
        dest?.writeString(this.ul)
        dest?.writeString(this.valeur)
        dest?.writeString(this.marche)
        dest?.writeString(this.date.toString())
        this.id?.let { dest?.writeInt(it) }
    }

    val isValid: Boolean
        get() = !uml.equals("") && !ul.equals("") && !valeur.equals("")
                && !date.equals("")&& !marche.equals("")

    constructor(parcel: android.os.Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readSerializable() as java.util.Date,
            parcel.readInt()) {
    }

    internal constructor(etalonnage: Etalonnage):this() {
        this.uml = etalonnage.uml.toString()
        this.ul = etalonnage.ul.toString()
        this.valeur=etalonnage.valeur.toString()
        this.date=etalonnage.date
        this.id = etalonnage.id
    }


    override fun toString(): String {
        return   "uml : " + uml + System.getProperty("line.separator") +
                "ul : " + ul + System.getProperty("line.separator") +
                    "valeur : " + valeur + System.getProperty("line.separator") +
                    "id : " + id + System.getProperty("line.separator")

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<EtalonnageToUI> {
        override fun createFromParcel(parcel: android.os.Parcel): EtalonnageToUI {
            return EtalonnageToUI(parcel)
        }

        override fun newArray(size: Int): Array<EtalonnageToUI?> {
            return arrayOfNulls(size)
        }
    }


}
