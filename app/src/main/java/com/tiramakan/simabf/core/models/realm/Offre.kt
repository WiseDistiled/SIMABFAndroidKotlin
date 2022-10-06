package com.tiramakan.simabf.core.models.realm

import java.util.Date

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 22/12/2015.
 */
open class Offre : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var offerType: String = ""
    var date: Date = Date()
    var expirationDate: Date = Date()
    var quantite: Double = 0.0
    var mesure: Mesure? = null
    var region: Region? = null
    var conditions: String = ""
    var produit: Produit? = null
    var prixUnitaire: Double = 0.0
    var montant: Double = 0.0
    var nomAuteur: String = ""
    var telephone: String = ""
    var qualite: Qualite? = null
    var email: String = ""
    var status:String = ""
    var longitude: String = ""
    var latitude: String = ""
    var lieuxStockage: String = ""
    var lieuxLivraison: String = ""
    var photo: String = ""
}
