package com.niksaen.galery.ui
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.niksaen.galery.adapters.ImageAdapter
import com.niksaen.galery.databinding.ActivityMainBinding
import java.io.File


class MainActivity : AppCompatActivity() {
    private lateinit var ui:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ui = ActivityMainBinding.inflate(layoutInflater)
        setContentView(ui.root)
        getPermission()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == 1){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                showImages()
            }else{
                Snackbar
                    .make(ui.root,"Разрешение на чтение не дано",Snackbar.LENGTH_INDEFINITE)
                    .setAction("Предоставить"){ getPermission() }
                    .show()
            }
        }
    }
    private fun getPermission(){
        if (checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                requestPermissions(arrayOf(Manifest.permission.READ_MEDIA_IMAGES), 1)
            else
                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            showImages()
        }
    }
    private fun showImages(){
        val path = Environment.getExternalStorageDirectory().absolutePath+ "/DCIM/Camera/"
        ui.list.setHasFixedSize(true)
        ui.list.setItemViewCacheSize(20)
        val imagePathList = getListImagePath(path);
        val adapter = ImageAdapter(this,imagePathList)
        adapter.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            run {
                val intent = Intent(this, ViewImageActivity::class.java)
                intent.putExtra("imagePath",imagePathList[position])
                startActivity(intent)
            }
        }
        ui.list.adapter = adapter
    }
    private fun getListImagePath(path:String):ArrayList<String>{
        val listImagePath = ArrayList<String>()
        val file = File(path)
        if(file.listFiles() != null){
            for(imagePath in file.listFiles()!!){
                listImagePath.add(imagePath.absolutePath)
            }
        }
        return listImagePath
    }
}