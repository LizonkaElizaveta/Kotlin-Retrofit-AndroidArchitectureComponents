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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import stanevich.elizaveta.hoteldisplay.R
import stanevich.elizaveta.hoteldisplay.databinding.FragmentHotelDescriptionBinding
import stanevich.elizaveta.hoteldisplay.network.HotelApi
import stanevich.elizaveta.hoteldisplay.network.HotelProperty


class HotelDescriptionFragment : Fragment() {
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)
    private lateinit var binding: FragmentHotelDescriptionBinding
    private lateinit var hotelProperty: HotelProperty

    companion object {
        private const val BUNDLE_HOTEL = "classname.hotel"

        fun newInstance(hotelProperty: HotelProperty): HotelDescriptionFragment {
            val hotelDescriptionFragment = HotelDescriptionFragment()
            val args = Bundle()
            args.putParcelable(BUNDLE_HOTEL, hotelProperty)
            hotelDescriptionFragment.arguments = args
            return hotelDescriptionFragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_description, container, false)

        setHotelFromArgs()

        requestHotelDescription(binding)
        return binding.root
    }

    private fun setHotelFromArgs() {
        val hotel = arguments?.getParcelable<HotelProperty>(BUNDLE_HOTEL)
        if (hotel != null) {
            hotelProperty = hotel
        } else {
            Log.e(this.javaClass.simpleName, "no hotel was provided")
            fragmentManager?.popBackStack()
        }
    }

    private fun requestHotelDescription(binding: FragmentHotelDescriptionBinding) {
        binding.progressBar.show()
        coroutineScope.launch {
            try {
                val hotel = HotelApi.retrofitService.getHotelById(hotelProperty.id)
                bindResponse(binding, hotel)
                binding.textDescriptionLayout.visibility = View.VISIBLE
            } catch (e: Exception) {
                Toast.makeText(context, "Couldn't fetch data", Toast.LENGTH_SHORT).show()
                Log.e("Fail", e.message!!)
            } finally {
                binding.progressBar.hide()
            }
        }
    }


    private fun bindResponse(binding: FragmentHotelDescriptionBinding, hotel: HotelProperty) {
        binding.apply {
            nameOfHotel.text = hotel.name
            Glide.with(context!!)
                .load(HotelApi.BASE_URL + hotel.image)
                .apply(
                    RequestOptions().override(1000)
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.ic_broken_image)
                )
                .into(imageHotel)
            addressOfHotel.text = hotel.address
            distance.text = hotel.distance.toString()
            starsOfHotel.rating = hotel.stars
            suitesAvailability.text = hotel.suites_availability
        }
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)

        var requestFromServer = false
        if (savedInstanceState != null) {
            val hotel = savedInstanceState.getParcelable<HotelProperty>(BUNDLE_HOTEL)
            if (hotel != null) {
                bindResponse(binding, hotel)
                binding.progressBar.hide()
            } else {
                requestFromServer = true
            }
        } else {
            requestFromServer = true
        }

        if (requestFromServer) {
            requestHotelDescription(binding)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.apply {
            putParcelable(BUNDLE_HOTEL, hotelProperty)
        }
    }
}