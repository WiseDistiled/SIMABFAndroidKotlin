package com.tiramakan.simabf.bootstrap

import android.content.Context

import com.squareup.otto.Bus

import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 * Created by tiramakan on 28/01/2016.
 */
class AssetImporterImpl internal constructor(private val context: Context, private val bus: Bus) : AssetImporter {
    override fun execute() {

        //  copyBundledRealmFile(context.getResources().openRawResource(R.raw.simagri), preferences.dbName);

        bus.post("La réinitialisation s'est bien passée ")

    }


    private fun copyBundledRealmFile(inputStream: InputStream, outFileName: String): String? {
        try {

            val file = File(context.filesDir, outFileName)
            val outputStream = FileOutputStream(file)
            val buf = ByteArray(1024)
            var bytesRead: Int
            while (inputStream.read(buf) > 0) {
                bytesRead = inputStream.read(buf)
                outputStream.write(buf, 0, bytesRead)
            }
            outputStream.close()
            return file.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return null
    }

}
