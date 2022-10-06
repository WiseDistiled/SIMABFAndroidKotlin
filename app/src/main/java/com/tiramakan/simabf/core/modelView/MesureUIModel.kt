package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.Mesure

import java.io.Serializable
import java.util.ArrayList

import io.realm.RealmResults
import io.realm.Sort

/**
 * Created by tiramakan on 17/02/2016.
 */
class MesureUIModel internal constructor(mesure: Mesure?) : Serializable {
    var name: String? = null
    var code: String? = null

    var isSelected: Boolean = false

    init {
        if (mesure != null) {
            this.name = mesure.nom
            this.code = mesure.code
            this.isSelected = false
        }
    }

    override fun toString(): String {
        return " nom : " + name
    }

    companion object {
        public const val serialVersionUID = 1L
        fun getList(bootstrapService: BootstrapService): ArrayList<MesureUIModel> {
            val temp = ArrayList<MesureUIModel>()
            val mesures = bootstrapService.mesures
            mesures.sort("nom", Sort.ASCENDING)
            for (mesure in mesures) {
                temp.add(MesureUIModel(mesure))
            }
            return temp
        }

        fun getMesureStartingWith(bootstrapService: BootstrapService, mesureName: String): MesureUIModel {

            val mesure = bootstrapService.getMesureSartingWith(mesureName)
            return MesureUIModel(mesure)
        }
    }
}
