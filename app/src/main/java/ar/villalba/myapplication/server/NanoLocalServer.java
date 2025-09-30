package ar.villalba.myapplication.server;

import android.content.Context;
import android.os.Build;
import android.util.Log;
import org.json.JSONObject;
import java.io.IOException;
import java.util.function.Supplier;
import ar.villalba.myapplication.getters.JSONManager;
import ar.villalba.myapplication.getters.SensorInDevice;
import fi.iki.elonen.NanoHTTPD;

public class NanoLocalServer extends NanoHTTPD {

    private final Context localContext;

    public NanoLocalServer(int port, Context context) {
        super(port);
        this.localContext = context;
    }

    private static final String API_VERSION = "v1";

    private Response streamSafeResponseAbstraction(Supplier<JSONObject> supplier){
        try{
            return ServiceController.stream(supplier);
        }catch (IOException e){
            Log.e("LocalServer", "An error has occurred.", e);
            return newFixedLengthResponse(Response.Status.INTERNAL_ERROR, "text/plain",
                                "Something went wrong. Try again later or contact support.");
        }
    }


    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        JSONManager jsonManager = new JSONManager(localContext);
        SensorInDevice sensorInDevice = new SensorInDevice(localContext);
        sensorInDevice.start();

        switch (uri){
            case "/api/" + API_VERSION + "/hardware/cpu-sse":
                    return streamSafeResponseAbstraction(jsonManager::CPUJson);

            case "/api/" + API_VERSION + "/hardware/ram-sse":
                    return streamSafeResponseAbstraction(jsonManager::ramJson);

            case "/api/" + API_VERSION + "/hardware/battery-sse":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    return streamSafeResponseAbstraction(jsonManager::batteryJson);
                }

            case "/api/" + API_VERSION + "/hardware/device-static":
                return newFixedLengthResponse(Response.Status.OK, "application/json",
                        jsonManager.deviceJson().toString());

            case "/api/" + API_VERSION + "/hardware/screen-static":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    return newFixedLengthResponse(Response.Status.OK, "application/json",
                            jsonManager.screenJson().toString());
                } else {
                    return newFixedLengthResponse(Response.Status.NOT_IMPLEMENTED, "text/plain",
                            "Service not yet available in Android " + Build.VERSION.SDK_INT + ".");
                }

            case "/api/" + API_VERSION + "/hardware/storage-static":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    return newFixedLengthResponse(Response.Status.OK, "application/json",
                            jsonManager.storageJson().toString());
                } else {
                    return newFixedLengthResponse(Response.Status.NOT_IMPLEMENTED, "text/plain",
                            "Service not yet available in Android " + Build.VERSION.SDK_INT + ".");
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
