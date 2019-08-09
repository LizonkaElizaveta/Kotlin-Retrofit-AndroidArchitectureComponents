package stanevich.elizaveta.hoteldisplay.overview

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stanevich.elizaveta.hoteldisplay.R
import stanevich.elizaveta.hoteldisplay.RecyclerViewAdapter
import stanevich.elizaveta.hoteldisplay.databinding.FragmentHotelListBinding
import stanevich.elizaveta.hoteldisplay.detail.OnHotelSelected
import stanevich.elizaveta.hoteldisplay.network.ApiHotelInterface
import stanevich.elizaveta.hoteldisplay.network.HotelProperty

class HotelListFragment : Fragment() {
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    companion object {
        private const val hotelListName = "0777.json"

        fun newInstance(): HotelListFragment {
            return HotelListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val binding: FragmentHotelListBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_hotel_list, container, false)
        setHasOptionsMenu(true)
        recyclerViewAdapter = RecyclerViewAdapter()
        binding.apply {
            progressBar.show()
            recyclerview.adapter = recyclerViewAdapter
            recyclerview.layoutManager = LinearLayoutManager(activity)
        }

        val apiInterface = ApiHotelInterface.create()

        apiInterface.getHotels(hotelListName).enqueue(object : Callback<List<HotelProperty>> {
            override fun onResponse(call: Call<List<HotelProperty>>?, response: Response<List<HotelProperty>>?) {
                if (response?.body() != null) {
                    recyclerViewAdapter.addHotelListItems(response.body()!!)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
                binding.progressBar.hide()
            }
            override fun onFailure(call: Call<List<HotelProperty>>?, t: Throwable?) {
                binding.progressBar.hide()
                Toast.makeText(context, "Couldn't fetch data", Toast.LENGTH_SHORT).show()
                Log.e("Fail", t?.message)
            }
        })
        if (context is OnHotelSelected) {
            recyclerViewAdapter.listener = context as OnHotelSelected
        } else {
            throw ClassCastException(
                context.toString() + " must implement OnHotelSelected."
            )
        }
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var asc = true
        when (item.itemId) {
            R.id.action_asc -> asc = true
            R.id.action_desc -> asc = false
        }
        recyclerViewAdapter.sort(asc)
        return true
    }
}

