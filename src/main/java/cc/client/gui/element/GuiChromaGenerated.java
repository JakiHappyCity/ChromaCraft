package cc.client.gui.element;

import org.lwjgl.opengl.GL11;

import cc.utils.MathUtils;
import cc.utils.MiscUtils;
import cc.api.IHotBlock;
import cc.common.tile.TileHeatGenerator;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class GuiChromaGenerated extends GuiTextField {

    public TileEntity tile;
    public String tileValue;

    public GuiChromaGenerated(int i, int j, TileEntity t, String tileType)
    {
        super(i,j);
        tile = t;
        tileValue = tileType;
    }

    @Override
    public ResourceLocation getElementTexture() {
        // TODO Auto-generated method stub
        return super.getElementTexture();
    }

    @Override
    public void draw(int posX, int posY) {
        super.draw(posX, posY);
    }

    @Override
    public int getX() {
        // TODO Auto-generated method stub
        return super.getX();
    }

    @Override
    public int getY() {
        // TODO Auto-generated method stub
        return super.getY();
    }

    @Override
    public void drawText(int posX, int posY) {
        if(tileValue.equals("heatGenerator"))
        {
            if(tile instanceof TileHeatGenerator)
            {
                TileHeatGenerator furnace = (TileHeatGenerator) tile;
                MiscUtils.bindTexture("minecraft", "textures/gui/container/furnace.png");
                this.drawTexturedModalRect(posX+100, posY+2, 55, 36, 15, 15);
                if(furnace.currentBurnTime > 0)
                {
                    int scaledSize = MathUtils.pixelatedTextureSize(furnace.currentBurnTime, furnace.currentMaxBurnTime, 14)+1;
                    this.drawTexturedModalRect(posX+101, posY+2+15-scaledSize, 176, 15-scaledSize, 15, scaledSize);
                }
                float chromaGenerated = TileHeatGenerator.chromaGenerated;
                float chromaFactor = 1.0F;
                Block[] b = new Block[4];
                b[0] = furnace.getWorldObj().getBlock(furnace.xCoord+2, furnace.yCoord, furnace.zCoord);
                b[1] = furnace.getWorldObj().getBlock(furnace.xCoord-2, furnace.yCoord, furnace.zCoord);
                b[2] = furnace.getWorldObj().getBlock(furnace.xCoord, furnace.yCoord, furnace.zCoord+2);
                b[3] = furnace.getWorldObj().getBlock(furnace.xCoord, furnace.yCoord, furnace.zCoord-2);
                int[] ox = new int[]{2,-2,0,0};
                int[] oz = new int[]{0,0,2,-2};
                for(int i = 0; i < 4; ++i)
                {
                    if(b[i] == Blocks.air)
                    {
                        chromaFactor*=0;
                    }else if(b[i] == Blocks.netherrack)
                    {
                        chromaFactor*=0.75F;
                    }else if(b[i] == Blocks.lava)
                    {
                        chromaFactor*=0.95F;
                    }else if(b[i] == Blocks.fire)
                    {
                        chromaFactor*=0.7F;
                    }else if(b[i] instanceof IHotBlock)
                    {
                        chromaFactor*=(((IHotBlock)b[i]).getHeatModifier(tile.getWorldObj(), tile.xCoord+ox[i], tile.yCoord, tile.zCoord+oz[i]));
                    }else
                    {
                        chromaFactor*=0.5F;
                    }

                }
                chromaGenerated*=chromaFactor;
                Minecraft.getMinecraft().fontRenderer.drawStringWithShadow((int)chromaGenerated+" Chroma/t", posX+2, posY+5, 0xffffff);
            }
        }
    }

}
