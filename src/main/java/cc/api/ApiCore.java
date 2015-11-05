package cc.api;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class ApiCore {

    /**
     * All categories the Chromaticum can have
     */
    public static List<CategoryEntry> categories = new ArrayList<CategoryEntry>();

    /**
     * Finds a DiscoveryEntry by the given ItemStack. The ItemStack would either be in the list of items at one of the pages, or will be a crafting result.
     * @param referal - the ItemStack to lookup.
     * @return A valid DiscoveryEntry if was found, null otherwise
     */
    public static DiscoveryEntry findDiscoveryByIS(ItemStack referal)
    {
        if(referal == null)return null;
        int size = referal.stackSize;
        referal.stackSize = 0;
        DiscoveryEntry de = ApiCore.discoveriesByIS.get(referal.toString());
        referal.stackSize = size;
        return de;
    }

    /**
     * A list of all discoveries bound to generic ItemStack
     */
    public static Hashtable<String, DiscoveryEntry> discoveriesByIS = new Hashtable<String, DiscoveryEntry>();

}
