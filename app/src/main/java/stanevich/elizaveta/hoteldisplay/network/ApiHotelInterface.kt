package stanevich.elizaveta.hoteldisplay.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(HotelApi.BASE_URL)
    .build()

interface ApiHotelInterface {
    @GET("{name}.json")
    suspend fun getHotels(@Path("name") name: String): List<HotelProperty>

    @GET("{id}.json")
    suspend fun getHotelById(@Path("id") id: String): HotelProperty

}

object HotelApi {
    const val BASE_URL = "https://raw.githubusercontent.com/iMofas/ios-android-test/master/"
    const val NAME_URL = "0777"
    val retrofitService: ApiHotelInterface by lazy {
        retrofit.create(ApiHotelInterface::class.java)
    }
}
