package com.tiramakan.simabf.core.uploads

import timber.log.Timber
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class UploadVideo {
    public var serverResponseCode = 0
    fun uploadVideo(file: String): String? {
        var conn: HttpURLConnection? = null
        var dos: DataOutputStream? = null
        val lineEnd = "\r\n"
        val twoHyphens = "--"
        val boundary = "*****"
        var bytesRead: Int
        var bytesAvailable: Int
        var bufferSize: Int
        val buffer: ByteArray
        val maxBufferSize = 1 * 1024 * 1024
        val sourceFile = File(file)
        if (!sourceFile.isFile) {
            Timber.tag("UPLOAD").d("Le fichier source n'existe pas")
            return null
        }
        try {
            val fileInputStream = FileInputStream(sourceFile)
            val url = URL(UPLOAD_URL)
            conn = url.openConnection() as HttpURLConnection
            conn.doInput = true
            conn.doOutput = true
            conn.useCaches = false
            conn.requestMethod = "POST"
            conn.setRequestProperty("Connection", "Keep-Alive")
            conn.setRequestProperty("ENCTYPE", "multipart/form-data")
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=$boundary")
            conn.setRequestProperty("myFile", file)
            dos = DataOutputStream(conn.outputStream)
            dos.writeBytes(twoHyphens + boundary + lineEnd)
            dos.writeBytes("Content-Disposition: form-data; name=\"myFile\";filename=\"$file\"$lineEnd")
            dos.writeBytes(lineEnd)
            bytesAvailable = fileInputStream.available()
            Timber.tag("UPLOAD").d( "Initial .available : $bytesAvailable")
            bufferSize = Math.min(bytesAvailable, maxBufferSize)
            buffer = ByteArray(bufferSize)
            bytesRead = fileInputStream.read(buffer, 0, bufferSize)
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize)
                bytesAvailable = fileInputStream.available()
                bufferSize = Math.min(bytesAvailable, maxBufferSize)
                bytesRead = fileInputStream.read(buffer, 0, bufferSize)
            }
            dos.writeBytes(lineEnd)
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd)
            serverResponseCode = conn.responseCode
            fileInputStream.close()
            dos.flush()
            dos.close()
        } catch (ex: MalformedURLException) {
            ex.printStackTrace()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return if (serverResponseCode == 200) {
            val sb = StringBuilder()
            try {
                val rd = BufferedReader(InputStreamReader(conn?.getInputStream()))
                var line: String?
                while (rd.readLine().also { line = it } != null) {
                    sb.append(line)
                }
                rd.close()
            } catch (ioex: IOException) {
            }
            sb.toString()
        } else {
            "Could not upload"
        }
    }

    companion object {
        const val UPLOAD_URL = "http://sima-bf.net:8091/restapi/uploadVideo"
    }
}