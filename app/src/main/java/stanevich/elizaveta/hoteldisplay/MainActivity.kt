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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerview)
        recyclerAdapter = RecyclerAdapter(this)
        recyclerview.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = recyclerAdapter


        val apiGitHubInterface = ApiGitHubInterface.create().getDescriptionFile()
        var descriptionList: List<String> = listOf()
        val apiInterface = ApiHostelInterface.create()

        apiGitHubInterface.enqueue(object : Callback<List<DescriptionFile>> {
            override fun onResponse(call: Call<List<DescriptionFile>>?, response: Response<List<DescriptionFile>>?) {
                if (response?.body() != null) {
                    val jsonFiles = response.body()

                    descriptionList =
                        jsonFiles?.filter { it.name.endsWith(".json")}!!.map { it.name }
                }

                Log.d("mLog", descriptionList.toString())
//                descriptionList.forEach {

                descriptionList.forEach {

                    apiInterface.getHotels(it).enqueue(object : Callback<Set<Hotel>> {
                        override fun onResponse(call: Call<Set<Hotel>>?, response: Response<Set<Hotel>>?) {

                            if (response?.body() != null) {
                                recyclerAdapter.setHotelListItems(response.body()!!)
                                recyclerAdapter.notifyDataSetChanged()


                                Log.d("mLog", it)
                                Log.d("mLog ", response.body()!!.toString())
                            }

                        }


                        override fun onFailure(call: Call<Set<Hotel>>?, t: Throwable?) {

                        }
                    })
                }
            }


            override fun onFailure(call: Call<List<DescriptionFile>>?, t: Throwable?) {
            }
        })


    }

    private fun addNewItem() {


    }
}
