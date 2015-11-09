package cc.client.gui;

import cc.api.ITEHasChroma;
import cc.client.gui.element.GuiChromaCrystalState;
import cc.client.gui.element.GuiChromaState;
import cc.client.gui.element.GuiChromaStorage;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class GuiChromaTower extends GuiCommon {

    public GuiChromaTower(Container c, TileEntity tile) {
        super(c,tile);
        this.elementList.add(new GuiChromaStorage(7, 7, (ITEHasChroma) tile));
        this.elementList.add(new GuiChromaCrystalState(26,41, tile, 0));
        this.elementList.add(new GuiChromaState(26, 61, (ITEHasChroma) tile, 0));
    }

}
