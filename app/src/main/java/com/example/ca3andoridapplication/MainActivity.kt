package com.example.ca3andoridapplication

//import DatabaseProvider
//import RoomDB
//import RoomDao
//import RoomDatabaseClass
import android.annotation.SuppressLint
import android.app.ActionBar.OnNavigationListener
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.ca3andoridapplication.ui.theme.Ca3AndoridApplicationTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.ActorScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Route

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
           navigation();
          }
        }
    }


@Composable
fun ResidentDetail(onNavigate:()->Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Background with custom color
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)) // Light gray background
    ) {
        // Main content
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column()
            {
                val alpha by rememberInfiniteTransition().animateFloat(
                    initialValue = 0f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(durationMillis = 1500),
                        repeatMode = RepeatMode.Reverse
                    ), label = "Keshav"
                )
                Text(
                    text = "Welcome to UDP",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF212121),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)

                )
                Text(
                    text = "Keep your data safe",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF757575), // Gray color
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                        .alpha(alpha)
                )
            }
            // Email Field
            TextField(
                value = username,
                onValueChange = { username = it },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                label = { Text("Email") },
                singleLine = true,
                colors = TextFieldDefaults.colors(

                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    )
            )


            TextField(
                value = password,
                onValueChange = { password = it },
                shape = RoundedCornerShape(30.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 24.dp),
                label = { Text("Password") },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,

                    )
            )


            Button(
                onClick = { onNavigate() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(30.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107)) // Yellow color
            ) {
                Text(
                    text = "LOGIN",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun navigation(){
    val naviagtion= rememberNavController()
    NavHost(navController = naviagtion, startDestination = "ResidentDetail"){
        composable("ResidentDetail") {
            ResidentDetail(onNavigate = { naviagtion.navigate("Next") })
        }
        composable("Next"){
            GetUDPDataScreen()
        }
    }
}

//@Composable
//fun navigation() {
//    val context = LocalContext.current
//    val db = Room.databaseBuilder(
//        context,
//        RoomDatabaseClass::class.java,
//        "resident_database"
//    ).build()
//    val dao = db.resident_method()
//
//    LaunchedEffect(Unit) {
//        CoroutineScope(Dispatchers.IO).launch {
//            addDummyData(dao)
//        }
//    }
//    val navigateControl = rememberNavController()
//    NavHost(navController = navigateControl, startDestination = "ResidentDetail") {
//        composable("ResidentDetail") {
//            ResidentDetail(onNavigate = { username, password, ->
//                CoroutineScope(Dispatchers.IO).launch {
//                    val isValid = ValidCredentail(username, password, dao)
//                    withContext(Dispatchers.Main) {
//                        if (isValid) {
//                            navigateControl.navigate("NextScreen")
//                        } else {
//                            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
//                        }
//                    }
//                }
//            },)
//        }
//        composable("NextScreen") {
//            GetUDPDataScreen()
//        }
//    }
//}
//
//suspend fun addDummyData(dao: RoomDao){
//    if (dao.getAllResident().isEmpty()){
//        val ResidentList= listOf<RoomDB>(
//            RoomDB("Keshav","password@123",20,"23"),
//            RoomDB("Alex","password@234",20,"21"),
//            RoomDB("John","password@567",20,"20"),
//            RoomDB("Bride","password@891",20,"25"),
//            RoomDB("Derek","password@123",20,"19")
//        )
//        ResidentList.forEach {resident-> dao.insertResident(resident) }
//    }
//
//
//}
//
//suspend fun ValidCredentail(username:String,password:String,dao: RoomDao):Boolean{
//    val Dao=dao;
//    val resident = Dao.validateResident(username, password)
//    return resident != null
//
//}


