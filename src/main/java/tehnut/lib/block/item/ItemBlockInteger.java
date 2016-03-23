package tehnut.lib.block.item;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import tehnut.lib.LendingLibrary;
import tehnut.lib.block.base.BlockInteger;

public class ItemBlockInteger extends ItemBlock {

    private boolean nincompooped;

    public ItemBlockInteger(Block block) {
        super(block);

        setHasSubtypes(true);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (getBlock() instanceof BlockInteger) {
            String unloc = super.getUnlocalizedName(stack);
            if (!unloc.endsWith("."))
                unloc += ".";
            return unloc + stack.getItemDamage();
        }

        if (!nincompooped) {
            LendingLibrary.getLogger().error("{} used {} for a non-{} block. The developer is a nincompoop. You should report this.", LendingLibrary.getMODID(), getClass().getSimpleName(), BlockInteger.class.getSimpleName());
            nincompooped = true;
        }

        return super.getUnlocalizedName(stack);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }
}
