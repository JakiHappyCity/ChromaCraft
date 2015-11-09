package cc.api;

import net.minecraft.item.ItemStack;

/**
 * Created by jakihappycity on 08.11.15.
 */
public interface IItemRequiresChroma {

    /**
     * This is called so you can use different ways of storing Chroma in your items. You can find some examples below
     * @param stack - ItemStack of your item. 
     * @param amount - the amount of Chroma to add or remove. Use negative numbers to decrease Chroma.
     * @return true, if this operation was successful, false if not
     */
    public abstract boolean setChroma(ItemStack stack, int amount);

    /**
     * This is called so you can use different ways of storing Chroma in your items. You can find some examples below
     * @param stack - ItemStack of your item. 
     * @return the amount of Chroma in your stack
     */
    public abstract int getChroma(ItemStack stack);

    /**
     * This is called so you can use different ways of storing Chroma in your items. You can find some examples below
     * @param stack - ItemStack of your item. 
     * @return the amount of Chroma your item can store
     */
    public abstract int getMaxChroma(ItemStack stack);
    
}
