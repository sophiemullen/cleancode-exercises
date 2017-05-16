package be.swsb.cleancode.ch9;

import org.junit.Before;
import org.junit.Test;

import static be.swsb.cleancode.ch9.EnvironmentController.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnvironmentControllerTest {

    private EnvironmentController controller;
    private MockControlHardware hw;

    @Before
    public void setUp() throws Exception {
        hw = new MockControlHardware();
        controller = new EnvironmentController(hw);
    }

    @Test
    public void turnOnCoolerAndBlowerIfTooHot() throws Exception {
        hw.setTemp(TOO_HOT);
        controller.tic();
        assertFalse(hw.heaterState());
        assertTrue(hw.blowerState());
        assertTrue(hw.coolerState());
        assertFalse(hw.hiTempAlarm());
        assertFalse(hw.loTempAlarm());
    }

    @Test
    public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
        hw.setTemp(TOO_COLD);
        controller.tic();
        assertTrue(hw.heaterState());
        assertTrue(hw.blowerState());
        assertFalse(hw.coolerState());
        assertFalse(hw.hiTempAlarm());
        assertFalse(hw.loTempAlarm());
    }

    @Test
    public void turnOnHiTempAlarmAtThreshold() throws Exception {
        hw.setTemp(WAY_TOO_HOT);
        controller.tic();
        assertFalse(hw.heaterState());
        assertTrue(hw.blowerState());
        assertTrue(hw.coolerState());
        assertTrue(hw.hiTempAlarm());
        assertFalse(hw.loTempAlarm());
    }

    @Test
    public void turnOnLoTempAlarmAtThreshold() throws Exception {
        hw.setTemp(WAY_TOO_COLD);
        controller.tic();
        assertTrue(hw.heaterState());
        assertTrue(hw.blowerState());
        assertFalse(hw.coolerState());
        assertFalse(hw.hiTempAlarm());
        assertTrue(hw.loTempAlarm());
    }

}