package tehnut.lib.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import tehnut.lib.LendingLibrary;
import tehnut.lib.block.base.BlockString;

public class ItemBlockString extends ItemBlock {

    private boolean nincompooped;

    public ItemBlockString(Block block) {
        super(block);

        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        try {
            if (getBlock() instanceof BlockString) {
                String unloc = super.getUnlocalizedName(stack);
                if (!unloc.endsWith("."))
                    unloc += ".";
                return unloc + ((BlockString) getBlock()).getValues().get(stack.getItemDamage());
            }
        } catch (IndexOutOfBoundsException e) {
            return super.getUnlocalizedName(stack);
        }

        if (!nincompooped) {
            LendingLibrary.getLogger().error("{} used {} for a non-{} block. The developer is a nincompoop. You should report this.", LendingLibrary.getMODID(), getClass().getSimpleName(), BlockString.class.getSimpleName());
            nincompooped = true;
        }

        return super.getUnlocalizedName(stack);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
