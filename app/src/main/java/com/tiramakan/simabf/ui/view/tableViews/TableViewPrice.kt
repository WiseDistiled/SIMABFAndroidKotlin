package com.tiramakan.simabf.ui.view.tableViews

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.PriceToUI

import de.codecrafters.tableview.SortableTableView
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter

class TableViewPrice @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, styleAttributes: Int = 0) : SortableTableView<PriceToUI>(context, attributes, styleAttributes) {

    init {


        val simpleTableHeaderAdapter = SimpleTableHeaderAdapter(context, "Produit", "Prix Gros", "Prix Détail")
        simpleTableHeaderAdapter.setTextSizeId(R.dimen.text_size_textview)
        simpleTableHeaderAdapter.setPaddings(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BUTTOM)
        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.table_header_text))
        setHeaderAdapter(simpleTableHeaderAdapter)
        //  setHeaderSortStateViewProvider(SortStateViewProviders.brightArrows());
        val headerColor = ContextCompat.getColor(context, R.color.primaryDark)
        setHeaderBackgroundColor(headerColor)
        weightSum = 10f

        setColumnWeight(0, 2)
        setColumnWeight(1, 4)
        setColumnWeight(2, 4)

        //  setColumnComparator(0, ComparatorsPrice.getPriceForUIProduitComparator());
        //    setColumnComparator(1, ComparatorsPrice.getPriceForUIPriceComparator());
        //    setColumnComparator(2, ComparatorsPrice.getPriceForUIDetailPriceComparator());
    }

    companion object {
        //    private static final int PADDING_LEFT = 5;
        //    private static final int PADDING_TOP = 5;
        //    private static final int PADDING_RIGHT = 5;
        //    private static final int PADDING_BUTTOM = 5;

        private val PADDING_LEFT = 2
        private val PADDING_TOP = 2
        private val PADDING_RIGHT = 2
        private val PADDING_BUTTOM = 2
    }

}
