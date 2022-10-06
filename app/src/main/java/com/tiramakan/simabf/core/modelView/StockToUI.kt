package com.tiramakan.simabf.core.modelView
import android.os.Parcelable
import com.tiramakan.simabf.bootstrap.beansProviders.MesureProvider
import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.bootstrap.util.UIUtils
import com.tiramakan.simabf.core.models.realm.Stock
import org.parceler.Parcel
import java.util.Date

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(Parcel.Serialization.FIELD)
class StockToUI(var depot: String = "",
                var produit: String = "",
                var balance: String = "",
                var mesure: String = "",
                var comment: String = "",
                var date: Date = Date(),
                var id: Int? = null
):Parcelable  {
    override fun writeToParcel(dest: android.os.Parcel?, flags: Int) {
        dest?.writeString(this.depot)
        dest?.writeString(this.produit)
        dest?.writeString(this.balance)
        dest?.writeString(this.mesure)
        dest?.writeString(this.comment)
        dest?.writeString(this.date.toString())
        this.id?.let { dest?.writeInt(it) }
    }

    @Transient
    public var myPreferences: MyPreferences? = null

    @Transient
    var mesuresProvider: MesureProvider? = null

    fun mesuresItems(): List<String>? {
        return mesuresProvider?.items
    }

    val prettyDate: String
        get() = UIUtils.prettyDateFormat.format(date)
    val prettyDateAndHour: String
        get() = UIUtils.prettyDateAndHourFormat.format(date)

    val isValid: Boolean
        get() = !balance.equals("") && !depot.equals("") && !produit.equals("")
                && !date.equals("")

    constructor(parcel: android.os.Parcel) : this(
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readString().toString(),
            parcel.readSerializable() as java.util.Date,
            parcel.readInt()) {
    }

    internal constructor(stock: Stock, myPreferences: MyPreferences):this() {
        this.depot = stock.depot?.nom.toString()
        this.produit = stock.produit?.nom.toString()
        this.mesure=stock.mesure?.code.toString()
        this.balance=stock.balance.toString()
        this.comment=stock.comment
        this.date=stock.date
        this.myPreferences = myPreferences
        this.id = stock.id
    }


    override fun toString(): String {
        return   "Produit : " + produit + System.getProperty("line.separator") +
                "Dépot : " + depot + System.getProperty("line.separator") +
                    "Stock : " + balance + System.getProperty("line.separator") +
                    "Unité Mesure : " + mesure + System.getProperty("line.separator")

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StockToUI> {
        override fun createFromParcel(parcel: android.os.Parcel): StockToUI {
            return StockToUI(parcel)
        }

        override fun newArray(size: Int): Array<StockToUI?> {
            return arrayOfNulls(size)
        }
    }


}
