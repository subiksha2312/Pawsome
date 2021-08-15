package com.example.pawsome.ui.adapter


import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.Image
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.pawsome.R
import com.example.pawsome.data.DogBreed
import com.example.pawsome.data.DogTable
import com.example.pawsome.data.ImageBitmapString
import com.example.pawsome.ui.activities.DogImageDetail
import com.example.pawsome.ui.activities.FavouritesSection
import com.example.pawsome.viewmodel.DogViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_dog_image_detail.*
import kotlinx.android.synthetic.main.dog_info.view.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URI
import java.net.URL
import android.widget.Toast
import androidx.core.content.ContextCompat

import androidx.core.content.FileProvider
import java.lang.Exception
import androidx.core.content.ContextCompat.startActivity





class DogFaveAdapter (private val dogFaves: List<DogTable>,
                      private val dogViewModel: DogViewModel) : RecyclerView.Adapter<DogFaveAdapter.ViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogFaveAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_info, parent, false)
        return DogFaveAdapter.ViewHolder(view, dogFaves, dogViewModel)

    }

    override fun onBindViewHolder(holder: DogFaveAdapter.ViewHolder, position: Int) {
        holder.bindFaveInfo(dogFaves[position])

    }

    override fun getItemCount(): Int = dogFaves.size


    @SuppressLint("ClickableViewAccessibility")
    class ViewHolder(val view: View, private val dogFaves: List<DogTable>, private val dogViewModel: DogViewModel) :
        RecyclerView.ViewHolder(view) {

        private lateinit var dogCardView: CardView
        lateinit var gstDetector: GestureDetector
        var x1: Float = 0f
        var x2: Float = 0f
        var y1: Float = 0f
        var y2: Float = 0f
        var currAdapterPosition = 0


        val faveGone = view.findViewById<FloatingActionButton>(R.id.favetouch)
        val shareGone = view.findViewById<FloatingActionButton>(R.id.shareintent)

        init {
            faveGone.visibility = View.INVISIBLE
            shareGone.visibility = View.INVISIBLE
            var gstDetector = GestureDetector(itemView.context, GestureListener())
            itemView.setOnTouchListener {
                v,event ->
                gstDetector.onTouchEvent(event)
                true
            }
        }

        fun bindFaveInfo(dogFave: DogTable) {
            if (dogFave.name.isNotEmpty()) {
                val bitmapString = ImageBitmapString()
                val bitmap: Bitmap? = bitmapString.StringToBitMap(dogFave.image)
                /*
                val fileName: String = "image$adapterPosition.png"
                val dir: String = Environment.getExternalStorageDirectory().getAbsolutePath()
                Log.d("convert1","$dir")

                val path: String = "/mnt/sdcard/$fileName"
                val f: File = File(path)
                val os = FileOutputStream(f)
                if (bitmap != null) {
                    bitmap.compress(Bitmap.CompressFormat.PNG,100,os)
                }
                os.flush()
                os.close()
                val uri: Uri = Uri.fromFile(f)

                */
                itemView.dogName.text = dogFave.name ?: "Not Available"
                itemView.temperament.text = dogFave.temperament ?: "Not Available"
                itemView.lifespan.text = dogFave.lifeSpan ?: "Not Available"
                itemView.breedFor.text = dogFave.dogWeight ?: "Not Available"
                itemView.dHeight.text = dogFave.dogHeight ?: "Not Available"
                if (bitmap != null) {
                    itemView.dogImage.setImageBitmap(bitmap)
                }
                //Picasso.get().load(uri).into(itemView.dogImage)
                //  Picasso.get().load(dogFave.image.url).into(itemView.dogImage)
            } else {
                itemView.dogName.text = "Breed Info Not Available"
                //  Picasso.get().load(dogInfo.image.url).into(itemView.dogImage)
            }


        }

        fun deleteFavDog(position: Int) {
            var id:Long = dogFaves[position].id
            dogViewModel.deleteDog(id)
        }

        inner class GestureListener: GestureDetector.SimpleOnGestureListener() {
            private var x1 =0f
            private var x2 = 0f
            private var y1 = 0f
            private var y2 =  0f
            private val SWIPE_THRESHOLD = 100




            override fun onDown(e: MotionEvent?): Boolean {
                return true
            }

            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                var result = false
                try {
                    val diffY = e2.y- e1.y
                    val diffX = e2.x - e1.x

                    if(Math.abs(diffX) > SWIPE_THRESHOLD || Math.abs(diffY) > SWIPE_THRESHOLD){
                        result = true
                        deleteFavDog(adapterPosition)
                    }

                } catch (exception: Exception) {
                    exception.printStackTrace()
                }
                return result
            }

        }
    }





}

class DogInfoAdapter( private val dogInfoList: List<DogBreed>,
                     private val onItemClicked: (position: Int) -> Unit,
                     private val dogViewModel: DogViewModel,
                     private val parentActivity: AppCompatActivity) : RecyclerView.Adapter<DogInfoAdapter.ViewHolder> () {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_info, parent, false)
        return ViewHolder(view, onItemClicked, dogInfoList, dogViewModel, parentActivity)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindDogInfo(dogInfoList[position])
    }

    override fun getItemCount(): Int = dogInfoList.size



    class ViewHolder(val view: View,
                     private val onItemClicked: (position: Int) -> Unit,
                     val dogInfoList: List<DogBreed>,
                     val dogViewModel: DogViewModel,
                     val parentActivity: AppCompatActivity) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        private var dogFave = view.findViewById<FloatingActionButton>(R.id.favetouch)
        private var shareButton = view.findViewById<FloatingActionButton>(R.id.shareintent)

        fun convertUrltoBitmap(imageUrl: URL) : Bitmap {
            if(imageUrl != null) {
                val iStream : InputStream = imageUrl.openConnection().getInputStream()
                //val iStream : InputStream? = parentActivity.contentResolver.openInputStream(imageUri)
                val bitmap: Bitmap = BitmapFactory.decodeStream(iStream)
                return bitmap
            }
            else {
                val imageUnavailable: Bitmap =
                    BitmapFactory.decodeResource(parentActivity.applicationContext.resources, R.drawable.imageunavailable)
                return imageUnavailable
            }
        }



        fun convertUritoBitmapString(imageUrl: URL) : String? {
            if( imageUrl != null) {

                //val iStream : InputStream? = parentActivity.contentResolver.openInputStream(imageUri)
                val bitmap: Bitmap = convertUrltoBitmap(imageUrl)
                val iBitmap = ImageBitmapString()
                val imageSource: String? = iBitmap.BitMapToString(bitmap)
                return imageSource
            }
            else {
                return null
            }
        }
        fun saveFaves() {
            //val imageUri: Uri = Uri.parse(dogInfoList[adapterPosition].image.url)
            val imageUrl = URL(dogInfoList[adapterPosition].image.url)
            val imageString: String? = convertUritoBitmapString(imageUrl)

            val dogTable = DogTable(
                dogInfoList[adapterPosition].id ?: 0L,
                dogInfoList[adapterPosition].name ?: "",
                dogInfoList[adapterPosition].weight.metric ?: "",
                dogInfoList[adapterPosition].height.metric ?: "",
                dogInfoList[adapterPosition].breedGroup ?: "",
                dogInfoList[adapterPosition].bredFor ?: "",
                dogInfoList[adapterPosition].lifeSpan ?: "",
                dogInfoList[adapterPosition].temperament ?:"",
                imageString ?:""
            )

            dogViewModel.insert(dogTable)

        }

        init {

            itemView.setOnClickListener(this)
            shareButton.setOnClickListener() {
                val imageUrl = URL(dogInfoList[adapterPosition].image.url)
                val bitmap:Bitmap = convertUrltoBitmap(imageUrl)
                shareImage(bitmap)
            }

            dogFave.setOnClickListener() {
                Log.d("fave", "faved")
                dogFave.visibility = View.INVISIBLE
                saveFaves()


            }
        }

        private fun getImageToShare(bitmap: Bitmap): Uri? {
            val imagefolder: File = File(parentActivity.cacheDir, "images")
            var uri: Uri? = null
            val path = MediaStore.Images.Media.insertImage(parentActivity.applicationContext.contentResolver,bitmap,"title",null)

            /*
            try {
                imagefolder.mkdirs()
                val file = File(imagefolder, "shared_image.png")
                val outputStream = FileOutputStream(file)
                val packageName: String = parentActivity.packageName
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.flush()
                outputStream.close()
                 parentActivity.applicationContext.grantUriPermission(packageName,uri,
                    Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                uri = FileProvider.getUriForFile(parentActivity.applicationContext, "com.example.pawsome.fileprovider", file)

            } catch (e: Exception) {
                Toast.makeText(parentActivity.applicationContext, "" + e.message, Toast.LENGTH_LONG).show()
            }

             */
            return Uri.parse(path)
        }

        fun shareImage(bitmap: Bitmap) {
            val uri = getImageToShare(bitmap)
            val intent = Intent(Intent.ACTION_SEND)
            intent.putExtra(Intent.EXTRA_STREAM, uri)
            intent.putExtra(Intent.EXTRA_TEXT, "Sharing Image")
            intent.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")
            intent.type = "image/png"
            parentActivity.startActivity(Intent.createChooser(intent, "Share Via"))


         }


        fun bindDogInfo(dogInfo: DogBreed) {

                if (dogInfo.name.isNotEmpty()) {
                    itemView.dogName.text = dogInfo.name ?: "Not Available"
                    itemView.temperament.text = dogInfo.temperament ?: "Not Available"
                    itemView.lifespan.text = dogInfo.lifeSpan ?: "Not Available"
                    itemView.breedFor.text = dogInfo.weight.metric ?: "Not Available"
                    itemView.dHeight.text = dogInfo.height.metric ?: "Not Available"
                    Picasso.get().load(dogInfo.image.url).into(itemView.dogImage)
                } else {
                    itemView.dogName.text = "Breed Info Not Available"
                    Picasso.get().load(dogInfo.image.url).into(itemView.dogImage)
                }


            }

            override fun onClick(v: View?) {
                val position = adapterPosition
                onItemClicked(position)
            }
        }
    }


