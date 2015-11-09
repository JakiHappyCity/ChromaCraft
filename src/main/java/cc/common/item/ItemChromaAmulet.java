package cc.common.item;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import cc.api.IItemRequiresChroma;
import cc.api.ItemStoresChromaInNBT;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;

/**
 * Created by jakihappycity on 08.11.15.
 */
public class ItemChromaAmulet extends ItemStoresChromaInNBT implements IBauble {

    public ItemChromaAmulet()
    {
        super();
        this.setMaxChroma(50000);
        this.maxStackSize = 1;
        this.bFull3D = true;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onEquipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public void onUnequipped(ItemStack itemStack, EntityLivingBase entityLivingBase) {

    }

    @Override
    public boolean canEquip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean canUnequip(ItemStack itemStack, EntityLivingBase entityLivingBase) {
        return true;
    }

    @Override
    public boolean setChroma(ItemStack stack, int amount) {
        return true;
    }

    @Override
    public int getChroma(ItemStack stack) {
        return 10000;
    }

    @Override
    public int getMaxChroma(ItemStack stack) {
        return 1000000;
    }
}
