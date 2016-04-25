package tehnut.lib.test.block;

import net.minecraft.block.material.Material;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.annot.ModBlock;
import tehnut.lib.annot.Used;
import tehnut.lib.block.base.BlockString;
import tehnut.lib.block.item.ItemBlockString;
import tehnut.lib.iface.IVariantProvider;
import tehnut.lib.test.LendingLibraryTest;

import java.util.ArrayList;
import java.util.List;

@ModBlock(name = "BlockStringTest", itemBlock = ItemBlockString.class)
@Used
public class BlockStringTest extends BlockString implements IVariantProvider {

    public static final String[] NAMES = new String[]{"test1", "test2"};

    public BlockStringTest() {
        super(Material.CLAY, NAMES);

        setCreativeTab(LendingLibraryTest.tabTest);
        setUnlocalizedName("test.string");
    }

    @Override
    public List<Pair<Integer, String>> getVariants() {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        for (int i = 0; i < NAMES.length; i++)
            ret.add(Pair.of(i, "type=" + NAMES[i]));
        return ret;
    }
}
