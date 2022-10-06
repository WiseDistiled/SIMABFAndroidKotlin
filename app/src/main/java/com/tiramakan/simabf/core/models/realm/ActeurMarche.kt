package com.tiramakan.simabf.core.models.realm

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

/**
 * Created by tiramakan on 22/12/2015.
 */
open class ActeurMarche : RealmObject() {
    @PrimaryKey
    var id: String = ""
    var marche: String = ""
}
