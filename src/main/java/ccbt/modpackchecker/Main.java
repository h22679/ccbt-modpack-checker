package ccbt.modpackchecker;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Main implements ModInitializer {

	ScreenManager screenManager = ScreenManager.getInstance();
	public static final Logger LOGGER = LoggerFactory.getLogger("ccbt-modpack-checker");

	@Override
	public void onInitialize() {
		ModConfig.loadConfig();
		LOGGER.info("Starting ModPack Checker");

		boolean modIsOn = ModConfig.getTurnOn();
		boolean notifyUpdate = ModConfig.getNotifyUpdate();
		boolean showNews = ModConfig.getShowNews();
		boolean shouldAutoDownload = ModConfig.getAutoDownload();
		boolean useCustomModFolder = ModConfig.getUseCustomFolder();
		String customModFolder = ModConfig.getCustomModFolder();


		File gameDir = FabricLoader.getInstance().getGameDirectory();
		File modsDir = new File(gameDir, "mods");
		String modsFolderPath = modsDir.getPath();
		String apiListUrl = ModConfig.getApiListUrl();
		String apiNewsUrl = ModConfig.getApiNewsUrl();
		String apiDownloadUrl = ModConfig.getAPIDownloadUrl();

		if (modIsOn) {
			// Initialize the ModManager with the mods folder path
			ModManager modManager = new ModManager(modsFolderPath);

			// Initialize the ModFileComparator
			ModFileComparator comparator = new ModFileComparator();

			if (useCustomModFolder) {
				modsFolderPath = customModFolder; // Modified value
			}

			try {
				// List the current mod files using ModManager
				List<String> currentMods = modManager.listModFiles();
				LOGGER.info("Current Mods: " + currentMods);

				// Fetch the expected mod files from the API using ModManager
				List<String> expectedMods = modManager.fetchExpectedModFiles(apiListUrl);
				String newsText = modManager.fetchNews(apiNewsUrl);
				LOGGER.info("Expected Mods: " + expectedMods);

				// Compare the lists using ModFileComparator and get the results
				Map<String, List<String>> differences = comparator.compareModFiles(currentMods, expectedMods);

				List missingFiles = differences.get("missingFiles");
				boolean filesAreMissing = !missingFiles.isEmpty();
				// Output the differences
				LOGGER.info("Missing Files: " + missingFiles);
				LOGGER.info("Extra Files: " + differences.get("extraFiles"));


				if (shouldAutoDownload && filesAreMissing) {
					modManager.downloadMissingMods(missingFiles, apiDownloadUrl, modsFolderPath);
					LOGGER.info("Missing files = " + filesAreMissing + " and auto download = " + shouldAutoDownload + ". Proceeding with download");
					MinecraftClient.getInstance().execute(() -> {
						screenManager.openScreen(new RestartScreen());
					});

				}
				else {
					LOGGER.info("Missing files = " + filesAreMissing + " and auto download = " + shouldAutoDownload + ". Skipping download");

				}
				if (notifyUpdate){
					checkForUpdates(modManager);
				}
				if (showNews){
					MinecraftClient.getInstance().execute(() -> {
						screenManager.openScreen(new NewsScreen(newsText));
					});
				}


			} catch (Exception e) {
				LOGGER.error("An error occurred during mod pack checking: ", e);
			}
		}
		else {
			LOGGER.info("Stopped process without running mod checker. Turn on mod in config or mod manager to enable.");
		}

		LOGGER.info("ModPack Checker Mod done loading!");


	}

	// Function to check for updates and handle them based on version comparison
	private void checkForUpdates(ModManager modManager) {
		try {
			// Fetch the update message from the API
			String updateMessage = modManager.fetchUpdate("https://api.cocobut.net/modpack-checker?action=checkupdates&client_version=0.3.2");

			LOGGER.info("Update Message: " + updateMessage);

			// Handle the update message based on your logic
			if (updateMessage.contains("lower than the current version")) {
				MinecraftClient.getInstance().execute(() -> {
					screenManager.openScreen(new UpdateScreen());
				});
			} else if (updateMessage.contains("higher than the current version")) {
				MinecraftClient.getInstance().execute(() -> {
					screenManager.openScreen(new UpdateScreenAlt());
				});
			} else {
				LOGGER.info("Mod is up to date");
			}
		} catch (Exception e) {
			LOGGER.error("An error occurred while checking for updates: ", e);
		}
	}
}
