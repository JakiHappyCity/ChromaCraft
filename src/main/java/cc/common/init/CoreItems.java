package cc.common.init;

import cc.common.item.*;
import cc.common.mod.CCCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class CoreItems {

    public static Item chromaticum = new ItemChromaticum().setUnlocalizedName("chromaticum").setCreativeTab(CCCore.tabCC).setTextureName("cc:chromaticum");
    public static Item crystalChroma = new ItemChromaCrystal().setUnlocalizedName("crystalChroma").setCreativeTab(CCCore.tabCC).setTextureName("chroma_crystal");
    public static Item lavaCrystal = new ItemLavaCrystal().setUnlocalizedName("lavaCrystal").setCreativeTab(CCCore.tabCC).setTextureName("cc:lava_crystal");
    public static Item chromaAmulet = new ItemChromaAmulet().setUnlocalizedName("chromaAmulet").setCreativeTab(CCCore.tabCC).setTextureName("cc:chroma_amulet");
    public static Item magicalDust = new ItemMagicalDust().setUnlocalizedName("magicalDust").setCreativeTab(CCCore.tabCC).setTextureName("cc:magical_dust");

    public static void Init()
    {
        GameRegistry.registerItem(chromaticum, "chromaticum");
        GameRegistry.registerItem(crystalChroma, "crystalChroma");
        GameRegistry.registerItem(lavaCrystal, "lavaCrystal");
        GameRegistry.registerItem(chromaAmulet, "chromaAmulet");
        GameRegistry.registerItem(magicalDust, "magicalDust");
    }

}
