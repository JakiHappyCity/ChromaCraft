package cc.utils;

import cc.common.mod.CCCore;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class MiscUtils {

    /**
     * <b>Deprecated!</b> Use DrawUtils from now!
     */
    @Deprecated
    @SideOnly(Side.CLIENT)
    public static void bindTexture(String mod, String texture)
    {
        DrawUtils.bindTexture(mod, texture);
    }

    /**
     * Creates a new NBTTagCompound for the given ItemStack
     * @param stack - the ItemStack to work with.
     */
    public static void createNBTTag(ItemStack stack)
    {
        if(stack.hasTagCompound())
        {
            return;
        }
        NBTTagCompound itemTag = new NBTTagCompound();
        stack.setTagCompound(itemTag);
    }

    /**
     * used to get the ItemStack's tag compound.
     * @param stack - the ItemStack to work with.
     * @return NBTTagCompound of the ItemStack
     */
    public static NBTTagCompound getStackTag(ItemStack stack)
    {
        createNBTTag(stack);
        return stack.getTagCompound();
    }

    /**
     * Have you ever thought that loading inventories from NBTTag takes too much code? Here is a nifty solution to do so!
     * @param t - the TileEntity
     * @param loadTag - the tag
     */
    public static void loadInventory(TileEntity t, NBTTagCompound loadTag)
    {
        if(t instanceof IInventory)
        {
            IInventory tile = (IInventory) t;
            for(int i = 0; i < tile.getSizeInventory(); ++i)
            {
                tile.setInventorySlotContents(i, null);
            }
            NBTTagList nbttaglist = loadTag.getTagList("Items", 10);
            for (int i = 0; i < nbttaglist.tagCount(); ++i)
            {
                NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
                byte b0 = nbttagcompound1.getByte("Slot");

                if (b0 >= 0 && b0 < tile.getSizeInventory())
                {
                    tile.setInventorySlotContents(b0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
                }
            }
        }
    }

    /**
     * Have you ever thought that saving inventories to NBTTag takes too much code? Here is a nifty solution to do so!
     * @param t - the TileEntity
     * @param saveTag - the tag
     */
    public static void saveInventory(TileEntity t, NBTTagCompound saveTag)
    {
        if(t instanceof IInventory)
        {
            IInventory tile = (IInventory) t;
            NBTTagList nbttaglist = new NBTTagList();
            for (int i = 0; i < tile.getSizeInventory(); ++i)
            {
                if (tile.getStackInSlot(i) != null)
                {
                    NBTTagCompound nbttagcompound1 = new NBTTagCompound();
                    nbttagcompound1.setByte("Slot", (byte)i);
                    tile.getStackInSlot(i).writeToNBT(nbttagcompound1);
                    nbttaglist.appendTag(nbttagcompound1);
                }
            }
            saveTag.setTag("Items", nbttaglist);
        }
    }

    /**
     * Used to send packets from SERVER to CLIENT.
     * @param w - the worldObj that we are operating in
     * @param pkt - the packet to send
     * @param x - the X coordinate
     * @param y - the Y coordinate
     * @param z - the Z coordinate
     * @param dimId - the ID of the dimension to look the players.
     * @param distance - the distance at which the players will get found.
     */
    @SuppressWarnings("unchecked")
    public static void sendPacketToAllAround(World w, Packet pkt, int x, int y, int z, int dimId, double distance)
    {
        List<EntityPlayer> playerLst = w.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(x-0.5D, y-0.5D, z-0.5D, x+0.5D, y+0.5D, z+0.5D).expand(distance, distance, distance));
        if(!playerLst.isEmpty())
        {
            for(int i = 0; i < playerLst.size(); ++i)
            {
                EntityPlayer player = playerLst.get(i);
                if(player instanceof EntityPlayerMP)
                {
                    if(pkt instanceof S35PacketUpdateTileEntity)
                    {
                        NBTTagCompound tileTag = new NBTTagCompound();
                        w.getTileEntity(x, y, z).writeToNBT(tileTag);
                        CCCore.network.sendTo(new DummyPacketIMSG_Tile(tileTag,-10), (EntityPlayerMP) player);
                    }else
                    {
                        if(player.dimension == dimId)
                            ((EntityPlayerMP)player).getServerForPlayer().func_73046_m().getConfigurationManager().sendPacketToAllPlayers(pkt);
                    }
                }else
                {
                    Notifier.notifyDebug("Trying to send packet "+pkt+" to all around on Client side, probably a bug, ending the packet send try");
                }
            }
        }
    }

    /**
     * Used to send packets from SERVER to CLIENT.
     * @param w - the worldObj that we are operating in
     * @param pkt - the packet to send
     * @param player - the player to whom we are sending the packet.
     */
    public static void sendPacketToPlayer(World w,Packet pkt,EntityPlayer player)
    {
        if(player instanceof EntityPlayerMP)
        {
            ((EntityPlayerMP)player).playerNetServerHandler.sendPacket(pkt);
        }else
        {
            Notifier.notifyDebug("Trying to send packet "+pkt+" to player "+player+"||"+player.getDisplayName()+" on Client side, probably a bug, ending the packet send try");
        }
    }

}
