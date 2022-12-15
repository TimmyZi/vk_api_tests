package framework.api.models;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import framework.helpers.JsonHelper;

public record ResponseModel(int statusCode, String body) {

    public int getStatusCode() {
        return statusCode;
    }

    public String getBody() {
        return body;
    }

    public JsonObject getBodyAsJsonObject() {
        return JsonHelper.parseToJsonObject(body);
    }

    public JsonArray getBodyAsJsonArray() {
        return JsonHelper.parseToJsonArray(body);
    }
}