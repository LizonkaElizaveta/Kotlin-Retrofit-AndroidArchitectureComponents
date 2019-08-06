package stanevich.elizaveta.hoteldisplay

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.io.IOException
import java.nio.charset.Charset


interface ApiHostelInterface {
    @GET("iMofas/ios-android-test/master/{name}")
//    fun getHotels(name: String) : Call<List<Hotel>>
    fun getHotels(@Path("name") name: String): Call<Set<Hotel>>

    companion object {

        var URL = "https://raw.githubusercontent.com/"

        fun create(): ApiHostelInterface {

            val client = OkHttpClient().newBuilder()
                .addInterceptor(ObjToArray())
                .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .connectTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .build()

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .client(client)
                .build()
            return retrofit.create(ApiHostelInterface::class.java)

        }
    }

    class ObjToArray : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()

            val response = chain.proceed(request)
            val body = response.body()!!
            var bodyText = body.source().readString(Charset.defaultCharset())

            if (bodyText.startsWith("{")) {
                bodyText = "[$bodyText]"
            }

            return response.newBuilder()
                .body(ResponseBody.create(body.contentType(), bodyText))
                .build()
        }
    }
}