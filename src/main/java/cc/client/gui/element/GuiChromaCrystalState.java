package cc.client.gui.element;

import cc.utils.MathUtils;
import cc.api.ITEHasChroma;
import cc.common.item.ItemChromaCrystal;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

/**
 * Created by jakihappycity on 08.11.15.
 */
public class GuiChromaCrystalState extends GuiTextField {

    public TileEntity tile;
    public int slotNum;

    public GuiChromaCrystalState(int i, int j, TileEntity t, int slot)
    {
        super(i,j);
        tile = t;
        slotNum = slot;
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
        IInventory inventory = (IInventory) tile;
        if(inventory.getStackInSlot(slotNum) == null || !(inventory.getStackInSlot(slotNum).getItem() instanceof ItemChromaCrystal))
        {
            Minecraft.getMinecraft().fontRenderer.drawString("No Chroma Crystal!", posX+6, posY+5, 0xff0000, true);
        }else
        {
            if(inventory.getStackInSlot(slotNum).getTagCompound() == null)
            {
                Minecraft.getMinecraft().fontRenderer.drawString("Chroma Crystal Not Bound!", posX+4, posY+5, 0xff0000, true);
            }else
            {
                int o[] = ItemChromaCrystal.getCoords(inventory.getStackInSlot(slotNum));
                if(this.tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) == null)
                {
                    Minecraft.getMinecraft().fontRenderer.drawString("No Tile At Pos!", posX+5, posY+5, 0xff0000, true);
                }else
                {
                    if(!(this.tile.getWorldObj().getTileEntity(o[0], o[1], o[2]) instanceof ITEHasChroma))
                    {
                        Minecraft.getMinecraft().fontRenderer.drawString("Not Magical!", posX+12, posY+5, 0xff0000, true);
                    }else
                    {
                        if(!(MathUtils.getDifference(tile.xCoord, o[0]) <= 16 && MathUtils.getDifference(tile.yCoord, o[1]) <= 16 && MathUtils.getDifference(tile.zCoord, o[2]) <= 16))
                        {
                            Minecraft.getMinecraft().fontRenderer.drawString("Not In Range!", posX+8, posY+5, 0xff0000, true);
                        }else
                        {
                            if(((ITEHasChroma)(tile.getWorldObj().getTileEntity(o[0], o[1], o[2]))).getChroma() <= 0)
                            {
                                Minecraft.getMinecraft().fontRenderer.drawString("No Chroma In Tile!", posX+6, posY+5, 0xff0000, true);
                            }else
                            {
                                Minecraft.getMinecraft().fontRenderer.drawString("Working", posX+22, posY+5, 0x00ff00, true);
                            }
                        }
                    }
                }
            }
        }
    }


}
