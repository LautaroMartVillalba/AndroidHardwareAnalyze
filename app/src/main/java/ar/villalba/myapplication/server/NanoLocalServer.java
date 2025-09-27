package ar.villalba.myapplication.server;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import java.io.IOException;
import ar.villalba.myapplication.getters.JSONManager;
import fi.iki.elonen.NanoHTTPD;

public class NanoLocalServer extends NanoHTTPD {

    private final Context localContext;
    public NanoLocalServer(int port, Context context) {
        super(port);
        this.localContext = context;
    }


    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        String apiVersion = "v1";

        JSONManager jsonManager = new JSONManager(localContext);

        switch (uri){
            case "/api/v1/hardware/cpu-sse":
                try {
                    return ServiceController.stream(jsonManager::CPUJson);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case "/api/v1/hardware/ram-sse":
                try {
                    return ServiceController.stream(jsonManager::ramJson);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case "/api/v1/hardware/battery-sse":
                try {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        return ServiceController.stream(jsonManager::batteryJson);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            case "/api/v1/hardware/sensors-sse":
                try {
                    return ServiceController.stream(jsonManager::ramJson);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

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
