import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ca3andoridapplication.GetUDPDataScreen
import com.example.ca3andoridapplication.ReprofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class AddPage : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
           Navigation()
        }
    }
}
@Composable
fun AddDataScreen(navController: NavController) {
    val context = LocalContext.current

    var firstname by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }
    var priority by remember { mutableStateOf("") }
    var preferedTemperature by remember { mutableStateOf("") }
    var preferedHumidity by remember { mutableStateOf("") }
    var ImageUrl by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Welcome to UDP",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "Keep your data safe",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        // Input fields
        CustomTextField(value = firstname, onValueChange = { firstname = it }, placeholder = "First Name")
        CustomTextField(value = age, onValueChange = { age = it }, placeholder = "Age")
        CustomTextField(value = temperature, onValueChange = { temperature = it }, placeholder = "Temperature")
        CustomTextField(value = humidity, onValueChange = { humidity = it }, placeholder = "Humidity")
        CustomTextField(value = priority, onValueChange = { priority = it }, placeholder = "Priority")
        CustomTextField(value = preferedTemperature, onValueChange = { preferedTemperature = it }, placeholder = "Preferred Temperature")
        CustomTextField(value = preferedHumidity, onValueChange = { preferedHumidity = it }, placeholder = "Preferred Humidity")
        CustomTextField(value = ImageUrl, onValueChange = { ImageUrl = it }, placeholder = "Image URL")

        Button(
            onClick = {
                adddata(
                    firstname = firstname,
                    age = age,
                    temperature = temperature.toIntOrNull(),
                    humidity = humidity.toIntOrNull(),
                    priority = priority,
                    preferedTemperature = preferedTemperature,
                    preferedHumidity = preferedHumidity,
                    ImageUrl = ImageUrl,
                    context = context
                )
                backScreen(navController)
            },

            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFFC107))
        ) {
            Text(
                text = "SUBMIT",
                color = Color.Black,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

fun adddata(
    firstname: String,
    age: String,
    temperature: Int?,
    humidity: Int?,
    priority: String,
    preferedTemperature: String,
    preferedHumidity: String,
    ImageUrl: String,
    context: Context
) {
    CoroutineScope(Dispatchers.IO).launch {
        try {
            val data = mapOf(
                "firstname" to firstname,
                "age" to age,
                "temperature" to temperature.toString(),
                "humidity" to humidity.toString(),
                "priority" to priority,
                "preferedTemperature" to preferedTemperature,
                "preferedHumidity" to preferedHumidity,
                "ImageUrl" to ImageUrl
            )

            val response = ReprofitInstance.ApiServiceAdd.adddata(data)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(context, "Data successfully added", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Failed to add data", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main) {
                Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
 fun backScreen(navController: NavController) {
        navController.popBackStack()//go back to screen
 }


@Composable
fun Navigation(){
    val navControl= rememberNavController()
    NavHost(navController = navControl, startDestination = "Addform" ){
        composable("Addform"){
            AddDataScreen(navControl)
        }
    }
}
@Composable
fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = {
            Text(
                text = placeholder,
                color = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(56.dp),
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            cursorColor = Color.Blue
        ),
        singleLine = true,
        shape = RoundedCornerShape(24.dp)
    )
}
