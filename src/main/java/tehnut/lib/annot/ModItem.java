package tehnut.lib.annot;

import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to automatically register Items with
 * {@link net.minecraftforge.fml.common.registry.GameRegistry#register(IForgeRegistryEntry)}.
 * If the Item does not have a registry name set via {@link net.minecraft.item.Item#setRegistryName(String)},
 * this will set it for you using the provided name.
 * <p/>
 * Uses {@code ItemClass.class.getSimpleName()} for {@link #name()} if one is not provided.
 * <p/>
 * Annotate any class that should be registered.
 * <p/>
 * Requires a no-args constructor. If you need args, register the item manually.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ModItem {
    String name();
}
