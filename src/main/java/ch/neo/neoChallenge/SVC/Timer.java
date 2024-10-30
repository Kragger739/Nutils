package ch.neo.neoChallenge.SVC;

import ch.neo.neoChallenge.Main;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitScheduler;

public class Timer {
    private boolean running;
    private int time;

    public Timer(boolean running, int time){
        this.running = running;
        this.time = time;

        run();
    }

    public int getTime() {
        return time;
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
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(ChatColor.GOLD.toString() + ChatColor.BOLD + getTime()));
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

                if(getTime()== 60){
                    for(Player player : Bukkit.getOnlinePlayers()){
                        player.setGameMode(GameMode.SPECTATOR);
                    }
                }

                setTime(getTime()+ 1);
            }
        }.runTaskTimer(Main.getInstance(), 20, 20);
    }
}
