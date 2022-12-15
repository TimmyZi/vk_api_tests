package vk.configs;

public class ApiVkConfig extends MainConfig {
    public static final String API_URL = VK_CONFIG.get("apiUrl").getAsString();
    public static final String API_VERSION = VK_CONFIG.get("apiVersion").getAsString();
}
