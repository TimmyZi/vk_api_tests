package vk.api.util;

import aquality.selenium.core.logging.Logger;
import com.google.gson.JsonArray;
import framework.api.apimanager.ApiManager;
import framework.api.models.ResponseModel;
import vk.api.models.Photo;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static vk.configs.AccountConfig.*;
import static vk.configs.ApiVkConfig.*;
import static vk.configs.MainConfig.CONTENT_TYPE;

public class ApiVkUtil {

    public static String addWallPost(String text) {
        Logger.getInstance().info("API: Adding wall post");
        String request = String.format("%s/wall.post?owner_id=%s&message=%s&access_token=%s&v=%s"
                , API_URL, OWNER_ID, text, ACCESS_TOKEN, API_VERSION);
        return ApiManager.post(request).getBodyAsJsonObject().get("response").getAsJsonObject().get("post_id").getAsString();
    }

    public static String editWallPost(String postId, String text, String pictureId) {
        Logger.getInstance().info("API: Sending request for edit post on the wall");
        String request = String.format("%s/wall.edit?owner_id=%s&post_id=%s&message=%s&attachments=photo%s_%s&access_token=%s&v=%s"
                , API_URL, OWNER_ID, postId, text, OWNER_ID, pictureId, ACCESS_TOKEN, API_VERSION);
        return ApiManager.post(request).getBodyAsJsonObject().get("response").getAsJsonObject().get("post_id").getAsString();
    }

    public static String addCommentToWallPost(String postId, String text) {
        Logger.getInstance().info("API: Adding comment to wall post by id - " + postId);
        String request = String.format("%s/wall.createComment?owner_id=%s&post_id=%s&message=%s&access_token=%s&v=%s"
                , API_URL, OWNER_ID, postId, text, ACCESS_TOKEN, API_VERSION);
        return ApiManager.post(request).getBodyAsJsonObject().get("response").getAsJsonObject().get("comment_id").getAsString();
    }

    public static List<String> getWallPostLikes(String postId) {
        Logger.getInstance().info("API: Getting wall post likes by id - " + postId);
        String request = String.format("%s/wall.getLikes?owner_id=%s&post_id=%s&access_token=%s&v=%s"
                , API_URL, OWNER_ID, postId, ACCESS_TOKEN, API_VERSION);
        List<String> usersIdWhoLiked = new ArrayList<>();
        JsonArray response = ApiManager.get(request).getBodyAsJsonObject().get("response").getAsJsonObject().get("users").getAsJsonArray();
        for (int i = 0; i < response.size(); i++) {
            usersIdWhoLiked.add(response.get(i).getAsJsonObject().get("uid").getAsString());
        }
        return usersIdWhoLiked;
    }

    public static String deleteWallPost(String postId) {
        Logger.getInstance().info("API: Deleting wall post by id - " + postId);
        String request = String.format("%s/wall.delete?owner_id=%s&post_id=%s&access_token=%s&v=%s", API_URL, OWNER_ID, postId, ACCESS_TOKEN, API_VERSION);
        return ApiManager.post(request).getBody();
    }

    public static String savePhotoForWallOnTheServer(String filePath) {
        Photo photo = uploadWallPhotoOnTheServer(filePath);
        Logger.getInstance().info("API: Save uploaded photo to the server");
        String request = String.format("%s/photos.saveWallPhoto?user_id=%s&photo=%s&server=%s&hash=%s&access_token=%s&v=%s"
                , API_URL, OWNER_ID, URLEncoder.encode(photo.getPhoto(), StandardCharsets.UTF_8)
                , photo.getServer(), photo.getHash(), ACCESS_TOKEN, API_VERSION);
        return ApiManager.post(request).getBodyAsJsonObject().get("response").getAsJsonArray().get(0).getAsJsonObject().get("id").getAsString();
    }

    private static String getWallPhotoUploadServer() {
        Logger.getInstance().info("API: Sending request for get wall photo upload server");
        String request = String.format("%s/photos.getWallUploadServer?user_id=%s&access_token=%s&v=%s"
                , API_URL, OWNER_ID, ACCESS_TOKEN, API_VERSION);
        String jsonResponseKey = "response";
        String jsonUploadUrlKey = "upload_url";
        return ApiManager.get(request).getBodyAsJsonObject().get(jsonResponseKey).getAsJsonObject().get(jsonUploadUrlKey).getAsString();
    }

    private static Photo uploadWallPhotoOnTheServer(String filePath) {
        Logger.getInstance().info("API: Uploading wall photo to the server");
        String fileType = "photo";
        ResponseModel response = ApiManager.upload(getWallPhotoUploadServer(), filePath, fileType, CONTENT_TYPE);
        String server = response.getBodyAsJsonObject().get("server").getAsString();
        String photo = response.getBodyAsJsonObject().get("photo").getAsString();
        String hash = response.getBodyAsJsonObject().get("hash").getAsString();
        return new Photo(server, photo, hash);
    }
}