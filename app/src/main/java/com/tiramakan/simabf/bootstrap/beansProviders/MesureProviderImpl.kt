package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Mesure

import java.util.ArrayList

import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by tiramakan on 17/01/2016.
 */
class MesureProviderImpl(internal var bootstrapService: BootstrapService, internal var realm: Realm) : MesureProvider {

    override val items: List<String>
        get() {

            val theMesures = ArrayList<String>()
            val mesures = realm.where(Mesure::class.java)
                    ?.findAll()
            mesures?.sort("nom")

            for (mesure in mesures!!) {
                theMesures.add(mesure.code.toString())
            }
            return theMesures
        }

    override fun getId(valueName: String): Mesure {
        return realm.where(Mesure::class.java)
                ?.equalTo("nom", valueName)
                ?.findFirst()!!
    }
}
