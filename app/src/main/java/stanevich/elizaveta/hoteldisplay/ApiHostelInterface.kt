package stanevich.elizaveta.hoteldisplay

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


interface ApiHostelInterface {
    @GET("{name}")
    fun getHotels(@Path("name") name: String): Call<Set<Hotel>>

    companion object {

        var URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

        fun create(): ApiHostelInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
            return retrofit.create(ApiHostelInterface::class.java)
        }
    }

}