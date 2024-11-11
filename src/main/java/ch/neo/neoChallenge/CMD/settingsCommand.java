package ch.neo.neoChallenge.CMD;

import ch.neo.neoChallenge.SVC.ItemService;
import ch.neo.neoChallenge.SVC.SimpleFile;
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
        Player p =(Player)sender;
        if (command.getName().equalsIgnoreCase("settings"))
        {

        }if(args.length == 2)
        {
            String time = args[0];
            if(time.equalsIgnoreCase("time")){
                SimpleFile settingsFile = new SimpleFile("plugins//Nutils//time.yml");
                String trialTime = args[1];

                settingsFile.of_getSetInt("time", 18000);

                settingsFile.of_save("settings");
                /*
                if(settingsFile.of_fileExists()){


                }else{
                    p.sendMessage("§6lSettings File not found!");
                    settingsFile.of_getSetInt("time", 18000);
                }*/
            }
    } else if (args.length == 0) {
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
