package stanevich.elizaveta.hoteldisplay

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import stanevich.elizaveta.hoteldisplay.detail.DetailFragment
import stanevich.elizaveta.hoteldisplay.network.HotelProperty


class MainActivity : AppCompatActivity(), OnHotelSelected {

    override fun onHotelSelected(hotelProperty: HotelProperty) {
        val detailsFragment =
            DetailFragment.newInstance(hotelProperty)
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> toast()
        }
        return true
    }

    private fun toast(length: Int = Toast.LENGTH_SHORT) {
        Toast.makeText(this, R.string.distance, length).show()
    }
}
