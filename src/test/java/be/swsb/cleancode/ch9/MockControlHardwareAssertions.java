package be.swsb.cleancode.ch9;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.Lists;
import org.assertj.core.util.Strings;

import java.util.List;

public class MockControlHardwareAssertions extends Assertions {

    public static MockControlHardwareAssert assertThat(MockControlHardware hw) {
        return new MockControlHardwareAssert(hw);
    }

    public static class MockControlHardwareAssert extends AbstractAssert<MockControlHardwareAssert, MockControlHardware> {

        private String onDescription = "Expected <%s> to be\n" +
                "\ton\n" +
                "but was\n" +
                "\toff";

        private boolean hiTempAlarmOnWasAsserted;
        private boolean blowerOnWasAsserted;
        private boolean coolerOnWasAsserted;
        private boolean heaterOnWasAsserted;
        private boolean loTempAlarmOnWasAsserted;

        private MockControlHardwareAssert(MockControlHardware mockControlHardware) {
            super(mockControlHardware, MockControlHardwareAssert.class);
        }

        public MockControlHardwareAssert hasBlowerOn() {
            blowerOnWasAsserted = true;
            if (!actual.blowerState()) {
                failWithMessage(onDescription, "blower");
            }
            return this;
        }

        public MockControlHardwareAssert hasCoolerOn() {
            coolerOnWasAsserted = true;
            if (!actual.coolerState()) {
                failWithMessage(onDescription, "cooler");
            }
            return this;
        }

        public MockControlHardwareAssert hasHeaterOn() {
            heaterOnWasAsserted = true;
            if (!actual.heaterState()) {
                failWithMessage(onDescription, "heater");
            }
            return this;
        }

        public MockControlHardwareAssert hasLoTempAlarmOn() {
            loTempAlarmOnWasAsserted = true;
            if (!actual.loTempAlarm()) {
                failWithMessage(onDescription, "loTempAlarm");
            }
            return this;
        }

        public MockControlHardwareAssert hasHiTempAlarmOn() {
            hiTempAlarmOnWasAsserted = true;
            if (!actual.hiTempAlarm()) {
                failWithMessage(onDescription, "hiTempAlarm");
            }
            return this;
        }

        public MockControlHardwareAssert hasTheRestOff() {
            List<String> togglesThatShouldBeOff = Lists.newArrayList();
            if (!hiTempAlarmOnWasAsserted) {
                if (actual.hiTempAlarm()) {
                    togglesThatShouldBeOff.add("hiTempAlarm");
                }
            }
            if (!blowerOnWasAsserted) {
                if (actual.blowerState()) {
                    togglesThatShouldBeOff.add("blower");
                }
            }
            if (!coolerOnWasAsserted) {
                if (actual.coolerState()) {
                    togglesThatShouldBeOff.add("cooler");
                }
            }
            if (!heaterOnWasAsserted) {
                if (actual.heaterState()) {
                    togglesThatShouldBeOff.add("heater");
                }
            }
            if (!loTempAlarmOnWasAsserted) {
                if (actual.loTempAlarm()) {
                    togglesThatShouldBeOff.add("loTempAlarm");
                }
            }

            if (!togglesThatShouldBeOff.isEmpty()) {
                failWithMessage("Expected " +
                        Strings.join(togglesThatShouldBeOff).with(", ") +
                        " to be\n" +
                        "\toff\n" +
                        "but " + (togglesThatShouldBeOff.size() == 1 ? "was" : "were") + " still\n" +
                        "\ton");
            }
            return this;
        }
    }
}
