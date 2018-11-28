package rnk.l14.servlets.ws;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.io.IOException;
import java.io.Writer;

public class JsonEncoder implements Encoder.TextStream<JsonArray> {

    @Override
    public void init(EndpointConfig config) {}

    @Override
    public void encode(JsonArray payload, Writer writer) throws EncodeException, IOException {
        Gson gson=new Gson();
        gson.toJson(payload,writer);
    }

    @Override
    public void destroy() {}
}
