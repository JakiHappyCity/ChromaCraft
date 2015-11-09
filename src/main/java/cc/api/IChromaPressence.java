package cc.api;

/**
 * Created by jakihappycity on 08.11.15.
 */
public interface IChromaPressence {

    /**
     * A method to know can the ChromaPressence increase it's Chroma by it's own.
     * @return true if can, false if can't
     */
    public boolean getFlag();

    /**
     * You can change will the ChromaPressence create corruption
     * @param b : boolean - true if you do not want, false if you do
     */
    public void setFlag(boolean b);


    /**
     * This is a chesk, if can the ChromaPressence leave in the world, if it has 0 chroma left.
     * @return true if it stays, false if it will be removed from the world.
     */
    public boolean canAlwaysStay();

    /**
     * You can change will the ChromaPressence stay in the world if it has 0 energy left.
     * @param b : boolean - true if you want it to stay, false if you want it to dissapear
     */
    public void setAlwaysStay(boolean b);


    /**
     * The Chroma balance. The basics of the corruption mechanics. Should return 1.0F if the ChromaPressence is not corrupted, and anything else if it is.
     * @return 1.0F if not corrupted, anything from 0.0F to 2.0F if it is.
     */
    public float getBalance();

    /**
     * You can change the balance using this. Do not put numbers lower than 0.1F and greater than 1.9F. 
     * @param f : float - the more difference it has to 1.0F the faster the Chroma amount will increase. The more the amount is, the faster the ChromaPressence will spin.
     */
    public void setBalance(float f);

    /**
     * The chroma amount currently stored in the ChromaPressence. The higher the number is, the more corruption will be affected.
     * @return integer of the Chroma amount.
     */
    public int getChroma();

    /**
     * Set your chroma amount here. If the ChromaPressence is corrupted, and Chroma is higher than 50000 it will automatically release all corruption in the world and become pure.
     * @param i : integer. Do not put values lower than 0.
     */
    public void setChroma(int i);
    
}
