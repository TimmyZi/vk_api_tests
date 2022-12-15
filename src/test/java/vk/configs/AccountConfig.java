package vk.configs;

import static framework.config.ConfigManager.ACCOUNT_DATA;

public class AccountConfig {
    public static final String LOGIN = ACCOUNT_DATA.getValue("/testUserLogin").toString();
    public static final String PASSWORD = ACCOUNT_DATA.getValue("/testUserPassword").toString();
    public static final String ACCESS_TOKEN = ACCOUNT_DATA.getValue("/testUserApiToken").toString();
    public static final String OWNER_ID = ACCOUNT_DATA.getValue("/testUserOwnerId").toString();
}