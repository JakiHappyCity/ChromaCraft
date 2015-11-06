package cc.api;

import java.util.UUID;

/**
 * Created by jakihappycity on 06.11.15.
 */
public interface ITEHasChroma {

    /**
     * this is used to get current Chroma of your tile entities
     * @return current amount of Chroma
     */
    public abstract int getChroma();

    /**
     * this is used to get max Chroma of your tile entities
     * @return max amount of Chroma the device can store
     */
    public abstract int getMaxChroma();

    /**
     * this is used to set current Chroma of your tile entities
     * @param i - the amount to add. Use negative values to remove Chroma
     * @return true if was successful, false if not
     */
    public abstract boolean setChroma(int i);

    /**
     * this is used to set maxChroma of your tile entities
     * @param f - the amount to set.
     * @return true if was successful, false if not
     */
    public abstract boolean setMaxChroma(float f);

    public abstract UUID getUUID();
    
}
