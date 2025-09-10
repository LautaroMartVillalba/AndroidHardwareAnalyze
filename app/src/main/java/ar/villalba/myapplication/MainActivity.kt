package ar.villalba.myapplication

import android.os.Build
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ar.villalba.myapplication.getters.Battery
import ar.villalba.myapplication.getters.CPU
import ar.villalba.myapplication.getters.Device
import ar.villalba.myapplication.getters.JSONResponse
import ar.villalba.myapplication.getters.RAM
import ar.villalba.myapplication.getters.Screen
import ar.villalba.myapplication.getters.Sensor
import ar.villalba.myapplication.getters.Storage
import ar.villalba.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Composable
    fun Greeting(name: String, modifier: Modifier = Modifier) {
        val jsonResponse = JSONResponse()
        val battery = Battery(this)
        val CPU = CPU()
        val device = Device()
        val RAM = RAM(this)
        val storage = Storage()
        val screen = Screen(this)
        val sensor = Sensor(this)

        val json = jsonResponse.createJSONResponse(
            battery,
            CPU,
            device,
            screen,
            RAM,
            sensor,
            storage
        )

        Text(
            text = """
                ${json.getJSONObject("battery")}
            """.trimIndent(),
            modifier = modifier
        )
    }

    @RequiresApi(Build.VERSION_CODES.R)
    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        MyApplicationTheme {
            Greeting("Android")
        }
    }
}