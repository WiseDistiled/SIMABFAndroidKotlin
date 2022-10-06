package com.tiramakan.simabf.ui.view.recyclers

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.MesureUIModel
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.ui.view.SelectableAdapter

import java.util.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterMesureRadio].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterMesureRadio(private val context: Context, private val mValues: ArrayList<MesureUIModel>, private val clickListener: VHItem.ClickListener) : SelectableAdapter<RecyclerViewAdapterMesureRadio.VHItem>() {

    val selectedItem: MesureUIModel?
        get() = if (selectedItemPosition!! > -1)
            mValues[selectedItemPosition!!]
        else
            null


    fun getPositionByName(marcheName: String): Int {
        var index = 0
        for (mesureUIModel in mValues) {
            if (mesureUIModel.name == marcheName)
                break
            index++
        }
        return index
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {


        val itemLayoutView = LayoutInflater.from(parent.context).inflate(
                R.layout.measure_item_radio, null)

        return VHItem(itemLayoutView, clickListener)

    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {

        holder.itemView.isSelected = selectedPos == position
        holder.mItem = mValues[position]
        holder.mlibelleView.text = mValues[position].name
        holder.mView.tag = mValues[position]
        holder.mContainer.setBackgroundColor(Color.parseColor("#0c180c"))
        holder.mlibelleView.setTextColor(Color.WHITE)
        holder.mContainer.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.layout_color, null))
        if (isSelected(position)) {
            holder.mContainer.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.GridBtnBgSelectedColor, null))
            holder.mlibelleView.setTextColor(Color.BLACK)
            setSelection(position)
            selectedPos = -1
        } else {
            holder.mContainer.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.GridBtnBgColor, null))
            holder.mlibelleView.setTextColor(Color.WHITE)
        }


    }


    override fun getItemCount(): Int {
        return mValues.size
    }


    class VHItem(var mView: View, private val listener: ClickListener?) : RecyclerView.ViewHolder(mView), View.OnClickListener, View.OnLongClickListener {
        var mlibelleView: TextView
        var mItem: MesureUIModel? = null
        var mContainer: RelativeLayout

        init {
            mlibelleView = mView.findViewById<View>(R.id.libelle) as TextView
            mContainer = mView.findViewById<View>(R.id.container) as RelativeLayout
            mView.setOnClickListener(this)
            mView.setOnLongClickListener(this)
        }


        override fun toString(): String {
            return super.toString() + " '" + mlibelleView.text + "'"
        }

        override fun onClick(v: View) {
            listener?.onItemClicked(adapterPosition)
        }

        override fun onLongClick(view: View): Boolean {
            return listener != null && listener.onItemLongClicked(adapterPosition)
        }

        interface ClickListener {
            fun onItemClicked(position: Int)

            fun onItemLongClicked(position: Int): Boolean
        }
    }


}
