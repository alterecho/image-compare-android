package com.vjc.imagecompare.view

import android.app.Fragment
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.provider.MediaStore
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.vjc.imagecompare.R
import com.vjc.imagecompare.extensions.center
import com.vjc.imagecompare.model.MetaData
import java.util.*

class ImageInspectorFragment constructor() : Fragment() {
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater?.inflate(com.vjc.imagecompare.R.layout.fragment_image_inspector, container, false)


        view?.let {
            val random = Random();
            it.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)))

            _imageInspectorView = it.findViewById<ImageInspectorView>(R.id.imageInspectorView)

            val addButton = it.findViewById<Button>(R.id.addButton)
            addButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    addButtonAction()
                }
            })

            val cameraButton = it.findViewById<Button>(R.id.cameraButton)
            cameraButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    cameraButtonAction()
                }
            })

            val detailsButton = it.findViewById<Button>(R.id.detailsButton)
            detailsButton.setOnClickListener(object : View.OnClickListener {
                override fun onClick(v: View?) {
                    detailsButtonAction();
                }
            })



        }


        return view
    }

    fun addButtonAction() {
        println("addButtonAction");
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"

        this.startActivityForResult(intent, com.vjc.imagecompare.view._REQUEST_CODE_IMAGE_GALLERY)
    }

    fun cameraButtonAction() {
        print("cameraButtonAction")
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)



        this.startActivityForResult(intent, com.vjc.imagecompare.view._REEQUEST_CODE_IMAGE_CAMERA)
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

        when(requestCode) {
            _REQUEST_CODE_IMAGE_GALLERY -> {
                _imageInspectorView?.bitmapUri = data?.data
                var metaData: List<MetaData>? = null
                data?.let {
                    val array = MetaData.metaDataArray(it.data, context)
                    metaData = array.asList()
                }
                _imageDetailsView?.metaData = metaData
            }

            _REEQUEST_CODE_IMAGE_CAMERA -> {

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
        _imageDetailsView?.hide()
    }
}

private const val _REQUEST_CODE_IMAGE_GALLERY = 1
private const val _REEQUEST_CODE_IMAGE_CAMERA = 2