import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("Homepage/")
    fun getUdpData(): Call<List<UdpData>>
}