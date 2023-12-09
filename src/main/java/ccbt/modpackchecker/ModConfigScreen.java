package ccbt.modpackchecker;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import me.shedaniel.clothconfig2.gui.entries.StringListEntry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.TranslatableText;

@Environment(EnvType.CLIENT)
public class ModConfigScreen {

    public static Screen getConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(new TranslatableText("config.title"));

        // Settings categories
        ConfigCategory general = builder.getOrCreateCategory(new TranslatableText("configCategory.general"));
        ConfigCategory technical = builder.getOrCreateCategory(new TranslatableText("configCategory.technical"));

        // Boolean fields
        BooleanListEntry turnOnEntry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.turnOn"), ModConfig.getTurnOn())
                .setDefaultValue(ModConfig.getTurnOn()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setTurnOn) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.turnOn.line1"),
                        new TranslatableText("tooltip.config.turnOn.line2"),
                        new TranslatableText("tooltip.config.turnOn.line3")
                )
                .build();

        BooleanListEntry autoDownloadEntry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.autoDownload"), ModConfig.getAutoDownload())
                .setDefaultValue(ModConfig.getAutoDownload()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setAutoDownload) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.autoDownload.line1")
                )
                .build();

        BooleanListEntry notifyUpdateEntry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.notifyUpdate"), ModConfig.getNotifyUpdate())
                .setDefaultValue(ModConfig.getNotifyUpdate()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setNotifyUpdate) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.notifyUpdate.line1")
                )
                .build();

        BooleanListEntry showNewsEntry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.showNews"), ModConfig.getShowNews())
                .setDefaultValue(ModConfig.getShowNews()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setShowNews) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.showNews.line1")
                )
                .build();

        BooleanListEntry useCustomModFolderEntry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.useCustomModFolder"), ModConfig.getUseCustomFolder())
                .setDefaultValue(ModConfig.getUseCustomFolder()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setUseCustomFolder) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.useCustomModFolder.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        // String fields
        StringListEntry apiDownloadURLEntry = builder.entryBuilder()
                .startStrField(new TranslatableText("config.apiDownloadURL"), ModConfig.getAPIDownloadUrl())
                .setDefaultValue(ModConfig.getAPIDownloadUrl()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setAPIDownloadUrl) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.apiDownloadURL.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        StringListEntry apiListURLEntry = builder.entryBuilder()
                .startStrField(new TranslatableText("config.apiListURL"), ModConfig.getApiListUrl())
                .setDefaultValue(ModConfig.getApiListUrl()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setApiListUrl) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.apiListURL.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        StringListEntry apiNewsURLEntry = builder.entryBuilder()
                .startStrField(new TranslatableText("config.apiNewsURL"), ModConfig.getApiNewsUrl())
                .setDefaultValue(ModConfig.getApiNewsUrl()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setApiNewsUrl) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.apiNewsURL.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        StringListEntry customModFolderEntry = builder.entryBuilder()
                .startStrField(new TranslatableText("config.customModFolder"), ModConfig.getCustomModFolder())
                .setDefaultValue(ModConfig.getCustomModFolder()) // Use the default from ModConfig
                .setSaveConsumer(ModConfig::setCustomModFolder) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.customModFolder.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        // Add entries to categories
        general.addEntry(turnOnEntry);
        general.addEntry(autoDownloadEntry);
        general.addEntry(notifyUpdateEntry);
        general.addEntry(showNewsEntry);
        technical.addEntry(useCustomModFolderEntry);
        technical.addEntry(apiListURLEntry);
        technical.addEntry(apiDownloadURLEntry);
        technical.addEntry(apiNewsURLEntry);
        technical.addEntry(customModFolderEntry);

        return builder.build();
    }
}
