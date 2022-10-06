package com.tiramakan.simabf.core.models.savers

import com.tiramakan.simabf.core.modelView.NoteForUI

/**
 * Created by tiramakan on 23/01/2016.
 */
interface ISaverNote {
    fun save(noteForUI: NoteForUI)
}
