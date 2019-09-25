package stanevich.elizaveta.hoteldisplay.description

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import stanevich.elizaveta.hoteldisplay.network.HotelsProperty

class DescriptionViewModel(hotelsProperty: HotelsProperty, app: Application) :
    AndroidViewModel(app) {

    private val _selectedProperty = MutableLiveData<HotelsProperty>()

    val selectedProperty: LiveData<HotelsProperty>
        get() = _selectedProperty

    init {
        _selectedProperty.value = hotelsProperty
    }

    val displayPropertyStarsOfHotel = Transformations.map(selectedProperty) {
        app.applicationContext.getString(
            it.stars.toInt()
        )
    }

//    val displayPropertyStars = Transformations.map(selectedProperty) {
//        app.applicationContext.getString(
//            R.string.display_type,
//            app.applicationContext.getString(
//                when (it.isRental) {
//                    true -> R.string.type_rent
//                    false -> R.string.type_sale
//                }
//            )
//        )
}
