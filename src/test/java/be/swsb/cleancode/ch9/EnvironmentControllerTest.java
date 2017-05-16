package be.swsb.cleancode.ch9;

import org.junit.Before;
import org.junit.Test;

import static be.swsb.cleancode.ch9.EnvironmentController.*;
import static be.swsb.cleancode.ch9.MockControlHardwareAssertions.assertThat;

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
        setupControllerWith(TOO_HOT);

        assertThat(hardware)
                .hasBlowerOn()
                .hasCoolerOn()
                .hasTheRestOff();
    }

    @Test
    public void turnOnHeaterAndBlowerIfTooCold() throws Exception {
        setupControllerWith(TOO_COLD);

        assertThat(hardware)
                .hasBlowerOn()
                .hasHeaterOn()
                .hasTheRestOff();
    }

    @Test
    public void turnOnHiTempAlarmAtThreshold() throws Exception {
        setupControllerWith(WAY_TOO_HOT);

        assertThat(hardware)
                .hasBlowerOn()
                .hasCoolerOn()
                .hasHiTempAlarmOn()
                .hasTheRestOff();
    }

    @Test
    public void turnOnLoTempAlarmAtThreshold() throws Exception {
        setupControllerWith(WAY_TOO_COLD);

        assertThat(hardware)
                .hasBlowerOn()
                .hasHeaterOn()
                .hasLoTempAlarmOn()
                .hasTheRestOff();
    }

    private void setupControllerWith(double tooHot) {
        hardware.setTemp(tooHot);
        controller.tic();
    }

}