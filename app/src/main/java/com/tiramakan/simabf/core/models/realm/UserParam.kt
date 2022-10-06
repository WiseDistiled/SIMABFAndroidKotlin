package com.tiramakan.simabf.core.models.realm
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 19/12/2015.
 */
open class UserParam : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var reseau: String = ""
}
