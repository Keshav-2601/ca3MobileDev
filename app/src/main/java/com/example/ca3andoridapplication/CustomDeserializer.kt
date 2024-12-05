

import UdpData


import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import java.lang.reflect.Type


class CustomDeserializer : JsonDeserializer<List<UdpData>> {
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type,
        context: JsonDeserializationContext
    ): List<UdpData> {
        return json.asJsonObject.getAsJsonArray("data")?.map {
            context.deserialize(it, UdpData::class.java)
        } ?: emptyList()
    }
}
