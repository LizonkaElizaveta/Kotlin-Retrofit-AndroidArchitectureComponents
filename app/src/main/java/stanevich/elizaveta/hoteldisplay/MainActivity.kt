package stanevich.elizaveta.hoteldisplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(), OnHotelSelected {
    override fun onHotelSelected(hotel: Hotel) {
        val detailsFragment =
            HotelDescriptionFragment.newInstance(hotel)
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
