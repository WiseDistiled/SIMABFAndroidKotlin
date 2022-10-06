package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Produit

import java.util.ArrayList

import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by tiramakan on 17/01/2016.
 */
class ProduitProviderImpl(internal var bootstrapService: BootstrapService, internal var realm: Realm) : ProduitProvider {

    override val items: List<String>
        get() {
            val itemList = ArrayList<String>()
            val produits = bootstrapService.produits
            for (produit in produits) {
                itemList.add(produit.nom.toString())

            }
            return itemList
        }

    override fun getId(valueName: String): Produit {
        return realm.where(Produit::class.java)
                ?.equalTo("nom", valueName)
                ?.findFirst()!!
    }
}
