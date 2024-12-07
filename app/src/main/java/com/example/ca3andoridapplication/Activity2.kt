package com.example.ca3andoridapplication

import AddDataScreen
import UdpData
import android.content.Context
//import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.semantics.SemanticsActions.OnClick
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.processNextEventInCurrentThread
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Activity2 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainScreen()
        }
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "getUdpDataScreen") {
        composable("getUdpDataScreen") { GetUDPDataScreen(navController) }
        composable("addDataScreen") { AddDataScreen(navController) }
    }
}

    @Composable
    fun GetUDPDataScreen(navController: NavController) {
        var udpDataList by remember { mutableStateOf<List<UdpData>>(emptyList()) }
        var errorMessage by remember { mutableStateOf("") }

        // Fetch data with Retrofit

        fun Onclick():Unit{

        }
        fun lauchdata() {
            val call = ReprofitInstance.apiService.getUdpData()
            call.enqueue(object : Callback<List<UdpData>> {
                override fun onResponse(
                    call: Call<List<UdpData>>,
                    response: Response<List<UdpData>>
                ) {
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
        LaunchedEffect(Unit) {
            lauchdata()
        }
        if (errorMessage.isNotEmpty()) {
            Text(
                text = errorMessage,
                modifier = Modifier.padding(16.dp)
            )
        } else {
            // Pass the list to UdpDataList composable
            UdpDataList(udpDataList = udpDataList, ondelete = { lauchdata() },onAddClick = { navController.navigate("addDataScreen") })
        }

    }


@Composable
fun UdpDataList(udpDataList: List<UdpData>, ondelete: () -> Unit,onAddClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Button(
            onClick = { onAddClick() },
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(bottom = 16.dp)
        ) {
            Text(text = "Add")
        }


        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(udpDataList) { data ->
                UdpDataCard(data, ondelete)
            }
        }
    }
}


@Composable
    fun UdpDataCard(data: UdpData, ondelete: () -> Unit) {
        val context = LocalContext.current

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
                    painter = rememberAsyncImagePainter(data.ImageUrl),
                    contentDescription = "",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color(0xFF1976D2), CircleShape),
                    contentScale = ContentScale.Inside
                )
                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Name: ${data.firstname}",
                        style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = Color.Black
                        )
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
                    Column() {
                        Button(
                            modifier = Modifier
                                .offset(40.dp, 1.dp)
                                .width(100.dp)
                                .height(50.dp),
                            onClick = {
                                deletefun(data.Id, context, ondelete)
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFDC3545), // Danger Red
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Delete",
                            )
                        }
                    }

                }
            }
        }
    }

    fun deletefun(ID: String, context: Context, ondelete: () -> Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val requestBody = mapOf("Id" to ID)
                val response = ReprofitInstance.ApiServiceDelete.getdelete(requestBody)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show()

                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
            ondelete()
        }

    }



