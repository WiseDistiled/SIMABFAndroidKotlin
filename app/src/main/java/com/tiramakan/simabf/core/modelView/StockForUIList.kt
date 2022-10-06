package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.core.models.realm.Stock
import com.tiramakan.simabf.core.models.realm.Depot
import io.realm.Realm
import io.realm.RealmResults
import java.util.ArrayList

object StockForUIList{
    fun getListStockForUI(realm: Realm, depot: Depot): List<StockForUI> {
        val theList = ArrayList<StockForUI>()
        val produits = realm.where(Produit::class.java)
                .findAll()
        produits.sort("produit")


        for (produit in produits) {
            val stockForUI = StockForUI(depot.nom.toString(), produit?.nom.toString(), produit?.mesure?.code.toString())
            theList.add(stockForUI)
        }
        return theList

    }

    fun getRealmStocksNotSended(realm: Realm): RealmResults<Stock> {

        return realm.where(Stock::class.java)
                .equalTo("status", "created")
                .findAll()

    }

    fun getListFromDBNotSended(realm: Realm): ArrayList<StockForUI> {
        val theList = ArrayList<StockForUI>()
        val stocks = realm.where(Stock::class.java)
                .equalTo("status", "created")
                .findAll()
        if (stocks.size > 0) {
            for (stock in stocks) {
                theList.add(StockForUI(stock))
            }
        }
        return theList

    }

    fun getRealmStockById(realm: Realm, id: Int): Stock? {
        return realm.where(Stock::class.java)
                .equalTo("id", id)
                .findFirst()

    }
}