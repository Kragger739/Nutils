package ch.neo.neoChallenge.CMD;

import ch.neo.neoChallenge.SVC.ItemService;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class settingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("settings"))
        {
            Player p =(Player)sender;
            openGui(p);
        }
        return false;
    }

    private void openGui(Player p){
        Inventory gui = Bukkit.createInventory(p, 27, "Settings");

        gui.setItem(10, ItemService.of_createItemStack(Material.RED_STAINED_GLASS_PANE, "Reset World", null, 1));

        gui.setItem(13, ItemService.of_createItemStack(Material.CLOCK, "Timer settings", null, 1));

        p.openInventory(gui);
    }
}
