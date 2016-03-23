package tehnut.lib.iface;

import net.minecraft.client.renderer.ItemMeshDefinition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Provides a custom {@link ItemMeshDefinition} for automatic registration of
 * renders.
 */
public interface IMeshProvider {

    @SideOnly(Side.CLIENT)
    ItemMeshDefinition getMeshDefinition();

    List<String> getVariants();

    @Nullable
    ResourceLocation getCustomLocation();
}
