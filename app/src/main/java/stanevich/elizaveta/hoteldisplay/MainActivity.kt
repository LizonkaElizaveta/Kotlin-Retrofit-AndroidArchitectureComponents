package stanevich.elizaveta.hoteldisplay

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter
    var hotelListName = "0777.json"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter()
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter

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
    }
}
