package stanevich.elizaveta.hoteldisplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import stanevich.elizaveta.hoteldisplay.detail.HotelDescriptionFragment
import stanevich.elizaveta.hoteldisplay.detail.OnHotelSelected
import stanevich.elizaveta.hoteldisplay.network.HotelProperty
import stanevich.elizaveta.hoteldisplay.overview.HotelListFragment


class MainActivity : AppCompatActivity(), OnHotelSelected {

    override fun onHotelSelected(hotelProperty: HotelProperty) {
        val detailsFragment =
            HotelDescriptionFragment.newInstance(hotelProperty)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.root_layout, detailsFragment, "hotelDetails")
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.root_layout, HotelListFragment.newInstance(), "hotelList")
                .commit()
        }


    }
}
