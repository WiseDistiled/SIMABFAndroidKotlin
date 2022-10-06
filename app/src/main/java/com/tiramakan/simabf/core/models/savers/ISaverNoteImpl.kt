package com.tiramakan.simabf.core.models.savers

import com.squareup.otto.Bus
import com.tiramakan.simabf.core.modelView.NoteForUI
import com.tiramakan.simabf.core.models.realm.Note

import io.realm.Realm

/**
 * Created by tiramakan on 23/01/2016.
 */
class ISaverNoteImpl(protected var realm: Realm, protected var bus: Bus) : ISaverNote {
    public val nextKey: Int
        get() {
            val maxNumber = realm.where(Note::class.java).max("id")
            return if (maxNumber != null)
                maxNumber.toInt() + 1
            else
                1
        }

    override fun save(noteForUI: NoteForUI) {
        realm.executeTransaction {
            realm.delete(Note::class.java)
            if (noteForUI.isValid) {
                val note: Note
                if (noteForUI.id == null) {
                    note = realm.createObject(Note::class.java, nextKey)

                } else {
                    note = realm.where(Note::class.java)
                            ?.equalTo("id", noteForUI.id)
                            ?.findFirst()!!
                }
                note.status = "created"
                note.date = noteForUI.date.get()
                note.contenu = noteForUI.contenu.get()
                note.photo = noteForUI.photo.get()
                note.latitude = noteForUI.latitude.get()
                note.longitude = noteForUI.longitude.get()

            }

        }
    }
}
