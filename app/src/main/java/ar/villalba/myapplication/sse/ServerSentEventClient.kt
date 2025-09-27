//package ar.villalba.myapplication.sse
//
//import android.os.Build
//import android.util.Log
//import com.launchdarkly.eventsource.EventSource
//import com.launchdarkly.eventsource.MessageEvent
//import com.launchdarkly.eventsource.background.BackgroundEventHandler
//import kotlinx.coroutines.CoroutineScope
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.launch
//import okhttp3.internal.closeQuietly
//import java.net.URI
//import java.time.Duration
//import java.util.concurrent.TimeUnit
//
//class ServerSentEventClient {
//
//    private var sseHandler: SSEHandler? = null
//    private var eventSource: EventSource? = null
//
//    fun initSSE(sseHandler: SSEHandler, errorCallback: (Throwable) -> Unit, endpoint: String){
//        this.sseHandler = sseHandler
//        val baseUrl = "http://localhost:5200"
//
//        try {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                eventSource = EventSource.Builder(URI.create(baseUrl + endpoint))
//                    .retryDelay(1, TimeUnit.SECONDS)
//                    .build()
//                eventSource?.start()
//            }
//        } catch (e: Exception) {
//            errorCallback(e)
//        }
//    }
//
//    private class DefaultEventHandler(private val sseHandler: SSEHandler): BackgroundEventHandler{
//        override fun onOpen() {
//            sseHandler.onSSEConnectionOpened()
//        }
//
//        override fun onClosed() {
//            sseHandler.onSSEConnectionClosed()
//        }
//
//        override fun onMessage(
//            event: String?,
//            messageEvent: MessageEvent?
//        ) {
//            sseHandler.onSSEEventReceived(event, messageEvent)
//        }
//
//        override fun onComment(comment: String?) {
//            Log.i("SSE_CONNECTION", comment.toString())
//        }
//
//        override fun onError(t: Throwable?) {
//            sseHandler.onSSEError(t)
//        }
//    }
//
//    fun disconnect(){
//        CoroutineScope(Dispatchers.IO).launch{
//            try {
//                eventSource?.close()
//            }catch (e: Exception){
//                e.printStackTrace()
//            }
//        }
//    }
//
//}