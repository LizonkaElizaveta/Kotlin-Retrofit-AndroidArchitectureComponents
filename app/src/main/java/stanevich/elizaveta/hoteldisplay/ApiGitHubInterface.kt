package stanevich.elizaveta.hoteldisplay

import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiGitHubInterface {

    @GET("repos/iMofas/ios-android-test/contents")
//    @Query("name") name:String
    fun getDescriptionFile(): Call<List<DescriptionFile>>

    companion object {

        var URL = "https://api.github.com/"

        fun create(): ApiGitHubInterface {
            val client = OkHttpClient().newBuilder()
                .readTimeout(100, java.util.concurrent.TimeUnit.SECONDS)
                .connectTimeout(100, java.util.concurrent.TimeUnit.SECONDS).build()
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .client(client)
                .build()
            return retrofit.create(ApiGitHubInterface::class.java)

        }
    }
}