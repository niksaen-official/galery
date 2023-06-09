package com.niksaen.galery.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.niksaen.galery.databinding.ActivityViewImageBinding
import com.squareup.picasso.Picasso
import java.io.File

class ViewImageActivity : AppCompatActivity() {
    var scale = 1f
    lateinit var ui:ActivityViewImageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityViewImageBinding.inflate(layoutInflater)
        Picasso.get().load(File(intent.getStringExtra("imagePath")!!)).into(ui.image)
        ui.zoomPlus.setOnClickListener {
            scale*=2
            imageZoomed()
        }
        ui.zoomMinus.setOnClickListener {
            scale /= 2
            imageZoomed()
        }
        setContentView(ui.root)
    }
    private fun imageZoomed(){
        ui.image.scaleX = scale
        ui.image.scaleY = scale
    }
}