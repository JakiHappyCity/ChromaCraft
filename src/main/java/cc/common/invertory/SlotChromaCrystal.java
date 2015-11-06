package cc.common.invertory;

import cc.common.item.ItemChromaCrystal;
import cc.common.item.ItemDrop;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class SlotChromaCrystal extends Slot {

    public SlotChromaCrystal(IInventory par2IInventory, int par3, int par4, int par5)
    {
        super(par2IInventory, par3, par4, par5);
        this.setBackgroundIcon(ItemDrop.itemIcons[3]);
    }

    @Override
    public boolean isItemValid(ItemStack par1ItemStack)
    {
        return par1ItemStack.getItem() instanceof ItemChromaCrystal;
    }

}
