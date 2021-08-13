package com.example.pawsome.viewmodel

import androidx.lifecycle.*
import com.example.pawsome.data.DogRepository
import com.example.pawsome.data.DogTable
import kotlinx.coroutines.launch

class DogViewModel(private val repository : DogRepository) : ViewModel() {

    val allDogs : LiveData<List<DogTable>> = repository.allDogs.asLiveData()

    fun insert(dogTable : DogTable) = viewModelScope.launch {
        repository.insert(dogTable)
    }

    fun deleteDog(id : Int) = viewModelScope.launch {
        repository.deleteDog(id)
    }
}

class WordViewModelFactory(private val repository : DogRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DogViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return DogViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}