package cc.api;

import net.minecraft.world.World;

/**
 * Created by jakihappycity on 06.11.15.
 */
public interface IHotBlock {

    /**
     * This is used to check how the chroma gain will get affected(it is basically a multiplier)
     * @return the multiplier of Chroma gain
     */
    public float getHeatModifier(World w, int x, int y, int z);

}