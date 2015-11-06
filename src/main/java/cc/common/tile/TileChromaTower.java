package cc.common.tile;

import java.util.UUID;

import cc.utils.MiscUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import cc.api.ITETransfersChroma;
import cc.common.item.ItemChromaCrystal;
import cc.utils.CCUtils;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class TileChromaTower extends TileEntity implements IInventory, ITETransfersChroma {

    public int syncTick;
    int chroma;
    int maxChroma = 50000;
    UUID uuid = UUID.randomUUID();
    public int innerRotation;
    public ItemStack[] items = new ItemStack[1];

    @Override
    public void readFromNBT(NBTTagCompound i)
    {
        super.readFromNBT(i);
        MiscUtils.loadInventory(this, i);
    }

    @Override
    public void writeToNBT(NBTTagCompound i)
    {
        super.writeToNBT(i);
        MiscUtils.saveInventory(this, i);
    }

    @Override
    public void updateEntity()
    {
        ++this.innerRotation;
        //Sending the sync packets to the CLIENT. 
        if(syncTick == 0)
        {
            /*if(!this.worldObj.isRemote)
                MiscUtils.sendPacketToAllAround(worldObj, getDescriptionPacket(), xCoord, yCoord, zCoord, this.worldObj.provider.dimensionId, 16);
            */syncTick = 10;
        }else
            --this.syncTick;
    }

    @Override
    public Packet getDescriptionPacket()
    {
        NBTTagCompound nbttagcompound = new NBTTagCompound();
        this.writeToNBT(nbttagcompound);
        return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, -10, nbttagcompound);
    }

    @Override
    public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
    {
        if(net.getNetHandler() instanceof INetHandlerPlayClient)
            if(pkt.func_148853_f() == -10)
                this.readFromNBT(pkt.func_148857_g());
    }

    @Override
    public int getChroma() {
        return chroma;
    }

    @Override
    public int getMaxChroma() {
        return maxChroma;
    }

    @Override
    public boolean setChroma(int i) {
        chroma = i;
        return true;
    }

    @Override
    public boolean setMaxChroma(float f) {
        maxChroma = (int) f;
        return true;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public int getSizeInventory() {
        return this.items.length;
    }

    @Override
    public ItemStack getStackInSlot(int par1) {
        return this.items[par1];
    }

    @Override
    public ItemStack decrStackSize(int par1, int par2) {
        if (this.items[par1] != null)
        {
            ItemStack itemstack;

            if (this.items[par1].stackSize <= par2)
            {
                itemstack = this.items[par1];
                this.items[par1] = null;
                return itemstack;
            }
            else
            {
                itemstack = this.items[par1].splitStack(par2);

                if (this.items[par1].stackSize == 0)
                {
                    this.items[par1] = null;
                }

                return itemstack;
            }
        }
        else
        {
            return null;
        }
    }

    @Override
    public ItemStack getStackInSlotOnClosing(int par1)
    {
        if (this.items[par1] != null)
        {
            ItemStack itemstack = this.items[par1];
            this.items[par1] = null;
            return itemstack;
        }
        else
        {
            return null;
        }
    }


    @Override
    public void setInventorySlotContents(int par1, ItemStack par2ItemStack)
    {
        this.items[par1] = par2ItemStack;

        if (par2ItemStack != null && par2ItemStack.stackSize > this.getInventoryStackLimit())
        {
            par2ItemStack.stackSize = this.getInventoryStackLimit();
        }
    }


    @Override
    public String getInventoryName() {
        return "cc.container.chromaTower";
    }

    @Override
    public boolean hasCustomInventoryName() {
        return false;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUseableByPlayer(EntityPlayer player) {
        return this.worldObj.getTileEntity(xCoord, yCoord, zCoord) == this && player.dimension == this.worldObj.provider.dimensionId;
    }

    @Override
    public void openInventory() {

    }

    @Override
    public void closeInventory() {

    }

    @Override
    public boolean isItemValidForSlot(int p_94041_1_, ItemStack p_94041_2_) {
        if(p_94041_2_.getItem() instanceof ItemChromaCrystal)
            return true;
        return false;
    }


}
