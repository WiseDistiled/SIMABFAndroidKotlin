package com.tiramakan.simabf.core.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 22/12/2015.
 */
open class Produit : RealmObject() {
    @PrimaryKey
    var code: String = ""
    var nom: String = ""
    var categorie: ProduitCategorie? = null
    var mesure: Mesure? = null
    var prixMinimum: Double = 0.0
    var prixMaximum: Double = 0.0
}
