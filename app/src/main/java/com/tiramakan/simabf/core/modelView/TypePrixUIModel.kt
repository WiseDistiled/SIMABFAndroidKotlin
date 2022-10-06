package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.bootstrap.BootstrapService
import com.tiramakan.simabf.core.models.realm.TypePrix
import com.tiramakan.simabf.ui.view.comparators.ComparatorsTypePrix

import java.io.Serializable
import java.util.ArrayList
import java.util.Collections

import io.realm.Sort

/**
 * Created by tiramakan on 17/02/2016.
 */
class TypePrixUIModel internal constructor(typePrix: TypePrix?) : Serializable {
    var code: String? = null
    var name: String? = null
    var isSelected: Boolean = false

    init {
        if (typePrix != null) {
            this.name = typePrix.nom
            this.code = typePrix.nom
            this.isSelected = false
        }
    }

    override fun toString(): String {
        return " nom : " + name!!
    }

    companion object {
        public const val serialVersionUID = 1L

        fun getList(bootstrapService: BootstrapService): ArrayList<TypePrixUIModel> {
            val temp = ArrayList<TypePrixUIModel>()
            val typesPrix = bootstrapService.typesPrix
            typesPrix.sort("nom", Sort.ASCENDING)
            for (typePrix in typesPrix) {
                temp.add(TypePrixUIModel(typePrix))
            }
            Collections.sort(temp, ComparatorsTypePrix.typePrixUIModelComparator)
            return temp
        }

    }
}
