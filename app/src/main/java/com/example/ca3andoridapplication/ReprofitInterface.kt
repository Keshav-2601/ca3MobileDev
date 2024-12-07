import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.HTTP
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @GET("Homepage/")
    fun getUdpData(): Call<List<UdpData>>
    @HTTP(method = "DELETE", path = "admin/delete", hasBody = true)
    suspend fun getdelete(@Body id: Map<String, String>): Response<Void>

    @HTTP(method = "POST", path = "admin/patient", hasBody = true)
    suspend fun adddata(@Body id: Map<String, String>):Response<String>
}