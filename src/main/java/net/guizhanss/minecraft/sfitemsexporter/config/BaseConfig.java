package net.guizhanss.minecraft.sfitemsexporter.config;

import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import net.guizhanss.minecraft.sfitemsexporter.SfItemsExporter;

import java.io.File;

public class BaseConfig {
    private final Config config;

    public BaseConfig(String filename) {
        File file = new File(SfItemsExporter.getInstance().getDataFolder(), filename);
        if (!file.exists()) {
            file.getParentFile().mkdirs();
            SfItemsExporter.getInstance().saveResource(filename, true);
        }

        this.config = new Config(SfItemsExporter.getInstance(), filename);
    }

    public Config getConfig() {
        return config;
    }

    public void reload() {
        config.reload();
    }
}
