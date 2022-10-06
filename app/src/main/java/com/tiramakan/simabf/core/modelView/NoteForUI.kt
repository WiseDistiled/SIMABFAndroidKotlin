package com.tiramakan.simabf.core.modelView

import android.graphics.Bitmap

import com.tiramakan.simabf.core.models.realm.Note
import com.tiramakan.simabf.ui.view.utils.Binding.BindableDate
import com.tiramakan.simabf.ui.view.utils.Binding.BindableDouble
import com.tiramakan.simabf.ui.view.utils.Binding.BindableString
import com.tiramakan.simabf.bootstrap.util.UIUtils

import org.parceler.Parcel

import java.util.ArrayList
import java.util.Date

import io.realm.Realm
import io.realm.RealmResults

/**
 * Created by tiramakan on 22/12/2015.
 */
@Parcel(value = Parcel.Serialization.BEAN)
 class NoteForUI {

     var date = BindableDate()
     var contenu = BindableString()
     var longitude = BindableDouble()
     var latitude = BindableDouble()
     var photo = BindableString()
     var marche = BindableString()
     var id: Int? = null
      val noteDate: String
        get() = UIUtils.dateFormat.format(date.get())
     val prettyDate: String
        get() = UIUtils.prettyDateFormat.format(date.get())
      val prettyDateAndHour: String
        get() = UIUtils.prettyDateAndHourFormat.format(date.get())
      val isValid: Boolean
        get() =  contenu.get().toString() != ""

    constructor() {
        contenu.set("")
        photo.set("")
        marche.set("")
        longitude.set(0.0)
        latitude.set(0.0)
        date.set(Date())
    }


    fun getLongitudeText(): String {
        return longitude.get().toString()
    }

    fun getLatitudeText(): String {
        return latitude.get().toString()
    }

    fun getResizedBitmap(image: Bitmap, maxSize: Int): Bitmap {
        var width = image.width
        var height = image.height

        val bitmapRatio = width.toFloat() / height.toFloat()
        if (bitmapRatio > 1) {
            width = maxSize
            height = (width / bitmapRatio).toInt()
        } else {
            height = maxSize
            width = (height * bitmapRatio).toInt()
        }

        return Bitmap.createScaledBitmap(image, width, height, true)
    }
    constructor(note: Note) {
        contenu.set(note.contenu)
        photo.set(note.photo)
        longitude.set(note.longitude)
        latitude.set(note.latitude)
        date.set(note.date)
        marche.set(note.marche)
        this.id = note.id
    }

    fun validate(): Boolean {
        return contenu.get() != ""
    }

    companion object {
        fun getRealmNoteById(realm: Realm, id: Int): Note? {
            return realm.where(Note::class.java)
                    .equalTo("id", id)
                    .findFirst()

        }

        fun getRealmNotesNotSended(realm: Realm): RealmResults<Note> {
            return realm.where(Note::class.java)
                    .equalTo("status", "created")
                    .findAll()

        }

        fun getListFromDBNotSended(realm: Realm): ArrayList<NoteForUI> {
            val theList = ArrayList<NoteForUI>()

            val notes = realm.where(Note::class.java)
                    .equalTo("status", "created")
                    .findAll()
            if (notes.size > 0) {
                for (note in notes) {
                    val noteForUI = NoteForUI(note)
                    theList.add(noteForUI)
                }
            }
            return theList

        }
    }

}
