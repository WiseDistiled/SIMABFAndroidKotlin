package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Qualite

import java.util.ArrayList

import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by tiramakan on 17/01/2016.
 */
class EtatApproProviderImpl(internal var bootstrapService: BootstrapService, internal var realm: Realm) : EtatApproProvider {

    override val items: List<String>
        get() {
            val itemList = ArrayList<String>()
            val etatsApprov = bootstrapService.etatsAppro
            for (etatApprov in etatsApprov) {
                itemList.add(etatApprov.nom.toString())

            }
            return itemList
        }

    override fun getId(valueName: String): Qualite {
        return realm.where(Qualite::class.java)
                ?.equalTo("nom", valueName)
                ?.findFirst()!!
    }
}
