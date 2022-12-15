package framework.api.apimanager;

import aquality.selenium.core.logging.Logger;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import framework.api.models.ResponseModel;
import org.openqa.selenium.grid.config.ConfigException;

import java.io.File;

public class ApiManager {

    public static ResponseModel get(String requestPath) {
        Logger.getInstance().info("API: Sending GET request - " + requestPath);
        try {
            HttpResponse<JsonNode> response = Unirest.get(requestPath).asJson();
            Logger.getInstance().info("API: Response - \nStatus code: " + response.getStatus() + "\nBody: " + response.getBody());
            return new ResponseModel(response.getStatus(), response.getBody().toString());
        } catch (Exception e) {
            Logger.getInstance().error("API: Request not sent, exception - " + e);
            throw new ConfigException(e);
        }
    }

    public static ResponseModel post(String requestPath) {
        Logger.getInstance().info("API: Sending POST request - " + requestPath);
        try {
            HttpResponse<JsonNode> response = Unirest.post(requestPath).asJson();
            Logger.getInstance().info("API: Response - \nStatus code: " + response.getStatus() + "\nBody: " + response.getBody());
            return new ResponseModel(response.getStatus(), response.getBody().toString());
        } catch (Exception e) {
            Logger.getInstance().error("API: Request not sent, exception - " + e);
            throw new ConfigException(e);
        }
    }

    public static ResponseModel upload(String requestPath, String filePath, String fileType, String contentType) {
        Logger.getInstance().info("API: Sending POST request with a file - " + requestPath);
        try {
            HttpResponse<JsonNode> response = Unirest.post(requestPath).field(fileType, new File(filePath), contentType).asJson();
            Logger.getInstance().info("API: Response - \nStatus code: " + response.getStatus() + "\nBody: " + response.getBody());
            return new ResponseModel(response.getStatus(), response.getBody().toString());
        } catch (Exception e) {
            Logger.getInstance().error("API: Request not sent, exception - " + e);
            throw new ConfigException(e);
        }
    }
}