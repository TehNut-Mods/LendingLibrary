package tehnut.lib.test;

import tehnut.lib.json.JsonHelper;

import java.io.File;

public class JsonConfigHandler {

    public static void init(File jsonConfig) {
        JsonHelper.<TestConfig>tryCreateDefault(jsonConfig, new TestConfig());
        TestConfig testConfig = JsonHelper.fromJson(jsonConfig, TestConfig.class);
        LendingLibraryTest.instance.getLogger().info(testConfig == null ? "null" : testConfig.toString());
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
