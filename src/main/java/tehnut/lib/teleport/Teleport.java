package tehnut.lib.teleport;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

public abstract class Teleport implements ITeleport {

    private final BlockPos pos;
    private final Entity entity;

    public Teleport(BlockPos pos, Entity entity) {
        this.pos = pos;
        this.entity = entity;
    }

    public Teleport(int x, int y, int z, Entity entity) {
        this(new BlockPos(x, y, z), entity);
    }

    public BlockPos getPos() {
        return pos;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return "Teleport{" +
                "pos=" + pos +
                ", entity=" + entity +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Teleport teleport = (Teleport) o;

        if (getPos() != null ? !getPos().equals(teleport.getPos()) : teleport.getPos() != null) return false;
        return getEntity() != null ? getEntity().equals(teleport.getEntity()) : teleport.getEntity() == null;

    }

    @Override
    public int hashCode() {
        int result = getPos() != null ? getPos().hashCode() : 0;
        result = 31 * result + (getEntity() != null ? getEntity().hashCode() : 0);
        return result;
    }
}
