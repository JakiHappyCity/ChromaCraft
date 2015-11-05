package cc.network.proxy;

import cc.client.gui.GuiChromaticum;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    @Override
    public void registerRenderInformation()
    {

    }

    @Override
    public void openBookGUIForPlayer()
    {
        Minecraft.getMinecraft().displayGuiScreen(new GuiChromaticum());
    }

}
