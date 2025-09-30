package ar.villalba.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import ar.villalba.myapplication.server.ServerService

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val intent = Intent(this, ServerService::class.java)
        ContextCompat.startForegroundService(this, intent)

        enableEdgeToEdge()
        setContent {
            SimpleTextCall()
        }
    }

    @Composable
    fun SimpleTextCall() {
        Text(
            text = "Go to your browser!",
            modifier = Modifier.padding(16.dp)
        )
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}