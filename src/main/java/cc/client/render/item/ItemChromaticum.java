package cc.client.render.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cc.utils.MiscUtils;
import cc.common.mod.CCCore;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class ItemChromaticum extends Item {

    public IIcon icon;
    public ItemChromaticum() {
        super();
        this.maxStackSize = 1;
    }


    @Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
        CCCore.proxy.openBookGUIForPlayer();
        return par1ItemStack;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public void addInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, List par3List, boolean par4)
    {
        NBTTagCompound theTag = MiscUtils.getStackTag(par1ItemStack);
        par3List.add("\u00a76" + StatCollector.translateToLocal("cc.txt.book.containedKnowledge"));
        int tier = theTag.getInteger("tier");
        for(int i = 0; i <= tier; ++i)
        {
            par3List.add("\u00a77-\u00a7o" + StatCollector.translateToLocal("cc.txt.book.tier_"+i));
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < 5; ++i)
        {
            ItemStack book = new ItemStack(p_150895_1_);
            NBTTagCompound bookTag = new NBTTagCompound();
            bookTag.setInteger("tier", i);
            book.setTagCompound(bookTag);
            p_150895_3_.add(book);
        }
    }

    @Override
    public void registerIcons(IIconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.icon = par1IconRegister.registerIcon("cc:chromaticum");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconIndex(ItemStack i)
    {
        return this.icon;
    }

}
