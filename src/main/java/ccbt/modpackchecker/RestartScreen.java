package ccbt.modpackchecker;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class RestartScreen extends Screen {

    ScreenManager screenManager = ScreenManager.getInstance();
    public RestartScreen() {
        super(Text.of("Restart Required"));
    }

    @Override
    protected void init() {
        super.init();
        int buttonWidth = 100;
        int buttonHeight = 20;

        // X coordinate for the first button
        int x1 = this.width / 2 - buttonWidth - 5; // 5 pixels padding between buttons
        // X coordinate for the second button
        int x2 = this.width / 2 + 5; // 5 pixels padding from the center

        int y = this.height / 2 - buttonHeight / 2 + 10; // Common Y coordinate for both buttons

        // Quit Game Button
        this.addDrawableChild(new ButtonWidget(
                x1, y, buttonWidth, buttonHeight,
                Text.of("Quit Game"),
                button -> this.client.scheduleStop()
        ));

        // Close Screen Button
        this.addDrawableChild(new ButtonWidget(
                x2, y, buttonWidth, buttonHeight,
                Text.of("Close"),
                button -> {
                    closeAndOpenNextScreen(screenManager, null);
                }));

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

        Text line1 = new LiteralText("New mod files have been downloaded.");
        Text line2 = new LiteralText("Please restart your game");

        // Convert Text to OrderedText
        OrderedText orderedText1 = line1.asOrderedText();
        OrderedText orderedText2 = line2.asOrderedText();

        // Render the centered text
        drawCenteredTextWithShadow(matrices, textRenderer, orderedText1, width / 2, height / 2 - 60, 0xFFFFFF);
        drawCenteredTextWithShadow(matrices, textRenderer, orderedText2, width / 2, height / 2 - 40, 0xFFFFFF);
    }
}
