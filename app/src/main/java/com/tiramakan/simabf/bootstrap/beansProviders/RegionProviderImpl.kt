package com.tiramakan.simabf.bootstrap.beansProviders

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.core.models.realm.Region

import java.util.ArrayList

import io.realm.Realm

/**
 * Created by tiramakan on 17/01/2016.
 */
class RegionProviderImpl(internal var bootstrapService: BootstrapService, internal var realm: Realm) : RegionProvider {

    override val items: List<String>
        get() {
            val itemList = ArrayList<String>()
            val regions = bootstrapService.regions
            for (region in regions) {
                itemList.add(region.nom.toString())

            }
            return itemList
        }

    override fun getId(valueName: String): Region {
        return realm.where(Region::class.java)
                ?.equalTo("nom", valueName)
                ?.findFirst()!!
    }
}
