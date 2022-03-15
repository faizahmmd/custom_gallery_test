package com.example.test2

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import me.rail.customgallery.main.PermissionActivity
import me.rail.customgallery.models.Media


class MainActivity : AppCompatActivity() {
    private lateinit var fetchImageOnly: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fetchImageOnly = findViewById(R.id.button1)
        fetchImageOnly.setOnClickListener {
            intent = Intent(this@MainActivity, PermissionActivity()::class.java).apply {
                putExtra("addVideoGallery", false)
                putExtra("selectionLimitOn", true)
                putExtra("selectionLimitCount", 5)
                putExtra("multipleSelection", true)
            }
            startActivityForResult(intent, 0)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 0){
            if(resultCode == Activity.RESULT_OK){
                var data: ArrayList<Media> = data?.getSerializableExtra("Data") as ArrayList<Media>
                println("PPPPPPPPPPPPPPPPPPPPPPPPPPPP${data[0].uri}")
            }
        }
    }
}