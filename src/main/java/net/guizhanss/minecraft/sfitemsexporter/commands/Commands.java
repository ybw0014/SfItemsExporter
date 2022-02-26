package net.guizhanss.minecraft.sfitemsexporter.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import net.guizhanss.minecraft.sfitemsexporter.config.ConfigManager;
import net.guizhanss.minecraft.sfitemsexporter.exporter.Exporter;
import net.guizhanss.minecraft.sfitemsexporter.utils.ChatUtil;
import org.bukkit.command.CommandSender;

@CommandAlias("sfie|sfexporter")
public class Commands extends BaseCommand {
    @Default
    public void onDefault(CommandSender sender) {
        ChatUtil.send(sender, "&6==== &a&lSfItemsExporter &6- by &bybw0014 &6====");
        ChatUtil.send(sender, "&fAlias: sfitemsexporter, sfie, sfexporter");
        ChatUtil.send(sender, "&e/sfie generate &7&l- &fGenerate items json");
        ChatUtil.send(sender, "&e/sfie reload &7&l- &fReload exporter configuration");
    }

    @Subcommand("generate")
    @CommandPermission("sfitemexporter.generate")
    @Description("Generate items json")
    public void generate(CommandSender sender) {
        Exporter.export();
    }

    @Subcommand("reload")
    @CommandPermission("sfitemexporter.reload")
    @Description("Reload exporter config")
    public void reload(CommandSender sender) {
        ConfigManager.reloadAll();
    }
}
