package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.core.models.realm.Prix
import io.realm.Realm
import io.realm.RealmResults
import java.util.ArrayList

object PriceToUIList{
    fun getRealmPricesNotSended(realm: Realm): RealmResults<Prix> {
        return realm.where(Prix::class.java)
                .equalTo("status", "created")
                .findAll()

    }

    fun getRealmPriceById(realm: Realm, id: Int): Prix? {
        return realm.where(Prix::class.java)
                .equalTo("id", id)
                .findFirst()

    }
    fun getPriceToUIById(realm: Realm, id: Int, myPreferences: MyPreferences): PriceToUI? {
        return getRealmPriceById(realm,id)?.let { PriceToUI(it,myPreferences) }


    }
    fun getListFromDBNotSended(realm: Realm, myPreferences: MyPreferences): ArrayList<PriceToUI> {
        val theList = ArrayList<PriceToUI>()
        val prices = realm.where(Prix::class.java)
                .equalTo("status", "created")
                .findAll()
        if (prices.size > 0) {
            for (price in prices) {
                val priceForUI = PriceToUI(price, myPreferences)
                theList.add(priceForUI)
            }
        }
        return theList

    }
}