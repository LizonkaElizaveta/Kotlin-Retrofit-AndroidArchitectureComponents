package stanevich.elizaveta.hoteldisplay.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

const val BASE_URL = "https://raw.githubusercontent.com/LizonkaElizaveta/HotelDisplay/master/data/"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface HotelsApiService {

    @GET("{name}")
    fun getHotels(@Path("name") name: String): Deferred<List<HotelsProperty>>
}

object HotelApi {
    const val NAME_URL = "0777.json"
    val retrofitService: HotelsApiService by lazy { retrofit.create(HotelsApiService::class.java) }
}