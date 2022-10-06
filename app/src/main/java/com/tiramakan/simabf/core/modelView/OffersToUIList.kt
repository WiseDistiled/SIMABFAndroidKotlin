package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.util.MyPreferences
import com.tiramakan.simabf.core.models.realm.Offre
import io.realm.Realm
import io.realm.RealmResults
import java.util.ArrayList

object OffersToUIList{
    fun getListOffersFromDBSended(realm: Realm): ArrayList<OfferToUI> {
        val theList = ArrayList<OfferToUI>()
        val offers = realm.where(Offre::class.java)
                .equalTo("status", "sended")
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

    fun getRealmOfferById(realm: Realm, id: Int): Offre? {
        return realm.where(Offre::class.java)
                .equalTo("id", id)
                .findFirst()

    }
    fun getOfferToUIById(realm: Realm, id: Int): OfferToUI? {
        return getRealmOfferById(realm,id)?.let { OfferToUI(it) }


    }

}