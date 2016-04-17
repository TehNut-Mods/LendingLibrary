package tehnut.lib.teleport;

import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketUpdateHealth;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class Teleports {

    public static class SameDim extends Teleport {

        public SameDim(BlockPos pos, Entity entity) {
            super(pos, entity);
        }

        public SameDim(int x, int y, int z, Entity entity) {
            super(x, y, z, entity);
        }

        @Override
        public void teleport() {
            if (getEntity() != null) {
                if (getEntity().timeUntilPortal <= 0) {
                    if (getEntity() instanceof EntityPlayer) {
                        EntityPlayerMP player = (EntityPlayerMP) getEntity();

                        player.setPositionAndUpdate(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5);
                        player.worldObj.updateEntityWithOptionalForce(player, false);
                        player.playerNetServerHandler.sendPacket(new SPacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
                        player.timeUntilPortal = 150;

                        player.worldObj.playSound(getPos().getX(), getPos().getY(), getPos().getZ(), SoundEvents.entity_endermen_teleport, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
                    } else {
                        WorldServer world = (WorldServer) getEntity().worldObj;

                        getEntity().setPosition(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5);
                        getEntity().timeUntilPortal = 150;
                        world.resetUpdateEntityTick();

                        getEntity().worldObj.playSound(getPos().getX(), getPos().getY(), getPos().getZ(), SoundEvents.entity_endermen_teleport, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
                    }
                }
            }
        }
    }

    @Getter
    public static class DifferentDim extends Teleport {

        private World oldWorld;
        private int newWorldID;

        public DifferentDim(BlockPos pos, Entity entity, World oldWorld, int newWorldID) {
            super(pos, entity);

            this.oldWorld = oldWorld;
            this.newWorldID = newWorldID;
        }

        public DifferentDim(int x, int y, int z, Entity entity, World oldWorld, int newWorldID) {
            super(x, y, z, entity);

            this.oldWorld = oldWorld;
            this.newWorldID = newWorldID;
        }

        @Override
        public void teleport() {
            if (getEntity() != null) {
                if (getEntity().timeUntilPortal <= 0) {
                    MinecraftServer server = FMLCommonHandler.instance().getMinecraftServerInstance();
                    WorldServer oldWorldServer = server.worldServerForDimension(getEntity().dimension);
                    WorldServer newWorldServer = server.worldServerForDimension(newWorldID);

                    if (getEntity() instanceof EntityPlayer) {
                        EntityPlayerMP player = (EntityPlayerMP) getEntity();

                        if (!player.worldObj.isRemote) {

                            player.changeDimension(newWorldID);
                            player.setPositionAndUpdate(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5);
                            player.worldObj.updateEntityWithOptionalForce(player, false);
                            player.playerNetServerHandler.sendPacket(new SPacketUpdateHealth(player.getHealth(), player.getFoodStats().getFoodLevel(), player.getFoodStats().getSaturationLevel()));
                        }
                        getEntity().worldObj.playSound(getPos().getX(), getPos().getY(), getPos().getZ(), SoundEvents.entity_endermen_teleport, SoundCategory.AMBIENT, 1.0F, 1.0F, false);

                    } else if (!getEntity().worldObj.isRemote) {
                        NBTTagCompound tag = new NBTTagCompound();

                        getEntity().writeToNBTOptional(tag);
                        getEntity().setDead();
                        oldWorld.playSound(getEntity().posX, getEntity().posY, getEntity().posZ, SoundEvents.entity_endermen_teleport, SoundCategory.AMBIENT, 1.0F, 1.0F, false);

                        Entity teleportedEntity = EntityList.createEntityFromNBT(tag, newWorldServer);
                        if (teleportedEntity != null) {
                            teleportedEntity.setLocationAndAngles(getPos().getX() + 0.5, getPos().getY() + 0.5, getPos().getZ() + 0.5, getEntity().rotationYaw, getEntity().rotationPitch);
                            teleportedEntity.forceSpawn = true;
                            newWorldServer.spawnEntityInWorld(teleportedEntity);
                            teleportedEntity.setWorld(newWorldServer);
                            teleportedEntity.timeUntilPortal = teleportedEntity instanceof EntityPlayer ? 150 : 20;
                        }

                        oldWorldServer.resetUpdateEntityTick();
                        newWorldServer.resetUpdateEntityTick();
                    }
                    getEntity().timeUntilPortal = getEntity() instanceof EntityLiving ? 150 : 20;
                    getEntity().worldObj.playSound(getPos().getX(), getPos().getY(), getPos().getZ(), SoundEvents.entity_endermen_teleport, SoundCategory.AMBIENT, 1.0F, 1.0F, false);
                }
            }
        }
    }
}
