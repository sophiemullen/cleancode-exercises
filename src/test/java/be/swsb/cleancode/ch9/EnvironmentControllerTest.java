package be.swsb.cleancode.ch9;

import org.junit.Before;
import org.junit.Test;

import static be.swsb.cleancode.ch9.EnvironmentController.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class EnvironmentControllerTest {

    private EnvironmentController controller;
    private MockControlHardware hardware;

    @Before
    public void setUp() throws Exception {
        hardware = new MockControlHardware();
        controller = new EnvironmentController(hardware);
    }

    @Test
    public void turnOnCoolerAndBlowerIfTooHot() throws Exception {
        hardware.setTemp(TOO_HOT);
        controller.tic();
        assertFalse(hardware.heaterState());
        assertTrue(hardware.blowerState());
        assertTrue(hardware.coolerState());
        assertFalse(hardware.highTempAlarm());
        assertFalse(hardware.lowTempAlarm());
    }

    @Test
    public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
        hardware.setTemp(TOO_COLD);
        controller.tic();
        assertTrue(hardware.heaterState());
        assertTrue(hardware.blowerState());
        assertFalse(hardware.coolerState());
        assertFalse(hardware.highTempAlarm());
        assertFalse(hardware.lowTempAlarm());
    }

    @Test
    public void turnOnHiTempAlarmAtThreshold() throws Exception {
        hardware.setTemp(WAY_TOO_HOT);
        controller.tic();
        assertFalse(hardware.heaterState());
        assertTrue(hardware.blowerState());
        assertTrue(hardware.coolerState());
        assertTrue(hardware.highTempAlarm());
        assertFalse(hardware.lowTempAlarm());
    }

    @Test
    public void turnOnLoTempAlarmAtThreshold() throws Exception {
        hardware.setTemp(WAY_TOO_COLD);
        controller.tic();
        assertTrue(hardware.heaterState());
        assertTrue(hardware.blowerState());
        assertFalse(hardware.coolerState());
        assertFalse(hardware.highTempAlarm());
        assertTrue(hardware.lowTempAlarm());
    }

}