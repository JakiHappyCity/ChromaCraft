package cc.common.init;

import cc.common.item.ItemChromaCrystal;
import cc.common.item.ItemChromaticum;
import cc.common.mod.CCCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class CoreItems {

    public static Item chromaticum = new ItemChromaticum().setUnlocalizedName("chromaticum").setCreativeTab(CCCore.tabCC).setTextureName("cc:chromaticum");
    public static Item crystalChroma = new ItemChromaCrystal().setUnlocalizedName("crystalChroma").setCreativeTab(CCCore.tabCC);

    public static void Init()
    {
        GameRegistry.registerItem(chromaticum, "chromaticum");
        GameRegistry.registerItem(crystalChroma, "crystalChroma");
    }

}
