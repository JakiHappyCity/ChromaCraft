package cc.api;

import cc.utils.CCUtils;
import cc.utils.MiscUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

/**
 * Created by jakihappycity on 08.11.15.
 */
public class ItemStoresChromaInNBT extends Item implements IItemRequiresChroma{
    int maxChroma = 100000;

    public ItemStoresChromaInNBT()
    {
    }

    public ItemStoresChromaInNBT setMaxChroma(int max)
    {
        maxChroma = max;
        return this;
    }

    @Override
    public boolean setChroma(ItemStack stack, int amount) {
        if(MiscUtils.getStackTag(stack).getInteger("chroma")+amount >= 0 && MiscUtils.getStackTag(stack).getInteger("chroma")+amount<=MiscUtils.getStackTag(stack).getInteger("maxChroma"))
        {
            MiscUtils.getStackTag(stack).setInteger("chroma", MiscUtils.getStackTag(stack).getInteger("chroma")+amount);
            return true;
        }
        return false;
    }

    @Override
    public int getChroma(ItemStack stack) {
        // TODO Auto-generated method stub
        return MiscUtils.getStackTag(stack).getInteger("chroma");
    }

    @Override
    public void onUpdate(ItemStack itemStack, World world, Entity entity, int indexInInventory, boolean isCurrentItem)
    {
        CCUtils.initChromaTag(itemStack, maxChroma);
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        par3List.add(CCUtils.getStackTag(par1ItemStack).getInteger("chroma") + "/" + CCUtils.getStackTag(par1ItemStack).getInteger("maxChroma") + " Chroma");
    }

    public void aFunc(ItemStack stack, World world, Entity entity, int par4, boolean par5)
    {

    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @Override
    public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
    {
        for (int var4 = 0; var4 < 1; ++var4)
        {
            ItemStack min = new ItemStack(par1, 1, 0);
            CCUtils.initChromaTag(min, maxChroma);
            ItemStack max = new ItemStack(par1, 1, 0);
            CCUtils.initChromaTag(max, maxChroma);
            CCUtils.getStackTag(max).setInteger("chroma", CCUtils.getStackTag(max).getInteger("maxChroma"));
            par3List.add(min);
            par3List.add(max);
        }
    }

    @Override
    public int getMaxChroma(ItemStack stack) {
        // TODO Auto-generated method stub
        return this.maxChroma;
    }
}