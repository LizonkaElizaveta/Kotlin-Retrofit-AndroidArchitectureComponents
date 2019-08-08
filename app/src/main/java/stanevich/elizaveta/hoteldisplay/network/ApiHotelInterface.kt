package stanevich.elizaveta.hoteldisplay.network

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiHotelInterface {
    @GET("{name}")
    fun getHotels(@Path("name") name: String): Call<List<HotelProperty>>

    @GET("{id}.json")
    fun getHotelById(@Path("id") id: String): Call<HotelProperty>

    companion object {
        private const val URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"
        fun create(): ApiHotelInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
            return retrofit.create(ApiHotelInterface::class.java)
        }
    }

}