package com.tiramakan.simabf.core.modelView

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.tiramakan.simabf.bootstrap.beansProviders.MesureProvider
import com.tiramakan.simabf.bootstrap.beansProviders.QualiteProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RegionProvider

import com.tiramakan.simabf.core.models.realm.Mesure
import com.tiramakan.simabf.core.models.realm.Offre
import com.tiramakan.simabf.bootstrap.util.UIUtils
import io.realm.Realm
import io.realm.RealmResults
import com.tiramakan.simabf.ui.view.utils.Binding.*
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parceler
import kotlinx.parcelize.Parcelize
import org.parceler.Parcel

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(value = Parcel.Serialization.FIELD)
class OfferToUI(var offerType: BindableString? = BindableString(),
                var produit: BindableString? = BindableString(),
                var nomAuteur: BindableString? = BindableString(),
                var quantite: BindableDouble? = BindableDouble(),
                var prixUnitaire: BindableDouble? = BindableDouble(),
                var montant: BindableDouble? = BindableDouble(),
                var mesure: String = "",
                var conditions: BindableString? = BindableString(),
                internal var checked: Boolean = false,
                var telephone: BindableString? = BindableString(),
                var email: BindableString? = BindableString(),
                var date: BindableDate? = BindableDate(),
                var expirationDate: BindableDate? = BindableDate(),
                var qualite: String = "",
                var region: String = "",
                var longitude: String = "",
                var latitude: String = "",
                var photo: String = "",
                var lieuxStockage: BindableString? = BindableString(),
                var lieuxLivraison: BindableString? = BindableString(),
                var id: Int? = null


) : Parcelable {
    @IgnoredOnParcel
    @Transient
    var qualityProvider: QualiteProvider? = null
    @IgnoredOnParcel
    @Transient
    var mesuresProvider: MesureProvider? = null
    @IgnoredOnParcel
    @Transient
    var regionsProvider: RegionProvider? = null
    val prixUnitaireTxt: String
        get() {
            var pricestr: String

            if (prixUnitaire?.get()!! > 0)
                pricestr = UIUtils.formatPrice(prixUnitaire?.get())
            else
                pricestr = "-"
            if (pricestr != "-")
                pricestr = pricestr + " / " + mesure

            return pricestr

        }
    val prettyDate: String

        get() = UIUtils.prettyDateFormat.format(date?.get()!!)

    val prixGlobal: Double?
        get() = prixUnitaire?.get()?.let { quantite?.get()?.times(it) }


    val conditionOffre: String
        get() {
            val commentstr: String

            if (conditions?.get() != "")
                commentstr = conditions?.get().toString()
            else
                commentstr = "-"
            return commentstr

        }
    val isValid: Boolean
        get() = quantite?.get()!! > 0.0 && telephone?.get() != "" && produit?.get() != ""

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: android.os.Parcel) : this(
            parcel.readParcelable(BindableString::class.java.classLoader),
            parcel.readParcelable(BindableString::class.java.classLoader),
            parcel.readParcelable(BindableString::class.java.classLoader),
            parcel.readParcelable(BindableDouble::class.java.classLoader),
            parcel.readParcelable(BindableDouble::class.java.classLoader),
            parcel.readParcelable(BindableDouble::class.java.classLoader),
            parcel.readString().toString(),
            parcel.readParcelable(BindableString::class.java.classLoader),
            parcel.readBoolean(),
            parcel.readParcelable(BindableString::class.java.classLoader),
            parcel.readParcelable(BindableString::class.java.classLoader),
            parcel.readParcelable(BindableDate::class.java.classLoader),
            parcel.readParcelable(BindableDate::class.java.classLoader),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readParcelable(BindableString::class.java.classLoader),
            parcel.readParcelable(BindableString::class.java.classLoader),
            parcel.readValue(Int::class.java.classLoader) as? Int) {
    }

    constructor(offerType: String, mProduit: String, defaultMesure: Mesure):this() {
        this.offerType?.set(offerType)
        this.produit?.set(mProduit)
        this.mesure=defaultMesure.code.toString()
        this.conditions?.set("")
        this.telephone?.set("")
        this.checked=false
        this.prixUnitaire?.set(0.0)
        this.montant?.set(0.0)
        this.quantite?.set(0.0)
        this.qualite=""
        this.longitude=""
        this.latitude=""
        this.region=""
        this.photo=""
        this.lieuxStockage?.set("")
        this.lieuxLivraison?.set("")

    }

    constructor(offre: Offre):this() {
        if (offre.produit != null) {
            this.offerType?.set(offre.offerType.toString())
            this.telephone?.set(offre.telephone.toString())
            this.produit?.set(offre.produit?.code.toString())
            if (offre.mesure != null) {
                this.mesure=offre.mesure?.code.toString()
            }
            this.prixUnitaire?.set(offre.prixUnitaire.toDouble())
            this.montant?.set(offre.montant.toDouble())
            this.conditions?.set(offre.conditions.toString())
            this.checked=false
            this.quantite?.set(offre.quantite)
            this.nomAuteur?.set(offre.nomAuteur)
            this.email?.set(offre.email)
            this.date?.set(offre.date)
            this.expirationDate?.set(offre.expirationDate)

            if (offre.qualite != null)
                this.qualite=offre.qualite?.nom.toString()
            this.id = offre.id
            this.longitude = offre.longitude
            this.latitude = offre.latitude
            this.photo = offre.photo
            this.region= offre.region?.nom.toString()
            this.lieuxStockage?.set(offre.lieuxStockage)
            this.lieuxLivraison?.set(offre.lieuxLivraison)
        }

    }

    fun getPrixTotal(devise: String): String {
        var pricestr: String

        if (montant?.mValue!! > 0)
            pricestr = UIUtils.formatPrice(montant!!.get())
        else
            pricestr = "-"
        if (pricestr != "-")
            pricestr = "$pricestr  $devise"


        return pricestr

    }

    fun validate(): Boolean {
      return produit?.get() != "" && mesure != "" && quantite?.get()!! > 0
    }
    fun qualityItems(): List<String>? {
        return qualityProvider?.items
    }
    fun mesuresItems(): List<String>? {
        return mesuresProvider?.items
    }
    fun regionsItems(): List<String>? {
        return regionsProvider?.items
    }
    override fun toString(): String {
        return "Produit : " + produit

    }

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(parcel: android.os.Parcel?, flags: Int) {
            parcel?.writeParcelable(offerType, flags)
            parcel?.writeParcelable(produit, flags)
            parcel?.writeParcelable(nomAuteur, flags)
            parcel?.writeParcelable(prixUnitaire, flags)
            parcel?.writeParcelable(montant, flags)
            parcel?.writeParcelable(quantite, flags)
            parcel?.writeString(mesure)
            parcel?.writeParcelable(conditions, flags)
            parcel?.writeByte(if (checked) 1 else 0)
            parcel?.writeParcelable(telephone, flags)
            parcel?.writeParcelable(email, flags)
            parcel?.writeParcelable(date, flags)
            parcel?.writeParcelable(expirationDate, flags)
            parcel?.writeString(qualite)
            parcel?.writeString(region)
            parcel?.writeString(longitude)
            parcel?.writeString(latitude)
            parcel?.writeString(photo)
            parcel?.writeParcelable(lieuxStockage, flags)
            parcel?.writeParcelable(lieuxLivraison, flags)
            parcel?.writeValue(id)
    }

    fun getListFromDBNotSended(realm: Realm): ArrayList<OfferToUI> {
        val theList = ArrayList<OfferToUI>()
        val offers = realm.where(Offre::class.java)
                .equalTo("status", "created")
                .findAll()
        if (offers.size > 0) {
            for (offer in offers) {
                val offerToUI = OfferToUI(offer)
                if (offer.produit != null)
                    theList.add(offerToUI)
            }
        }
        return theList

    }

    fun getRealmOffersNotSended(realm: Realm): RealmResults<Offre> {
        return realm.where(Offre::class.java)
                .equalTo("status", "created")
                .findAll()

    }
//
//    companion object : Parceler<OfferToUI> {
//
//        override fun OfferToUI.write(parcel: android.os.Parcel, flags: Int) {
//            parcel.writeParcelable(offerType, flags)
//            parcel.writeParcelable(produit, flags)
//            parcel.writeParcelable(nomAuteur, flags)
//            parcel.writeParcelable(prixUnitaire, flags)
//            parcel.writeParcelable(montant, flags)
//            parcel.writeParcelable(quantite, flags)
//            parcel.writeString(mesure)
//            parcel.writeParcelable(conditions, flags)
//            parcel.writeByte(if (checked) 1 else 0)
//            parcel.writeParcelable(telephone, flags)
//            parcel.writeParcelable(email, flags)
//            parcel.writeParcelable(date, flags)
//            parcel.writeParcelable(expirationDate, flags)
//            parcel.writeString(qualite)
//            parcel.writeString(region)
//            parcel.writeString(longitude)
//            parcel.writeString(latitude)
//            parcel.writeString(photo)
//            parcel.writeParcelable(lieuxStockage, flags)
//            parcel.writeParcelable(lieuxLivraison, flags)
//            parcel.writeValue(id)
//        }
//
//        override fun create(parcel: android.os.Parcel): OfferToUI {
//            return if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
//                OfferToUI(parcel)
//            } else {
//                TODO("VERSION.SDK_INT < Q")
//            }
//        }
//    }
//
//    override fun writeToParcel(parcel: android.os.Parcel, flags: Int) {
//        parcel.writeParcelable(offerType, flags)
//        parcel.writeParcelable(produit, flags)
//        parcel.writeParcelable(nomAuteur, flags)
//        parcel.writeParcelable(quantite, flags)
//        parcel.writeParcelable(prixUnitaire, flags)
//        parcel.writeParcelable(montant, flags)
//        parcel.writeString(mesure)
//        parcel.writeParcelable(conditions, flags)
//        parcel.writeByte(if (checked) 1 else 0)
//        parcel.writeParcelable(telephone, flags)
//        parcel.writeParcelable(email, flags)
//        parcel.writeParcelable(date, flags)
//        parcel.writeParcelable(expirationDate, flags)
//        parcel.writeString(qualite)
//        parcel.writeString(region)
//        parcel.writeString(longitude)
//        parcel.writeString(latitude)
//        parcel.writeString(photo)
//        parcel.writeParcelable(lieuxStockage, flags)
//        parcel.writeParcelable(lieuxLivraison, flags)
//        parcel.writeValue(id)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }

    companion object CREATOR : Parcelable.Creator<OfferToUI> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: android.os.Parcel): OfferToUI {
            return OfferToUI(parcel)
        }

        override fun newArray(size: Int): Array<OfferToUI?> {
            return arrayOfNulls(size)
        }
    }


}
