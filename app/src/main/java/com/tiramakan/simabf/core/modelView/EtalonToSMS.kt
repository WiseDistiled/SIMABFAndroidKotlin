package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Etalonnage

/**
 * Created by tiramakan on 21/02/2016.
 */
class EtalonToSMS(etalonnage: Etalonnage) {
    var message: String = ""

    init {
        val tokenSeparator = " "
        val uml = etalonnage.uml
        val ul = etalonnage.ul
        val valeur = etalonnage.valeur

        message = "setetalon$tokenSeparator#$uml#$ul=$valeur"


    }
}
