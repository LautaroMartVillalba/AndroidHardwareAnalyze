package ar.villalba.myapplication;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;
import org.json.JSONException;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import ar.villalba.myapplication.getters.JSONResponse;
import fi.iki.elonen.NanoHTTPD;

@RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
public class NanoLocalServer extends NanoHTTPD {

    private Context localContext = null;

    public NanoLocalServer(int port, Context context) {
        super(port);
        this.localContext = context;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        String apiVersion = "v1";

        JSONResponse jsonManager = new JSONResponse(localContext);

        switch (uri){
            case "/api/v1/hardware/cpu":
                Log.d("NanoLocalServer","Llamado al endpoint.");

                try {
                    return newFixedLengthResponse(Response.Status.OK,
                            "text/plain",
                            jsonManager.CPUJson().toString(4));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            case "/api/v1/hardware/ram-sse":
                Log.d("LocalServer","Se llamó al endpoint");
                PipedInputStream pipedInputStream = new PipedInputStream();
                PipedOutputStream pipedOutputStream;
                try {
                    Log.w("LocalServer", "Intentando crear el pipedOut.");
                    pipedOutputStream = new PipedOutputStream(pipedInputStream);
                } catch (IOException e) {
                    Log.e("LocalServer", "Error al crear el pipedOut");
                    throw new RuntimeException(e);
                }

                Log.d("LocalServer","Creando sseResponse");
                Response sseResponse = newChunkedResponse(Response.Status.OK,
                        "text/event-stream",
                        pipedInputStream);

                sseResponse.addHeader("Cache-Control", "no-cache");
                sseResponse.addHeader("Connection", "keep-alive");

                new Thread(() -> {
                    Log.d("LocalServer","Iniciando hilo");
                    try {
                        while (true){
                            Log.d("LocalServer","Creando JsonResponse");
                            String jsonResponse = jsonManager.ramJson().toString(4);
                            String sseDataResponse = "data: " + jsonResponse + "\n\n";
                            try {
                                Log.d("LocalServer","Intentando escribir pipedOut.");
                                pipedOutputStream.write(sseDataResponse.getBytes());
                                Log.d("LocalServer","Intentando flushear pipedOut.");
                                pipedOutputStream.flush();
                            }catch (IOException e){
                                Log.e("LocalServer","Estás hasta los huevos");
                                newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain",
                                        "Endpoint not found");
                            }
                            Log.d("LocalServer","Esperando...");
                            Thread.sleep(1000);
                        }
                    } catch (JSONException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                Log.d("LocalServer","Returning sseResponse.");
                return sseResponse;
            default:
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain",
                        "Endpoint not found");
        }
    }

    public void startSv(){
        try{
            start(SOCKET_READ_TIMEOUT, false);
            Log.d("LocalServer", "Starting in port: " + getListeningPort());
        }catch (IOException e){
            Log.e("LocalServer", "An error has occurred.", e);
        }
    }

    public void stopSv(){
        stop();
        Log.d("LocalServer", "Server stopped.");
    }

}
