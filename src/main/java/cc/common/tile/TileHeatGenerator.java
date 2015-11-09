package cc.common.tile;

import cc.api.ApiCore;
import cc.api.IHotBlock;
import cc.common.init.CoreItems;
import cc.common.mod.CCCore;
import cc.utils.DataStorage;
import cc.utils.DummyData;
import cc.utils.MathUtils;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.util.ForgeDirection;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class TileHeatGenerator extends TileChromaGeneric {

    public int currentBurnTime, currentMaxBurnTime;
    public static float cfgMaxChroma =  ApiCore.GENERATOR_MAX_Chroma_GENERIC;
    public static float chromaGenerated = 20;

    public TileHeatGenerator()
    {
        super();
        this.maxChroma = (int)cfgMaxChroma;
        this.setSlotsNum(2);
    }

    public boolean canGenerateChroma()
    {
        return false;
    }

    @Override
    public void updateEntity()
    {
        float chromaGen = chromaGenerated;
        super.updateEntity();
        if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
        {
            if (this.currentBurnTime > 0)
            {
                --this.currentBurnTime;
                if(!this.worldObj.isRemote)
                {
                    float chromaFactor = 1.0F;
                    Block[] b = new Block[4];
                    b[0] = this.worldObj.getBlock(xCoord+2, yCoord, zCoord);
                    b[1] = this.worldObj.getBlock(xCoord-2, yCoord, zCoord);
                    b[2] = this.worldObj.getBlock(xCoord, yCoord, zCoord+2);
                    b[3] = this.worldObj.getBlock(xCoord, yCoord, zCoord-2);
                    int[] ox = new int[]{2,-2,0,0};
                    int[] oz = new int[]{0,0,2,-2};
                    for(int i = 0; i < 4; ++i)
                    {
                        if(b[i] == Blocks.air)
                        {
                            chromaFactor*=0;
                        }else if(b[i] == Blocks.netherrack)
                        {
                            chromaFactor*=0.75F;
                        }else if(b[i] == Blocks.lava)
                        {
                            chromaFactor*=0.95F;
                        }else if(b[i] == Blocks.fire)
                        {
                            chromaFactor*=0.7F;
                        }else if(b[i] instanceof IHotBlock)
                        {
                            chromaFactor*=(((IHotBlock)b[i]).getHeatModifier(getWorldObj(), xCoord+ox[i], yCoord, zCoord+oz[i]));
                        }else
                        {
                            chromaFactor*=0.5F;
                        }

                    }

                    chromaGen*=chromaFactor;
                    if(chromaGen >= 1)
                    {
                        this.setChroma((int) (this.getChroma()+chromaGen));
                        if(this.getChroma() > this.getMaxChroma())
                            this.setChroma(this.getMaxChroma());
                    }
                }
            }

            if (!this.worldObj.isRemote)
            {
                if (this.getStackInSlot(0) != null)
                {
                    if (this.currentBurnTime == 0 && this.getChroma() < this.getMaxChroma())
                    {
                        this.currentMaxBurnTime = this.currentBurnTime = TileEntityFurnace.getItemBurnTime(this.getStackInSlot(0));

                        if (this.currentBurnTime > 0)
                        {
                            if (this.getStackInSlot(0) != null)
                            {

                                if(this.getStackInSlot(1) == null || this.getStackInSlot(1).stackSize < this.getInventoryStackLimit())
                                {
                                    if(this.getStackInSlot(1) != null && this.getStackInSlot(1).getItem() == CoreItems.lavaCrystal)
                                    {
                                        ItemStack stk = this.getStackInSlot(1);
                                        ++stk.stackSize;
                                        this.setInventorySlotContents(1, stk);
                                    }
                                    if(this.getStackInSlot(1) == null)
                                    {
                                        ItemStack stk = new ItemStack(CoreItems.lavaCrystal,1,0);
                                        this.setInventorySlotContents(1, stk);
                                    }
                                }
                                if(this.getStackInSlot(0).stackSize == 0)
                                {
                                    this.setInventorySlotContents(0, this.getStackInSlot(0).getItem().getContainerItem(this.getStackInSlot(0)));
                                }
                                this.decrStackSize(0, 1);
                            }
                        }
                    }
                }
            }
        }
        for(int i = 2; i < 6; ++i)
        {
            ForgeDirection rotation = ForgeDirection.VALID_DIRECTIONS[i];
            float rotXAdv = rotation.offsetX-0.5F;
            float rotZAdv = rotation.offsetZ-0.5F;
            CCCore.proxy.FlameFX(xCoord+0.725F+rotXAdv/2.2F, yCoord+0.4F, zCoord+0.725F+rotZAdv/2.2F, 0, 0F, 0, 0.8D, 0.5D, 0.5F, 0.5F);
            CCCore.proxy.FlameFX(xCoord+0.5F+ MathUtils.randomFloat(this.worldObj.rand)*0.2F, yCoord+0.65F, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.2F, 0, 0.01F, 0, 0.8D, 0.5D, 0.5F, 1F);

        }
        CCCore.proxy.SmokeFX(xCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.05F, yCoord+0.8F, zCoord+0.5F+MathUtils.randomFloat(this.worldObj.rand)*0.05F, 0, 0, 0, 1);
    }

    @Override
    public void readFromNBT(NBTTagCompound i)
    {
        currentBurnTime = i.getInteger("burn");
        currentMaxBurnTime = i.getInteger("burnMax");
        super.readFromNBT(i);
    }

    @Override
    public void writeToNBT(NBTTagCompound i)
    {
        i.setInteger("burn", currentBurnTime);
        i.setInteger("burnMax", currentMaxBurnTime);
        super.writeToNBT(i);
    }

    public static void setupConfig(Configuration cfg)
    {
        try
        {
            cfg.load();
            String[] cfgArrayString = cfg.getStringList("HeatGeneratorSettings", "tileentities", new String[]{
                    "Max Chroma:"+ApiCore.GENERATOR_MAX_Chroma_GENERIC,
                    "Max Chroma generated per tick:20"
            },"");
            String dataString="";

            for(int i = 0; i < cfgArrayString.length; ++i)
                dataString+="||"+cfgArrayString[i];

            DummyData[] data = DataStorage.parseData(dataString);

            cfgMaxChroma = Float.parseFloat(data[0].fieldValue);
            chromaGenerated = Float.parseFloat(data[2].fieldValue);

            cfg.save();
        }catch(Exception e)
        {
            return;
        }
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{1};
    }


}
