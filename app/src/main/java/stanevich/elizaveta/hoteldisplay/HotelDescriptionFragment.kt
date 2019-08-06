package stanevich.elizaveta.hoteldisplay

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class HotelDescriptionFragment(hotel: Hotel) : Fragment() {


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

        return view
    }

}