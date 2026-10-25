package puppy.code.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import puppy.code.Constantes;
import puppy.code.GameLluvia;

public final class Lwjgl3Launcher {
    public static void main(String[] args) {
        if (StartupHelper.startNewJvmIfRequired()) return;
        new Lwjgl3Application(new GameLluvia(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration config
                = new Lwjgl3ApplicationConfiguration();
        config.setTitle("GameLluvia2024");
        config.useVsync(true);
        config.setForegroundFPS(60);
        config.setWindowedMode(Constantes.ANCHO, Constantes.ALTO);
        config.setResizable(false);
        config.setWindowIcon("libgdx128.png", "libgdx64.png",
                "libgdx32.png", "libgdx16.png");
        return config;
    }
}
