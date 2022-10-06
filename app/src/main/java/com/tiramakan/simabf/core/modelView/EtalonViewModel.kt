package com.tiramakan.simabf.core.modelView

import java.util.*
/**
 * Created by tiramakan on 14/02/2016.
 */
abstract class EtalonViewModel (var uml: String = "",
                                var ul: String = "",
                                var valeur: String = "",
                                var date: Date = Date(),
                                var id: Int? = null

) : ViewModelBase() {

    open lateinit var listEtalonnages: ArrayList<EtalonnageToUI>
        protected set
    open val isNotEmpty: Boolean?
        get() = false


    open fun updateEtalonnages(marcheChoisi: String) {

    }

    open fun save(marcheChoisi: String): Boolean {
        return false
    }

}
