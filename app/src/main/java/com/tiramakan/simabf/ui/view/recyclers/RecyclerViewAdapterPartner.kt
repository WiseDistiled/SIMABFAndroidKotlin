package com.tiramakan.simabf.ui.view.recyclers

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView

import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.PartnerForUI
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.ui.view.SelectableAdapter

import java.util.ArrayList

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterPartner].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterPartner
//    BitmapFactory.Options options;
//    Bitmap bm;


(private val context: Context, private var mValues: ArrayList<PartnerForUI>?)//        options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        final int REQUIRED_SIZE = 200;
//        int scale = 1;
//        while (options.outWidth / scale / 2 >= REQUIRED_SIZE
//                && options.outHeight / scale / 2 >= REQUIRED_SIZE)
//            scale *= 2;
//        options.inSampleSize = scale;
//        options.inJustDecodeBounds = false;
    : SelectableAdapter<RecyclerViewAdapterPartner.VHItem>() {
    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    fun setItems(items: ArrayList<PartnerForUI>) {
        mValues = items

        notifyDataSetChanged()

    }

    fun getElementAt(position: Int): PartnerForUI? {
        return if (itemCount > 0)
            mValues!![position]
        else
            null
    }

    fun setElement(item: PartnerForUI, position: Int) {
        mValues!![position] = item
        notifyDataSetChanged()
    }

    fun deleteSelection(position: Int) {
        mValues!!.removeAt(position)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.partner_item, parent, false)
        return VHItem(view)


    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onBindViewHolder(holder: VHItem, position: Int) {
        holder.mItem = mValues!![position]
        //        holder.mCommentView.setText(holder.mItem.commentaire);

        if (holder.mItem!!.photo != -1) {
            val myDrawable = context.getDrawable(holder.mItem!!.photo)
            holder.mPhoto.setImageDrawable(myDrawable)
        }

    }

    override fun getItemCount(): Int {
        return mValues!!.size
    }


    class VHItem(val mView: View) : RecyclerView.ViewHolder(mView) {
        //        TextView mCommentView;
        internal var mPhoto: ImageView
        internal var mContainer: RelativeLayout
        internal var mRoleView: TextView? = null

        var mItem: PartnerForUI? = null

        init {
            //            mCommentView= (TextView) view.findViewById(R.id.comment);
            mPhoto = mView.findViewById<View>(R.id.photo) as ImageView
            mContainer = mView.findViewById<View>(R.id.container) as RelativeLayout


            //   mcheckPriceView = (CheckBox) view.findViewById(R.id.checkPrice);
        }


        interface ClickListener {
            fun onItemClicked(position: Int)

        }

        override fun toString(): String {
            return super.toString() + " '" + "'"
        }
    }

    companion object {
        private val TYPE_ITEM = 1
    }

}
