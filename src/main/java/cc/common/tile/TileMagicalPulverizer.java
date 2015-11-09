package cc.common.tile;

import java.util.List;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidTank;
import net.minecraftforge.fluids.FluidTankInfo;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.oredict.OreDictionary;
import cc.utils.DataStorage;
import cc.utils.DummyData;
import cc.utils.MathUtils;
import cc.api.ApiCore;
import cc.common.init.CoreItems;
import cc.utils.CCUtils;
import cc.utils.EnumOreColoring;

/**
 * Created by jakihappycity on 08.11.15.
 */
public class TileMagicalPulverizer extends TileChromaGeneric implements IFluidTank {

    public int progressLevel, smeltingLevel;
    public FluidTank lavaTank = new FluidTank(32000);
    public static float cfgMaxChroma = ApiCore.DEVICE_MAX_CHROMA_GENERIC;
    public static boolean generatesCorruption = false;
    public static int genCorruption = 2;
    public static int chromaUsage = 1000;
    public static int smeltChromaUsage = 30;
    public static int oreSmeltingTime = 400;
    public static int alloySmeltingTime = 40;
    public static float quadriplingChance = 0.33F;

    public TileMagicalPulverizer()
    {
        super();
        this.maxChroma = (int)cfgMaxChroma;
        this.setSlotsNum(8);
    }

    @Override
    public void updateEntity()
    {
        super.updateEntity();
        CCUtils.manage(this, 0);
        if(!this.worldObj.isBlockIndirectlyGettingPowered(xCoord, yCoord, zCoord))
        {
            if(!worldObj.isRemote)
            {
                if(FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1)) != null && this.getStackInSlot(2) == null)
                {
                    if(lavaTank.getFluid() == null)
                    {
                        lavaTank.fill(FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1)), true);
                        setInventorySlotContents(2,consumeItem(getStackInSlot(1)));
                        this.decrStackSize(1, 1);
                    }
                    else if (lavaTank.getFluidAmount() != 8000 && lavaTank.getFluid().isFluidEqual(FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1))))
                    {
                        lavaTank.fill(FluidContainerRegistry.getFluidForFilledItem(getStackInSlot(1)), true);
                        setInventorySlotContents(2,consumeItem(getStackInSlot(1)));
                        this.decrStackSize(1, 1);
                    }
                }
            }

            ItemStack ore = this.getStackInSlot(3);
            if(ore != null && !this.worldObj.isRemote)
            {
                int[] oreIds = OreDictionary.getOreIDs(ore);

                String oreName = "Unknown";
                if(oreIds.length > 0)
                    oreName = OreDictionary.getOreName(oreIds[0]);
                int metadata = -1;
                for(int i = 0; i < EnumOreColoring.values().length; ++i)
                {
                    EnumOreColoring oreColor = EnumOreColoring.values()[i];
                    if(oreName.equalsIgnoreCase(oreColor.oreName))
                    {
                        metadata = i;
                        break;
                    }
                }
                if(metadata != -1)
                {
                    if(this.getStackInSlot(4) == null)
                    {
                        if(this.getChroma()-chromaUsage >= 0 && this.lavaTank != null && this.lavaTank.getFluid() != null && this.lavaTank.getFluid().getFluid() == FluidRegistry.LAVA && this.lavaTank.getFluidAmount() > 0)
                        {
                            this.setChroma(this.getChroma()-chromaUsage);
                            if(this.worldObj.rand.nextFloat() <= 0.1F)
                                this.drain(1, true);
                            this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
                            ++this.progressLevel;
                            if(this.progressLevel >= oreSmeltingTime)
                            {
                                this.decrStackSize(3, 1);
                                int suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount;
                                if(this.worldObj.rand.nextFloat() <= 1)
                                    suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount*2;
                                this.setInventorySlotContents(4, new ItemStack(CoreItems.lavaCrystal,suggestedStackSize,metadata));
                                this.progressLevel = 0;
                                if(this.getStackInSlot(7) == null)
                                {
                                    this.setInventorySlotContents(7, new ItemStack(CoreItems.lavaCrystal,1,0));
                                }else
                                {
                                    if(this.getStackInSlot(7).getItem() == CoreItems.lavaCrystal && this.getStackInSlot(7).stackSize < 64)
                                    {
                                        ItemStack slagIS = this.getStackInSlot(7);
                                        slagIS.stackSize += 1;
                                        this.setInventorySlotContents(7, slagIS);
                                    }
                                }
                            }
                        }
                    }else
                    if(this.getStackInSlot(4).getItem() == CoreItems.magicalDust && this.getStackInSlot(4).getItemDamage() == metadata && this.getStackInSlot(4).stackSize+2 <= this.getStackInSlot(4).getMaxStackSize() && this.getStackInSlot(4).stackSize + 4 <= this.getInventoryStackLimit())
                    {
                        if(this.getChroma()-chromaUsage >= 0 && this.lavaTank != null && this.lavaTank.getFluid() != null && this.lavaTank.getFluid().getFluid() == FluidRegistry.LAVA && this.lavaTank.getFluidAmount() > 0)
                        {
                            this.setChroma(this.getChroma()-chromaUsage);
                            if(this.worldObj.rand.nextFloat() <= 0.1F)
                                this.drain(1, true);
                            this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
                            ++this.progressLevel;
                            if(this.progressLevel >= oreSmeltingTime)
                            {
                                this.decrStackSize(3, 1);
                                int suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount;
                                if(this.worldObj.rand.nextFloat() <= 1)
                                    suggestedStackSize = EnumOreColoring.values()[metadata].dropAmount*2;
                                ItemStack is = this.getStackInSlot(4);
                                is.stackSize += suggestedStackSize;
                                if(is.stackSize > is.getMaxStackSize())
                                    is.stackSize = is.getMaxStackSize();
                                this.setInventorySlotContents(4, is);
                                this.progressLevel = 0;
                                if(this.getStackInSlot(7) == null)
                                {
                                    this.setInventorySlotContents(7, new ItemStack(CoreItems.lavaCrystal,1,0));
                                }else
                                {
                                    if(this.getStackInSlot(7).getItem() == CoreItems.lavaCrystal && this.getStackInSlot(7).stackSize < 64)
                                    {
                                        ItemStack slagIS = this.getStackInSlot(7);
                                        slagIS.stackSize += 1;
                                        this.setInventorySlotContents(7, slagIS);
                                    }
                                }
                            }
                        }
                    }
                }else
                {
                    this.progressLevel = 0;
                }
            }else
                this.progressLevel = 0;

            ItemStack alloy = this.getStackInSlot(5);
            if(alloy != null && this.getStackInSlot(5).getItem() == CoreItems.magicalDust)
            {
                EnumOreColoring oreColor = EnumOreColoring.values()[alloy.getItemDamage()];
                String oreName = oreColor.oreName;
                String outputName = oreColor.outputName;
                String suggestedIngotName;
                if(outputName.isEmpty())
                    suggestedIngotName = "ingot"+oreName.substring(3);
                else
                    suggestedIngotName = outputName;
                List<ItemStack> oreLst = OreDictionary.getOres(suggestedIngotName);

                if(oreLst != null && !oreLst.isEmpty() && !this.worldObj.isRemote)
                {
                    ItemStack ingotStk = oreLst.get(0).copy();
                    if(this.getStackInSlot(6) == null)
                    {
                        if(this.getChroma()-smeltChromaUsage >= 0)
                        {
                            this.setChroma(this.getChroma()-smeltChromaUsage);
                            this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
                            ++this.smeltingLevel;
                            if(this.smeltingLevel >= alloySmeltingTime)
                            {
                                this.decrStackSize(5, 1);
                                int suggestedStackSize = 2;
                                ingotStk.stackSize = suggestedStackSize;
                                this.setInventorySlotContents(6, ingotStk);
                                this.smeltingLevel = 0;
                                if(this.getStackInSlot(7) == null)
                                {
                                    this.setInventorySlotContents(7, new ItemStack(CoreItems.lavaCrystal,1,0));
                                }else
                                {
                                    if(this.getStackInSlot(7).getItem() == CoreItems.lavaCrystal && this.getStackInSlot(7).stackSize < 64)
                                    {
                                        ItemStack slagIS = this.getStackInSlot(7);
                                        slagIS.stackSize += 1;
                                        this.setInventorySlotContents(7, slagIS);
                                    }
                                }
                            }
                        }
                    }else
                    if(this.getStackInSlot(6).isItemEqual(ingotStk) && this.getStackInSlot(6).stackSize+2 <= this.getStackInSlot(6).getMaxStackSize() && this.getStackInSlot(6).stackSize + 2 <= this.getInventoryStackLimit())
                    {
                        if(this.getChroma()-smeltChromaUsage >= 0)
                        {
                            this.setChroma(this.getChroma()-smeltChromaUsage);
                            this.worldObj.spawnParticle("flame", xCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, yCoord, zCoord+0.5D+MathUtils.randomDouble(this.worldObj.rand)/2.2D, 0, -0.1D, 0);
                            ++this.smeltingLevel;
                            if(this.smeltingLevel >= alloySmeltingTime)
                            {
                                this.decrStackSize(5, 1);
                                int suggestedStackSize = 2;
                                ItemStack is = this.getStackInSlot(6);
                                is.stackSize += suggestedStackSize;
                                if(is.stackSize > is.getMaxStackSize())
                                    is.stackSize = is.getMaxStackSize();
                                this.setInventorySlotContents(6, is);
                                this.smeltingLevel = 0;
                                if(this.getStackInSlot(7) == null)
                                {
                                    this.setInventorySlotContents(7, new ItemStack(CoreItems.lavaCrystal,1,0));
                                }else
                                {
                                    if(this.getStackInSlot(7).getItem() == CoreItems.lavaCrystal && this.getStackInSlot(7).stackSize < 64)
                                    {
                                        ItemStack slagIS = this.getStackInSlot(7);
                                        slagIS.stackSize += 1;
                                        this.setInventorySlotContents(7, slagIS);
                                    }
                                }
                            }
                        }
                    }
                }else
                    this.smeltingLevel = 0;
            }else
                this.smeltingLevel = 0;
        }
    }

    public static ItemStack consumeItem(ItemStack stack)
    {
        if (stack.stackSize == 1)
        {
            if (stack.getItem().hasContainerItem(stack))
            {
                return stack.getItem().getContainerItem(stack);
            }else
            {
                return null;
            }
        }else
        {
            if (stack.getItem().hasContainerItem(stack))
            {
                return stack.getItem().getContainerItem(stack);
            }else
            {
                return null;
            }
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound i)
    {
        super.readFromNBT(i);
        lavaTank.readFromNBT(i);
    }

    @Override
    public void writeToNBT(NBTTagCompound i)
    {
        super.writeToNBT(i);
        lavaTank.writeToNBT(i);
    }

    @Override
    public FluidStack getFluid() {
        return lavaTank.getFluid();
    }

    @Override
    public int getFluidAmount() {
        return lavaTank.getFluidAmount();
    }

    @Override
    public int getCapacity() {
        return lavaTank.getCapacity();
    }

    @Override
    public FluidTankInfo getInfo() {
        return lavaTank.getInfo();
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        return lavaTank.fill(resource, doFill);
    }

    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return lavaTank.drain(maxDrain, doDrain);
    }

    public static void setupConfig(Configuration cfg)
    {
        try
        {
            cfg.load();
            String[] cfgArrayString = cfg.getStringList("MagmaticSmelterySettings", "tileentities", new String[]{
                    "Max Chroma:"+ApiCore.DEVICE_MAX_CHROMA_GENERIC,
                    "Chroma Usage when smelting Ores:50",
                    "Can this device actually generate corruption:false",
                    "The amount of corruption generated each tick(do not set to 0!):3",
                    "Chroma Usage when smelting Alloys:30",
                    "Ticks required to smelt an ore into Alloy:400",
                    "Ticks required to smelt an alloy:40",
                    "A chance to quadriple the outcome:0.33"
            },"");
            String dataString="";

            for(int i = 0; i < cfgArrayString.length; ++i)
                dataString+="||"+cfgArrayString[i];

            DummyData[] data = DataStorage.parseData(dataString);

            chromaUsage = Integer.parseInt(data[1].fieldValue);
            cfgMaxChroma = Float.parseFloat(data[0].fieldValue);
            generatesCorruption = Boolean.parseBoolean(data[2].fieldValue);
            genCorruption = Integer.parseInt(data[3].fieldValue);
            smeltChromaUsage = Integer.parseInt(data[4].fieldValue);
            oreSmeltingTime = Integer.parseInt(data[5].fieldValue);
            alloySmeltingTime = Integer.parseInt(data[6].fieldValue);
            quadriplingChance = Float.parseFloat(data[7].fieldValue);

            cfg.save();
        }catch(Exception e)
        {
            return;
        }
    }

    @Override
    public int[] getOutputSlots() {
        return new int[]{2};
    }

    @Override
    public int[] getAccessibleSlotsFromSide(int side)
    {
        if(side == 1)
        {
            return new int[]{0};
        }else
        if(side == 0)
        {
            return getOutputSlots();
        }else
        {
            return new int[]{1,3,5};
        }
    }

}
