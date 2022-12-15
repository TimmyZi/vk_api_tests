package framework.helpers;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class JsonHelper {

    public static JsonArray parseToJsonArray(String string) {
        return JsonParser.parseString(string).getAsJsonArray();
    }

    public static JsonObject parseToJsonObject(String string) {
        return JsonParser.parseString(string).getAsJsonObject();
    }
}