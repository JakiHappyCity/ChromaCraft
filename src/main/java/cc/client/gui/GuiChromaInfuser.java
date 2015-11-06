package cc.client.gui;

import cc.api.ITEHasChroma;
import cc.client.gui.element.GuiChromaStorage;
import cc.common.tile.TileChromaInfuser;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class GuiChromaInfuser extends GuiCommon {

    public int recipiesAmount = 0;
    public int selectedRecipe;
    protected static RenderItem itemRender = new RenderItem();

    public int mouseX, mouseY;

    public GuiChromaInfuser(Container c, TileEntity tile) {
        super(c,tile);
        this.elementList.add(new GuiChromaStorage(7, 7, (ITEHasChroma) tile));
    }

    @SuppressWarnings("unchecked")
    public void initGui()
    {
        super.initGui();
    }

    @Override
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_) {
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }
}
