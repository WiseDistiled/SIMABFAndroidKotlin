package com.tiramakan.simabf.core.modelView

import com.tiramakan.simabf.core.models.realm.Offre

/**
 * Created by tiramakan on 21/02/2016.
 */
class OfferToSMS(offre: Offre) {
    var message: String = ""

    init {
        var textPrixProduits = ""
        val tokenSeparator = " "
        val mesureCode = offre.mesure!!.code
        val produitCode = offre.produit!!.code
        val quantite = offre.quantite.toString()
        val prix = offre.montant.toString()
        val typeOffreCode = offre.offerType
        val categPrix = "pt"
        val auteur = offre.telephone
        val note = offre.conditions
        textPrixProduits += "$produitCode=$quantite/$mesureCode+$prix/$categPrix"

        message = "setoffre$tokenSeparator#$typeOffreCode#$textPrixProduits"
        message += "#" + auteur
        if ("" != note) {
            message += "__" + note
        }

    }
}
