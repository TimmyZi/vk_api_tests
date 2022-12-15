package vk.configs;

import com.google.gson.JsonObject;
import framework.helpers.JsonHelper;

import static framework.config.ConfigManager.SETTINGS;

public class MainConfig {
    protected static final JsonObject VK_CONFIG = JsonHelper.parseToJsonObject(SETTINGS.getValue("/site").toString()).get("vk").getAsJsonObject();
    public static final String URL = VK_CONFIG.get("url").getAsString();
    public static final String PHOTO_PATH = VK_CONFIG.get("picture").getAsString();
    public static final String CONTENT_TYPE = VK_CONFIG.get("contentType").getAsString();
}