package com.tiramakan.simabf.core.models.savers

import com.tiramakan.simabf.core.modelView.UserToUI

/**
 * Created by tiramakan on 23/01/2016.
 */
interface ISaverUser {
    fun save(userToUI: UserToUI)
}
