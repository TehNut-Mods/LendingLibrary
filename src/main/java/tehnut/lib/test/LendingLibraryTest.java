package tehnut.lib.test;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import tehnut.lib.LendingLibrary;

@Mod(modid = LendingLibraryTest.MODID, name = LendingLibraryTest.MODID, version = "1.0.0")
public class LendingLibraryTest {

    public static final String MODID = "lendinglibrary-test";

    public static final CreativeTabs tabTest = new CreativeTabs("test") {
        @Override
        public Item getTabIconItem() {
            return Items.bed;
        }
    };

    private LendingLibrary library;

    public LendingLibraryTest() {
        library = new LendingLibrary(MODID);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        getLibrary().registerObjects(event);
    }

    public LendingLibrary getLibrary() {
        return library;
    }
}
