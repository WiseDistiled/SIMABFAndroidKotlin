package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Stock
import com.tiramakan.simabf.bootstrap.util.UIUtils
import java.util.Date
import com.tiramakan.simabf.ui.view.utils.Binding.*
import org.parceler.Parcel
import org.parceler.ParcelConstructor

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(value = Parcel.Serialization.BEAN)
class StockForUI ( var mesure:BindableString = BindableString(),
                   var produit:BindableString = BindableString(),
                   var date:BindableDate = BindableDate(),
                   var balance:BindableDouble =BindableDouble(),
                   var depot:BindableString = BindableString(),
                   var comment:BindableString = BindableString(),
                   var id: Int? = null) {

   
    val isValid: Boolean
        get() = balance.get() > 0.0
    val prettyDate: String
        get() = UIUtils.prettyDateFormat.format(date)
    val fullBalance: String
        get() {
            var pricestr: String

            if (balance.get() > 0.0)
                pricestr = UIUtils.formatPrice(balance.get())
            else
                pricestr = "-"
            if (pricestr != "-")
                pricestr = pricestr + " / " + mesure.get()


            return pricestr

        }
    val prettyDateAndHour: String
        get() = UIUtils.prettyDateAndHourFormat.format(date.get())

   
    internal constructor(mDepot: String, mProduit: String, codeMesure: String):this() {
        this.depot.set(mDepot)
        this.produit.set(mProduit)
        this.mesure.set(codeMesure)
        this.balance.set(0.0)
        this.comment.set("")
    }
    constructor(stock: Stock):this() {
        this.depot.set(stock.depot?.nom.toString())
        this.produit.set(stock.produit?.nom.toString())
        this.mesure.set(stock.mesure?.code.toString())
        this.balance.set(stock.balance)
        this.date.set(stock.date)
        this.comment.set("")
        this.id = stock.id
    }

    internal constructor(mDepot: String, mProduit: String, mesure: String, date: Date, balance: Double,  depot: String, comment: String):this() {
        this.depot.set(mDepot)
        this.produit.set(mProduit)
        this.mesure.set(mesure)
        this.date.set(date)
        this.balance.set(balance)
        this.depot.set(depot)
        this.comment.set(comment)
    }


}
