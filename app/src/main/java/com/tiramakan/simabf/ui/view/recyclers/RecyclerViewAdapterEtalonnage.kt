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
import com.tiramakan.simabf.core.modelView.EtalonnageToUI
import com.tiramakan.simabf.ui.view.SelectableAdapter

import java.util.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterEtalonnage].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterEtalonnage(private val context: Context, private var mValues: ArrayList<EtalonnageToUI>?, private val clickListener: VHItem.ClickListener) : SelectableAdapter<RecyclerViewAdapterEtalonnage.VHItem>() {
    override fun getItemViewType(position: Int): Int {
        //        if(isPositionHeader(position))
        //            return TYPE_HEADER;
        return TYPE_ITEM
    }

    fun setItems(items: ArrayList<EtalonnageToUI>) {
        mValues = items

        notifyDataSetChanged()

    }

    fun getElementAt(position: Int): EtalonnageToUI? {
          return  mValues!![position]

    }

    fun setElement(item: EtalonnageToUI, position: Int) {
        mValues!![position] = item
        notifyDataSetChanged()
    }

    fun deleteSelection(position: Int) {
        mValues?.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.etalonnage_item, parent, false)
        return VHItem(view, clickListener)
    }

    override fun onBindViewHolder(holder: VHItem, position: Int) {
        holder.mItem = mValues?.get(position)
        holder.mUML.text = holder.mItem?.uml
        holder.mUL.text = holder.mItem?.ul
        holder.mValeur.text = holder.mItem?.valeur
        holder.mView.isLongClickable = true
        holder.mContainer.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.layout_color, null))
        holder.mUML.setTextColor(Color.BLACK)
        holder.mUL.setTextColor(Color.BLACK)
        holder.mValeur.setTextColor(Color.BLACK)
        if (isSelected(position) || selectedPos == position) {
            setSelection(position)
            selectedPos = -1
        }

    }


    override fun getItemCount(): Int {
        return mValues?.size?:0
    }


    class VHItem(val mView: View, private val listener: ClickListener?) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        internal var mUML: TextView
        internal var mUL: TextView
        internal var mValeur: TextView
        internal var mContainer: RelativeLayout


        var mItem: EtalonnageToUI? = null

        init {
            mUML = mView.findViewById<View>(R.id.uml) as TextView
            mUL = mView.findViewById<View>(R.id.ul) as TextView
            mValeur = mView.findViewById<View>(R.id.valeur) as TextView
            mContainer = mView.findViewById<View>(R.id.container) as RelativeLayout
            // mView.setOnLongClickListener (this);
            mView.setOnClickListener(this)
        }

        override fun onClick(v: View) {
            listener?.onItemClicked(adapterPosition)
        }

        interface ClickListener {
            fun onItemClicked(position: Int)
        }

        override fun toString(): String {
            return super.toString() + " '" + "'"
        }
    }

    internal inner class VHHeader(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var txtTitle: TextView

        init {
            this.txtTitle = itemView.findViewById<View>(R.id.txtHeader) as TextView
        }
    }

    companion object {
        private val TYPE_ITEM = 1
    }
}
