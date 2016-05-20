package tehnut.lib.translate;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiNewChat;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class MessageLocalization implements IMessage {

    private int helperId;
    private String key;
    private String[] format;

    public MessageLocalization() {

    }

    public MessageLocalization(int helperId, String key, String... format) {
        this.helperId = helperId;
        this.key = key;
        this.format = format;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.helperId = buf.readInt();
        this.key = ByteBufUtils.readUTF8String(buf);
        this.format = new String[buf.readInt()];
        for (int i = 0; i < format.length; i++)
            format[i] = ByteBufUtils.readUTF8String(buf);
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(helperId);
        ByteBufUtils.writeUTF8String(buf, key);
        buf.writeInt(format.length);
        for (String string : format)
            ByteBufUtils.writeUTF8String(buf, string);
    }

    public static class Handler implements IMessageHandler<MessageLocalization, IMessage> {

        @Override
        public IMessage onMessage(MessageLocalization message, MessageContext ctx) {
            GuiNewChat chat = Minecraft.getMinecraft().ingameGUI.getChatGUI();
            LocalizationHelper localizationHelper = LocalizationHelper.localizationHelpers.get(message.helperId);

            chat.deleteChatLine(localizationHelper.getDeletionId() + localizationHelper.getKeyId(message.key));
            chat.printChatMessageWithOptionalDeletion(localizationHelper.localize(message.key, message.format), localizationHelper.getDeletionId() + localizationHelper.getKeyId(message.key));

            return null;
        }
    }
}
