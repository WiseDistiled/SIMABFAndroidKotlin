package com.tiramakan.simabf.ui.view.adapters

import android.content.Context
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.databinding.DataBindingUtil

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.StockForUI
import com.tiramakan.simabf.databinding.TableBalanceBinding
import com.tiramakan.simabf.bootstrap.util.UIUtils
import java.util.ArrayList

import de.codecrafters.tableview.TableDataAdapter





class TableDataAdapterStock(context: Context, data: List<StockForUI>, mesures: ArrayList<String>) : TableDataAdapter<StockForUI>(context, data) {
    private val mesureAdapter: ArrayAdapter<String>

    init {
        mesureAdapter = ArrayAdapter(getContext(), android.R.layout.simple_spinner_item, mesures)
    }

    override fun getCellView(rowIndex: Int, columnIndex: Int, parentView: ViewGroup): View? {
        val stockForUI = getRowData(rowIndex)
        var renderedView: View? = null

        when (columnIndex) {
            0 -> renderedView = renderProduit(stockForUI)
            1 -> renderedView = renderBalance(stockForUI, parentView)
        }

        return renderedView
    }


    private fun renderBalance(stockForUI: StockForUI, parentView: ViewGroup): View {

        val binding = DataBindingUtil.inflate<TableBalanceBinding>(layoutInflater, R.layout.table_etalonnage, parentView, false)
        val view = binding.root
        binding.stockForUI = stockForUI
        var priceString = ""

        if (stockForUI.balance.get() > 0)
            priceString = UIUtils.formatPrice(stockForUI.balance.get())

        binding.decimalvalue.setText(priceString)
        binding.mesure.adapter=mesureAdapter

        if (stockForUI.mesure.get()!= "") {
            val spinnerPosition = mesureAdapter.getPosition(stockForUI.mesure.get())
            binding.mesure.setSelection(spinnerPosition)
        }
        return view
    }

    private fun renderProduit(stockForUI: StockForUI): View {
        val textView = TextView(context)
        textView.text = stockForUI.produit.get()
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, resources.getDimension(R.dimen.text_size_textview))

        return textView
    }

    companion object {

        private val TEXT_SIZE = 14
        private val PADDING_LEFT = 5
        private val PADDING_TOP = 5
        private val PADDING_RIGHT = 5
        private val PADDING_BUTTOM = 10
    }

}
