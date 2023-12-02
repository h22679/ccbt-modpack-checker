package ccbt.modpackchecker;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Util;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class UpdateScreen extends Screen {

    ScreenManager screenManager = ScreenManager.getInstance();

    public UpdateScreen() {
        super(Text.of("Update Available"));
    }

    @Override
    protected void init() {
        super.init();
        int buttonWidth = 100;
        int buttonHeight = 20;

        // Calculate the starting X position for the buttons
        int startX = this.width / 2 - (3 * buttonWidth + 10) / 2; // 10 pixels total padding between buttons

        int y = this.height / 2 - buttonHeight / 2 + 10; // Common Y coordinate for all buttons

        // Open URL Button
        this.addDrawableChild(new ButtonWidget(
                startX, y, buttonWidth, buttonHeight,
                Text.of("Open URL"),
                button -> {
                    Util.getOperatingSystem().open("https://github.com/h22679/ccbt-modpack-checker");
                }
        ));

        // Close Screen Button
        this.addDrawableChild(new ButtonWidget(
                startX + buttonWidth + 5, y, buttonWidth, buttonHeight, // Adjust X position for layout
                Text.of("Close"),
                button -> {
                    closeAndOpenNextScreen(screenManager, null);
                }));

        // Copy URL Button
        this.addDrawableChild(new ButtonWidget(
                startX + 2 * (buttonWidth + 5), y, buttonWidth, buttonHeight, // Adjust X position for layout
                Text.of("Copy URL"),
                button -> {
                    MinecraftClient.getInstance().keyboard.setClipboard("https://github.com/h22679/ccbt-modpack-checker");
                }
        ));
    }

    private void closeAndOpenNextScreen(ScreenManager screenManager, Screen nextScreen) {
        screenManager.closeScreen(); // This will close the current screen
        if (nextScreen != null) {
            screenManager.openScreen(nextScreen); // Open the next screen if it's not null
        }
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices); // Render the background
        super.render(matrices, mouseX, mouseY, delta); // Ensure any buttons or other elements are rendered

        Text line1 = new LiteralText("A new \"Mod pack checker\" update is available");
        Text line2 = new LiteralText("Do you want to download it?");

        // Convert Text to OrderedText
        OrderedText orderedText1 = line1.asOrderedText();
        OrderedText orderedText2 = line2.asOrderedText();

        // Render the centered text
        drawCenteredTextWithShadow(matrices, textRenderer, orderedText1, width / 2, height / 2 - 60, 0xFFFFFF);
        drawCenteredTextWithShadow(matrices, textRenderer, orderedText2, width / 2, height / 2 - 40, 0xFFFFFF);
    }
}
