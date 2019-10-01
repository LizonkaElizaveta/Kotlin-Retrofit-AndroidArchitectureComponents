package stanevich.elizaveta.hoteldisplay.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

const val BASE_URL = "https://raw.githubusercontent.com/LizonkaElizaveta/HotelDisplay/master/data/"

enum class HotelApiFilter(val value: String) {
    SORT_BY_DISTANCE("distance"), SHOW_ALL("all"), SORT_BY_NAME(
        "name"
    )
}


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface HotelsApiService {

    @GET("0777.json")
    fun getHotels(@Query("filter") type: String): Deferred<List<HotelsProperty>>
}

object HotelApi {
    val retrofitService: HotelsApiService by lazy { retrofit.create(HotelsApiService::class.java) }
}