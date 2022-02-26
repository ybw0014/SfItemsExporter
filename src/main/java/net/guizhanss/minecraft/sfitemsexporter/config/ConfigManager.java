package net.guizhanss.minecraft.sfitemsexporter.config;

import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;

public class ConfigManager {
    private ConfigManager() {
        throw new IllegalStateException("Utility class");
    }

    private static PluginConfig config;
    public static void setup() {
        config = new PluginConfig();
    }

    public static Config getConfig() {
        return config.getConfig();
    }

    public static void reloadAll() {
        config.reload();
    }
}
