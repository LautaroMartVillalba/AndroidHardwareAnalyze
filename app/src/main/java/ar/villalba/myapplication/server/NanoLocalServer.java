package ar.villalba.myapplication.server;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.Build;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Objects;
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
    private Response withCORS(Response resp) {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type, Accept, Authorization");
        return resp;
    }

    @Override
    public Response serve(IHTTPSession session) {
        String uri = session.getUri();
        JSONManager jsonManager = new JSONManager(localContext);
        SensorInDevice sensorInDevice = new SensorInDevice(localContext);
        sensorInDevice.start();

        switch (uri){
            case "/api/" + API_VERSION + "/hardware/cpu":
                    return streamSafeResponseAbstraction(jsonManager::CPUJson);

            case "/api/" + API_VERSION + "/hardware/ram":
                    return streamSafeResponseAbstraction(jsonManager::ramJson);

            case "/api/" + API_VERSION + "/hardware/battery":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    return streamSafeResponseAbstraction(jsonManager::batteryJson);
                }

            case "/api/" + API_VERSION + "/hardware/sensors":
                return ServiceController.sensorStream(sensorInDevice);

            case "/api/" + API_VERSION + "/hardware/device":
                    return newFixedLengthResponse(
                            Response.Status.OK,
                            "application/json",
                            jsonManager.deviceJson().toString()
                    );


            case "/api/" + API_VERSION + "/hardware/screen":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    return newFixedLengthResponse(
                            Response.Status.OK,
                            "application/json",
                            jsonManager.screenJson().toString()
                    );
                } else {
                    return withCORS(newFixedLengthResponse(
                            Response.Status.NOT_IMPLEMENTED,
                            "text/plain",
                            "Service not yet available in Android " + Build.VERSION.SDK_INT + ".")
                    );
                }

            case "/api/" + API_VERSION + "/hardware/storage":
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                        return newFixedLengthResponse(
                                Response.Status.OK,
                                "application/json",
                                jsonManager.storageJson().toString()
                        );
                } else {
                    return newFixedLengthResponse(
                            Response.Status.NOT_IMPLEMENTED,
                            "text/plain",
                            "Service not yet available in Android " + Build.VERSION.SDK_INT + "."
                    );
                }

            case "/swagger/index.html":
                return serveAsset("swagger-ui/index.html", "text/html");

            case "/swagger/swagger-ui.css":
                return serveAsset("swagger-ui/swagger-ui.css", "text/css");

            case "/swagger/swagger-ui-bundle.js":
                return serveAsset("swagger-ui/swagger-ui-bundle.js", "application/javascript");

            case "/openapi.json":
                Response resp;

                resp = newFixedLengthResponse(Response.Status.OK, "application/json", obtainOpenApiJson());
                resp.addHeader("Access-Control-Allow-Origin", "*");
                resp.addHeader("Access-Control-Allow-Methods", "GET, OPTIONS");

                return withCORS(resp);

            default:
                return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain",
                        "Endpoint not found");
        }
    }

    private String obtainOpenApiJson(){
        InputStream is;
        try {
            is = localContext.getAssets().open("openapi.json");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while (true) {
            try {
                if ((line = reader.readLine()) == null) break;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            sb.append(line);
        }
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sb.toString();
    }


    private Response serveAsset(String assetPath, String contentType) {
        try {
            AssetManager am = localContext.getAssets();
            InputStream is = am.open(assetPath);
            return newChunkedResponse(Response.Status.OK, contentType, is);
        } catch (IOException e) {
            return newFixedLengthResponse(Response.Status.NOT_FOUND, "text/plain", "Not found");
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
