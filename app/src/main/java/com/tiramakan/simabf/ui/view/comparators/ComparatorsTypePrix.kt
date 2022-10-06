package com.tiramakan.simabf.ui.view.comparators

import com.tiramakan.simabf.core.modelView.MarcheUIModel
import com.tiramakan.simabf.core.modelView.TypePrixUIModel
import java.util.Comparator

/**
 * Created by Ingo on 28.07.2015.
 */
object ComparatorsTypePrix {

    val typePrixUIModelComparator: Comparator<TypePrixUIModel>
        get() = TypePrixUIModelComparator()

    private class TypePrixUIModelComparator : Comparator<TypePrixUIModel> {

        override fun compare(typePrixUIModel1: TypePrixUIModel, typePrixUIModel2: TypePrixUIModel): Int {
            return typePrixUIModel1.name!!.compareTo(typePrixUIModel2.name!!)
        }
    }


}
