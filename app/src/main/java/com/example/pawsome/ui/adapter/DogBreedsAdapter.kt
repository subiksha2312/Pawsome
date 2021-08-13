package com.example.pawsome.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.pawsome.R
import com.example.pawsome.data.DogBreedInfo
import kotlinx.android.synthetic.main.dog_info.view.*


class DogBreedsAdapter(private val dogBreedInfoList: List<DogBreedInfo>)
                       : RecyclerView.Adapter<DogBreedsAdapter.BreedViewHolder> () {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreedViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.dogbreeds, parent, false)
        return BreedViewHolder(view)

    }

    override fun onBindViewHolder(holder: BreedViewHolder, position: Int) {
        holder.bindBreedInfo(dogBreedInfoList[position],this.itemCount)
    }

    override fun getItemCount(): Int =dogBreedInfoList.size

    class BreedViewHolder(val view: View): RecyclerView.ViewHolder(view) {

        fun bindBreedInfo(dogBreedInfo: DogBreedInfo, itemCount: Int) {

            if (itemCount > 0) {
                itemView.dogName.text = dogBreedInfo.name ?: "Not Available"
                itemView.temperament.text = dogBreedInfo.temperament ?: "Not Available"
                itemView.lifespan.text = dogBreedInfo.life_span ?: "Not Available"
                itemView.breedFor.text = dogBreedInfo.weight.metric ?: "Not Available"
                itemView.dHeight.text = dogBreedInfo.height.metric ?: "Not Available"
            } else {
                itemView.dogName.text = "No Breeds Available"
            }


        }

    }




}









