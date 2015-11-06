package cc.network.proxy;

import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class CommonProxy implements IGuiHandler {

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
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

    public EntityPlayer getClientPlayer()
    {
        return null;
    }
}
