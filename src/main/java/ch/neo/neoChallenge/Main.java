package ch.neo.neoChallenge;

import ch.neo.neoChallenge.CMD.settingsCommand;
import ch.neo.neoChallenge.LST.LST;
import ch.neo.neoChallenge.SVC.Timer;
import ch.neo.neoChallenge.SVC.WorldSVC;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;


public final class Main extends JavaPlugin {
    public static Plugin plugin;
    private static Main instance;
    private Timer timer;
    public static WorldSVC _WORLDSVC;


    @Override
    public void onEnable() {
        // Plugin startup logic
        instance = this;
        getCommand("settings").setExecutor(new settingsCommand());
        getServer().getPluginManager().registerEvents(new LST(), this);

        timer = new Timer(false, 0);
        _WORLDSVC = new WorldSVC();

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

