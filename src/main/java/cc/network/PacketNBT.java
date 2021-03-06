package cc.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;

/**
 * Created by jakihappycity on 06.11.15.
 */
public class PacketNBT implements IMessage {

    public NBTTagCompound theTag;
    public int packetID;

    public PacketNBT()
    {
        //FML Initialisation
    }

    public PacketNBT(NBTTagCompound t)
    {
        theTag = t;
    }

    public PacketNBT setID(int i)
    {
        packetID = i;
        return this;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        theTag = ByteBufUtils.readTag(buf);
        packetID = theTag.getInteger("ссpacketData.id");
    }

    @Override
    public void toBytes(ByteBuf buf) {
        theTag.setInteger("ссpacketData.id", packetID);
        ByteBufUtils.writeTag(buf, theTag);
    }

}