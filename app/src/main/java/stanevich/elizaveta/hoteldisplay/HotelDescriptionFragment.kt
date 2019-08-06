package stanevich.elizaveta.hoteldisplay

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

class HotelDescriptionFragment(var hotel: Hotel) : Fragment() {


    companion object {
        fun newInstance(hotel: Hotel): HotelDescriptionFragment {
            return HotelDescriptionFragment(hotel)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotel_description, container, false)
        val apiInterface = ApiHostelInterface.create()
        val nameOfHotel = view.findViewById<TextView>(R.id.nameOfHotel)
        val imageHotel = view.findViewById<ImageView>(R.id.imageHotel)
        val addressOfHotel = view.findViewById<TextView>(R.id.addressOfHotel)
        val distance = view.findViewById<TextView>(R.id.distance)
        val starsOfHotel = view.findViewById<RatingBar>(R.id.starsOfHotel)
        val suitesAvailability = view.findViewById<TextView>(R.id.suites_availability)

        apiInterface.getHotelById(hotel.id).enqueue(object : Callback<Hotel> {
            override fun onResponse(call: Call<Hotel>?, response: Response<Hotel>?) {

                if (response?.body() != null) {
                    val hotel = response.body()!!
                    nameOfHotel.text = hotel.name
                    Glide.with(context)
                        .load("https://raw.githubusercontent.com/iMofas/ios-android-test/master/" + hotel.image)
                        .into(imageHotel)
                    addressOfHotel.text = hotel.address
                    distance.text = hotel.distance.toString()
                    starsOfHotel.rating = hotel.stars
                    suitesAvailability.text = hotel.suites_availability
                }
            }

            override fun onFailure(call: Call<Hotel>?, t: Throwable?) {
                Log.e("Fail", t?.message)
            }
        })
        return view
    }

}