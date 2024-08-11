package com.example.sep4_and.utils;

//TODO: Use SharedPreferences to store useApi
public class Config {
    private static boolean useApi = false;
    private static boolean echoToLocalDatabase = false;

    public static boolean isUseApi() {
        return useApi;
    }

    public static void setUseApi(boolean useApi) {
        Config.useApi = useApi;
    }

    public static boolean isEchoToLocalDatabase() {
        return echoToLocalDatabase;
    }

    public static void setEchoToLocalDatabase(boolean echoToLocalDatabase) {
        Config.echoToLocalDatabase = echoToLocalDatabase;
    }
}