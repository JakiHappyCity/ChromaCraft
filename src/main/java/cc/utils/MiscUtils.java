package cc.utils;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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

}
