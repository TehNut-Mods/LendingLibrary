package tehnut.lib.test;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.lib.LendingLibrary;
import tehnut.lib.util.helper.LogHelper;

import java.io.File;

@Mod(modid = LendingLibraryTest.MODID, name = LendingLibraryTest.MODID, version = "1.0.0")
public class LendingLibraryTest {

    public static final String MODID = "lendinglibrary-test";

    @Mod.Instance(MODID)
    public static LendingLibraryTest instance;

    public static final CreativeTabs tabTest = new CreativeTabs("test") {
        @Override
        public Item getTabIconItem() {
            return Items.bed;
        }
    };

    private LendingLibrary library;
    private LogHelper logger;

    public LendingLibraryTest() {
        library = new LendingLibrary(MODID);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = new LogHelper(event.getModLog());
        getLibrary().registerObjects(event);

        JsonConfigHandler.init(new File(event.getModConfigurationDirectory(), "TestConfig.json"));
    }

    public LendingLibrary getLibrary() {
        return library;
    }

    public LogHelper getLogger() {
        return logger;
    }
}
