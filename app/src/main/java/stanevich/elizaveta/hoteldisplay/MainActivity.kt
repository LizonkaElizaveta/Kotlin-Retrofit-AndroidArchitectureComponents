package stanevich.elizaveta.hoteldisplay

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken





class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var recyclerAdapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter


        val apiGitHubInterface = ApiGitHubInterface.create().getDescriptionFile()
        var descriptionList : List<String> = listOf()



        val apiInterface = ApiHostelInterface.create().getHotels("0777.json")

        apiGitHubInterface.enqueue(object : Callback<List<DescriptionFile>> {
            override fun onResponse(call: Call<List<DescriptionFile>>?, response: Response<List<DescriptionFile>>?) {
                if (response?.body() != null) {
                    val files = response?.body()
                    descriptionList = files?.filter { it.name.endsWith(".json")}!!.map{ files -> files.name }
                }
                Log.d("mLog", descriptionList.toString())
            }

            override fun onFailure(call: Call<List<DescriptionFile>>?, t: Throwable?) {
            }
        })

        apiInterface.enqueue(object : Callback<List<Hotel>> {
            override fun onResponse(call: Call<List<Hotel>>?, response: Response<List<Hotel>>?) {

                if (response?.body() != null)
                    recyclerAdapter.setHotelListItems(response.body()!!)
            }

            override fun onFailure(call: Call<List<Hotel>>?, t: Throwable?) {

            }
        })
    }
}
