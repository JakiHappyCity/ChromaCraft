package cc.common.item;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class ItemLavaCrystal extends Item implements IFuelHandler {

    @Override
    public int getBurnTime(ItemStack fuel) {
        return 600;
    }
}
