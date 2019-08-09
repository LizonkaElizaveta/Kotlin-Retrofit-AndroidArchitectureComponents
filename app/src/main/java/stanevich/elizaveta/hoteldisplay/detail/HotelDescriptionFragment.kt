package stanevich.elizaveta.hoteldisplay.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stanevich.elizaveta.hoteldisplay.R
import stanevich.elizaveta.hoteldisplay.databinding.FragmentHotelDescriptionBinding
import stanevich.elizaveta.hoteldisplay.network.ApiHotelInterface
import stanevich.elizaveta.hoteldisplay.network.HotelProperty

class HotelDescriptionFragment(val hotelProperty: HotelProperty) : Fragment() {
    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

        fun newInstance(hotelProperty: HotelProperty): HotelDescriptionFragment {
            return HotelDescriptionFragment(hotelProperty)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHotelDescriptionBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_description, container, false)
        val apiInterface = ApiHotelInterface.create()
        apiInterface.getHotelById(hotelProperty.id).enqueue(object : Callback<HotelProperty> {
            override fun onResponse(call: Call<HotelProperty>?, response: Response<HotelProperty>?) {
                response?.let { res ->
                    val hotel = res.body()
                    hotel?.let {
                        bindResponse(binding, it)
                    }
                }
            }

            override fun onFailure(call: Call<HotelProperty>?, t: Throwable?) {
                Log.e("Fail", t?.message!!)
            }
        })
        return binding.root
    }

    private fun bindResponse(binding: FragmentHotelDescriptionBinding, hotel: HotelProperty) {
        binding.apply {
            nameOfHotel.text = hotel.name
            Glide.with(context!!)
                .load(BASE_URL + hotel.image)
                .into(imageHotel)
            addressOfHotel.text = hotel.address
            distance.text = hotel.distance.toString()
            starsOfHotel.rating = hotel.stars
            suitesAvailability.text = hotel.suites_availability
        }
    }
}