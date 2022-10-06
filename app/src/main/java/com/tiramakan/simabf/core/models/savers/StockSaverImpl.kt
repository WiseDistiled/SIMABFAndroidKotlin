package com.tiramakan.simabf.core.models.savers

import com.squareup.otto.Bus
import com.tiramakan.simabf.core.modelView.StockForUI
import com.tiramakan.simabf.core.modelView.StockToUI
import com.tiramakan.simabf.core.models.realm.Mesure
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.core.models.realm.Stock
import com.tiramakan.simabf.core.models.realm.Depot

import java.util.Date

import io.realm.Realm

/**
 * Created by tiramakan on 23/01/2016.
 */
class StockSaverImpl(protected var realm: Realm, protected var bus: Bus) : ISaverStock {
    public val nextKey: Int
        get() {
            val maxNumber = realm.where(Stock::class.java).max("id")
            return if (maxNumber != null)
                maxNumber.toInt() + 1
            else
                1
        }

    override fun save(stocks: List<StockForUI>, date: Date) {
        realm.beginTransaction()
        realm.delete(Stock::class.java)
        try {
            for (stockForUI in stocks) {
                if (stockForUI.isValid) {

                    val depot = realm.where(Depot::class.java)
                          .equalTo("nom", stockForUI.depot.get())
                          .findFirst()
                    val produit = realm.where(Produit::class.java)
                          .equalTo("nom", stockForUI.produit.get())
                          .findFirst()
                    val mesure = realm.where(Mesure::class.java)
                          .equalTo("code", stockForUI.mesure.get())
                          .findFirst()

                    val stock: Stock
                    if (stockForUI.id == null) {
                        stock = realm.createObject(Stock::class.java, nextKey)

                    } else {
                        stock = realm.where(Stock::class.java)
                              .equalTo("id", stockForUI.id)
                              .findFirst()!!
                    }
                    stock.date = date
                    stock.mesure = mesure
                    stock.balance = stockForUI.balance.get()
                    stock.produit = produit
                    stock.status = "created"
                    stock.depot = depot

                }
            }

            realm.commitTransaction()
        } catch (e: Exception) {
            realm.cancelTransaction()

        }

    }
    override fun saveRow(stockToUI: StockToUI) {
        realm.executeTransaction {
            if (stockToUI.isValid) {
                val depot = realm.where(Depot::class.java)
                        .equalTo("nom", stockToUI.depot)
                        .findFirst()
                val produit = realm.where(Produit::class.java)
                        .equalTo("nom", stockToUI.produit)
                        .findFirst()
                val mesure= realm.where(Mesure::class.java)
                        .equalTo("code", stockToUI.mesure)
                        .findFirst()
                val stock: Stock
                if (stockToUI.id == null) {
                    stock = realm.createObject(Stock::class.java, nextKey)

                } else {
                    stock = realm.where(Stock::class.java)
                            ?.equalTo("id", stockToUI.id)
                            ?.findFirst()!!
                }

                stock.date = stockToUI.date
                stock.comment = stockToUI.comment
                stock.mesure = mesure
                stock.depot = depot
                stock.produit = produit
                stock.status = "created"
                var balance = 0.0
                if (!stockToUI.balance.equals(""))
                    balance = stockToUI.balance.toDouble()

                stock.balance = balance
                stockToUI.id = stock.id
            }
        }

    }

}
