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
        SimpleFile currentWorlds = new SimpleFile("plugins//Nutils//worlds");
        Integer randomNumber = new Random().nextInt(99999999);
        Integer finalrndmnum = randomNumber;
        String worldName = "world" + finalrndmnum;
        String worldName_N = "world_nether" + finalrndmnum;
        String worldName_E = "world_end"+ finalrndmnum;
        worldsToDelete = Bukkit.getServer().getWorlds().toArray(new World[0]);
        World world = createNewWorldByName(worldName, World.Environment.NORMAL);

        if(world == null) {
            System.out.println("Error while creating the new world: " + worldName + ". Stopped proccessing!!");
            return false;
        }

        Location spawnLoc = world.getSpawnLocation();
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.teleport(spawnLoc);
            SimpleFile worldfile = new SimpleFile("plugins//Nutils//accounts//"+p.getUniqueId());
            worldfile.of_setLocation("locO", spawnLoc);
            worldfile.of_save("WorldSVC");
        }

        World newNether = createNewWorldByName(worldName_N, World.Environment.NETHER);
        World newEnd = createNewWorldByName(worldName_E, World.Environment.THE_END);

        Location nether = newNether.getSpawnLocation();
        Location end = newEnd.getSpawnLocation();
        currentWorlds.of_setLocation("nether", nether);
        currentWorlds.of_setLocation("end", end);
        currentWorlds.of_save("worldSVC");

        for(World currentWorld : worldsToDelete) {
            //deleteWorld(currentWorld);
        }

        return true;
    }

    public World createNewWorldByName(String newWorldName, World.Environment envi) {
        WorldCreator worldCreator = new WorldCreator(newWorldName);
        worldCreator.environment(envi);
        World newWorld = worldCreator.createWorld();
        if (newWorld == null) {
            System.out.println("Could not create the world. " + newWorldName);
            return null;
        }
        newWorld.save();

        return newWorld;
    }

    public void deleteWorld(World world) {
        try {
            if(world.getEnvironment().equals(World.Environment.NORMAL)){
                return;
            }else{
                Bukkit.unloadWorld(world, false);
            }

            for(World currentWorld : worldsToDelete) {
                if(currentWorld.getEnvironment().equals(World.Environment.NORMAL)){
                    return;
                }else{
                    Bukkit.getWorlds().remove(currentWorld);
                }
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
