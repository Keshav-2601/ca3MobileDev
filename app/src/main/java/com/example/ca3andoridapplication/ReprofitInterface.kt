import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("Homepage/")
    fun getUdpData(): Call<List<UdpData>>
}