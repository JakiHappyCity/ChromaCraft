package cc.client.render.tile;

import cc.common.block.BlockHeatGenerator;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import org.lwjgl.opengl.GL11;

/**
 * Created by jakihappycity on 08.11.15.
 */
public class RenderBlocksCC implements ISimpleBlockRenderingHandler {

    @Override
    public void renderInventoryBlock(Block block, int metadata, int modelId,
                                     RenderBlocks renderer) {
        if(block instanceof BlockHeatGenerator)
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0F,-0.5F,0F);
            Minecraft.getMinecraft().renderEngine.bindTexture(RenderHeatGenerator.textures);
            RenderHeatGenerator.model.renderAll();
            GL11.glPopMatrix();
        }
    }

    @Override
    public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
        return true;
    }

    @Override
    public boolean shouldRender3DInInventory(int modelId) {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public int getRenderId() {
        // TODO Auto-generated method stub
        return 2634;
    }

}
