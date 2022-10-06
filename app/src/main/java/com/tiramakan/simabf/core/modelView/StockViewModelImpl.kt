package com.tiramakan.simabf.core.modelView

import android.app.Activity
import android.widget.ArrayAdapter

import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.beansProviders.MesureProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RealmServiceProvider
import com.tiramakan.simabf.bootstrap.beansProviders.DepotProvider
import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.core.models.realm.Mesure
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.core.models.realm.Stock
import com.tiramakan.simabf.core.models.realm.Depot

import java.util.ArrayList
import java.util.Collections
import java.util.HashSet

/**
 * Created by tiramakan on 14/02/2016.
 */
class StockViewModelImpl(internal var realmServiceProvider: RealmServiceProvider, internal var myPreferences: MyPreferences, internal var bus: Bus, internal var mesureProvider: MesureProvider, internal var depotProvider: DepotProvider) : StockViewModel() {
     override var listStocks: ArrayList<StockForUI>? = null
    internal var allStocks: ArrayList<StockForUI>
    internal var mesures: List<String>? = null
    internal var setOfStocks: MutableSet<StockForUI> = HashSet()
    public val nextKey: Int
        get() {
            val maxNumber = realmServiceProvider.realm.where(Stock::class.java)?.max("id")
            return if (maxNumber != null)
                maxNumber.toInt() + 1
            else
                1
        }
    override val isNotEmpty: Boolean?
        get() {
            if (listStocks == null)
                return false
            var totalBalance: Double? = 0.0
            if (listStocks!!.size > 0) {
                for (stockForUI in listStocks!!) {
                    totalBalance = stockForUI.balance.get().let { totalBalance?.plus(it) }
                }
            }
            return totalBalance!! > 0.0
        }

    init {
        this.mesures = mesureProvider.items
        listStocks = ArrayList()
        allStocks = ArrayList()
        generateStocksList()

    }

    override fun getDepotAdapter(activity: Activity): ArrayAdapter<String>? {
        return getStringAdapter(activity, depotProvider.items)
    }

    override fun save(): Boolean {
        val realm = realmServiceProvider.realm
        realm.executeTransaction{
        realm.delete(Stock::class.java)
      
            for (stockForUI in listStocks!!) {
                if (stockForUI.isValid) {

                    val depot = realm.where(Depot::class.java)
                          ?.equalTo("nom", stockForUI.depot.get())
                          ?.findFirst()
                    val produit = realm.where(Produit::class.java)
                          ?.equalTo("nom", stockForUI.produit.get())
                          ?.findFirst()
                    val mesure = realm.where(Mesure::class.java)
                          ?.equalTo("code", stockForUI.mesure.get())
                          ?.findFirst()

                    val stock: Stock
                    if (stockForUI.id == null) {
                        stock = realm.createObject(Stock::class.java, nextKey)!!
                    } else {
                        stock = realm.where(Stock::class.java)
                                ?.equalTo("id", stockForUI.id)
                                ?.findFirst()!!
                    }

                    stock.depot = depot
                    stock.produit = produit
                    stock.mesure = mesure
                    stock.status = "created"
                    stock.date = stockForUI.date.get()
                    stock.balance = stockForUI.balance.get()
                    stock.comment = stockForUI.comment.get()

                }
            }
        }
            return true
       

    }

    public fun generateStocksList() {
        val realm = realmServiceProvider.realm
        setOfStocks.clear()
        allStocks.clear()
        val produits = realm.where(Produit::class.java)
                ?.findAll()
        val depots = realm.where(Depot::class.java)
                ?.findAll()
        produits?.sort("produit")
        for (depot in depots!!) {
            for (produit in produits!!) {
                val defaultMesure = produit.mesure
                val stockForUI: StockForUI
                if (defaultMesure != null)
                    stockForUI = StockForUI(depot.nom.toString(), produit.nom.toString(), defaultMesure.code.toString())
                else
                    stockForUI = StockForUI(depot.nom.toString(), produit.nom.toString(), "Kg")
                setOfStocks.add(stockForUI)
            }
        }

        for (stockForUI in setOfStocks) {
            allStocks.add(stockForUI)
        }


    }

    override fun updateStocks() {
        if (depot != "") {
            listStocks!!.clear()
            for (stockForUI in allStocks) {
                if (stockForUI.depot.get() == depot) {
                    listStocks!!.add(stockForUI)
                    //   Log.d("INFO", "updateStocks: "+"depot "+stockForUI.depot+" Produit :"+stockForUI.produit);
                }
            }
            Collections.sort(listStocks) { s1, s2 ->
                s1.produit.get().compareTo(s2.produit.get(), ignoreCase = true)
            }


        }
    }


    override fun getListStockClone(): ArrayList<StockForUI>? {
        return ViewModelBase.copyList(listStocks!!)
    }
    override fun getMesures(): List<String>? {
        return mesureProvider.items
    }
}
