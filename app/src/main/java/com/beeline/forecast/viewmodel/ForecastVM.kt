package com.beeline.forecast.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagedList
import com.beeline.forecast.data.api.Response
import com.beeline.forecast.data.repo.ApiRepo
import com.beeline.forecast.data.repo.FetchRepo
import com.beeline.forecast.data.repo.RoomRepo
import com.beeline.forecast.data.room.entity.LocalWeather
import kotlinx.coroutines.launch

class ForecastVM constructor(private val apiRepo: ApiRepo,private  val roomRepo: RoomRepo,private val fetchRepo: FetchRepo): ViewModel() {

    private val _state = MutableLiveData<Response<Boolean>>()
    val state: LiveData<Response<Boolean>> get() = _state

    var recordedCities:  LiveData<PagedList<LocalWeather>> = MutableLiveData()

    fun createDb(){
        viewModelScope.launch {
            _state.value = Response.Loading
            val db =fetchRepo.createDb()
            _state.value = db

            if (db is Response.Error){
                db.exception.printStackTrace()
            }
        }
    }

    fun createCity(id : Int?){
        viewModelScope.launch {
            _state.value = Response.Loading
            val db =fetchRepo.createCity(id)
            _state.value = db

            if (db is Response.Error){
                db.exception.printStackTrace()
            }
        }
    }

    fun getRecordedCities() {
        recordedCities = roomRepo.getAll()
    }

    fun updateCities() {
        viewModelScope.launch {
            _state.value = Response.Loading
            val db =fetchRepo.updateCities()
            _state.value = db

            if (db is Response.Error){
                db.exception.printStackTrace()
            }

        }
    }

    fun deleteCity(localWeather: LocalWeather) {
        viewModelScope.launch {
            _state.value = Response.Loading
            roomRepo.deleteCity(localWeather)
            runCatching {
             }.onSuccess {
                _state.value = Response.Success(true)
             }.onFailure {
                it.printStackTrace()
             }
        }
    }

}