package tehnut.lib.tile;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import tehnut.lib.iface.IOwnedTile;

import java.util.UUID;

public class TileEntityOwned extends TileEntity implements IOwnedTile {

    public static final String OWNER_UUID = "ll:ownerUUID";
    public static final String OWNER_NAME = "ll:ownerName";

    private UUID ownerUUID = UUID.randomUUID();
    private String ownerName = "";

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString(OWNER_UUID, ownerUUID.toString());
        compound.setString(OWNER_NAME, ownerName);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        ownerUUID = UUID.fromString(compound.getString(OWNER_UUID));
        ownerName = compound.getString(OWNER_NAME);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        writeToNBT(nbt);
        return new SPacketUpdateTileEntity(getPos(), -999, nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    // IOwnedTile

    @Override
    public UUID getOwner() {
        return ownerUUID;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }
}
