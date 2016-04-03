package tehnut.lib.test.block;

import net.minecraft.block.material.Material;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.annot.ModBlock;
import tehnut.lib.annot.Used;
import tehnut.lib.block.base.BlockInteger;
import tehnut.lib.block.item.ItemBlockInteger;
import tehnut.lib.iface.IVariantProvider;
import tehnut.lib.test.LendingLibraryTest;

import java.util.ArrayList;
import java.util.List;

@ModBlock(name = "BlockIntegerTest", itemBlock = ItemBlockInteger.class)
@Used
public class BlockIntegerTest extends BlockInteger implements IVariantProvider {

    public BlockIntegerTest() {
        super(Material.carpet, 3);

        setCreativeTab(LendingLibraryTest.tabTest);
        setUnlocalizedName("test.integer");
    }

    @Override
    public List<Pair<Integer, String>> getVariants() {
        List<Pair<Integer, String>> ret = new ArrayList<Pair<Integer, String>>();
        ret.add(Pair.of(0, "meta=0"));
        ret.add(Pair.of(1, "meta=1"));
        ret.add(Pair.of(2, "meta=2"));
        ret.add(Pair.of(3, "meta=3"));
        return ret;
    }
}
