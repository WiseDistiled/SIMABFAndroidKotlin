package com.tiramakan.simabf.ui.view.comparators

import com.tiramakan.simabf.core.modelView.MarcheUIModel
import java.util.Comparator

/**
 * Created by Ingo on 28.07.2015.
 */
object ComparatorsMarche {

    val marcheUIModelComparator: Comparator<MarcheUIModel>
        get() = MarcheUIModelComparator()

    private class MarcheUIModelComparator : Comparator<MarcheUIModel> {

        override fun compare(marche1: MarcheUIModel, marche2: MarcheUIModel): Int {
            return marche1.name.compareTo(marche2.name)
        }
    }


}
