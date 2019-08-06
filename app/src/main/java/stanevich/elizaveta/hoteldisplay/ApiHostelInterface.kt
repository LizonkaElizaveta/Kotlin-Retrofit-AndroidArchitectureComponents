package stanevich.elizaveta.hoteldisplay

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiHostelInterface {
    @GET("iMofas/ios-android-test/master/{name}")
//    fun getHotels(name: String) : Call<List<Hotel>>
    fun getHotels(@Path("name") name: String) : Call<List<Hotel>>

    companion object {

        var URL = "https://raw.githubusercontent.com/"

        fun create() : ApiHostelInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
            return retrofit.create(ApiHostelInterface::class.java)

        }
    }
}