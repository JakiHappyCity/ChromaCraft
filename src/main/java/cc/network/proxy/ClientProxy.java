package cc.network.proxy;

import cc.client.gui.GuiChromaTower;
import cc.client.gui.GuiChromaticum;
import cc.client.gui.GuiHeatGenerator;
import cc.client.regular.EntityChromaFX;
import cc.client.regular.EntityColoredFlameFX;
import cc.client.render.tile.RenderChromaTower;
import cc.client.render.tile.RenderHeatGenerator;
import cc.common.invertory.ContainerChromaTower;
import cc.common.invertory.ContainerHeatGenerator;
import cc.common.tile.TileChromaTower;
import cc.common.tile.TileHeatGenerator;
import cc.utils.cfg.Config;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ClientRegistry;
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
            if(tile instanceof TileHeatGenerator)
            {
                return new GuiHeatGenerator(new ContainerHeatGenerator(player.inventory, tile),tile);
            }
        }
        return null;
    }

    @Override
    public void registerRenderInformation()
    {
        ClientRegistry.bindTileEntitySpecialRenderer(TileHeatGenerator.class, new RenderHeatGenerator());
        ClientRegistry.bindTileEntitySpecialRenderer(TileChromaTower.class, new RenderChromaTower());
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

    @Override
    public void FlameFX(double... ds)
    {
        Minecraft.getMinecraft().effectRenderer.addEffect(new EntityColoredFlameFX(
                Minecraft.getMinecraft().theWorld, ds[0], ds[1], ds[2], ds[3], ds[4], ds[5], ds[6], ds[7], ds[8], ds[9]
        ));
    }

    public void SmokeFX(double... ds)
    {
        if(ds.length == 7)
        {
            Minecraft.getMinecraft().effectRenderer.addEffect(new cc.client.regular.SmokeFX(
                    Minecraft.getMinecraft().theWorld, ds[0], ds[1], ds[2], ds[3], ds[4], ds[5], (float) ds[6]
            ));
        }
        if(ds.length == 10)
        {
            Minecraft.getMinecraft().effectRenderer.addEffect(new cc.client.regular.SmokeFX(
                    Minecraft.getMinecraft().theWorld, ds[0], ds[1], ds[2], ds[3], ds[4], ds[5], (float) ds[6], ds[7], ds[8], ds[9]
            ));
        }
    }

    @Override
    public World getClientWorld()
    {
        return FMLClientHandler.instance().getClient().theWorld;
    }

    @Override
    public void CHROMAFX(double... ds)
    {
        if(ds.length <= 6)
        {
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityChromaFX(getClientWorld(), ds[0], ds[1], ds[2], ds[3], ds[4], ds[5]));
        }else
            Minecraft.getMinecraft().effectRenderer.addEffect(new EntityChromaFX(getClientWorld(), ds[0], ds[1], ds[2], ds[3], ds[4], ds[5], ds[6], ds[7], ds[8]));
    }
    
}
