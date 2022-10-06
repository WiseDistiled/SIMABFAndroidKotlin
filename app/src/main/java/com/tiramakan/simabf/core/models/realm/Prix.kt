package com.tiramakan.simabf.core.models.realm


import java.util.Date

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 22/12/2015.
 */
open class Prix : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var status: String = ""
    var produit: Produit? = null
    var marche: Marche? = null
    var date: Date = Date()
    var montant: Double = 0.0
    var typePrix: String = "Detail"
    var comment: String = ""
    var mesure: Mesure? = null
    var note_contenu: String = ""
    var note_longitude: String = ""
    var note_latitude: String = ""
    var note_photo: String = ""
    }
