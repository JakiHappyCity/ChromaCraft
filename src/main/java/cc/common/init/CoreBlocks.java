package cc.common.init;

import cc.common.block.BlockChromaTower;
import cc.common.block.BlocksRegistry;
import cc.common.mod.CCCore;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public class CoreBlocks {

    public static void Init()
    {
        BlocksRegistry.registerBlock(chromaTower, "chromaTower", core, ItemBlock.class);
    }

    public static Block chromaTower = new BlockChromaTower(Material.glass).setBlockName("chromaTower").setBlockTextureName("cc:chroma_tower").setCreativeTab(CCCore.tabCC);
    public static final Class<CCCore> core = CCCore.class;
}