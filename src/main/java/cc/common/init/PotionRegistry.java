/*package cc.common.init;

import cc.common.potion.PotionChromed;
import cc.utils.MiscUtils;
import net.minecraft.potion.Potion;

/**
 * Created by jakihappycity on 08.11.15.
 *//*
public class PotionRegistry {

    public static void registerPotions()
    {
        int pStart = 20;
        pStart = getNextPotionId(pStart);
        if(pStart >= 0)
        {
            potionChromed = new PotionChromed(pStart, true, 0xff00ff);
        }
    }

    static int getNextPotionId(int start)
    {
        if(Potion.potionTypes != null && start > 0 && start < Potion.potionTypes.length && Potion.potionTypes[start] == null)
            return start;
        if(++start < Potion.potionTypes.length)
            start = getNextPotionId(start);
        else
            start = -1;
        if(start == -1)
            start = MiscUtils.extendPotionArray(1);
        return start;
    }

    public static PotionChromed potionChromed;

}
*/