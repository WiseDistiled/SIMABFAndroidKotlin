package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Etalonnage
import io.realm.Realm
import io.realm.RealmResults
import java.util.ArrayList

object EtalonnageForUIList{

    fun getRealmEtalonnagesNotSended(realm: Realm): RealmResults<Etalonnage> {

        return realm.where(Etalonnage::class.java)
                .equalTo("status", "created")
                .findAll()

    }

    fun getListFromDBNotSended(realm: Realm): ArrayList<EtalonnageToUI> {
        val theList = ArrayList<EtalonnageToUI>()
        val etalonnages = realm.where(Etalonnage::class.java)
                .equalTo("status", "created")
                .findAll()
        if (etalonnages.size > 0) {
            for (etalonnage in etalonnages) {
                theList.add(EtalonnageToUI(etalonnage))
            }
        }
        return theList

    }

    fun getRealmEtalonnageById(realm: Realm, id: Int): Etalonnage? {
        return realm.where(Etalonnage::class.java)
                .equalTo("id", id)
                .findFirst()

    }
}