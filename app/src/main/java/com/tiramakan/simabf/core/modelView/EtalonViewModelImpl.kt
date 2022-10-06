package com.tiramakan.simabf.core.modelView

import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.beansProviders.MesureProvider
import com.tiramakan.simabf.bootstrap.beansProviders.RealmServiceProvider
import com.tiramakan.simabf.core.models.realm.*
import java.util.*

/**
 * Created by tiramakan on 14/02/2016.
 */
class EtalonViewModelImpl(internal var realmServiceProvider: RealmServiceProvider, internal var bus: Bus) : EtalonViewModel() {


    init {
        listEtalonnages = ArrayList()
     //   updateEtalonnages()

    }
    override val isNotEmpty: Boolean?
        get() = true


    override fun save(marcheChoisi: String): Boolean {
        val realm = realmServiceProvider.realm
        realm.executeTransaction {
            for (etalonnagetoUI in listEtalonnages) {
                if (etalonnagetoUI.isValid) {
                  val etalonnage: Etalonnage
                    if (etalonnagetoUI.id !== null) {
                        etalonnage = realm.where(Etalonnage::class.java)
                                ?.equalTo("id", etalonnagetoUI.id)
                                ?.findFirst()!!

                        etalonnage.valeur = etalonnagetoUI.valeur.toDouble()
                        etalonnage.marche = marcheChoisi
                    }
                }
            }
        }
            return true
       

    }

     override fun updateEtalonnages(marcheChoisi: String) {
        val realm = realmServiceProvider.realm
         listEtalonnages.clear()
        val etalonnages = realm.where(Etalonnage::class.java)
                ?.equalTo("marche", marcheChoisi)
                ?.findAll()
         etalonnages?.sort("uml")
         if (etalonnages != null) {
             for (etalonnage in etalonnages) {
                     listEtalonnages.add(EtalonnageToUI(etalonnage.uml,etalonnage.ul,
                             etalonnage.valeur.toString(),etalonnage.marche.toString(), Date(),etalonnage.id))

             }
         }

    }


}
