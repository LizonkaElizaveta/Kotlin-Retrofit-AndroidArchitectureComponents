package stanevich.elizaveta.hoteldisplay.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.fragment_hotel_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import stanevich.elizaveta.hoteldisplay.R
import stanevich.elizaveta.hoteldisplay.databinding.FragmentHotelDescriptionBinding
import stanevich.elizaveta.hoteldisplay.network.HotelApi
import stanevich.elizaveta.hoteldisplay.network.HotelProperty

class HotelDescriptionFragment(val hotelProperty: HotelProperty) : Fragment() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

        fun newInstance(hotelProperty: HotelProperty): HotelDescriptionFragment {
            return HotelDescriptionFragment(hotelProperty)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHotelDescriptionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_description, container, false)
        binding.progressBar.show()
        coroutineScope.launch {
            val getPropertiesDeferred = HotelApi.retrofitService.getHotelById(hotelProperty.id)
            try {
                val hotel = getPropertiesDeferred.await()
                bindResponse(binding, hotel)
                setViewsVisible(binding)
                progressBar.hide()

            } catch (e: Exception) {
                Toast.makeText(context, "Couldn't fetch data", Toast.LENGTH_SHORT).show()
                Log.e("Fail", e.message!!)
                progressBar.hide()
            }
        }
        return binding.root
    }

    private fun setViewsVisible(binding: FragmentHotelDescriptionBinding) {
        binding.apply {
            tvDistance.visibility = View.VISIBLE
            tvRooms.visibility = View.VISIBLE
            additionalInf.visibility = View.VISIBLE
            starsOfHotel.visibility = View.VISIBLE
        }
    }

    private fun bindResponse(binding: FragmentHotelDescriptionBinding, hotel: HotelProperty) {
        binding.apply {
            nameOfHotel.text = hotel.name
            Glide.with(context!!)
                .load(BASE_URL + hotel.image)
                .error(Glide.with(imageHotel).load(R.drawable.ic_broken_image))
                .apply(RequestOptions().override(1000))
                .into(imageHotel)
            addressOfHotel.text = hotel.address
            distance.text = hotel.distance.toString()
            starsOfHotel.rating = hotel.stars
            suitesAvailability.text = hotel.suites_availability
        }
    }
}