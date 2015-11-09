package cc.utils;

import cc.api.ITEHasChroma;
import cc.common.item.ItemChromaCrystal;
import cc.common.mod.CCCore;
import cc.common.tile.TileChromaTower;
import cc.network.PacketNBT;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class CCUtils {

    private static final List<ScheduledServerAction> actions = new ArrayList<ScheduledServerAction>();

    protected static void actionsTick()
    {
        if(!actions.isEmpty())
            for(int i = 0; i < actions.size(); ++i)
            {
                ScheduledServerAction ssa = actions.get(i);
                --ssa.actionTime;
                if(ssa.actionTime <= 0)
                {
                    ssa.execute();
                    actions.remove(i);
                }
            }
    }

    public static void addScheduledAction(ScheduledServerAction ssa)
    {
        if(FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER)
            Notifier.notifyCustomMod("ChromaCraft", "[WARNING][SEVERE]Trying to add a scheduled server action not on server side, aborting!");

        actions.add(ssa);
    }

    public static void requestScheduledTileSync(TileEntity tile, EntityPlayer requester)
    {
        Side s = FMLCommonHandler.instance().getEffectiveSide();
        if(s == Side.CLIENT)
        {
            if(tile.getWorldObj() == null || tile.getWorldObj().provider == null)
                return;

            NBTTagCompound clientData = new NBTTagCompound();
            clientData.setString("playername", requester.getCommandSenderName());
            clientData.setInteger("x", tile.xCoord);
            clientData.setInteger("y", tile.yCoord);
            clientData.setInteger("z", tile.zCoord);
            clientData.setInteger("dim", tile.getWorldObj().provider.dimensionId);
            PacketNBT packet = new PacketNBT(clientData).setID(7);
            CCCore.network.sendToServer(packet);
        }else
        {
            addScheduledAction(new ServerToClientSyncAction(requester, tile));
        }
    }

    public static void initChromaTag(ItemStack stack, int maxChroma)
    {
        if(maxChroma == 0)
        {
            try
            {
                Class<? extends Item> itemClz = stack.getItem().getClass();
                Field f = itemClz.getField("maxChroma");
                f.setInt(stack.getItem(), 5000);
                maxChroma = 5000;
            }catch(Exception e)
            {
                //Silent error tracking
            }
        }
        getStackTag(stack).setInteger("maxChroma", maxChroma);
        if(!getStackTag(stack).hasKey("chroma"))
            getStackTag(stack).setInteger("chroma", 0);
    }

    public static NBTTagCompound getStackTag(ItemStack stack)
    {
        createNBTTag(stack);
        return stack.getTagCompound();
    }

    public static void createNBTTag(ItemStack stack)
    {

        if(stack.hasTagCompound())
        {
            return;
        }
        NBTTagCompound itemTag = new NBTTagCompound();
        stack.setTagCompound(itemTag);
    }

    public static void chromaIn(TileEntity tile, int slotNum)
    {
        if(tile instanceof IInventory && tile instanceof ITEHasChroma)
        {
            IInventory inv = (IInventory) tile;
            ITEHasChroma chromat = (ITEHasChroma) tile;

            if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemChromaCrystal && inv.getStackInSlot(slotNum).getTagCompound() != null)
            {
                ItemStack s = inv.getStackInSlot(slotNum);
                int[] o = ItemChromaCrystal.getCoords(s);
                if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
                {
                    if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasChroma)
                    {
                        ITEHasChroma t = (ITEHasChroma) tile.getWorldObj().getTileEntity(o[0], o[1], o[2]);
                        if(t != tile && t != null && !tile.getWorldObj().isRemote)
                        {
                            if(chromat.getChroma() < chromat.getMaxChroma())
                            {
                                int chroma = t.getChroma();
                                if(chroma > chromat.getMaxChroma() - chromat.getChroma())
                                {
                                    t.setChroma(chroma-(chromat.getMaxChroma() - chromat.getChroma()));
                                    chromat.setChroma(chromat.getMaxChroma());
                                }else
                                {
                                    t.setChroma(0);
                                    chromat.setChroma(chromat.getChroma()+chroma);
                                }
                            }
                        }
                    }
                }
            }
            if(chromat.getChroma() < 0)
                chromat.setChroma(0);
        }
    }

    public static void spawnChromaParticles(TileEntity tile, int slotNum)
    {
        IInventory inv = (IInventory) tile;
        if(tile.getWorldObj().isRemote)
        {
            if(inv.getStackInSlot(slotNum) != null && inv.getStackInSlot(slotNum).getItem() instanceof ItemChromaCrystal && inv.getStackInSlot(slotNum).getTagCompound() != null)
            {
                ItemStack s = inv.getStackInSlot(slotNum);
                int[] o = ItemChromaCrystal.getCoords(s);
                if(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16)
                {
                    if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) != null && tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasChroma)
                    {
                        float colorRRender = 0.0F;
                        float colorGRender = 1.0F;
                        float colorBRender = 1.0F;
                        if(tile instanceof TileChromaTower)
                        {
                            if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof TileChromaTower)
                                CCCore.proxy.CHROMAFX((float) (o[0]+0.5D), (float) (o[1]+1.85D), (float) (o[2]+0.5D), tile.xCoord-o[0], tile.yCoord-o[1]+0.25D, tile.zCoord-o[2],colorRRender,colorGRender,colorBRender);
                           else
                                CCCore.proxy.CHROMAFX((float) (o[0]+0.5D), (float) (o[1]+0.5D), (float) (o[2]+0.5D), tile.xCoord-o[0], tile.yCoord-o[1]+1.5D, tile.zCoord-o[2],colorRRender,colorGRender,colorBRender);
                        }else
                        {
                            if(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof TileChromaTower)
                                CCCore.proxy.CHROMAFX((float) (o[0]+0.5D), (float) (o[1]+1.85D), (float) (o[2]+0.5D), tile.xCoord-o[0], tile.yCoord-o[1]-1.5D, tile.zCoord-o[2],colorRRender,colorGRender,colorBRender);
                            else
                                CCCore.proxy.CHROMAFX((float) (o[0]+0.5D), (float) (o[1]+0.5D), (float) (o[2]+0.5D), tile.xCoord-o[0], tile.yCoord-o[1], tile.zCoord-o[2],colorRRender,colorGRender,colorBRender);
                        }
                    }
                }
            }
        }
    }
    
    public static void manage(TileEntity tile, int slotNum)
    {
        chromaIn(tile, 0);
        spawnChromaParticles(tile, 0);
    }

}
