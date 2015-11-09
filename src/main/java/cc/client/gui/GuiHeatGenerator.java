package cc.client.gui;

import cc.api.ITEHasChroma;
import cc.client.gui.element.GuiChromaGenerated;
import cc.client.gui.element.GuiChromaState;
import cc.client.gui.element.GuiChromaStorage;
import net.minecraft.inventory.Container;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class GuiHeatGenerator extends GuiCommon {

    public GuiHeatGenerator(Container c, TileEntity tile) {
        super(c,tile);
        this.elementList.add(new GuiChromaStorage(7, 4, (ITEHasChroma) tile));
        this.elementList.add(new GuiChromaState(25, 58, (ITEHasChroma) tile, 0));
        this.elementList.add(new GuiChromaGenerated(25, 40,tile,"heatGenerator"));
    }

}
