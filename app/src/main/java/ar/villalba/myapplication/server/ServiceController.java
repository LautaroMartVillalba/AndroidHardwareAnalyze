package ar.villalba.myapplication.server;

import android.util.Log;
import org.json.JSONObject;
import java.io.IOException;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.util.function.Supplier;
import fi.iki.elonen.NanoHTTPD;

public class ServiceController {

    public static NanoHTTPD.Response stream(Supplier<JSONObject> supplier) throws IOException {

        PipedInputStream input = new PipedInputStream();
        PipedOutputStream output = new PipedOutputStream(input);

        NanoHTTPD.Response sseResponse = NanoHTTPD.newChunkedResponse(
                NanoHTTPD.Response.Status.OK,
                "text/event-stream",
                input);
        sseResponse.addHeader("Cache-Control", "public");
        sseResponse.addHeader("Connection", "keep-alive");

        new Thread(() -> {
            try {
                while (true) {
                    JSONObject jsonResponse = supplier.get();
                    String data = "data: " + jsonResponse.toString(4) + "\n\n";
                    try {
                        output.write(data.getBytes());
                    }catch (IOException e){
                        Log.d("ServiceController", "Thread closed.");
                        Thread.currentThread().interrupt();
                        break;
                    }
                    output.flush();
                    Thread.sleep(1000);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        return sseResponse;
    }


}
