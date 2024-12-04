import com.google.gson.annotations.SerializedName

data class UdpData(
    val firstname: String,
    val age: String,
    val temperature: Int?,
    val humidity: Int?,
    val priority: String?,
    val preferedTemperature: String?,
    val preferedHumidity: String?,
   @SerializedName("ImageUrl") val imageUrl:String
)
