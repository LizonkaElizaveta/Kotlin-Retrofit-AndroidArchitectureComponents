package stanevich.elizaveta.hoteldisplay

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HotelListFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    var hotelListName = "0777.json"

    companion object {
        fun newInstance(): HotelListFragment {
            return HotelListFragment()
        }
    }

    //3
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_hotel_list, container, false)

        recyclerView = view.findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter()
        recyclerView.adapter = recyclerAdapter
        val activity = activity as Context
        recyclerView.layoutManager = LinearLayoutManager(activity)


        val apiInterface = ApiHostelInterface.create()

        apiInterface.getHotels(hotelListName).enqueue(object : Callback<Set<Hotel>> {
            override fun onResponse(call: Call<Set<Hotel>>?, response: Response<Set<Hotel>>?) {

                if (response?.body() != null) {
                    recyclerAdapter.setHotelListItems(response.body()!!)
                    recyclerAdapter.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Set<Hotel>>?, t: Throwable?) {
                Log.e("Fail", t?.message)
            }
        })
        return view
    }
}