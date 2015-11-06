package cc.utils;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class ServerToClientSyncAction extends ScheduledServerAction{

    EntityPlayer p;
    TileEntity t;

    public ServerToClientSyncAction(EntityPlayer requester, TileEntity tile)
    {
        super(20);
    }

    @Override
    public void execute()
    {
        if(t != null && p != null && t.getWorldObj() != null)
            if(t.getWorldObj().blockExists(t.xCoord, t.yCoord, t.zCoord))
                if(t.getWorldObj().getTileEntity(t.xCoord, t.yCoord, t.zCoord) == t)
                    MiscUtils.sendPacketToPlayer(t.getWorldObj(), t.getDescriptionPacket(), p);
    }


}