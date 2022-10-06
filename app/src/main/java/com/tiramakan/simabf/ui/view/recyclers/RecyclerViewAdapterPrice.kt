package com.tiramakan.simabf.ui.view.recyclers

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.tiramakan.simabf.R
import com.tiramakan.simabf.core.modelView.PriceToUI
import com.tiramakan.simabf.core.models.realm.Produit
import com.tiramakan.simabf.ui.view.SelectableAdapter
import java.util.*

/**
 * [RecyclerView.Adapter] that can display a [Produit] and makes a call to the
 * specified [RecyclerViewAdapterPrice].
 * TODO: Replace the implementation with code for your data type.
 */
class RecyclerViewAdapterPrice(private val context: Context, private var mValues: ArrayList<PriceToUI>, private val clickListener: VHItem.ClickListener) : SelectableAdapter<RecyclerViewAdapterPrice.VHItem>() {
    internal var options: BitmapFactory.Options
    internal var bm: Bitmap? = null
    fun getElementAt(position: Int): PriceToUI? {
        return if (itemCount > 0)
            mValues[position]
        else
            null
    }
    init {
        options = BitmapFactory.Options()
        options.inJustDecodeBounds = false
    }
    fun setElement(item: PriceToUI, position: Int) {
        mValues[position] = item
        notifyDataSetChanged()
    }

    fun deleteSelection(position: Int) {
        mValues.removeAt(position)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return TYPE_ITEM
    }

    fun setItems(items: ArrayList<PriceToUI>) {
        mValues = items

        notifyDataSetChanged()

    }

    private fun isFirstItem(position: Int): Boolean {
        return position == 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VHItem {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.price_item, parent, false)
        return VHItem(view, clickListener)

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VHItem, position: Int) {

        holder.mItem = mValues[position]
        holder.mProduit.text = holder.mItem?.produit
        holder.mMontant.setText(holder.mItem?.montant?.toString() + "/" + holder.mItem?.mesure)
        holder.mMarche.text = holder.mItem?.marche
        holder.mComment.text = holder.mItem?.comment
        holder.mDate.text = holder.mItem?.prettyDateAndHour
        holder.mLongitude.text = holder.mItem?.note_longitude?.toString() ?: ""
        holder.mLatitude.text = holder.mItem?.note_latitude?.toString() ?: ""
        holder.mContenu.text = holder.mItem?.note_contenu?.toString() ?: ""
        holder.typePrix.text = holder.mItem?.typePrix?.toString() ?: ""

        holder.mView.isLongClickable = true
        if (isFirstItem(position)) {
            holder.mMontant.requestFocus()
        }
        holder.mContainer.setBackgroundColor(ResourcesCompat.getColor(context.resources, R.color.layout_color, null))
        holder.mProduit.setTextColor(Color.BLACK)
        holder.mMontant.setTextColor(Color.BLACK)
        holder.mMarche.setTextColor(Color.BLACK)
        holder.mDate.setTextColor(Color.BLACK)
        if (holder.mItem?.note_photo != "") {
            if (bm == null) {
                var inSampleSize = 1
                val reqHeight = 80
                val reqWidth = 80
                options.inJustDecodeBounds = true
                //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
                BitmapFactory.decodeFile(holder.mItem?.note_photo, options)
                val height = options.outHeight
                val width = options.outWidth


                if (height > reqHeight || width > reqWidth) {
                    val halfHeight = height / 2
                    val halfWidth = width / 2

                    // Calculate the largest inSampleSize value that is a power of 2 and keeps both
                    // height and width larger than the requested height and width.
                    while (halfHeight / inSampleSize >= reqHeight
                            && halfWidth / inSampleSize >= reqWidth) {
                        inSampleSize *= 2
                    }
                }


                options.inSampleSize = inSampleSize
                options.inJustDecodeBounds = false

                holder.mItem?.note_photo
                bm = BitmapFactory.decodeFile(holder.mItem?.note_photo, options)
                if (bm != null)
                    holder.mPhoto.setImageBitmap(bm)
                bm = null

            }
        }
        if (isSelected(position) || selectedPos == position) {
            setSelection(position)
            selectedPos = -1
        }

    }

    override fun getItemCount(): Int {
        return mValues.size
    }


    class VHItem(val mView: View, private val listener: ClickListener?) : RecyclerView.ViewHolder(mView), View.OnClickListener {
        internal var typePrix: TextView
        internal var mProduit: TextView
        internal var mMontant: TextView
        internal var mComment: TextView
        internal var mMarche: TextView
        internal var mDate: TextView
        internal var mContenu: TextView
        internal var mLongitude: TextView
        internal var mLatitude: TextView
        internal var mPhoto: ImageView
        internal var mContainer: RelativeLayout
        var mItem: PriceToUI? = null

        init {

            typePrix = mView.findViewById<View>(R.id.typePrix) as TextView
            mProduit = mView.findViewById<View>(R.id.produit) as TextView
            mMontant = mView.findViewById<View>(R.id.prix) as TextView
            mMarche = mView.findViewById<View>(R.id.marche) as TextView
            mDate = mView.findViewById<View>(R.id.date) as TextView
            mContenu = mView.findViewById<View>(R.id.noteMarche) as TextView
            mComment = mView.findViewById<View>(R.id.comment) as TextView
            mContainer = mView.findViewById<View>(R.id.container) as RelativeLayout
            mLongitude = mView.findViewById<View>(R.id.longitude) as TextView
            mLatitude = mView.findViewById<View>(R.id.latitude) as TextView
            mPhoto = mView.findViewById<View>(R.id.photo) as ImageView
            mView.setOnClickListener(this)
            //   mcheckPriceView = (CheckBox) view.findViewById(R.id.checkPrice);
        }

        override fun toString(): String {
            return super.toString() + " '" + "'"
        }

        override fun onClick(v: View) {
            listener?.onItemClicked(adapterPosition)
        }

        interface ClickListener {
            fun onItemClicked(position: Int)
        }
    }

    companion object {
        private val TYPE_ITEM = 1
    }

}
