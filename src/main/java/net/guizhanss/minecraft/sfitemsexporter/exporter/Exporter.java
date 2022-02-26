package net.guizhanss.minecraft.sfitemsexporter.exporter;

import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import io.github.thebusybiscuit.slimefun4.api.items.ItemSetting;
import io.github.thebusybiscuit.slimefun4.api.items.SlimefunItem;
import io.github.thebusybiscuit.slimefun4.core.attributes.EnergyNetComponent;
import io.github.thebusybiscuit.slimefun4.core.attributes.Radioactive;
import io.github.thebusybiscuit.slimefun4.implementation.Slimefun;
import io.github.thebusybiscuit.slimefun4.libraries.dough.config.Config;
import net.guizhanss.guizhanlib.minecraft.helper.inventory.ItemStackHelper;
import net.guizhanss.minecraft.sfitemsexporter.SfItemsExporter;
import net.guizhanss.minecraft.sfitemsexporter.config.ConfigManager;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * This exporter is modified from original SfItemsExporter software.
 * @see <a href="https://github.com/WalshyDev/SFItemsExporter">SfItemsExporter</a>
 *
 * @author Walshy
 * @author TheBusyBiscuit
 * @author ybw0014
 */
public class Exporter {
    private static JsonArray root;

    private Exporter() {
        throw new IllegalStateException("Utility class");
    }

    public static void export() {
        loadItems();

        File file = new File(SfItemsExporter.getInstance().getDataFolder(), "items.json");

        try (Writer fw = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);) {
            fw.write(root.toString());
            fw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadItems() {
        root = new JsonArray();
        for (SlimefunItem item : Slimefun.getRegistry().getAllSlimefunItems()) {
            JsonObject jsonObj = new JsonObject();
            jsonObj.addProperty("id", item.getId());

            if (ConfigManager.getConfig().getBoolean("detailed-item")) {
                jsonObj.add("item", getAsJson(item.getItem()));
            } else {
                jsonObj.addProperty("name", stripColor(ItemStackHelper.getDisplayName(item.getItem())));
            }

            if (ConfigManager.getConfig().getBoolean("category")) {
                jsonObj.addProperty("category", item.getItemGroup().getUnlocalizedName());
                jsonObj.addProperty("categoryTier", item.getItemGroup().getTier());
            }

            if (ConfigManager.getConfig().getBoolean("item-settings")) {
                final JsonObject itemSettings = new JsonObject();

                for (ItemSetting<?> setting : item.getItemSettings()) {
                    itemSettings.addProperty(setting.getKey(), String.valueOf(setting.getDefaultValue()));
                }

                jsonObj.add("settings", itemSettings);

                jsonObj.addProperty("useableInWorkbench", item.isUseableInWorkbench());
            }

            loadRecipe(item, jsonObj);

            if (ConfigManager.getConfig().getBoolean("research") && item.getResearch() != null) {
                jsonObj.addProperty("research", item.getResearch().getKey().toString());
                jsonObj.addProperty("researchCost", item.getResearch().getCost());
            }

            if (ConfigManager.getConfig().getBoolean("electric")) {
                jsonObj.addProperty("electric", item instanceof EnergyNetComponent);
                if (item instanceof EnergyNetComponent) {
                    EnergyNetComponent component = (EnergyNetComponent) item;
                    jsonObj.addProperty("electricType", component.getEnergyComponentType().toString());
                    jsonObj.addProperty("electricCapacity", component.getCapacity());
                }
            }

            if (ConfigManager.getConfig().getBoolean("radioactive")) {
                jsonObj.addProperty("radioactive", item instanceof Radioactive);

                if (item instanceof Radioactive) {
                    jsonObj.addProperty("radioactivityLevel", ((Radioactive) item).getRadioactivity().toString());
                }
            }

            root.add(jsonObj);
        }
    }

    private static String stripColor(String str) {
        if (ConfigManager.getConfig().getBoolean("strip-color")) {
            return ChatColor.stripColor(str);
        } else {
            return str;
        }
    }

    /**
     * This loads the recipe for this {@link SlimefunItem} into the specified {@link JsonObject}.
     *
     * @param item The {@link SlimefunItem}
     * @param json Our target {@link JsonObject}
     */
    private static void loadRecipe(SlimefunItem item, JsonObject json) {
        if (item.getRecipeType() != null && item.getRecipe() != null) {
            json.addProperty("recipeType", item.getRecipeType().getKey().toString());

            JsonArray recipe = new JsonArray();
            for (ItemStack is : item.getRecipe()) {
                if (is != null) {
                    recipe.add(getAsJson(is));
                } else {
                    recipe.add(JsonNull.INSTANCE);
                }

            }
            json.add("recipe", recipe);
        }
    }

    /**
     * This converts the given {@link ItemStack} into a {@link JsonObject}.
     *
     * @param is Our {@link ItemStack}.
     * @return The {@link JsonObject}-representation of this {@link ItemStack}
     */
    private static JsonObject getAsJson(final ItemStack is) {
        final JsonObject json = new JsonObject();
        json.addProperty("material", is.getType().toString());

        if (is.getAmount() > 1) {
            json.addProperty("amount", is.getAmount());
        }

        final ItemMeta im = is.getItemMeta();
        if (im.hasDisplayName()) {
            json.addProperty("name", im.getDisplayName().replace(ChatColor.COLOR_CHAR, '&'));
        }

        final JsonArray lore = new JsonArray();
        if (im.hasLore()) {
            im.getLore().stream().map(s -> s.replace(ChatColor.COLOR_CHAR, '&')).forEach(lore::add);
        }
        json.add("lore", lore);

        return json;
    }
}
