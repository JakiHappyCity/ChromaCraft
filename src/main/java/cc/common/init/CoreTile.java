package cc.common.init;

import cc.common.tile.TileChromaTower;
import cc.common.tile.TileHeatGenerator;
import cc.common.tile.TileMagicalPulverizer;
import cc.utils.cfg.Config;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.config.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class CoreTile {

    public static final List<Class<? extends TileEntity>> cfgDependant = new ArrayList<Class<? extends TileEntity>>();

    public static void Init()
    {
        addTileToMapping(TileChromaTower.class);
        addTileToMapping(TileHeatGenerator.class);
        addTileToMapping(TileMagicalPulverizer.class);
    }

    public static void addTileToMapping(Class<? extends TileEntity> tile)
    {
        GameRegistry.registerTileEntity(tile, "cc:"+tile.getCanonicalName());
        try
        {
            if(tile.getMethod("setupConfig", Configuration.class) != null)
            {
                cfgDependant.add(tile);
                tile.getMethod("setupConfig", Configuration.class).invoke(null, Config.config);
            }
        }catch(Exception e)
        {
            return;
        }

    }

}
