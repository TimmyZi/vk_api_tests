package framework.config;

import aquality.selenium.core.utilities.ISettingsFile;
import aquality.selenium.core.utilities.JsonSettingsFile;

public class ConfigManager {
    public static final ISettingsFile SETTINGS = new JsonSettingsFile("settings.json");
    public static final ISettingsFile ACCOUNT_DATA = new JsonSettingsFile("accountData.json");
}