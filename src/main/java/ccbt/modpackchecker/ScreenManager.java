package ccbt.modpackchecker;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

import java.util.Stack;

public class ScreenManager {
    private static ScreenManager instance;
    private final Stack<Screen> screenStack = new Stack<>();
    private final MinecraftClient client;

    public ScreenManager(MinecraftClient client) {
        this.client = client;
    }

    public static ScreenManager getInstance() {
        if (instance == null) {
            instance = new ScreenManager(MinecraftClient.getInstance());
        }
        return instance;
    }

    public void openScreen(Screen screen) {
        if (!screenStack.isEmpty()) {
            client.setScreen(null); // hide the current screen
        }
        screenStack.push(screen);
        client.setScreen(screen);
    }

    public void closeScreen() {
        if (!screenStack.isEmpty()) {
            screenStack.pop();
            if (!screenStack.isEmpty()) {
                client.setScreen(screenStack.peek());
            } else {
                client.setScreen(null);
            }
        }
    }

    public Screen getCurrentScreen() {
        return screenStack.isEmpty() ? null : screenStack.peek();
    }

}
