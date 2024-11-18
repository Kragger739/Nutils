package ch.neo.neoChallenge.CMD;

import ch.neo.neoChallenge.Main;
import ch.neo.neoChallenge.SVC.ItemService;
import ch.neo.neoChallenge.SVC.SimpleFile;
import ch.neo.neoChallenge.SVC.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class settingsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if (command.getName().equalsIgnoreCase("settings"))
        {
            Player p = (Player)sender;
            openGui(p);

        } else if (command.getName().equalsIgnoreCase("timer"))
        {
            Timer timer = Main.getInstance().getTimer();
            SimpleFile settingsFile = new SimpleFile("plugins//Nutils//time.yml");
            Player p = (Player)sender;
            settingsFile.of_getSetInt("time", 18000);

            settingsFile.of_save("settings");
            if(args.length == 0){
                p.sendMessage("§a§lTimer ist auf "+TimeFormatter.formatTime(timer.trialTime()));
            }else{
                String trialTime = args[0];
                try{
                    int finaltime = Integer.parseInt(trialTime);
                    p.sendMessage("Zeit wurde auf "+TimeFormatter.formatTime(finaltime)+" gestellt");
                    settingsFile.of_set("time", finaltime);
                    settingsFile.of_save("timer command");
                }catch (Exception e){

                }
            }

        }
        return false;

    }

    private void openGui(Player p){
        Inventory gui = Bukkit.createInventory(p, 27, "Settings");

        gui.setItem(10, ItemService.of_createItemStack(Material.RED_STAINED_GLASS_PANE, "Reset World", null, 1));

        gui.setItem(13, ItemService.of_createItemStack(Material.CLOCK, "Timer settings", null, 1));

        p.openInventory(gui);
    }

    public class TimeFormatter {

        public static String formatTime(long seconds) {
            long hours = seconds / 3600;
            long minutes = (seconds % 3600) / 60;
            long secs = seconds % 60;

            if (hours > 0) {
                return String.format("%02d:%02d:%02d", hours, minutes, secs);
            } else if (minutes > 0) {
                return String.format("%02d:%02d", minutes, secs);
            } else {
                return String.format("%02d", secs);
            }
        }
    }
}
