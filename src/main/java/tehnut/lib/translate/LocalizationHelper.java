package tehnut.lib.translate;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import tehnut.lib.LendingLibrary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LocalizationHelper {

    public static final List<LocalizationHelper> localizationHelpers = new ArrayList<LocalizationHelper>();

    private final List<String> keys;
    private final int deletionId;
    private final SimpleNetworkWrapper networkWrapper;

    public LocalizationHelper(SimpleNetworkWrapper networkWrapper, int id) {
        this(networkWrapper, id, new Random().nextInt());
    }

    public LocalizationHelper(SimpleNetworkWrapper networkWrapper, int id, int deletionId) {
        networkWrapper.registerMessage(MessageLocalization.Handler.class, MessageLocalization.class, id, Side.CLIENT);
        this.networkWrapper = networkWrapper;
        this.deletionId = deletionId;
        this.keys = new ArrayList<String>();
        localizationHelpers.add(this);
    }

    @SideOnly(Side.CLIENT)
    public ITextComponent localize(String key, Object... format) {
        if (!keys.contains(key))
            keys.add(key);
        return new TextComponentString(I18n.format(key, (Object[]) format));
    }

    @SideOnly(Side.CLIENT)
    public String getText(String key, Object... format) {
        return localize(key, format).getFormattedText();
    }

    protected int getKeyId(String key) {
        if (!keys.contains(key))
            keys.add(key);

        return keys.indexOf(key);
    }

    public void sendLocalization(EntityPlayerMP player, String key, Object... format) {
        LendingLibrary.getModLogger().debug("Sent key [{}] to client {} [{}].", key, player.getName(), player.getGameProfile().getId());
        networkWrapper.sendTo(new MessageLocalization(localizationHelpers.indexOf(this), key, format), player);
    }

    public List<String> getKeys() {
        return keys;
    }

    public int getDeletionId() {
        return deletionId;
    }
}
