package com.tiramakan.simabf.ui.view.tableViews

import android.content.Context
import android.util.AttributeSet
import androidx.core.content.ContextCompat

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.EtalonnageToUI
import com.tiramakan.simabf.core.modelView.StockForUI

import de.codecrafters.tableview.SortableTableView
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter

class TableViewEtalonnage @JvmOverloads constructor(context: Context, attributes: AttributeSet? = null, styleAttributes: Int = 0) : SortableTableView<EtalonnageToUI>(context, attributes, styleAttributes) {

    init {


        val simpleTableHeaderAdapter = SimpleTableHeaderAdapter(context, "UML","UL", "Valeur")
        simpleTableHeaderAdapter.setTextSizeId(R.dimen.text_size_textview)
        simpleTableHeaderAdapter.setPaddings(PADDING_LEFT, PADDING_TOP, PADDING_RIGHT, PADDING_BUTTOM)
        simpleTableHeaderAdapter.setTextColor(ContextCompat.getColor(context, R.color.table_header_text))
        setHeaderAdapter(simpleTableHeaderAdapter)
        weightSum = 10f

        val headerColor = ContextCompat.getColor(context, R.color.primaryDark)
        setHeaderBackgroundColor(headerColor)

        setColumnWeight(0, 3)
        setColumnWeight(1, 3)
        setColumnWeight(2, 4)

    }

    companion object {
        private val PADDING_LEFT = 20
        private val PADDING_TOP = 15
        private val PADDING_RIGHT = 20
        private val PADDING_BUTTOM = 15
    }

}
