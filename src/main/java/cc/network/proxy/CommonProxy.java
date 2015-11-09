package cc.network.proxy;

import cc.common.invertory.ContainerChromaTower;
import cc.common.invertory.ContainerHeatGenerator;
import cc.common.tile.TileChromaTower;
import cc.common.tile.TileHeatGenerator;
import cc.utils.cfg.Config;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class CommonProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        /**
         * FMLLog.info(String.format("Gui %d: [world: %s, x:%d, y:%d, z:%d] at side %s", ID, world.toString(), x, y, z, FMLCommonHandler.instance().getEffectiveSide().toString()));
         * This is debug log.
         */
        TileEntity tile = world.getTileEntity(x, y, z);

        if(ID == Config.guiID[0])
        {
            if(tile instanceof TileChromaTower)
            {
                return new ContainerChromaTower(player.inventory, tile);
            }
            if(tile instanceof TileHeatGenerator)
            {
                return new ContainerHeatGenerator(player.inventory, tile);
            }
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void registerRenderInformation()
    {

    }

    public void openBookGUIForPlayer()
    {

    }

    public Object getClientIcon(String iconName)
    {
        return null;
    }

    public EntityPlayer getClientPlayer()
    {
        return null;
    }

    public void FlameFX(double... ds)
    {

    }

    public void SmokeFX(double... ds)
    {

    }

    public World getClientWorld()
    {
        return null;
    }

    public void CHROMAFX(double... ds)
    {

    }

}
