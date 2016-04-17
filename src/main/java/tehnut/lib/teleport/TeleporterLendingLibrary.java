package tehnut.lib.teleport;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.WorldServer;

public class TeleporterLendingLibrary extends Teleporter {

    public TeleporterLendingLibrary(WorldServer worldServer) {
        super(worldServer);
    }

    @Override
    public boolean makePortal(Entity entity) {
        return true;
    }

    @Override
    public void removeStalePortalLocations(long worldTime) {
        // No-op
    }

    @Override
    public boolean placeInExistingPortal(Entity entityIn, float rotationYaw) {
        return true;
    }

    @Override
    public void placeInPortal(Entity entity, float rotationYaw) {
        entity.setLocationAndAngles(MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY) + 2, MathHelper.floor_double(entity.posZ), entity.rotationYaw, entity.rotationPitch);
    }
}
