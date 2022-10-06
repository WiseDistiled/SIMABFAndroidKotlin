package com.tiramakan.simabf.core.models.savers

import com.squareup.otto.Bus
import com.tiramakan.simabf.core.modelView.PriceToUI
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.core.models.realm.Mesure
import com.tiramakan.simabf.core.models.realm.Prix
import com.tiramakan.simabf.core.models.realm.Produit

import java.util.ArrayList
import java.util.Date

import io.realm.Realm

/**
 * Created by tiramakan on 23/01/2016.
 */
class ISaverPriceImpl(protected var realm: Realm, protected var bus: Bus) : ISaverPrice {
    public val nextKey: Int
        get() {
            val maxNumber = realm.where(Prix::class.java).max("id")
            return if (maxNumber != null)
                maxNumber.toInt() + 1
            else
                1
        }

    override fun save(prices: ArrayList<PriceToUI>, datePrice: Date) {
        realm.executeTransaction {
            it.delete(Prix::class.java)
                for (priceToUI in prices) {
                    if (priceToUI.isValid) {

                        val marche = realm.where(Marche::class.java)
                                .equalTo("nom", priceToUI.marche)
                                .findFirst()
                        val produit = realm.where(Produit::class.java)
                                .equalTo("nom", priceToUI.produit)
                                .findFirst()
                        val mesure = realm.where(Mesure::class.java)
                                .equalTo("code", priceToUI.mesure)
                                .findFirst()


                        val prixHolder = realm.where(Prix::class.java)
                                .equalTo("id", priceToUI.id).findFirst()

                        prixHolder?.typePrix = priceToUI.typePrix
                        prixHolder?.date = datePrice
                        prixHolder?.mesure = mesure
                        prixHolder?.marche = marche
                        prixHolder?.produit = produit
                        prixHolder?.comment = priceToUI.comment
                        prixHolder?.note_photo = priceToUI.note_photo
                        prixHolder?.note_contenu = priceToUI.note_contenu
                        prixHolder?.note_longitude = priceToUI.note_longitude
                        prixHolder?.note_latitude = priceToUI.note_latitude
                        prixHolder?.status = "created"
                        var montant = 0.0
                        if (!priceToUI.montant.equals(""))
                            montant = priceToUI.montant.toDouble()

                        prixHolder?.montant = montant

                    }
                }

        }
    }

    override fun saveRow(priceToUI: PriceToUI) {
        realm.executeTransaction {
                if (priceToUI.isValid) {
                    val marche = realm.where(Marche::class.java)
                            .equalTo("nom", priceToUI.marche)
                            .findFirst()
                    val produit = realm.where(Produit::class.java)
                            .equalTo("nom", priceToUI.produit)
                            .findFirst()
                    val mesure= realm.where(Mesure::class.java)
                            .equalTo("code", priceToUI.mesure)
                            .findFirst()

                    val prix: Prix
                    if (priceToUI.id == null) {
                        prix = realm.createObject(Prix::class.java, nextKey)

                    } else {
                        prix = realm.where(Prix::class.java)
                                ?.equalTo("id", priceToUI.id)
                                ?.findFirst()!!
                    }

                    prix.typePrix = priceToUI.typePrix
                    prix.date = priceToUI.date
                    prix.mesure = mesure
                    prix.marche = marche
                    prix.produit = produit
                    prix.comment = priceToUI.comment
                    prix.note_photo = priceToUI.note_photo
                    prix.note_contenu = priceToUI.note_contenu
                    prix.note_longitude = priceToUI.note_longitude
                    prix.note_latitude = priceToUI.note_latitude
                    prix.status = "created"
                    var montant = 0.0
                    if (!priceToUI.montant.equals(""))
                        montant = priceToUI.montant.toDouble()

                    prix.montant = montant
                    priceToUI.id = prix.id
                }
            }

    }

}
