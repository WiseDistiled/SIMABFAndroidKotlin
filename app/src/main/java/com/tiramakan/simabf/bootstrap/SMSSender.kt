package com.tiramakan.simabf.bootstrap

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.telephony.SmsManager

import com.tiramakan.simabf.bootstrap.util.MyPreferences

import java.util.ArrayList

/**
 * Created by tiramakan on 21/02/2016.
 */
class SMSSender internal constructor(internal var context: Context, internal var myPreferences: MyPreferences) {
    internal fun sendMessage(message: String) {
        val numeroCourt = myPreferences.numeroCourt
        val uri = Uri.parse("smsto:$numeroCourt")
        val sendIntent = Intent(Intent.ACTION_SENDTO, uri)
        sendIntent.putExtra("sms_body", message)
        sendIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        //     sendIntent.setType("vnd.android-dir/mms-sms");
        context.startActivity(sendIntent)

    }

    internal fun sendSMS(message: String) {
        val smsManager = SmsManager.getDefault()
        val numeroCourt = myPreferences.numeroCourt
        smsManager.sendTextMessage(numeroCourt, null, message, null, null)


    }

    internal fun sendMessages(messages: ArrayList<String>) {
        for (message in messages) {
            sendMessage(message)
        }

    }
}
