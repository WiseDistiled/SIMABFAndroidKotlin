package com.tiramakan.simabf.ui.view.adapters

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.EtalonnageToUI
import com.tiramakan.simabf.databinding.TableEtalonnageBinding
import java.util.ArrayList

import de.codecrafters.tableview.TableDataAdapter





class TableDataAdapterEtalonnage(context: Context?, data: List<EtalonnageToUI>) : TableDataAdapter<EtalonnageToUI>(context, data) {

    init {
    }

    override fun getCellView(rowIndex: Int, columnIndex: Int, parentView: ViewGroup): View? {
        val etalonnageToUI = getRowData(rowIndex)
        var renderedView: View? = null

        when (columnIndex) {
            0 -> renderedView = renderUML(etalonnageToUI)
            1 -> renderedView = renderUL(etalonnageToUI)
            2 -> renderedView = renderValeur(etalonnageToUI, parentView)
        }

        return renderedView
    }


    private fun renderValeur(etalonnageToUI: EtalonnageToUI, parentView: ViewGroup): View {

        val binding = DataBindingUtil.inflate<TableEtalonnageBinding>(layoutInflater, R.layout.table_etalonnage, parentView, false)
        val view = binding.root
        binding.etalonnageUI = etalonnageToUI

        binding.decimalvalue.setText(etalonnageToUI.valeur)

        return view
    }
    private fun renderUML(etalonnageToUI: EtalonnageToUI): View {
        val textView = TextView(context)
        textView.text = etalonnageToUI.uml
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_textview))

        return textView
    }
    private fun renderUL(etalonnageToUI: EtalonnageToUI): View {
        val textView = TextView(context)
        textView.text = etalonnageToUI.ul
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_textview))

        return textView
    }


}
