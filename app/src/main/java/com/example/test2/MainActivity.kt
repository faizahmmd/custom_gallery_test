package com.example.test2

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import me.rail.customgallery.main.PermissionActivity
import me.rail.customgallery.models.Image
import me.rail.customgallery.models.Media
import me.rail.customgallery.models.Video


class MainActivity : AppCompatActivity() {
    private lateinit var openGalleryButton: Button
    private lateinit var shareButton: Button
    private lateinit var imageView: ImageView
    private var arraySelectedItems: ArrayList<Media> = ArrayList()
    private var arraySelectedItemsUri: ArrayList<Uri> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        openGalleryButton = findViewById(R.id.button1)
        shareButton = findViewById(R.id.button)
        imageView = findViewById(R.id.imageView)
        openGalleryButton.setOnClickListener {
            intent = Intent(this@MainActivity, PermissionActivity()::class.java).apply {
                putExtra("addVideoGallery", true)
                putExtra("addImageGallery", true)
                putExtra("selectionLimitOn", true)
                putExtra("selectionLimitCount", 5)
                putExtra("multipleSelection", true)
            }
            startActivityForResult(intent, 0)
        }
        shareButton.setOnClickListener {
            if (arraySelectedItems.isNotEmpty()) {
                setUriReceivedIntoArray()
                shareSelectedItems(arraySelectedItemsUri)
            } else {
                Toast.makeText(
                    this@MainActivity,
                    "select from gallery to share",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 0) {
            if (resultCode == Activity.RESULT_OK) {
                var data: ArrayList<Media> = data?.getSerializableExtra("Data") as ArrayList<Media>
                if (data.isNotEmpty() && data[0] is Image) {
                    imageView.setImageURI(data[0].uri)
                } else if (data.isNotEmpty() && data[0] is Video) {
                    var video: Video = data[0] as Video
                    imageView.setImageBitmap(video.thumbnail)
                }
                setReceivedDataHere(data)
                println("<----------$data--------->")
                println("<----------${data.size}--------->")
            }
        }
    }

    private fun setReceivedDataHere(arrayData: ArrayList<Media>) {
        arraySelectedItems.clear()
        arraySelectedItems.addAll(arrayData)
    }

    private fun setUriReceivedIntoArray() {
        arraySelectedItemsUri.clear()
        for (i in 0 until arraySelectedItems.size) {
            arraySelectedItems[i].uri?.let { it1 -> arraySelectedItemsUri.add(it1) }
        }
    }

    private fun shareSelectedItems(uriArray: ArrayList<Uri>) {
        val intent = Intent(Intent.ACTION_SEND_MULTIPLE)
        intent.putExtra(Intent.EXTRA_STREAM, uriArray)
        intent.type = "image/*"
        intent.type = "video/*"
        startActivity(Intent.createChooser(intent, "Share Via"))
    }
}