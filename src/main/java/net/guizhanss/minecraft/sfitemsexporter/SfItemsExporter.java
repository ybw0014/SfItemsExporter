package net.guizhanss.minecraft.sfitemsexporter;

import co.aikar.commands.PaperCommandManager;
import io.github.thebusybiscuit.slimefun4.api.SlimefunAddon;
import net.guizhanss.minecraft.sfitemsexporter.commands.Commands;
import net.guizhanss.minecraft.sfitemsexporter.config.ConfigManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nonnull;
import java.util.logging.Level;

/**
 * A Slimefun addon that can export all registered items to json file
 *
 * @author ybw0014
 */
public final class SfItemsExporter extends JavaPlugin implements SlimefunAddon {

    private static SfItemsExporter instance;

    @Override
    public void onEnable() {
        instance = this;

        getLogger().log(Level.INFO, "Enabling SfItemsExporter...");
        getLogger().log(Level.INFO, "Author: ybw0014");
        getLogger().log(Level.INFO, "Thanks Walshy and TheBusyBiscuit for the original SfItemsExporter software.");

        // config
        ConfigManager.setup();

        // commands
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new Commands());

        getLogger().log(Level.INFO, "SfItemsExporter has been enabled.");
    }

    @Override
    public void onDisable() {
        instance = null;
    }

    @Override
    public String getBugTrackerURL() {
        return "https://github.com/ybw0014/SfItemsExporter/issues";
    }

    @Override
    @Nonnull
    public JavaPlugin getJavaPlugin() {
        return this;
    }

    public static SfItemsExporter getInstance() {
        return instance;
    }

}
