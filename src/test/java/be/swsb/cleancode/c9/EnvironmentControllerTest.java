package be.swsb.cleancode.c9;

import org.junit.Before;
import org.junit.Test;

import static be.swsb.cleancode.c9.TemperatureType.*;
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
        assertFalse(hardware.heaterIsOn());
        assertTrue(hardware.tempRegulatorIsOn());
        assertTrue(hardware.coolerIsOn());
        assertFalse(hardware.highTempAlarm());
        assertFalse(hardware.lowTempAlarm());
    }

    @Test
    public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
        hardware.setTemp(TOO_COLD);
        controller.tic();
        assertTrue(hardware.heaterIsOn());
        assertTrue(hardware.tempRegulatorIsOn());
        assertFalse(hardware.coolerIsOn());
        assertFalse(hardware.highTempAlarm());
        assertFalse(hardware.lowTempAlarm());
    }

    @Test
    public void turnOnHiTempAlarmAtThreshold() throws Exception {
        hardware.setTemp(WAY_TOO_HOT);
        controller.tic();
        assertFalse(hardware.heaterIsOn());
        assertTrue(hardware.tempRegulatorIsOn());
        assertTrue(hardware.coolerIsOn());
        assertTrue(hardware.highTempAlarm());
        assertFalse(hardware.lowTempAlarm());
    }

    @Test
    public void turnOnLoTempAlarmAtThreshold() throws Exception {
        hardware.setTemp(WAY_TOO_COLD);
        controller.tic();
        assertTrue(hardware.heaterIsOn());
        assertTrue(hardware.tempRegulatorIsOn());
        assertFalse(hardware.coolerIsOn());
        assertFalse(hardware.highTempAlarm());
        assertTrue(hardware.lowTempAlarm());
    }

}