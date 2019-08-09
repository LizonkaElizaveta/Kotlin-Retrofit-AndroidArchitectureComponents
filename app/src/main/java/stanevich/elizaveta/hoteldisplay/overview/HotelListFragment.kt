package stanevich.elizaveta.hoteldisplay.overview

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import stanevich.elizaveta.hoteldisplay.R
import stanevich.elizaveta.hoteldisplay.RecyclerViewAdapter
import stanevich.elizaveta.hoteldisplay.detail.OnHotelSelected
import stanevich.elizaveta.hoteldisplay.network.ApiHotelInterface
import stanevich.elizaveta.hoteldisplay.network.HotelProperty

class HotelListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var progress: ContentLoadingProgressBar
    lateinit var recyclerViewAdapter: RecyclerViewAdapter
    var hotelListName = "0655.json"

    companion object {
        fun newInstance(): HotelListFragment {
            return HotelListFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_hotel_list, container, false)
        progress = view.findViewById(R.id.progressBar)
        progress.show()
        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerViewAdapter = RecyclerViewAdapter()
        recyclerView.adapter = recyclerViewAdapter
        val activity = activity as Context
        recyclerView.layoutManager = LinearLayoutManager(activity)

        val apiInterface = ApiHotelInterface.create()

        apiInterface.getHotels(hotelListName).enqueue(object : Callback<List<HotelProperty>> {
            override fun onResponse(call: Call<List<HotelProperty>>?, response: Response<List<HotelProperty>>?) {

                if (response?.body() != null) {
                    progress.hide()
                    recyclerViewAdapter.addHotelListItems(response.body()!!)
                    recyclerViewAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<List<HotelProperty>>?, t: Throwable?) {
                progress.hide()
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
        return view
    }


}

