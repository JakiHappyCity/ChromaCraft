package cc.client.gui.element;

import cc.api.ITEHasChroma;
import cc.client.gui.GuiElement;
import cc.common.mod.CCCore;
import cc.utils.MathUtils;
import cc.utils.MiscUtils;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class GuiChromaStorage extends GuiElement {

    private ResourceLocation rec = new ResourceLocation("cc","textures/gui/chromaStorage.png");

    public int x;
    public int y;
    public ITEHasChroma tile;

    public GuiChromaStorage(int i, int j, ITEHasChroma t)
    {
        x = i;
        y = j;
        tile = t;
    }

    @Override
    public ResourceLocation getElementTexture() {
        // TODO Auto-generated method stub
        return rec;
    }

    @Override
    public void draw(int posX, int posY) {
        this.drawTexturedModalRect(posX, posY, 0, 0, 18, 72);
        int percentageScaled = MathUtils.pixelatedTextureSize(tile.getChroma(), tile.getMaxChroma(), 72);
        IIcon icon = (IIcon) CCCore.proxy.getClientIcon("chroma");
        MiscUtils.drawTexture(posX+1, posY-1+(74-percentageScaled), icon, 16, percentageScaled-2, 0);
    }

    @Override
    public int getX() {
        // TODO Auto-generated method stub
        return x;
    }

    @Override
    public int getY() {
        // TODO Auto-generated method stub
        return y;
    }

}
