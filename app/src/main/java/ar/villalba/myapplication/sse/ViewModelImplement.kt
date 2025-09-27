package ar.villalba.myapplication.sse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.launchdarkly.eventsource.MessageEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModelImplement (private val sseClient: ServerSentEventClient): ViewModel(), SSEHandler {

    init {
        viewModelScope.launch(Dispatchers.IO) {
            sseClient.initSSE(this@ViewModelImplement) {
                error ->
                error.printStackTrace()
            }
        }
    }

    override fun onSSEConnectionOpened() {
        TODO("Not yet implemented")
    }

    override fun onSSEConnectionClosed() {
        TODO("Not yet implemented")
    }

    override fun onSSEEventReceived(
        event: String?,
        messageEvent: MessageEvent?
    ) {
        TODO("Not yet implemented")
    }

    override fun onSSEError(t: Throwable?) {
        TODO("Not yet implemented")
    }

    fun disconnect() {
        sseClient.disconnect()
    }
}