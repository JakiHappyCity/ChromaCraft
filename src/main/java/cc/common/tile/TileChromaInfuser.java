package cc.common.tile;

import cc.utils.Coord3D;
import cc.utils.UnformedItemStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class TileChromaInfuser extends TileChromaGeneric {

    public List<UnformedItemStack> requiredItemsToCraft = new ArrayList<UnformedItemStack>();
    public List<Integer> requiredStackSizeToCraft = new ArrayList<Integer>();

    public ItemStack currentCraft;

    public List<ItemStack> allRecipes = new ArrayList<ItemStack>();
    public List<IRecipe> actualRecipes = new ArrayList<IRecipe>();

    public IRecipe currentSelectedOne;

    public int currentRecipe = -1;

    public int recipeType = -1;

    public boolean isWorking = false;

    @Override
    public int[] getAccessibleSlotsFromSide(int side) {
        return new int[]{0,17};
    }

    @Override
    public void writeToNBT(NBTTagCompound i) {
        super.writeToNBT(i);
    }

    @Override
    public void readFromNBT(NBTTagCompound i) {
        super.readFromNBT(i);
    }

    @Override
    public int[] getOutputSlots() {
        return new int[0];
    }
}
