package com.tiramakan.simabf.core.modelView


import java.io.Serializable
import java.util.ArrayList

/**
 * Created by tiramakan on 17/02/2016.
 */
class TypeGeoUIModel internal constructor(nom: String) : Serializable {
    var code: String = ""
    var name: String = ""
    var isSelected: Boolean = false

    init {
            this.code=nom;
            this.name = nom;
            this.isSelected = false

    }

    override fun toString(): String {
        return " nom : " + name
    }

    companion object {
        public const val serialVersionUID = 1L

        fun getList(): ArrayList<TypeGeoUIModel> {
            val temp = ArrayList<TypeGeoUIModel>()
             temp.add(TypeGeoUIModel("Dépôts"))
            temp.add(TypeGeoUIModel("Marchés"))
            return temp
        }

    }
}
