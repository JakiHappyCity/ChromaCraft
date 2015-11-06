package cc.common.item;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cc.network.proxy.ClientProxy;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class ItemDrop extends Item {

    public static String[] dropNames = new String[]{"flame","water","earth","air","chroma","unknown"};
    public static IIcon[] itemIcons = new IIcon[15];

    public ItemDrop()
    {
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    public String getUnlocalizedName(ItemStack p_77667_1_)
    {
        return this.getUnlocalizedName()+dropNames[p_77667_1_.getItemDamage()];
    }

    public void registerIcons(IIconRegister p_94581_1_)
    {
        super.registerIcons(p_94581_1_);
        for(int i = 0; i < 5; ++i)
            itemIcons[i] = p_94581_1_.registerIcon("cc:chromaCrystals/crystal_"+dropNames[i]);
        ClientProxy.chromaParticleIcon = p_94581_1_.registerIcon("cc:particles/particle_chroma_dummy_icon");
    }

    @SideOnly(Side.CLIENT)
    public IIcon getIconFromDamage(int i)
    {
        return itemIcons[i];
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item p_150895_1_, CreativeTabs p_150895_2_, List p_150895_3_)
    {
        for(int i = 0; i < 5; ++i)
        {
            p_150895_3_.add(new ItemStack(p_150895_1_,1,i));
        }
    }

}
