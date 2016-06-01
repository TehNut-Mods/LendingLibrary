package tehnut.lib.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.lib.LendingLibrary;

@SideOnly(Side.CLIENT)
public class RenderHelper {

    public static boolean addPlayerLayerRenderer(Class<? extends LayerRenderer<?>> layerRendererClass) {
        RenderManager renderManager = Minecraft.getMinecraft().getRenderManager();
        try {
            RenderPlayer renderPlayer = ObfuscationReflectionHelper.getPrivateValue(RenderManager.class, renderManager, "playerRenderer", "field_178637_m");
            LayerRenderer<?> layerRenderer = layerRendererClass.getConstructor(RenderPlayer.class).newInstance(renderPlayer);
            renderPlayer.addLayer(layerRenderer);
            return true;
        } catch (Exception e) {
            LendingLibrary.getLogger().error(e.getLocalizedMessage());
        }

        return false;
    }
}
