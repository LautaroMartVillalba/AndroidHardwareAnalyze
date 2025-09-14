package ar.villalba.myapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ar.villalba.myapplication.getters.*
import ar.villalba.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            RealTimeJsonScreen(this)
        }
    }


    @Composable
    fun SensorScreen(sensorText: String, modifier: Modifier = Modifier){
        Text(
            text = sensorText,
            modifier = modifier
        )
    }

    @RequiresApi(TIRAMISU)
    @Composable
    fun RealTimeJsonScreen(context: Context) {
        val jsonResponse = remember { JSONResponse(context) }
        val jsonText by jsonResponse.completeJsonResponse

        Text(
            text = jsonText,
            modifier = Modifier.padding(16.dp)
        )
    }

    @RequiresApi(TIRAMISU)
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {

        val JSONResponse = JSONResponse(this)

        Text(
            text = "".trimIndent(),
            modifier = modifier
        )
    }

    @RequiresApi(TIRAMISU)
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MyApplicationTheme {
            Greeting("Android")
        }
    }

}