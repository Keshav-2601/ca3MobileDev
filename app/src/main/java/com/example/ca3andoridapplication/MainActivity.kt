package com.example.ca3andoridapplication

//import DatabaseProvider
import android.annotation.SuppressLint
import android.app.ActionBar.OnNavigationListener
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.FlowRowScopeInstance.align
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
fun Residentdetail(onNavigate: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .background(Color.Black)
    ) {
        Text(
            text = "Login Details",
            color = Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        TextField(
            value = username,
            onValueChange = { newUsername ->
                username = newUsername
            },
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.padding(20.dp),
            label = { Text("Username") },
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )
        TextField(
            value = password,
            onValueChange = { newPassword ->
                password = newPassword
            },
            modifier = Modifier.padding(20.dp),
            shape = RoundedCornerShape(20.dp),
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),//make password in . form
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        Button(
            onClick = {
                 onNavigate(username,password);
                }

        ) {
            Text(text = "Login")
        }
    }
}


@Composable
fun navigation() {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        RoomDatabaseClass::class.java,
        "resident_database"
    ).build()
    val dao = db.resident_method()

    LaunchedEffect(Unit) {
        CoroutineScope(Dispatchers.IO).launch {
            addDummyData(dao)
        }
    }
    val navigateControl = rememberNavController()
    NavHost(navController = navigateControl, startDestination = "ResidentDetail") {
        composable("ResidentDetail") {
            Residentdetail(onNavigate = { username, password, ->
                CoroutineScope(Dispatchers.IO).launch {
                    val isValid = ValidCredentail(username, password, dao)
                    withContext(Dispatchers.Main) {
                        if (isValid) {
                            navigateControl.navigate("NextScreen")
                        } else {
                            Toast.makeText(context, "Invalid Credentials", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            },)
        }
        composable("NextScreen") {
            GetUDPDataScreen()
        }
    }
}

suspend fun addDummyData(dao: RoomDao){
    if (dao.getAllResident().isEmpty()){
        val ResidentList= listOf<RoomDB>(
            RoomDB("Keshav","password@123",20,"23"),
            RoomDB("Alex","password@234",20,"21"),
            RoomDB("John","password@567",20,"20"),
            RoomDB("Bride","password@891",20,"25"),
            RoomDB("Derek","password@123",20,"19")
        )
        ResidentList.forEach {resident-> dao.insertResident(resident) }
    }


}

suspend fun ValidCredentail(username:String,password:String,dao: RoomDao):Boolean{
    val Dao=dao;
    val resident = Dao.validateResident(username, password)
    return resident != null

}

//Now:-

@Composable
fun StoreDB(){

}
//Now i want to submit these Values to some Random DB as Json format .