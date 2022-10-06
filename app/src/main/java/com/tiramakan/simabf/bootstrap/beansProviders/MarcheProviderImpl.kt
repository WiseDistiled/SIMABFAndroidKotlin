package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Marche

import java.util.ArrayList

import io.realm.Realm

/**
 * Created by tiramakan on 17/01/2016.
 */
class MarcheProviderImpl(internal var bootstrapService: BootstrapService, internal var realm: Realm) : MarcheProvider {

    override val items: List<String>
        get() {
            val itemList = ArrayList<String>()
            val marches = bootstrapService.marches
            for (marche in marches) {
                itemList.add(marche.nom.toString())

            }
            return itemList
        }
    override val itemsForWriting: List<String>
        get() {
            val itemList = ArrayList<String>()
            val marches = bootstrapService.marches
            val marchesActeurs = bootstrapService.marchesActeurs
            for (marcheActeur in marchesActeurs) {
                for (marche in marches) {
                    if (marcheActeur.marche.equals(marche.code))
                    itemList.add(marche.nom.toString())

                }
            }
            return itemList
        }

    override fun getId(valueName: String): Marche {
        return realm.where(Marche::class.java)
                ?.equalTo("nom", valueName)
                ?.findFirst()!!
    }
}
