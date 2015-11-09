package cc.utils;

import cc.common.mod.CCCore;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.ReflectionHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.potion.Potion;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
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
     * <b>Deprecated!</b> Use DrawUtils from now!
     */
    @Deprecated
    @SideOnly(Side.CLIENT)
    public static void drawTexture(int x, int y, IIcon icon, int width, int height, float zLevel)
    {
        DrawUtils.drawTexture(x, y, icon, width, height, zLevel);
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

    /**
     * Used to drop items from IInventory when the block is broken.
     * @param par1World - the World object
     * @param par2 - X coordinate of the block
     * @param par3 - Y coordinate of the block
     * @param par4 - Z coordinate of the block
     */
    public static void dropItemsOnBlockBreak(World par1World, int par2, int par3, int par4, Block par5, int par6)
    {
        //Was causing too much issues, had to add a try/catch statement...
        try
        {
            IInventory inv = (IInventory)par1World.getTileEntity(par2, par3, par4);

            if (inv != null)
            {
                for (int j1 = 0; j1 < inv.getSizeInventory(); ++j1)
                {
                    ItemStack itemstack = inv.getStackInSlot(j1);

                    if (itemstack != null)
                    {
                        float f = par1World.rand.nextFloat() * 0.8F + 0.1F;
                        float f1 = par1World.rand.nextFloat() * 0.8F + 0.1F;
                        float f2 = par1World.rand.nextFloat() * 0.8F + 0.1F;

                        while (itemstack.stackSize > 0)
                        {
                            int k1 = par1World.rand.nextInt(21) + 10;

                            if (k1 > itemstack.stackSize)
                            {
                                k1 = itemstack.stackSize;
                            }

                            itemstack.stackSize -= k1;
                            EntityItem entityitem = new EntityItem(par1World, (double)((float)par2 + f), (double)((float)par3 + f1), (double)((float)par4 + f2), new ItemStack(itemstack.getItem(), k1, itemstack.getItemDamage()));

                            if (itemstack.hasTagCompound())
                            {
                                entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
                            }

                            float f3 = 0.05F;
                            entityitem.motionX = (double)((float)par1World.rand.nextGaussian() * f3);
                            entityitem.motionY = (double)((float)par1World.rand.nextGaussian() * f3 + 0.2F);
                            entityitem.motionZ = (double)((float)par1World.rand.nextGaussian() * f3);
                            par1World.spawnEntityInWorld(entityitem);
                        }
                    }
                }
            }
        }catch(Exception ex)
        {
            Notifier.notifyCustomMod("ChromaCraft", "[ERROR]Trying to drop items upon block breaking, but caught an exception:");
            ex.printStackTrace();
            return;
        }
    }

    /**
     * Extends the default mc potionArray(which is declared as public static final Potion[] potionTypes = new Potion[32]) by the given amount
     * @param byAmount - how much to extends for
     * @return the first free index in the new potionArray.
     */
    public static int extendPotionArray(int byAmount)
    {
        int potionOffset = Potion.potionTypes.length;
        Potion[] potionTypes = new Potion[potionOffset + byAmount];
        System.arraycopy(Potion.potionTypes, 0, potionTypes, 0, potionOffset);
        setPrivateFinalValue(Potion.class,null,potionTypes, ObfuscationReflectionHelper.remapFieldNames(Potion.class.getName(), new String[] {"potionTypes","field_76425_a","a"}));
        for(int i = 0; i < Potion.potionTypes.length; ++i)
            if(Potion.potionTypes[i] == null)
                return i;

        return -1;
    }

    /**
     * Allows changes of variables declared like private final || private static final. Advanced. Do not use if you do not know what you are doing!
     * Sometimes considered as a dirty hacking of the java code. I agree. There is nothing more dirty, than just removing the FINAL modifier of the variable. It's like Java can't even do anything, no matter the protection given.
     * This should not be done. However, in vanilla MC it is pretty much the only way to do so, so I can't help it.
     * The only thing, that would be worse is using ASM to remotely change the compiled final variable. That is the most disgusting thing you can do with Java, I believe.
     * @param classToAccess - the class in wich you are changing the variable
     * @param instance - if you want to modify non-static field you should put the instance of the class here. Leave null for static
     * @param value - what you actually want to be set in the variable field
     * @param fieldNames - the names of the field you are changing. Should be both for obfuscated and compiled code.
     */
    public static void setPrivateFinalValue(Class<?> classToAccess, Object instance, Object value, String fieldNames[])
    {
        Field field = ReflectionHelper.findField(classToAccess, ObfuscationReflectionHelper.remapFieldNames(classToAccess.getName(), fieldNames));
        try
        {
            Field modifiersField = Field.class.getDeclaredField("modifiers");
            modifiersField.setAccessible(true);
            modifiersField.setInt(field, field.getModifiers() & ~Modifier.FINAL);
            field.set(instance, value);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

}
