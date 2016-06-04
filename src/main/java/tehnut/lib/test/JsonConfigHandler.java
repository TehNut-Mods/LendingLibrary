package tehnut.lib.test;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import tehnut.lib.json.JsonHelper;
import tehnut.lib.json.serialization.SerializerBlockPos;
import tehnut.lib.json.serialization.SerializerBlockStack;
import tehnut.lib.json.serialization.SerializerItemStack;
import tehnut.lib.json.serialization.SerializerPair;
import tehnut.lib.util.BlockStack;

import java.io.File;

public class JsonConfigHandler {

    public static void init(File jsonConfig) {
        JsonHelper.setGson(
                new SerializerBlockPos(),
                new SerializerBlockStack(),
                new SerializerItemStack(),
                new SerializerPair<BlockPos, BlockStack>()
        );
        JsonHelper.<TestConfig>tryCreateDefault(jsonConfig, new TestConfig());
        TestConfig testConfig = JsonHelper.fromJson(jsonConfig, TestConfig.class);
        LendingLibraryTest.instance.getLogger().info(testConfig == null ? "null" : testConfig.toString());
    }

    public static void init2(File jsonConfig) {
        ItemStack def = new ItemStack(Items.DIAMOND_SWORD, 4, 1);
        def.setTagCompound(new NBTTagCompound());
        def.getTagCompound().setInteger("newInt", 1);
        def.getTagCompound().setBoolean("newBool", true);
        JsonHelper.<ItemStack>tryCreateDefault(jsonConfig, def);
        ItemStack stack = JsonHelper.fromJson(jsonConfig, ItemStack.class);
        LendingLibraryTest.instance.getLogger().info(stack == null ? "null" : stack.toString());
    }

    public static class TestConfig {
        private String testString = "blah";
        private int testInt = 0;
        private double testDouble = 0.5;
        private Pair<BlockPos, BlockStack> testPair = Pair.of(new BlockPos(1, 2, 3), new BlockStack(Blocks.GOLD_BLOCK));

        public TestConfig(String testString, int testInt, double testDouble, Pair<BlockPos, BlockStack> testPair) {
            this.testString = testString;
            this.testInt = testInt;
            this.testDouble = testDouble;
            this.testPair = testPair;
        }

        public TestConfig() {

        }

        public String getTestString() {
            return testString;
        }

        public int getTestInt() {
            return testInt;
        }

        public double getTestDouble() {
            return testDouble;
        }

        public Pair<BlockPos, BlockStack> getTestPair() {
            return new ImmutablePair<BlockPos, BlockStack>(testPair.getLeft(), testPair.getRight());
        }

        @Override
        public String toString() {
            return "TestConfig{" +
                    "testString='" + testString + '\'' +
                    ", testInt=" + testInt +
                    ", testDouble=" + testDouble +
                    ", testPair=" + testPair +
                    '}';
        }
    }
}
