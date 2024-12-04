package com.example.ca3andoridapplication

import UdpData
//import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GetUDPDataScreen()
        }
    }
}

@Composable
fun GetUDPDataScreen() {
    var udpDataList by remember { mutableStateOf<List<UdpData>>(emptyList()) }
    var errorMessage by remember { mutableStateOf("") }

    // Fetch data with Retrofit
    LaunchedEffect(Unit) {
        val call = ReprofitInstance.apiService.getUdpData()
        call.enqueue(object : Callback<List<UdpData>> {
            override fun onResponse(call: Call<List<UdpData>>, response: Response<List<UdpData>>) {
                if (response.isSuccessful) {
                    udpDataList = response.body() ?: emptyList()
                    udpDataList.forEach {
                        Log.d("UdpDataDebug", "Received Data: $it")
                    }
                } else {
                    errorMessage = "Error: ${response.code()}"
                }
            }

            override fun onFailure(call: Call<List<UdpData>>, t: Throwable) {
                errorMessage = "Error: ${t.message}"
            }
        })
    }
    if (errorMessage.isNotEmpty()) {
        Text(
            text = errorMessage,
            modifier = Modifier.padding(16.dp)
        )
    } else {
        // Pass the list to UdpDataList composable
        UdpDataList(udpDataList = udpDataList)
    }
}

@Composable
fun UdpDataList(udpDataList: List<UdpData>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(udpDataList) { data ->
            UdpDataCard(data)
        }
    }
}

@Composable

fun UdpDataCard(data: UdpData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        shape = RoundedCornerShape(16.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .background(Color.White, RoundedCornerShape(12.dp))
        ) {

            Image(
                painter = rememberAsyncImagePainter(data.imageUrl),
                contentDescription = "",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color(0xFF1976D2), CircleShape),
                contentScale = ContentScale.Inside
            )
            Spacer(modifier = Modifier.width(16.dp))

            // Text Information Section
            Column {
                Text(
                    text = "Name: ${data.firstname}",
                    style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Color.Black)
                )
               Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Age: ${data.age}",
                    style = TextStyle(fontSize = 16.sp, color = Color.DarkGray)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Priority: ${data.priority}",
                    style = TextStyle(fontSize = 16.sp, color = Color.DarkGray)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Temperature: ${data.temperature}°C",
                    style = TextStyle(fontSize = 16.sp, color = Color.Black)
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Humidity: ${data.humidity}%",
                    style = TextStyle(fontSize = 16.sp, color = Color.Black)
                )
            }
        }
    }
}

