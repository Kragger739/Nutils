package ch.neo.neoChallenge.SVC;

import ch.neo.neoChallenge.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class Timer {
    private boolean running;
    private int time;
    SimpleFile settingsFile = new SimpleFile("plugins//Nutils//time.yml");

    public Timer(boolean running, int time){
        this.running = running;
        this.time = time;

        run();
    }

    public int endTime(int time){
        return time;
    }

    public int getTime() {
        return time;
    }
    public int trialTime(){
        Integer trialtTime = settingsFile.of_getIntByKey("time");
        return trialtTime;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void sendActionBar(){
        for (Player player: Bukkit.getOnlinePlayers()){
            if(!isRunning()){
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.DARK_RED.toString() + ChatColor.BOLD + "Timer ist pausiert"));
                continue;
            }
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD.toString() + ChatColor.BOLD + TimeFormatter.formatTime(getTime())));
        }
    }

    private void run(){
        new BukkitRunnable(){
            @Override
            public void run()
            {
                sendActionBar();

                if(!isRunning()){
                    return;
                }

                if(getTime()== endTime(trialTime())){
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.setGameMode(GameMode.SPECTATOR);
                        player.playSound(player, Sound.ENTITY_WITHER_SPAWN,1,1);
                        player.sendTitle("§4§lDie Zeit ist abgelaufen", null, 15, 50, 20);
                        setRunning(false);
                    }
                }

                setTime(getTime()+ 1);
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
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
