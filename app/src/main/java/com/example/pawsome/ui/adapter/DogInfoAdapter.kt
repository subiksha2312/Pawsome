package com.example.pawsome.ui.adapter


import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pawsome.R
import com.example.pawsome.data.DogBreed
import com.example.pawsome.data.DogTable
import com.example.pawsome.viewmodel.DogViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dog_info.view.*

class DogFaveAdapter (private val dogFaves: List<DogTable>) : RecyclerView.Adapter<DogFaveAdapter.ViewHolder> () {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogFaveAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_info, parent, false)
        return DogFaveAdapter.ViewHolder(view)

    }

    override fun onBindViewHolder(holder: DogFaveAdapter.ViewHolder, position: Int) {
        holder.bindFaveInfo(dogFaves[position])
    }

    override fun getItemCount(): Int = dogFaves.size

    class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

        val faveGone = view.findViewById<FloatingActionButton>(R.id.favetouch)

        init {
            faveGone.visibility = View.INVISIBLE
        }

        fun bindFaveInfo(dogFave: DogTable) {

            if (dogFave.name.isNotEmpty()) {
                itemView.dogName.text = dogFave.name ?: "Not Available"
                itemView.temperament.text = dogFave.temperament ?: "Not Available"
                itemView.lifespan.text = dogFave.lifeSpan ?: "Not Available"
                itemView.breedFor.text = dogFave.dogWeight ?: "Not Available"
                itemView.dHeight.text = dogFave.dogHeight ?: "Not Available"
              //  Picasso.get().load(dogFave.image.url).into(itemView.dogImage)
            } else {
                itemView.dogName.text = "Breed Info Not Available"
              //  Picasso.get().load(dogInfo.image.url).into(itemView.dogImage)
            }


        }


    }


}



class DogInfoAdapter( private val dogInfoList: List<DogBreed>,
                     private val onItemClicked: (position: Int) -> Unit,
                     private val dogViewModel: DogViewModel) : RecyclerView.Adapter<DogInfoAdapter.ViewHolder> () {




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_info, parent, false)
        return ViewHolder(view, onItemClicked, dogInfoList, dogViewModel)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindDogInfo(dogInfoList[position])
    }

    override fun getItemCount(): Int = dogInfoList.size



    class ViewHolder(val view: View, private val onItemClicked: (position: Int) -> Unit, val dogInfoList: List<DogBreed>, val dogViewModel: DogViewModel) :
        RecyclerView.ViewHolder(view), View.OnClickListener {

        private var dogFave = view.findViewById<FloatingActionButton>(R.id.favetouch)

        fun saveFaves() {
            val dogTable = DogTable(
                dogInfoList[adapterPosition].id ?: 0L,
                dogInfoList[adapterPosition].name ?: "",
                dogInfoList[adapterPosition].weight.metric ?: "",
                dogInfoList[adapterPosition].height.metric ?: "",
                dogInfoList[adapterPosition].breedGroup ?: "",
                dogInfoList[adapterPosition].bredFor ?: "",
                dogInfoList[adapterPosition].lifeSpan ?: "",
                dogInfoList[adapterPosition].temperament ?:""
            )

            dogViewModel.insert(dogTable)
        }

        init {

            itemView.setOnClickListener(this)
            dogFave.setOnClickListener() {
                Log.d("fave", "faved")
                dogFave.visibility = View.INVISIBLE
                saveFaves()

            }
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


