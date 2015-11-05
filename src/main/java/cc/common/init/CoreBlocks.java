package cc.common.init;

import cc.common.block.ModBlock;
import cc.common.mod.CCCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class CoreBlocks {

    public static Block oreChroma = new ModBlock(Material.rock).setBlockName("oreChroma").setBlockTextureName("cc:ore_chroma").setCreativeTab(CCCore.tabCC);

    public static void Init()
    {
        GameRegistry.registerBlock(oreChroma, "oreChroma");
    }

}
