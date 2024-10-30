package ch.neo.neoChallenge.SVC;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemService {
    /**
     * This function is used to create an itemStack by the given attributes.
     * @param material The material of the itemStack.
     * @param displayName The displayName of the itemStack.
     * @param arrayLore The lore of the itemStack.
     * @param amount The amount of the itemStack.
     * @return The itemStack.
     */
    public static ItemStack of_createItemStack(Material material, String displayName, String[] arrayLore, int amount) {
        //  Create the item.
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        if(meta != null)  {
            //  Set the meta attributes.
            meta.setDisplayName(displayName);
            meta.setUnbreakable(true);
            meta.setUnbreakable(true);
            meta = of_setDefaultAttributes2ItemMeta(meta);

            if(arrayLore != null && arrayLore.length > 0) {
                meta.setLore(Arrays.asList(arrayLore));
            }

            if(amount <= 0) {
                amount = 1;
            }

            item.setAmount(amount);
            item.setItemMeta(meta);

            return item;
        }

        return null;
    }

    /**
     * This function creates a player-head-ItemStack from the given playerName.
     * @param playerName The name of the player.
     * @param displayName The displayName of the player-head.
     * @param itemLore The lore of the player-head.
     * @param amount The amount of the player-head.
     * @return The player-head-ItemStack.
     */
    @Deprecated
    public static ItemStack of_createPlayerHead(String playerName, String displayName, String[] itemLore, int amount)
    {
        ItemStack item = new ItemStack(Material.PLAYER_HEAD, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        if(meta == null) {
            return null;
        }

        meta.setOwner(playerName);
        meta.setDisplayName(displayName);
        meta.setUnbreakable(true);

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
        meta.addItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON);

        if(itemLore != null && itemLore.length > 0) {
            List<String> lore = Arrays.asList(itemLore);
            meta.setLore(lore);
        }

        item.setAmount(amount);
        item.setItemMeta(meta);

        return item;
    }

    /**
     * This function sets default attributes to the ItemMeta.
     * @param meta The ItemMeta which should be set.
     * @return The ItemMeta with the default attributes.
     */
    public static ItemMeta of_setDefaultAttributes2ItemMeta(ItemMeta meta)
    {
        if(meta != null) {
            meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_UNBREAKABLE);
            meta.addItemFlags(ItemFlag.HIDE_DESTROYS, ItemFlag.HIDE_PLACED_ON);

            return meta;
        }

        return null;
    }

    /**
     * This function checks if the given ItemStack is containing the given pattern.
     * @param item The ItemStack which should be checked.
     * @param pattern The pattern which should be checked.
     * @return True if the ItemStack is containing the pattern, otherwise false.
     */
    public static boolean of_check4ItemStackWithSpecificPattern(ItemStack item, String pattern) {
        if(item != null && item.hasItemMeta()) {
            if(Objects.requireNonNull(item.getItemMeta()).hasDisplayName()) {
                if(item.getItemMeta().getDisplayName().contains(pattern)) {
                    return true;
                }
                else
                {
                    if(item.getItemMeta().hasLore()) {
                        for(String lore : Objects.requireNonNull(item.getItemMeta().getLore())) {
                            if(lore.contains(pattern)) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public static boolean of_check4ItemStacksWithSpecificPattern(ItemStack[] items, String pattern) {
        if(items!= null) {
            for(ItemStack item : items) {
                if(of_check4ItemStackWithSpecificPattern(item, pattern)) {
                    return true;
                }
            }
        }

        return false;
    }


}

