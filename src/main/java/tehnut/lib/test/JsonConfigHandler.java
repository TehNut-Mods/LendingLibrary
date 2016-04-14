package tehnut.lib.test;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import tehnut.lib.json.JsonHelper;

import java.io.File;

public class JsonConfigHandler {

    public static void init(File jsonConfig) {
        JsonHelper.<TestConfig>tryCreateDefault(jsonConfig, new TestConfig());
        TestConfig testConfig = JsonHelper.fromJson(jsonConfig, TestConfig.class);
        LendingLibraryTest.instance.getLogger().info(testConfig == null ? "null" : testConfig.toString());
    }

    public static void init2(File jsonConfig) {
        ItemStack def = new ItemStack(Items.diamond_sword, 4, 1);
        def.setTagCompound(new NBTTagCompound());
        def.getTagCompound().setInteger("newInt", 1);
        def.getTagCompound().setBoolean("newBool", true);
        JsonHelper.<ItemStack>tryCreateDefault(jsonConfig, def);
        ItemStack stack = JsonHelper.fromJson(jsonConfig, ItemStack.class);
        LendingLibraryTest.instance.getLogger().info(stack == null ? "null" : stack.toString() + "{" + stack.getTagCompound().toString() + "}");
    }

    public static class TestConfig {
        private String testString = "blah";
        private int testInt = 0;
        private double testDouble = 0.5;

        public TestConfig(String testString, int testInt, double testDouble) {
            this.testString = testString;
            this.testInt = testInt;
            this.testDouble = testDouble;
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

        @Override
        public String toString() {
            return "TestConfig{" +
                    "testString='" + testString + '\'' +
                    ", testInt=" + testInt +
                    ", testDouble=" + testDouble +
                    '}';
        }
    }
}
