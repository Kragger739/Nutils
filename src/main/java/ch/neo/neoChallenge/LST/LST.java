package ch.neo.neoChallenge.LST;

import ch.neo.neoChallenge.Main;
import ch.neo.neoChallenge.SVC.ItemService;
import ch.neo.neoChallenge.SVC.SimpleFile;
import ch.neo.neoChallenge.SVC.Timer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

import static ch.neo.neoChallenge.Main._WORLDSVC;
import static ch.neo.neoChallenge.Main.plugin;



public class LST implements Listener {

    private final HashMap<UUID, Location> playerLocations = new HashMap<>();
    //private World[] worldsToDelete;
    @EventHandler
    public void onClick(InventoryClickEvent e){
        Timer timer = Main.getInstance().getTimer();
        Player p = (Player) e.getWhoClicked();

        //p.sendMessage(""+ e.getView().getTitle());

        if (e.getView().getTitle().equals("Settings"))
        {
            e.setCancelled(true);
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem != null && currentItem.getType() == Material.RED_STAINED_GLASS_PANE)
            {
                //kickAllPlayers("world reset");
                //worldsToDelete = Bukkit.getServer().getWorlds().toArray(new World[0]);

                boolean newWorldsCreated = _WORLDSVC.generateNewWorldsAndDeleteTheOldOnes();
                timer.setTime(0);

                if(!newWorldsCreated) {
                    p.sendMessage("§cKonnte keine neue Welt erstellten!");
                }
            } else if (currentItem != null && currentItem.getType() == Material.CLOCK) {
                Inventory gui = Bukkit.createInventory(p, 27, "Timer Settings");

                gui.setItem(13, ItemService.of_createItemStack(Material.GREEN_STAINED_GLASS_PANE, "§a§lStart Timer", null, 1));
                gui.setItem(16, ItemService.of_createItemStack(Material.ORANGE_STAINED_GLASS_PANE, "§6§lPause Timer", null, 1));
                gui.setItem(10, ItemService.of_createItemStack(Material.PURPLE_STAINED_GLASS_PANE, "§5§lResume Timer", null, 1));

                p.openInventory(gui);
            }
        } else if (e.getView().getTitle().equals("Timer Settings")) {
            e.setCancelled(true);
            ItemStack currentItem = e.getCurrentItem();
            if (currentItem != null && currentItem.getType() == Material.GREEN_STAINED_GLASS_PANE){
                timer.setRunning(true);
                p.sendMessage("§a§lDer Timer wurde gestartet und endet in: "+ TimeFormatter.formatTime(timer.trialTime()));
                p.closeInventory();
            } else if (currentItem != null && currentItem.getType() == Material.ORANGE_STAINED_GLASS_PANE) {
                if(timer.isRunning()){
                    p.sendMessage("§c§lDer Timer wurde Pausiert");
                    timer.setRunning(false);
                }
            } else if (currentItem != null && currentItem.getType() == Material.PURPLE_STAINED_GLASS_PANE) {
                p.sendMessage("timer");
                timer.setRunning(true);

            }
        }
    }
    @EventHandler
    public void onPlayerPortal(PlayerPortalEvent event) {
        SimpleFile currentWorlds = new SimpleFile("plugins//Nutils//worlds");
        Player player = event.getPlayer();
        UUID playerUUID = player.getUniqueId();

        if(event.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL){
        // Check if the player already has a stored location
        if (playerLocations.containsKey(playerUUID)) {
            // Teleport the player to the stored location
            Location savedLocation = playerLocations.get(playerUUID);
            Location loc = new Location(savedLocation.getWorld(), savedLocation.getX(), savedLocation.getY(), savedLocation.getZ());
            player.teleport(loc);
            //player.sendMessage("You have been teleported back to your last saved location!");
            event.setCancelled(true);
            playerLocations.remove(playerUUID);
        } else {
            // Save the player's current location
            Location currentLocation = player.getLocation();
            playerLocations.put(playerUUID, currentLocation);
            player.teleport(currentWorlds.of_getLocationByKey("nether").add(currentLocation.getX()/8, currentLocation.getY(), currentLocation.getZ()/8));
            //player.sendMessage("Your location has been saved! Use the portal again to return here.");
            event.setCancelled(true);
        }} else if (event.getCause() == PlayerTeleportEvent.TeleportCause.END_PORTAL) {
            player.teleport(currentWorlds.of_getLocationByKey("end"));
        }
    }
    @EventHandler
    public void onJoin(PlayerJoinEvent e){
        Player p = e.getPlayer();
        SimpleFile worldfile = new SimpleFile("plugins//Nutils//accounts//"+p.getUniqueId());
        Location loc = worldfile.of_getLocationByKey("locO");
        if(worldfile == null){
            return;
        }else{
            p.teleport(loc);
        }
    }
    @EventHandler
    public void onleave(PlayerQuitEvent e){
        Player p = e.getPlayer();
        SimpleFile worldfile = new SimpleFile("plugins//Nutils//accounts//"+p.getUniqueId());
        Location quitloc = p.getLocation();
        worldfile.of_setLocation("locO", quitloc);
        worldfile.of_save("QuitEvent");
    }
    @EventHandler
    public void onDeath(PlayerRespawnEvent e){
        Player p = e.getPlayer();
        SimpleFile worldfile = new SimpleFile("plugins//Nutils//accounts//"+p.getUniqueId());
        Location loc = worldfile.of_getLocationByKey("locO");

        if(e.isBedSpawn() == true){
            return;
        }else{
            e.setRespawnLocation(loc);
        }
    }
    public void kickAllPlayers(String kickMessage) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer(kickMessage);
        }
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
    /*
    private void deletWorld(){
        new BukkitRunnable(){

            @Override
            public void run() {

            }
        }.runTaskLater(Main.getInstance(), 20*50);
    }*/
}

