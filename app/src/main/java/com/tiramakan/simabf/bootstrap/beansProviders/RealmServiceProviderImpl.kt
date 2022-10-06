package com.tiramakan.simabf.bootstrap.beansProviders

import android.content.Context
import android.content.Intent
import android.content.res.AssetManager
import android.net.Uri
import android.util.Log
import com.github.ajalt.timberkt.Timber

import com.squareup.otto.Bus
import com.tiramakan.simabf.bootstrap.util.MyPreferences


import io.realm.DynamicRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration
import java.io.*

/**
 * Created by tiramakan on 19/12/2015.
 */
class RealmServiceProviderImpl(private val context: Context, private val preferences: MyPreferences, private val bus: Bus) : RealmServiceProvider {
    override// DynamicRealm exposes an editable schema
    //  return Realm.getDefaultInstance();
    //No Realm file to remove.
    val newRealm: Realm
        get() {

                Realm.init(context)
                val assetMgr = context.assets
             //   copyBundledRealmFile(assetMgr.open("bf/"+preferences.dbName), preferences.dbName)
                Realm.init(context)
                val realmConfigurationafter = RealmConfiguration.Builder()
                        .name(preferences.dbName)
                        .allowWritesOnUiThread(true)
                        .schemaVersion(0)
                        .deleteRealmIfMigrationNeeded()
                        .build()
                Realm.setDefaultConfiguration(realmConfigurationafter)
                return Realm.getInstance(realmConfigurationafter)
        }
    override// DynamicRealm exposes an editable schema
    //No Realm file to remove.
    val realm: Realm
        get() {

                try {
                    Realm.init(context)
                    val realmConfigurationafter = RealmConfiguration.Builder()
                            .name(preferences.dbName)
                            .schemaVersion(0)
                            .allowWritesOnUiThread(true)
                            .deleteRealmIfMigrationNeeded()
                            .build()
                    Realm.setDefaultConfiguration(realmConfigurationafter)
                    return Realm.getInstance(realmConfigurationafter)
                }catch (e:Exception) {
                    bus.post("Exception $e, reinitialisation de la base")
                    return newRealm
                }


        }

    override fun sendDatabase() {

        // init realm
        val realm = Realm.getDefaultInstance()
        try {
            // get or create an "export.realm" file
          val  exportRealmFile = File(context.externalCacheDir, "export.realm")

            // if "export.realm" already exists, delete
            if (exportRealmFile.delete())
            // copy current realm to "export.realm"
                realm.writeCopyTo(exportRealmFile)


            // init email intent and add export.realm as attachment
            val intent = Intent(Intent.ACTION_SEND)
            intent.type = "plain/text"
            intent.putExtra(Intent.EXTRA_EMAIL, preferences.adminMail)
            intent.putExtra(Intent.EXTRA_SUBJECT, "Base de données SIMABF")
            intent.putExtra(Intent.EXTRA_TEXT, "Veuillez trouver ci-joint la base de données de l'application Android ")
            val u = Uri.fromFile(exportRealmFile)
            intent.putExtra(Intent.EXTRA_STREAM, u)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            context.applicationContext.startActivity(Intent.createChooser(intent, "Envoyer du fichier de base de données"))
            bus.post("Le fichier a été envoyé avec succès à l'adresse " + preferences.adminMail)
        } catch (e: Exception) {
            e.printStackTrace()
            bus.post("Echec de l'envoie du fichier cause " + e.message)
        }

        realm.close()
    }

    private fun copyBundledRealmFile(inputStream: InputStream, outFileName: String): String {
        try {

            val file =File(context.filesDir, outFileName)
            file.writeBytes(inputStream.readBytes())
            val resultat = file.canonicalPath
            Timber.tag("SIMBF").d( "resultat: $resultat")
            return resultat
        } catch (e: IOException) {
            Timber.tag("SIMBF").d(e, "error message: %s", e.message)
            e.printStackTrace()
            return ""
        }

    }

}
