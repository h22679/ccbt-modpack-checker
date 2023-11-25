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
                .setDefaultValue(false)
                .setSaveConsumer(ModConfig::setTurnOn) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.turnOn.line1"),
                        new TranslatableText("tooltip.config.turnOn.line2"),
                        new TranslatableText("tooltip.config.turnOn.line3")
                )
                .build();

        BooleanListEntry autoDownloadEntry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.autoDownload"), ModConfig.getAutoDownload())
                .setDefaultValue(false)
                .setSaveConsumer(ModConfig::setAutoDownload) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.autoDownload.line1")
                )
                .build();

        BooleanListEntry notifyUpdateEntry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.notifyUpdate"), ModConfig.getNotifyUpdate())
                .setDefaultValue(false)
                .setSaveConsumer(ModConfig::setNotifyUpdate) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.notifyUpdate.line1")
                )
                .build();

        BooleanListEntry showNewsEntry = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.showNews"), ModConfig.getShowNews())
                .setDefaultValue(false)
                .setSaveConsumer(ModConfig::setShowNews) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.showNews.line1")
                )
                .build();

        BooleanListEntry useCustomModFolder = builder.entryBuilder()
                .startBooleanToggle(new TranslatableText("config.useCustomModFolder"), ModConfig.getUseCustomFolder())
                .setDefaultValue(false)
                .setSaveConsumer(ModConfig::setUseCustomFolder) // Use a method reference to save the value
                .setTooltip(
                        new TranslatableText("tooltip.config.useCustomModFolder.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        //String fields

        StringListEntry apiDownloadURL = builder.entryBuilder()
                .startStrField(new TranslatableText("config.apiDownloadURL"), ModConfig.getAPIDownloadUrl())
                .setDefaultValue("https://api.cocobut.net/download/")
                .setSaveConsumer(ModConfig::setAPIDownloadUrl)
                .setTooltip(
                        new TranslatableText("tooltip.config.apiDownloadURL.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        StringListEntry apiListURL = builder.entryBuilder()
                .startStrField(new TranslatableText("config.apiListURL"), ModConfig.getAPIDownloadUrl())
                .setDefaultValue("https://api.cocobut.net/modpack-checker?action=listfiles")
                .setSaveConsumer(ModConfig::setAPIDownloadUrl)
                .setTooltip(
                        new TranslatableText("tooltip.config.apiListURL.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        StringListEntry apiNewsURL = builder.entryBuilder()
                .startStrField(new TranslatableText("config.apiNewsURL"), ModConfig.getAPIDownloadUrl())
                .setDefaultValue("https://api.cocobut.net/modpack-checker?action=getnews")
                .setSaveConsumer(ModConfig::setAPIDownloadUrl)
                .setTooltip(
                        new TranslatableText("tooltip.config.apiNewsURL.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        StringListEntry customModFolder = builder.entryBuilder()
                .startStrField(new TranslatableText("config.customModFolder"), ModConfig.getCustomModFolder())
                .setDefaultValue("")
                .setSaveConsumer(ModConfig::setCustomModFolder)
                .setTooltip(
                        new TranslatableText("tooltip.config.customModFolder.line1"),
                        new TranslatableText("tooltip.config.warning")
                )
                .build();

        general.addEntry(turnOnEntry);
        general.addEntry(autoDownloadEntry);
        general.addEntry(notifyUpdateEntry);
        general.addEntry(showNewsEntry);
        technical.addEntry(customModFolder);
        technical.addEntry(useCustomModFolder);
        technical.addEntry(apiListURL);
        technical.addEntry(apiDownloadURL);

        return builder.build();
    }
}