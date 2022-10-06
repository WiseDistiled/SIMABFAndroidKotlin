package com.tiramakan.simabf.core.models.savers

import com.tiramakan.simabf.core.modelView.OfferToUI

/**
 * Created by tiramakan on 23/01/2016.
 */
interface ISaverOffer {
    fun save(offerToUI: OfferToUI)
}
