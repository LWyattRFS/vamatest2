package com.vama.example

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vama.example.network.ImageList
import com.vama.example.network.Network
import com.vama.example.network.VamaApi
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {
    var imagesLiveData = MutableLiveData<ImageList>()
    val imagesApi = Network.getInstance().create(VamaApi::class.java)
    var imagesList = ImageList(mutableListOf())
    var cursorIndex = 10
    private val limit = 10

    fun getImages() {
        viewModelScope.launch {
            try {
                cursorIndex = 10
                val result = imagesApi.getImages(cursorIndex, limit)
                imagesList = result
                imagesLiveData.postValue(result)
            } catch (throwable: Throwable) {
                //ignore
            }
        }
    }

    fun nextPage() {
        viewModelScope.launch {
            cursorIndex += limit
            try {
                val result = imagesApi.getImages(cursorIndex, limit)
                imagesList.imageURLs.addAll(result.imageURLs)
                imagesLiveData.postValue(imagesList)
            } catch (throwable: Throwable) {
                cursorIndex -= limit
            }
        }
    }
}