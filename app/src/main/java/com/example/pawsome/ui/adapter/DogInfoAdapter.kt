package com.example.pawsome.ui.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawsome.R
import com.example.pawsome.data.DogBreed
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.dog_info.view.*


class DogInfoAdapter(private val dogInfoList: List<DogBreed>,
                     private val onItemClicked: (position: Int) -> Unit) : RecyclerView.Adapter<DogInfoAdapter.ViewHolder> () {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dog_info, parent, false)
        return ViewHolder(view, onItemClicked)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindDogInfo(dogInfoList[position])
    }

    override fun getItemCount(): Int =dogInfoList.size


    class ViewHolder(val view: View, private val onItemClicked: (position: Int) -> Unit): RecyclerView.ViewHolder(view),View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
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

