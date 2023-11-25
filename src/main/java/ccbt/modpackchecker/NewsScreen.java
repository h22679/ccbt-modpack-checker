package ccbt.modpackchecker;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
public class NewsScreen extends Screen {

    ScreenManager screenManager = ScreenManager.getInstance();

    private final List<Text> newsLines;
    private final Identifier modIcon = new Identifier("ccbt-modpack-checker", "icon.png");

    public NewsScreen(String newsText) {
        super(Text.of("News"));
        this.newsLines = new ArrayList<>();

        // Split the newsText into lines based on newline characters
        String[] lines = newsText.split("\n");

        // Convert the lines into Text objects
        for (String line : lines) {
            newsLines.add(new LiteralText(line));
        }
    }

    @Override
    protected void init() {
        super.init();
        int buttonWidth = 100;
        int buttonHeight = 20;
        int x = this.width / 2 - buttonWidth / 2;
        int y = this.height - 30;

        this.addDrawableChild(new ButtonWidget(x, y, buttonWidth, buttonHeight, Text.of("Close"), button -> {
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
        this.renderBackground(matrices);

        // Draw the mod icon at the top, doesn't work but idk how to fix it
        int iconWidth = 32;
        int iconHeight = 32;
        int iconX = this.width / 2 - iconWidth / 2;
        int iconY = 10;
        MinecraftClient.getInstance().getTextureManager().bindTexture(modIcon);
        drawTexture(matrices, iconX, iconY, 0, 0, iconWidth, iconHeight, iconWidth, iconHeight);

        int textY = this.height / 2 - (newsLines.size() * 10 / 2); // Adjust the vertical position
        int lineHeight = 10;

        for (Text line : newsLines) {
            drawCenteredTextWithShadow(matrices, textRenderer, line.asOrderedText(), this.width / 2, textY, 0xFFFFFF);
            textY += lineHeight;
        }

        super.render(matrices, mouseX, mouseY, delta);
    }
}
