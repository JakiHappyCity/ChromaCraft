package cc.utils;

import cc.common.mod.CCCore;
import cc.network.PacketNBT;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jakihappycity on 05.11.15.
 */
public class CCUtils {

    private static final List<ScheduledServerAction> actions = new ArrayList<ScheduledServerAction>();

    public static void requestScheduledTileSync(TileEntity tile, EntityPlayer requester)
    {
        Side s = FMLCommonHandler.instance().getEffectiveSide();
        if(s == Side.CLIENT)
        {
            if(tile.getWorldObj() == null || tile.getWorldObj().provider == null)
                return;

            NBTTagCompound clientData = new NBTTagCompound();
            clientData.setString("playername", requester.getCommandSenderName());
            clientData.setInteger("x", tile.xCoord);
            clientData.setInteger("y", tile.yCoord);
            clientData.setInteger("z", tile.zCoord);
            clientData.setInteger("dim", tile.getWorldObj().provider.dimensionId);
            PacketNBT packet = new PacketNBT(clientData).setID(7);
            CCCore.network.sendToServer(packet);
        }else
        {
            addScheduledAction(new ServerToClientSyncAction(requester, tile));
        }
    }

    public static void addScheduledAction(ScheduledServerAction ssa)
    {
        if(FMLCommonHandler.instance().getEffectiveSide() != Side.SERVER)
            Notifier.notifyCustomMod("EssentialCraft", "[WARNING][SEVERE]Trying to add a scheduled server action not on server side, aborting!");

        actions.add(ssa);
    }

}
