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

class OverviewViewModel : ViewModel() {

    private val _navigateToSelectedProperty = MutableLiveData<HotelsProperty>()

    val navigateToSelectedProperty: LiveData<HotelsProperty>
        get() = _navigateToSelectedProperty

    private val _properties = MutableLiveData<List<HotelsProperty>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val properties: LiveData<List<HotelsProperty>>
        get() = _properties


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
            val getPropertiesDeferred = HotelApi.retrofitService.getHotels(filter.value)
            if (filter == HotelApiFilter.SORT_BY_DISTANCE) {
                _properties.value = getPropertiesDeferred.await().sortedByDescending { it.distance }
            } else {
                _properties.value = getPropertiesDeferred.await()
            }
        }
    }

    fun updateFilter(filter: HotelApiFilter) {
        getHotelProperties(filter)
    }

}