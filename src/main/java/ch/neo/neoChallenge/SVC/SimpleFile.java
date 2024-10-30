package ch.neo.neoChallenge.SVC;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SimpleFile
{
    private File file;
    public YamlConfiguration cfg;

    /* ************************************* */
    /* CONSTRUCTOR */
    /* ************************************* */

    /**
     * Constructor
     * @param absolutePath Absolute file path for example: 'plugins\\Plugin\\others\\settings.yml'
     */
    public SimpleFile(String absolutePath)
    {
        //	We correct the file-path in case if it's wrong!
        absolutePath = absolutePath.replace("\\", "//");

        //	Add a '.yml' to avoid errors.
        if(!absolutePath.contains(".yml"))
        {
            absolutePath += ".yml";
        }

        this.file = new File(absolutePath);
        this.cfg = new YamlConfiguration();

        //  Initialize the configuration.
        of_initializeData("constructor(String);");
    }

    /**
     * Constructor
     * @param file File object.
     */
    public SimpleFile(File file)
    {
        this.file = file;
        this.cfg = new YamlConfiguration();

        //  Initialize the configuration.
        of_initializeData("constructor(File);");
    }

    private void of_initializeData(String invokerName)
    {
        try
        {
            this.cfg.load(file);
        }
        catch (Exception ignored) { }
    }

    /**
     * This function deletes the file of this object.
     */
    public void of_delete()
    {
        if(file != null)
        {
            file.delete();
        }
    }

    /* ************************************* */
    /* GET-SET-Method */
    /* ************************************* */

    /**
     * This function checks if the current configKey is already set.
     * If the configKey is not set the defaultValue will be set and also returned.
     * If the configKey is already set, the value will be returned.
     * @param configKey Section in the .YML-File.
     * @param defaultValue Default value which will be used as initialize value for the given section.
     * @return The value in the configKey or the defaultValue if the configKey is not given.
     */
    public String of_getSetString(String configKey, String defaultValue)  {
        String tmpValue;

        if(cfg.isSet(configKey))  {
            tmpValue = cfg.getString(configKey);

            if(tmpValue == null)  {
                tmpValue = defaultValue;
            }
        }  else {
            cfg.set(configKey, defaultValue);
            tmpValue = defaultValue;
        }

        return tmpValue;
    }

    /**
     * This function checks if the current configKey is already set.
     * If the configKey is not set the defaultValue will be set and also returned.
     * If the configKey is already set, the value will be returned.
     * @param configKey Section in the .YML-File.
     * @param defaultValue Default value which will be used as initialize value for the given section.
     * @return The value in the configKey or the defaultValue if the configKey is not given.
     */
    public int of_getSetInt(String configKey, int defaultValue)
    {
        int tmpValue;

        if(cfg.isSet(configKey))
        {
            tmpValue = cfg.getInt(configKey);

            if(tmpValue == -1)
            {
                tmpValue = defaultValue;
            }
        }
        else
        {
            cfg.set(configKey, defaultValue);
            tmpValue = defaultValue;
        }

        return tmpValue;
    }

    /**
     * This function checks if the current configKey is already set.
     * If the configKey is not set the defaultValue will be set and also returned.
     * If the configKey is already set, the value will be returned.
     * @param configKey Section in the .YML-File.
     * @param defaultValue Default value which will be used as initialize value for the given section.
     * @return The value in the configKey or the defaultValue if the configKey is not given.
     */
    public long of_getSetLong(String configKey, long defaultValue)
    {
        long tmpValue;

        if(cfg.isSet(configKey))
        {
            tmpValue = cfg.getLong(configKey);

            if(tmpValue == -1)
            {
                tmpValue = defaultValue;
            }
        }
        else
        {
            cfg.set(configKey, defaultValue);
            tmpValue = defaultValue;
        }

        return tmpValue;
    }

    /**
     * This function checks if the current configKey is already set.
     * If the configKey is not set the defaultValue will be set and also returned.
     * If the configKey is already set, the value will be returned.
     * @param configKey Section in the .YML-File.
     * @param defaultBool Default value which will be used as initialize value for the given section.
     * @return The value in the configKey or the defaultValue if the configKey is not given.
     */
    public boolean of_getSetBoolean(String configKey, boolean defaultBool)
    {
        boolean tmpValue;

        if(cfg.isSet(configKey))
        {
            tmpValue = cfg.getBoolean(configKey);
        }
        else
        {
            cfg.set(configKey, defaultBool);
            tmpValue = defaultBool;
        }

        return tmpValue;
    }

    /**
     * This function checks if the current configKey is already set.
     * If the configKey is not set the defaultValue will be set and also returned.
     * If the configKey is already set, the value will be returned.
     * @param configKey Section in the .YML-File.
     * @param arrayList Default value which will be used as initialize value for the given section.
     * @return The value in the configKey or the defaultValue if the configKey is not given.
     */
    public String[] of_getSetStringArrayList(String configKey, ArrayList<String> arrayList)
    {
        String[] tmp = null;

        //  Does the path already exist?
        if(cfg.isSet(configKey))
        {
            //  Load existing one...
            tmp = of_getStringArrayByKey(configKey);
        }
        //  Create new...
        else
        {
            cfg.set(configKey, arrayList);
        }

        //  Error while getting the value? We ignore this...
        if(tmp == null)
        {
            //  We send back the default....
            return arrayList.toArray(new String[0]);
        }

        return tmp;
    }

    /**
     * This function checks if the current configKey is already set.
     * If the configKey is not set the defaultValue will be set and also returned.
     * If the configKey is already set, the value will be returned.
     * @param configKey Section in the .YML-File.
     * @param array Default value which will be used as initialize value for the given section.
     * @return The value in the configKey or the defaultValue if the configKey is not given.
     */
    public String[] of_getSetStringArray(String configKey, String[] array)
    {
        //	�berladung von of_getSetStringArray...
        ArrayList<String> tmpList = new ArrayList<>();

        if(array != null && array.length > 0)
        {
            //	String-Array zu einer ArrayList konvertieren.
            Collections.addAll(tmpList, array);

            return of_getSetStringArrayList(configKey, tmpList);
        }

        return null;
    }

    /* ************************************* */
    /* SAVE */
    /* ************************************* */

    /**
     * This function is used to save the current file.
     * @param invoker Invoker name or system area which calls this function.
     * @return 1 if the file was saved successfully, otherwise -1.
     */
    public int of_save(String invoker)
    {
        try
        {
            cfg.save(file);
            return 1;
        }
        catch (Exception e) {
            System.out.println("[SimpleFile] Invoked by: "+invoker+"\nError-Message: " + e.getMessage());
        }

        return -1;
    }

    /* ************************************* */
    /* SETTER // ADDER // REMOVER */
    /* ************************************* */

    /**
     * This function sets the given value to the configKey-section.
     * @param configKey ConfigKey Section in the .YML
     * @param object Object which will be set.
     */
    public void of_set(String configKey, Object object)
    {
        if(cfg != null)
        {
            if(object == null)
            {
                return;
            }

            cfg.set(configKey, object);
        }
    }

    /* ************************************* */
    /* GETTER */
    /* ************************************* */

    /**
     * This function returns a string from a configKey which contains
     * multiple lines of values.
     * @param configKey ConfigSection to the multiple lines.
     * @return String array with the multiple lines.
     */
    public String[] of_getStringArrayByKey(String configKey)
    {
        if(cfg != null)
        {
            try
            {
                List<String> values = cfg.getStringList(configKey);

                if(!values.isEmpty())
                {
                    return values.toArray(new String[0]);
                }
            }
            catch (Exception ignored) { }
        }

        return null;
    }

    /**
     * This function returns all configKeys for one specified section.
     * @param configKey Specified section from which the keys should come from.
     * @return An array which contains the specified configKeys.
     */
    public String[] of_getKeySectionsByKey(String configKey)
    {
        String[] keys = null;

        if(cfg != null)
        {
            try
            {
                keys = cfg.getConfigurationSection(configKey).getKeys(false).toArray(new String[0]);
            }
            catch (Exception ignored) { }
        }

        return keys;
    }

    /**
     * This function is used to get a string value from
     * a configKey in the YML-file.
     * @param configKey Section in the .YML-File.
     * @return The value in the configKey.
     */
    public String of_getString(String configKey)
    {
        return cfg.getString(configKey);
    }

    public int of_getIntByKey(String configKey)
    {
        int value = -1;

        if(cfg != null)
        {
            try
            {
                value = cfg.getInt(configKey);
            }
            catch (Exception ignored) { }
        }

        return value;
    }

    public String of_getFileName()
    {
        if(file != null)
        {
            return file.getName();
        }

        return "NoFileName";
    }

    /**
     * This method is used to load a location from a file.
     * @param key The ConfigKey of the location.
     * @return The location.
     */
    public Location of_getLocationByKey(String key)
    {
        String worldName = cfg.getString(key + ".World");

        // Get the information of the world.
        double x = cfg.getDouble(key + ".X");
        double y = cfg.getDouble(key + ".Y");
        double z = cfg.getDouble(key + ".Z");
        float yaw = cfg.getInt(key + ".Yaw");
        float pitch = cfg.getInt(key + ".Pitch");

        //  Check if the given world exists...
        World world = null;

        // Check if the worldName is valid!
        if(worldName != null && !worldName.isEmpty())
        {
            try
            {
                world = Bukkit.getWorld(worldName);
            }
            catch(Exception ignored) { }
        }

        if(world != null)
        {
            // ...if it exists return the location.
            return new Location(world, x, y, z, yaw, pitch);
        }
        //  If an error occurs send a message to the console.
        else
        {
            System.out.println(null+"SimpleFileExtended .of_getLocationByKey(); The given world does not exist.");
        }

        return null;
    }

    /**
     * This method is used to store a location in a file.
     *
     * @param key      The ConfigKey of the location.
     * @param location The location.
     * @return
     */
    public Location of_setLocation(String key, Location location)
    {
        if(location != null)
        {
            cfg.set(key + ".World", location.getWorld().getName());
            cfg.set(key + ".X", location.getX());
            cfg.set(key + ".Y", location.getY());
            cfg.set(key + ".Z", location.getZ());
            cfg.set(key + ".Yaw", location.getYaw());
            cfg.set(key + ".Pitch", location.getPitch());
        }
        //  If an error occurs send a message to the console.
        else
        {
            System.out.println(null+"SimpleFileExtended .of_setLocation(); The given location is null.");
        }
        return location;
    }

    /* ************************************* */
    /* BOOLS */
    /* ************************************* */

    public boolean of_fileExists()
    {
        if(file != null)
        {
            if(file.length() == 0)
            {
                file.delete();
                return false;
            }

            return file.exists();
        }

        return false;
    }
}