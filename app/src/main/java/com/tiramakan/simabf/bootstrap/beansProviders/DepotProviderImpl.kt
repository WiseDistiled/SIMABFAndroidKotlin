package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Depot

import java.util.ArrayList

import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by tiramakan on 17/01/2016.
 */
class DepotProviderImpl(internal var bootstrapService: BootstrapService, internal var realm: Realm) : DepotProvider {

    override val items: List<String>
        get() {
            val itemList = ArrayList<String>()
            val depots = bootstrapService.depots
            for (depot in depots) {
                itemList.add(depot.nom.toString())

            }
            return itemList
        }

    override fun getId(valueName: String): Depot {

        return realm.where(Depot::class.java)
                ?.equalTo("nom", valueName)
                ?.findFirst()!!
    }
}
