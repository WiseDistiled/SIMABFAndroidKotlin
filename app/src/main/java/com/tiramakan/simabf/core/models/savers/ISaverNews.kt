package com.tiramakan.simabf.core.models.savers

import com.tiramakan.simabf.core.modelView.NewsForUI

/**
 * Created by tiramakan on 23/01/2016.
 */
interface ISaverNews {
    fun save(newsForUI: NewsForUI)
}
