package cc.network.proxy;

import cc.client.gui.GuiChromaTower;
import cc.client.gui.GuiChromaticum;
import cc.common.invertory.ContainerChromaTower;
import cc.common.tile.TileChromaTower;
import cc.utils.cfg.Config;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class ClientProxy extends CommonProxy {

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {

        if(ID == Config.guiID[0])
        {
            TileEntity tile = world.getTileEntity(x, y, z);
            if(tile instanceof TileChromaTower)
            {
                return new GuiChromaTower(new ContainerChromaTower(player.inventory, tile), tile);
            }
        }
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

    @Override
    public EntityPlayer getClientPlayer()
    {
        return Minecraft.getMinecraft().thePlayer;
    }

    public static IIcon chromaIcon;

    public static IIcon chromaParticleIcon;

    @Override
    public Object getClientIcon(String str)
    {
        if(str.equals("chroma"))
            return chromaIcon;
        if(str.equals("chromaParticleIcon"))
            return chromaParticleIcon;
        return null;
    }

}
