package network

import com.squareup.moshi.JsonClass
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private const val BASE_URL = "https://spacedim.async-agency.com/api/"



private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()


interface LoginApiService {

    @GET("user/find/{name}")
    fun getPropertyConnection(@Query("name") name: String?):
            Call<PlayerProperty>

    @Headers("Content-Type: application/json")
    @POST("user/register")
    fun postPropertyRegistration(@Body MoshiObject : PlayerObjetName):
            Call<PlayerProperty>




}

@JsonClass(generateAdapter = true)
data class PlayerObjetName(var name : String?)

object LoginApi {
    val retrofitService : LoginApiService by lazy {
        retrofit.create(LoginApiService::class.java) }
}
