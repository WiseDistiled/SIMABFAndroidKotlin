package com.tiramakan.simabf.core.modelView
import android.os.Parcelable
import com.tiramakan.simabf.bootstrap.beansProviders.*
import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.core.models.realm.Prix
import com.tiramakan.simabf.bootstrap.util.UIUtils
import org.parceler.Parcel
import java.util.Date

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(Parcel.Serialization.FIELD)
class PriceToUI(var marche: String = "",
                var produit: String = "",
                var comment: String = "",
                var montant: String = "",
                var mesure: String = "",
                var typePrix: String = "",
                var note_contenu: String = "",
                var note_longitude: String = "",
                var note_latitude: String = "",
                var note_photo: String = "",
                var date: Date = Date(),
                var checked:Boolean = false,
                var id: Int? = null
):Parcelable  {
    override fun writeToParcel(dest: android.os.Parcel?, flags: Int) {
        dest?.writeString(this.marche)
        dest?.writeString(this.produit)
        dest?.writeString(this.comment)
        dest?.writeString(this.montant)
        dest?.writeString(this.mesure)
        dest?.writeString(this.typePrix)
        dest?.writeString(this.note_contenu)
        dest?.writeString(this.note_longitude)
        dest?.writeString(this.note_latitude)
        dest?.writeString(this.note_photo)
        dest?.writeString(this.date.toString())
        dest?.writeString(this.checked.toString())
        this.id?.let { dest?.writeInt(it) }
    }

    @Transient
    public var myPreferences: MyPreferences? = null

    @Transient
    var marcheProvider: MarcheProvider? = null
    @Transient
    var produitProvider: ProduitProvider? = null

    @Transient
    var etatApproProvider: EtatApproProvider? = null

    @Transient
    var mesuresProvider: MesureProvider? = null
    fun marcheItems(): List<String>? {
        return marcheProvider?.items
    }
    fun etatApproItems(): List<String>? {
        return etatApproProvider?.items
    }
    fun produitsItems(): List<String>? {
        return produitProvider?.items
    }
    fun mesuresItems(): List<String>? {
        return mesuresProvider?.items
    }

    val prettyDate: String
        get() = UIUtils.prettyDateFormat.format(date)
    val prettyDateAndHour: String
        get() = UIUtils.prettyDateAndHourFormat.format(date)

    val isValid: Boolean
        get() = !montant.equals("") && !typePrix.equals("") && !produit.equals("")&& !marche.equals("")
                && !date.equals("")

    constructor(parcel: android.os.Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readSerializable() as java.util.Date,
            parcel.readByte() != 0.toByte(),
            parcel.readInt()) {
    }

    internal constructor(prix: Prix, myPreferences: MyPreferences):this() {
        this.marche = prix.marche?.nom.toString()
        this.produit = prix.produit?.nom.toString()
        this.comment = prix.comment.toString()
        this.mesure=prix.mesure?.code.toString()
        this.typePrix=prix.typePrix
        this.montant=prix.montant.toString()
        this.note_contenu=prix.note_contenu?:""
        this.note_longitude=prix.note_longitude.toString()?:""
        this.note_latitude=prix.note_latitude.toString()?:""
        this.note_photo=prix.note_photo.toString()?:""

        this.checked=false
        this.date=prix.date
        this.myPreferences = myPreferences
        this.id = prix.id
    }


    override fun toString(): String {
        return   "Produit : " + produit + System.getProperty("line.separator") +
                "Marché : " + marche + System.getProperty("line.separator") +
                "date : " + date.toString() + System.getProperty("line.separator") +
                    "Prix : " + montant + System.getProperty("line.separator") +
                    "Unité Mesure : " + mesure + System.getProperty("line.separator")+
                "typePrix : " + typePrix + System.getProperty("line.separator")+
                "commentaire : " + comment + System.getProperty("line.separator")+
        "note contenu : " + note_contenu + System.getProperty("line.separator")+
        "note longitude : " + note_longitude + System.getProperty("line.separator")+
        "note latitude : " + note_latitude + System.getProperty("line.separator")
    }

    override fun describeContents(): Int {
        return 0
    }
    fun getLongitudeText(): String {
        return note_longitude.toString()
    }
    fun getTitle(): String {
        return "Saisie prix de : ".plus(typePrix)
    }

    fun getLatitudeText(): String {
        return note_latitude.toString()
    }
    companion object CREATOR : Parcelable.Creator<PriceToUI> {
        override fun createFromParcel(parcel: android.os.Parcel): PriceToUI {
            return PriceToUI(parcel)
        }

        override fun newArray(size: Int): Array<PriceToUI?> {
            return arrayOfNulls(size)
        }
    }


}
