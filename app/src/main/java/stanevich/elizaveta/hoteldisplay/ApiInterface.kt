package stanevich.elizaveta.hoteldisplay

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
    @GET("0777.json")
    fun getMovies() : Call<List<Hotel>>

    companion object {

        var BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

        fun create() : ApiInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiInterface::class.java)

        }
    }
}