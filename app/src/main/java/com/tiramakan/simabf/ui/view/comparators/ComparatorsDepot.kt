package com.tiramakan.simabf.ui.view.comparators

import com.tiramakan.simabf.core.modelView.DepotUIModel

import java.util.Comparator

/**
 * Created by Ingo on 28.07.2015.
 */
object ComparatorsDepot {

    val depotUIModelComparator: Comparator<DepotUIModel>
        get() = DepotUIModelComparator()


    private class DepotUIModelComparator : Comparator<DepotUIModel> {

        override fun compare(depotUIModel1: DepotUIModel, depotUIModel2: DepotUIModel): Int {
            return depotUIModel1.name.compareTo(depotUIModel2.name)
        }
    }


}
