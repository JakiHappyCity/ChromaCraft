package cc.utils.cfg;

import net.minecraftforge.common.config.Configuration;

import java.io.File;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class Config {

    public static Configuration config;

    public static boolean enableNotifierLogging;
    public static boolean renderStructuresFromAbove;

    public static int oreGenAttempts;

    public static void initialize(File file)
    {
        config = new Configuration(file);

        config.addCustomCategoryComment("Info", "This is configuration file of ChromaCraft mod");

        enableNotifierLogging = config.getBoolean("enableNotifierLogging", "Misc", true, "");
        renderStructuresFromAbove = config.getBoolean("renderStructuresFromAbove", "Misc", true, "");
        oreGenAttempts = config.getInt("oreGenAttempts", "Misc", 4, 0, Integer.MAX_VALUE, "The amount of tries to generate the elemental ore cluster in a chunk. Set to 0 to disable worldgen.");
    }

    public static void save()
    {
        config.save();
    }

}
