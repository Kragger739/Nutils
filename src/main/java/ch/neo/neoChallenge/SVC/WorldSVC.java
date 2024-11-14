package ch.neo.neoChallenge.SVC;

import ch.neo.neoChallenge.Main;
import org.bukkit.*;

import java.io.File;
import java.util.Random;

import org.bukkit.entity.Player;


public class WorldSVC
{
    //MultiverseCore core = (MultiverseCore) Bukkit.getServer().getPluginManager().getPlugin("Multiverse-Core");
    //MVWorldManager worldManager = core.getMVWorldManager();
    /* ************************************* */
    /* CONSTRUCTOR */
    /* ************************************* */

    private World[] worldsToDelete;

    public WorldSVC() { }

    public boolean generateNewWorldsAndDeleteTheOldOnes() {
        Integer randomNumber = new Random().nextInt(99999999);
        String worldName = "world" + randomNumber;
        worldsToDelete = Bukkit.getServer().getWorlds().toArray(new World[0]);
        World world = createNewWorldByName(worldName, World.Environment.NORMAL);

        if(world == null) {
            System.out.println("Error while creating the new world: " + worldName + ". Stopped proccessing!!");
            return false;
        }

        Location spawnLoc = world.getSpawnLocation();
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(spawnLoc);
        }

        createNewWorldByName(worldName + "_nether", World.Environment.NETHER);
        createNewWorldByName(worldName + "_end", World.Environment.THE_END);

        for(World currentWorld : worldsToDelete) {
            deleteWorld(currentWorld);
        }

        return true;
    }

    public World createNewWorldByName(String newWorldName, World.Environment envi) {
        WorldCreator worldCreator = new WorldCreator(newWorldName);
        worldCreator.environment(envi);
        worldCreator.createWorld();
        World newWorld = Bukkit.getWorld(newWorldName);

        if (newWorld == null) {
            System.out.println("Could not create the world. " + newWorldName);
            return null;
        }

        return newWorld;
    }

    public void deleteWorld(World world) {
        try {
            Bukkit.unloadWorld(world.getName(), false);

            for(World currentWorld : worldsToDelete) {
                Bukkit.getWorlds().remove(currentWorld);
            }

            File file = world.getWorldFolder();
            deleteWorldByDirectory(file);
        } catch (Exception ignored) {}
    }

    public void deleteWorldByDirectory(File file) {
        if (file != null && file.length() != 0L && file.exists())
        {
            try {
                new SimpleFile().deleteRecursive(file.getAbsoluteFile());
                // FileUtils....
                return;
            } catch (Exception exception) { }
        }
    }
}
