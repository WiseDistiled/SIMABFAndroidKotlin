package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Depot
import com.tiramakan.simabf.ui.view.comparators.ComparatorsDepot

import java.io.Serializable
import java.util.ArrayList
import java.util.Collections

import io.realm.RealmResults
import io.realm.Sort

/**
 * Created by tiramakan on 17/02/2016.
 */
class DepotUIModel internal constructor(depot: Depot?) : Serializable {
    var code: String = ""
    var name: String = ""
    var active: Boolean = false
    var description: String = ""
    var longitude: Double = java.lang.Double.valueOf("0.0")
    var latitude: Double = java.lang.Double.valueOf("0.0")

    var isSelected: Boolean = false

    init {
        if (depot != null) {
            this.longitude = depot.longitude
            this.latitude = depot.latitude
            this.name = depot.nom
            this.code = depot.code
            this.isSelected = false
        }
    }

    override fun toString(): String {
        return " nom : " + name
    }

    companion object {
        public const val serialVersionUID = 1L
        fun getList(bootstrapService: BootstrapService): ArrayList<DepotUIModel> {
            val temp = ArrayList<DepotUIModel>()
            val depots = bootstrapService.depots
            depots.sort("nom", Sort.ASCENDING)
            for (depot in depots) {
                temp.add(DepotUIModel(depot))
            }
            Collections.sort(temp, ComparatorsDepot.depotUIModelComparator)

            return temp
        }

        fun getDepotStartingWith(bootstrapService: BootstrapService, produitCategName: String): DepotUIModel {

            val depot = bootstrapService.getDepotSartingWith(produitCategName)
            return DepotUIModel(depot)
        }
    }
}
