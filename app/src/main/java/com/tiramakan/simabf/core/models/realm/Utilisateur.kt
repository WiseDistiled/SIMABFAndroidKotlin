package com.tiramakan.simabf.core.models.realm

import java.util.Date

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 19/12/2015.
 */
open class Utilisateur : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var mobilePhone: String = ""
    var prenom: String = ""
    var nom: String = ""
    var genre: String = ""
    var status: String = ""
    var secondPhone: String = ""
    var email: String = ""
    var reseau: String = ""
}
