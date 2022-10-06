package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Marche
import com.tiramakan.simabf.ui.view.comparators.ComparatorsMarche

import java.io.Serializable
import java.util.ArrayList
import java.util.Collections

import io.realm.Sort

/**
 * Created by tiramakan on 17/02/2016.
 */
class MarcheUIModel internal constructor(marche: Marche?) : Serializable {
    var code: String = ""
    var name: String = ""
    var active: Boolean = false
    var description: String? = null
    var longitude: Double? = java.lang.Double.valueOf("0.0")
    var latitude: Double? = java.lang.Double.valueOf("0.0")

    var isSelected: Boolean = false

    init {
        if (marche != null) {
            this.longitude = marche.longitude
            this.latitude = marche.latitude
            this.name = marche.nom
            this.code = marche.code
            this.isSelected = false
        }
    }

    override fun toString(): String {
        return " nom : " + name
    }

    companion object {
        public const val serialVersionUID = 1L

        fun getList(bootstrapService: BootstrapService): ArrayList<MarcheUIModel> {
            val temp = ArrayList<MarcheUIModel>()
            val marches = bootstrapService.marches
            marches.sort("nom", Sort.ASCENDING)
            for (marche in marches) {
                temp.add(MarcheUIModel(marche))
            }
            Collections.sort(temp, ComparatorsMarche.marcheUIModelComparator)
            return temp
        }
        fun getListForWriting(bootstrapService: BootstrapService): ArrayList<MarcheUIModel> {
           val temp = ArrayList<MarcheUIModel>()
            val marches = bootstrapService.marches
            marches.sort("nom", Sort.ASCENDING)
                for (marche in marches) {
                        temp.add(MarcheUIModel(marche))

                }

            Collections.sort(temp, ComparatorsMarche.marcheUIModelComparator)
            return temp
        }

        fun getListStartingWith(bootstrapService: BootstrapService, marchePref: String): ArrayList<MarcheUIModel> {
            val temp = ArrayList<MarcheUIModel>()
            val marches = bootstrapService.getMarchesSartingWith(marchePref)
            marches?.sort("nom", Sort.ASCENDING)
            for (marche in marches!!) {
                temp.add(MarcheUIModel(marche))
            }
            Collections.sort(temp, ComparatorsMarche.marcheUIModelComparator)
            return temp
        }

        fun getMarcheWith(bootstrapService: BootstrapService, marchePref: String): MarcheUIModel {

            val marche = bootstrapService.getMarcheSartingWith(marchePref)


            return MarcheUIModel(marche)
        }
    }
}
