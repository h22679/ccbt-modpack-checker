package ccbt.modpackchecker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ModConfig {
    private static final String CONFIG_FILENAME = "config/modpackchecker.json";
    private static final File configFile = new File(CONFIG_FILENAME);

    private static boolean turnOn = false;
    private static boolean autoDownload = false;

    private static boolean notifyUpdate = true;

    private static boolean showNews = true;

    private static String APIDownloadUrl = "https://api.cocobut.net/download/";

    private static String APIListUrl = "https://api.cocobut.net/modpack-checker?action=listfiles";

    private static String APINewsUrl = "https://api.cocobut.net/modpack-checker?action=getnews";

    private static boolean useCustomFolder = false;

    private static String customModFolder = "";

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static boolean getTurnOn() {
        return turnOn;
    }

    public static void setTurnOn(boolean value) {
        turnOn = value;
        saveConfig();
    }

    public static boolean getAutoDownload() {
        return autoDownload;
    }

    public static void setAutoDownload(boolean value) {
        autoDownload = value;
        saveConfig();
    }

    public static boolean getNotifyUpdate() {
        return notifyUpdate;
    }

    public static void setNotifyUpdate(boolean value) {
        notifyUpdate= value;
        saveConfig();
    }

    public static boolean getShowNews() {
        return showNews;
    }

    public static void setShowNews(boolean value) {
        showNews= value;
        saveConfig();
    }

    public static String getApiListUrl() {
        return APIListUrl;
    }

    public static void setApiListUrl(String value) {
        APIListUrl = value;
        saveConfig();
    }

    public static String getApiNewsUrl() {
        return APINewsUrl;
    }

    public static void setApiNewsUrl(String value) {
        APINewsUrl = value;
        saveConfig();
    }

    public static String getAPIDownloadUrl() {
        return APIDownloadUrl;
    }

    public static void setAPIDownloadUrl(String value) {
        APIDownloadUrl = value;
        saveConfig();
    }

    public static boolean getUseCustomFolder() {
        return useCustomFolder;
    }

    public static void setUseCustomFolder(boolean value) {
        useCustomFolder = value;
        saveConfig();
    }

    public static String getCustomModFolder() {
        return customModFolder;
    }

    public static void setCustomModFolder(String value) {
        customModFolder = value;
        saveConfig();
    }

    // Load the configuration from a file
    public static void loadConfig() {
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                JsonObject config = JsonParser.parseReader(reader).getAsJsonObject();
                turnOn = config.has("turnOn") ? config.get("turnOn").getAsBoolean() : turnOn;
                autoDownload = config.has("autoDownload") ? config.get("autoDownload").getAsBoolean() : autoDownload;
                notifyUpdate = config.has("notifyUpdate") ? config.get("notifyUpdate").getAsBoolean() : notifyUpdate;
                showNews = config.has("showNews") ? config.get("showNews").getAsBoolean() : showNews;
                useCustomFolder = config.has("useCustomFolder") ? config.get("useCustomFolder").getAsBoolean() : useCustomFolder;
                APIDownloadUrl = config.has("APIDownloadUrl") ? config.get("APIDownloadUrl").getAsString() : APIDownloadUrl;
                APIListUrl = config.has("APIListUrl") ? config.get("APIListUrl").getAsString() : APIListUrl;
                APINewsUrl = config.has("APINewsUrl") ? config.get("APINewsUrl").getAsString() : APINewsUrl;
                customModFolder = config.has("customModFolder") ? config.get("customModFolder").getAsString() : customModFolder;
            } catch (IOException e) {
                System.err.println("An error occurred while loading the config: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            saveConfig(); // Save the default configuration if the file doesn't exist
        }
    }

    // Save the configuration to a file
    public static void saveConfig() {
        JsonObject config = new JsonObject();
        config.addProperty("turnOn", turnOn);
        config.addProperty("autoDownload", autoDownload);
        config.addProperty("notifyUpdate", notifyUpdate);
        config.addProperty("APIDownloadUrl", APIDownloadUrl);
        config.addProperty("APIListUrl", APIListUrl);
        config.addProperty("useCustomFolder", useCustomFolder);
        config.addProperty("customModFolder", customModFolder);

        configFile.getParentFile().mkdirs(); // Create the config directory if it doesn't exist
        try (FileWriter writer = new FileWriter(configFile)) {
            gson.toJson(config, writer);
        } catch (IOException e) {
            System.err.println("An error occurred while saving the config: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
