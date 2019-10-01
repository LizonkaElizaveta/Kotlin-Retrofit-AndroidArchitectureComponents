package stanevich.elizaveta.hoteldisplay.overview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import stanevich.elizaveta.hoteldisplay.network.HotelApi
import stanevich.elizaveta.hoteldisplay.network.HotelApiFilter
import stanevich.elizaveta.hoteldisplay.network.HotelsProperty


enum class HotelApiStatus { LOADING, DONE, ERROR }


class OverviewViewModel : ViewModel() {

    private val _navigateToSelectedProperty = MutableLiveData<HotelsProperty>()

    val navigateToSelectedProperty: LiveData<HotelsProperty>
        get() = _navigateToSelectedProperty

    private val _properties = MutableLiveData<List<HotelsProperty>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<HotelsProperty>>
        get() = _properties

    private val _status = MutableLiveData<HotelApiStatus>()

    val status: LiveData<HotelApiStatus>
        get() = _status

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun displayPropertyDetails(hotelProperty: HotelsProperty) {
        _navigateToSelectedProperty.value = hotelProperty
    }

    fun displayPropertyDetailsComplete() {
        _navigateToSelectedProperty.value = null
    }

    private var viewModelJob = Job()

    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)

    init {
        getHotelProperties(HotelApiFilter.SHOW_ALL)
    }


    private fun getHotelProperties(filter: HotelApiFilter) {
        coroutineScope.launch {
            val listResult: List<HotelsProperty>
            val getPropertiesDeferred = HotelApi.retrofitService.getHotels(filter.value)
            try {
                _status.value = HotelApiStatus.LOADING
                listResult = when (filter) {
                    HotelApiFilter.SORT_BY_DISTANCE -> {
                        getPropertiesDeferred.await().sortedByDescending { it.stars }
                    }
                    HotelApiFilter.SORT_BY_NAME -> {
                        getPropertiesDeferred.await().sortedBy { it.name }
                    }
                    HotelApiFilter.SHOW_ALL -> {
                        getPropertiesDeferred.await()
                    }
                }
                _status.value = HotelApiStatus.DONE
                _properties.value = listResult
            } catch (e: Exception) {
                _status.value = HotelApiStatus.ERROR
                _properties.value = ArrayList()
            }
        }
    }

    fun updateFilter(filter: HotelApiFilter) {
        getHotelProperties(filter)
    }

}