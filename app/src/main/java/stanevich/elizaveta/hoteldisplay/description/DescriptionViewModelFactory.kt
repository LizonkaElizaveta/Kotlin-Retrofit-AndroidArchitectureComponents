package stanevich.elizaveta.hoteldisplay.description

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import stanevich.elizaveta.hoteldisplay.network.HotelsProperty

class DescriptionViewModelFactory(
    private val hotelsProperty: HotelsProperty,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DescriptionViewModel::class.java)) {
            return DescriptionViewModel(hotelsProperty, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
