package stanevich.elizaveta.hoteldisplay

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiHostelInterface {
    @GET("0777.json")
//    fun getHotels(name: String) : Call<List<Hotel>>
    fun getHotels() : Call<List<Hotel>>

    companion object {

        var URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

        fun create() : ApiHostelInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
            return retrofit.create(ApiHostelInterface::class.java)

        }
    }
}