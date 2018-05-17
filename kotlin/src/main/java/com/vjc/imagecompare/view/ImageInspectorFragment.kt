package com.vjc.imagecompare.view

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.provider.MediaStore
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import com.vjc.imagecompare.R
import com.vjc.imagecompare.extensions.center
import com.vjc.imagecompare.model.MetaData
import java.io.File
import java.util.*
import java.util.concurrent.atomic.AtomicReference

class ImageInspectorFragment constructor() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(com.vjc.imagecompare.R.layout.fragment_image_inspector, container, false)


        view?.let {
            val random = Random();

            it.setBackgroundColor(this.context.getColor(R.color.theme))

            _imageInspectorView = it.findViewById<ImageInspectorView>(R.id.imageInspectorView)

            val addButton = it.findViewById<ImageButton>(R.id.addButton)
            addButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    addButtonAction()
                }
            })

            val cameraButton = it.findViewById<ImageButton>(R.id.cameraButton)
            cameraButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    cameraButtonAction()
                }
            })

            val detailsButton = it.findViewById<ImageButton>(R.id.detailsButton)
            detailsButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    detailsButtonAction();
                }
            })



        }


        return view
    }

    fun addButtonAction() {
        _imageDetailsView?.close()

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        this.startActivityForResult(intent, com.vjc.imagecompare.view._REQUEST_CODE_IMAGE_GALLERY)
    }

    fun cameraButtonAction() {
        _imageDetailsView?.close()
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val dir = context.getExternalFilesDir(null)
        _tempFile = File.createTempFile("temp", ".jpg", dir)
        val uri = Uri.fromFile(_tempFile)

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)

        val vmPolicyBuilder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(vmPolicyBuilder.build())

        this.startActivityForResult(intent, com.vjc.imagecompare.view._REQUEST_CODE_IMAGE_CAMERA)
    }

    fun detailsButtonAction() {
        println("detailsButtonAction")

        if (_imageDetailsView != null && _imageDetailsView!!.isShown) {
            this.hideImageDetailsView()
        } else {
            this.showImageDetailsView()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when(requestCode) {
            _REQUEST_CODE_IMAGE_GALLERY -> {

                var tempUri: Uri? = null
                var metaData: List<MetaData>? = null
                data?.let {
                    tempUri = it.data
                }
                this.setImageUri(tempUri, metaData)

            }
            _REQUEST_CODE_IMAGE_CAMERA -> {
                var tempUri: Uri? = null
                var metaData: List<MetaData>? = null
                val exifInterfaceRef = AtomicReference<ExifInterface>()
                _tempFile?.let {
                    var uri = Uri.fromFile(_tempFile)
                    tempUri = uri


//                    if (!it.delete()) {
//
//                    }
                }
                this.setImageUri(tempUri, metaData)
            }
        }
    }




    private var _imageInspectorView: ImageInspectorView? = null
    private var _imageDetailsView: ImageDetailsView? = null

    private fun showImageDetailsView() {

        if (this.view !is ViewGroup) {
            return
        }
        val viewGroup = this.view as ViewGroup

        if (_imageDetailsView == null) {
            _imageDetailsView = ImageDetailsView(this.context)
        }

        if (_imageInspectorView != null) {
            _imageDetailsView!!.showIn(_imageInspectorView!!)
        }

    }

    private fun hideImageDetailsView() {
        _imageDetailsView?.close()
    }

    private fun setImageUri(uri: Uri?, metaDataList: List<MetaData>?) {
        _imageInspectorView?.setBitmapUri(uri)
        _imageDetailsView?.metaData = metaDataList
    }
}

private const val _REQUEST_CODE_IMAGE_GALLERY = 1
private const val _REQUEST_CODE_IMAGE_CAMERA = 2

/** to save camera capture */
private var _tempFile: File? = null