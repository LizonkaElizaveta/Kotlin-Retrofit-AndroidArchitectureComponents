package stanevich.elizaveta.hoteldisplay.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

private const val BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()

interface ApiHotelInterface {
    @GET("{name}")
    fun getHotels(@Path("name") name: String): Deferred<List<HotelProperty>>

    @GET("{id}.json")
    fun getHotelById(@Path("id") id: String): Deferred<HotelProperty>

}

object HotelApi {
    val NAME_URL = "0777.json"
    val retrofitService: ApiHotelInterface by lazy {
        retrofit.create(ApiHotelInterface::class.java)
    }
}
