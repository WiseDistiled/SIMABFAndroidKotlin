package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Prix

/**
 * Created by tiramakan on 21/02/2016.
 */
class PriceToSMS(prix: Prix) {
    var messageSMSPrixDeGros: String = ""
    var messageSMSPrixDeDetail: String = ""

    init {
        val separator = ","
        val tokenSeparator = " "
        val mesureGrosCode = prix.mesure!!.code
        val produitCode = prix.produit!!.code
        val prixDeGros = prix.montant.toString()
        val marche = prix.marche!!.code
        val note = prix.note_contenu

        messageSMSPrixDeGros = ""
        messageSMSPrixDeDetail = ""
        if (prix.montant > 0)
            messageSMSPrixDeGros = "setprix$tokenSeparator#$produitCode=$prixDeGros/0$separator#$marche#$mesureGrosCode"

        if ("" != note) {
            messageSMSPrixDeGros += "__" + note
        }


    }
}
