package com.tiramakan.simabf.ui.view.edits

/**
 * Created by tiramakan on 17/01/2016.
 */


import android.R.attr
import android.app.Activity
import androidx.loader.content.CursorLoader;
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.databinding.DataBindingUtil
import com.tiramakan.simabf.R
import com.tiramakan.simabf.bootstrap.BootstrapApplication
import com.tiramakan.simabf.core.constants.Constants.Extra.LAST_SAVED_PRICE_TAG
import com.tiramakan.simabf.core.uploads.UploadVideo
import com.tiramakan.simabf.ui.view.baseClasses.BaseFragment
import com.tiramakan.simabf.ui.view.utils.Binding.BindingComponent
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.parceler.Parcels
import javax.inject.Inject


/**
 * Created by tiramakan on 17/01/2016.
 */
//Wire the layout to the step
@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class TelechargerVideoUI : BaseFragment() {
    internal lateinit var pathVideo: TextView
    internal lateinit var resultFromServer: TextView
    lateinit var progressBar: ProgressBar
    val scope = MainScope()
    internal lateinit var buttonChoose: AppCompatButton
    internal lateinit var buttonUpload: AppCompatButton
    private var selectedPath: String = ""

    private val SELECT_VIDEO = 3
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BootstrapApplication.component().inject(this)
        setHasOptionsMenu(true)

    }

    private fun uploadVideo() = scope.launch {
           // ... here we can use suspending functions or coroutine builders with other dispatchers
            progressBar.setVisibility(View.VISIBLE);
            val u = UploadVideo()
            val s: String = u.uploadVideo(selectedPath).toString()
            progressBar.setVisibility(View.GONE);
            pathVideo.setText(Html.fromHtml("<b>Téléchargé à <a href='$s'>$s</a></b>"))
            resultFromServer.setMovementMethod(LinkMovementMethod.getInstance())

    }
    fun getPath(uri: Uri): String {
        var cursor: Cursor? = context?.getContentResolver()?.query(uri, null, null, null, null)
        cursor?.moveToFirst()
        var document_id: String? = cursor?.getString(0)
        document_id = document_id?.substring(document_id.lastIndexOf(":") + 1)
        cursor?.close()
        cursor = context?.getContentResolver()?.query(
                MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", arrayOf(document_id), null)
        cursor?.moveToFirst()
        val path: String = cursor?.getString(cursor.getColumnIndex(MediaStore.Video.Media.DATA)).toString()
        cursor?.close()
        return path
    }
    private fun chooseVideo() {
        val intent = Intent()
        intent.type = "video/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Sélectionnez une vidéo "), SELECT_VIDEO)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
                if (requestCode === SELECT_VIDEO) {
                    val selectedImageUri: Uri? = data?.getData()
                    selectedPath = selectedImageUri?.let { getPath(it) }.toString()
                    pathVideo.setText(selectedPath)
                }
        }
    }
    //Set your layout here
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        val view = inflater.inflate(R.layout.upload_video, container, false)

        pathVideo = view.findViewById<View>(R.id.pathVideo) as TextView
        resultFromServer = view.findViewById<View>(R.id.resultFromServer) as TextView


        buttonChoose = view.findViewById<View>(R.id.buttonChoose) as AppCompatButton
        buttonUpload = view.findViewById<View>(R.id.buttonUpload) as AppCompatButton
        buttonChoose.setOnClickListener { chooseVideo() }
        buttonUpload.setOnClickListener { uploadVideo() }

        val layout: RelativeLayout =  view.findViewById<View>(R.id.display) as RelativeLayout
        progressBar = ProgressBar(context, null, attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        layout.addView(progressBar, params)
        progressBar.setVisibility(View.GONE);
        setHasOptionsMenu(true)
        return layout
    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return false
    }
    override fun onDestroy() {
        scope.cancel("")
        super.onDestroy()
    }
    override fun doBack(): Boolean {

        return true

    }


}
