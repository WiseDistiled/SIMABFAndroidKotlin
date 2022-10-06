package com.tiramakan.simabf.core.models.savers

import com.squareup.otto.Bus
import com.tiramakan.simabf.core.modelView.OfferToUI
import com.tiramakan.simabf.core.models.realm.*

import io.realm.Realm

/**
 * Created by tiramakan on 23/01/2016.
 */
class ISaverOfferImpl(protected var realm: Realm, protected var bus: Bus) : ISaverOffer {
    public val nextKey: Int
        get() {
            val maxNumber = realm.where(Offre::class.java).max("id")
            return if (maxNumber != null)
                maxNumber.toInt() + 1
            else
                1
        }

    override fun save(offerToUI: OfferToUI) {
        realm.executeTransaction{
              if (offerToUI.isValid) {

                val produit = realm.where(Produit::class.java)
                      .equalTo("nom", offerToUI.produit?.get().toString())
                      .findFirst()

                val mesure = realm.where(Mesure::class.java)
                      .equalTo("code", offerToUI.mesure)
                      .findFirst()
                val quality = realm.where(Qualite::class.java)
                      .equalTo("nom", offerToUI.qualite)
                      .findFirst()

                val region = realm.where(Region::class.java)
                          .equalTo("nom", offerToUI.region)
                          .findFirst()
                val offre: Offre

                  if (offerToUI.id == null) {
                      offre = realm.createObject(Offre::class.java, nextKey)

                  } else {
                      offre = realm.where(Offre::class.java)
                              ?.equalTo("id", offerToUI.id)
                              ?.findFirst()!!
                  }

                offre.status = "created"
                offre.mesure = mesure
                offre.produit = produit
                offre.qualite = quality
                offre.email = offerToUI.email?.get().toString()
                offre.nomAuteur = offerToUI.nomAuteur?.get().toString()
                offre.telephone = offerToUI.telephone?.get().toString()
                offre.date= offerToUI.date?.get()!!
                offre.expirationDate = offerToUI.expirationDate?.get()!!
                offre.conditions = offerToUI.conditions?.get().toString()
                offre.quantite = offerToUI.quantite?.get()?:0.0
                offre.offerType = offerToUI.offerType?.get().toString()
                offre.region = region
                offre.montant = offerToUI.montant?.get()?:0.0
                offre.prixUnitaire = offerToUI.prixUnitaire?.get()?:0.0
                offre.photo = offerToUI.photo
                offerToUI.id = offre.id
            }

        }

    }

}
