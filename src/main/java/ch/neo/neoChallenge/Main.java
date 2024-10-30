package ch.neo.neoChallenge;

import ch.neo.neoChallenge.CMD.settingsCommand;
import ch.neo.neoChallenge.LST.LST;
import ch.neo.neoChallenge.SVC.Timer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.FileUtil;

import java.io.File;

public final class Main extends JavaPlugin {
    public static Plugin plugin;
    private static Main instance;
    private Timer timer;

    @Override
    public void onLoad() {
        instance = this;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getCommand("settings").setExecutor(new settingsCommand());
        getServer().getPluginManager().registerEvents(new LST(), this);

        timer = new Timer(false, 0);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

    }

    public static Main getInstance() {
        return instance;
    }

    public Timer getTimer() {
        return timer;
    }
}

