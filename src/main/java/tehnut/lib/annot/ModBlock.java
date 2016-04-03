package tehnut.lib.annot;

import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to automatically register Blocks with
 * {@link net.minecraftforge.fml.common.registry.GameRegistry#register(IForgeRegistryEntry)}
 * If the Block does not have a registry name set via {@link net.minecraft.block.Block#setRegistryName(String)},
 * this will set it for you using the provided name.
 * <p/>
 * Handles {@link ItemBlock} and {@link TileEntity} registration as well if values are
 * provided.
 * <p/>
 * Annotate any class that should be registered.
 * <p/>
 * Requires a no-args constructor. If you need args, register the block manually.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModBlock {
    String name();

    Class<? extends TileEntity> tileEntity() default TileEntity.class;

    Class<? extends ItemBlock> itemBlock() default ItemBlock.class;
}
