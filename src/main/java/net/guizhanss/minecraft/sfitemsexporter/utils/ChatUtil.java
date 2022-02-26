package net.guizhanss.minecraft.sfitemsexporter.utils;

import io.github.thebusybiscuit.slimefun4.libraries.dough.common.ChatColors;
import org.bukkit.command.CommandSender;

public class ChatUtil {
    private ChatUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void send(CommandSender sender, String message) {
        sender.sendMessage(ChatColors.color(message));
    }
}
