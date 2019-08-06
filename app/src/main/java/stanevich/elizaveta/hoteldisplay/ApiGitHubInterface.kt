package stanevich.elizaveta.hoteldisplay

import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiGitHubInterface {

    @GET("repos/iMofas/ios-android-test/contents")
//    @Query("name") name:String
    fun getDescriptionFile() : Call<List<DescriptionFile>>

    companion object {

        var URL = "https://api.github.com/"

        fun create() : ApiGitHubInterface {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(URL)
                .build()
            return retrofit.create(ApiGitHubInterface::class.java)

        }
    }
}