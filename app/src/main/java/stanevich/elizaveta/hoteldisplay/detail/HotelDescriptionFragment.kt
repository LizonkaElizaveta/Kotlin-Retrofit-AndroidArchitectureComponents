package stanevich.elizaveta.hoteldisplay.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stanevich.elizaveta.hoteldisplay.R
import stanevich.elizaveta.hoteldisplay.network.ApiHotelInterface
import stanevich.elizaveta.hoteldisplay.network.HotelProperty

class HotelDescriptionFragment(var hotelProperty: HotelProperty) : Fragment() {

    companion object {
        private const val BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

        fun newInstance(hotelProperty: HotelProperty): HotelDescriptionFragment {
            return HotelDescriptionFragment(hotelProperty)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_hotel_description, container, false)
        val apiInterface = ApiHotelInterface.create()
        val nameOfHotel = view.findViewById<TextView>(R.id.nameOfHotel)
        val imageHotel = view.findViewById<ImageView>(R.id.imageHotel)
        val addressOfHotel = view.findViewById<TextView>(R.id.addressOfHotel)
        val distance = view.findViewById<TextView>(R.id.distance)
        val starsOfHotel = view.findViewById<RatingBar>(R.id.starsOfHotel)
        val suitesAvailability = view.findViewById<TextView>(R.id.suites_availability)

        apiInterface.getHotelById(hotelProperty.id).enqueue(object : Callback<HotelProperty> {
            override fun onResponse(call: Call<HotelProperty>?, response: Response<HotelProperty>?) {

                if (response?.body() != null) {
                    val hotel = response.body()!!
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

            override fun onFailure(call: Call<HotelProperty>?, t: Throwable?) {
                Log.e("Fail", t?.message)
            }
        })
        return view
    }

}