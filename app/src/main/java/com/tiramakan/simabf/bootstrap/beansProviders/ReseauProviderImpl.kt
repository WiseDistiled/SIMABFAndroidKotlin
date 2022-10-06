package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Depot
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.core.models.realm.Reseau

import java.util.ArrayList

import io.realm.Realm

/**
 * Created by tiramakan on 17/01/2016.
 */
class ReseauProviderImpl(internal var bootstrapService: BootstrapService, internal var realm: Realm) : ReseauProvider {

    override val items: List<String>
        get() {
            val itemList = ArrayList<String>()
            val reseaux = bootstrapService.reseaux
            for (reseau in reseaux) {
                itemList.add(reseau.code.toString())

            }
            return itemList
        }

    override fun getId(valueName: String): Reseau {
        return realm.where(Reseau::class.java)
                ?.equalTo("nom", valueName)
                ?.findFirst()!!
    }
}
