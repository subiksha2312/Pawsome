package com.example.pawsome.ui.activities

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.pawsome.R
import com.example.pawsome.api.DogImageRetriever
import com.example.pawsome.data.DogImageUploadResponse
import kotlinx.android.synthetic.main.activity_upload_section.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream

class UploadSection : AppCompatActivity() {
    private var mImageUploadResponse: DogImageUploadResponse? = null
    private lateinit var chooseButton: Button
    private val _REQUESTCODE = 100
    private var dogRetriever = DogImageRetriever()

    private val callback = object : Callback<DogImageUploadResponse> {
        override fun onResponse(
            call: Call<DogImageUploadResponse>,
            response: Response<DogImageUploadResponse>
        ) {
            response?.isSuccessful.let{
                mImageUploadResponse =  response?.body() ?: null
                Log.d("upload1","${mImageUploadResponse.toString()}")
                Toast.makeText(applicationContext,"Image Uploaded",Toast.LENGTH_LONG).show()
            }
        }

        override fun onFailure(call: Call<DogImageUploadResponse>, t: Throwable) {
            Log.d("upload2","upload failed ${t?.message}")
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_section)

        chooseButton = findViewById(R.id.button)


        chooseButton.setOnClickListener{
            val intent = Intent(Intent.ACTION_PICK)
            intent.type="image/*"
            startActivityForResult(intent,_REQUESTCODE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(resultCode == Activity.RESULT_OK && requestCode==_REQUESTCODE) {
            Log.d("upload4","${data?.data}")
            val fileUri: Uri = data?.data as Uri
            Log.d("upload3","${fileUri.authority}")
            val imageView = findViewById(R.id.imageView) as ImageView
            imageView.setImageURI(fileUri)



            if(isNetworkConnected()) {
                val file: File =  convertUriToFile(fileUri)
                dogRetriever.uploadDogImage(file,fileUri,applicationContext,callback)

            }
            else {
                AlertDialog.Builder(this).setTitle("No internet connection")
                    .setMessage("check your connectivity")
                    .setPositiveButton(android.R.string.ok) { _, _ -> }
                    .setIcon(android.R.drawable.ic_dialog_alert).show()
            }
        }
    }

    fun convertUriToFile(uri: Uri) : File {
        val file: File = File(cacheDir, "Pictures")
        val iStream: InputStream? = contentResolver.openInputStream(uri)
        val oStream: OutputStream = FileOutputStream(file)
        val buf = ByteArray(1024)
        var len: Int = 0
        while (iStream?.read(buf).also {
                if (it != null) {
                    len = it
                }
            }!! > 0) {
            oStream.write(buf, 0, len)
        }
        oStream.close()
        iStream?.close()
        Log.d("upload8","${file.name},${file.path}")
        return file
    }


    fun isNetworkConnected() :Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork
        } else {
            TODO("VERSION.SDK_INT < M")
        }

        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


}