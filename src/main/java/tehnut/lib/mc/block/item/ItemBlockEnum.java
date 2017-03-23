package tehnut.lib.mc.block.item;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.MathHelper;
import tehnut.lib.mc.block.BlockEnum;

public class ItemBlockEnum<E extends Enum<E> & IStringSerializable> extends ItemBlock {

    private final BlockEnum<E> blockEnum;

    public ItemBlockEnum(BlockEnum<E> block) {
        super(block);

        this.blockEnum = block;

        if (block.getTypes().length > 1)
            setHasSubtypes(true);
    }

    @SuppressWarnings("unchecked")
    @Override
    public BlockEnum<E> getBlock() {
        return (BlockEnum<E>) super.getBlock();
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        String base = getBlock().getUnlocalizedName();
        if (!base.endsWith("."))
            base += ".";
        return base + blockEnum.getTypes()[MathHelper.clamp(stack.getItemDamage(), 0, blockEnum.getTypes().length > 15 ? 15 : blockEnum.getTypes().length)].getName();
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
