package be.swsb.cleancode.ch9;

import static be.swsb.cleancode.ch9.TemperatureType.*;

public class EnvironmentController {

    private ControlHardware controlHardware;

    public EnvironmentController(ControlHardware controlHardware) {
        this.controlHardware = controlHardware;
    }

    // Just like Uncle Bob says: I'd rather you not worry about that while reading the test.
    // I'd rather you just worry about whether you agree that the end state of the system is consistent with the temperature being "way too cold".
    public void tic() {
        double currentTemperature = controlHardware.getTemp();

        if (currentTemperature <= TOO_COLD) {
            turnOnHeater();
            setLowTempAlarm(currentTemperature);
        } else if (currentTemperature >= TOO_HOT) {
            turnOnCooler();
            setHighTempAlarm(currentTemperature);
        } else {
            currentTemperature = JUST_RIGHT;
            turnOffTempRegulators();
        }
    }

    private void turnOnHeater() {
        controlHardware.turnOnTempRegulator();
        controlHardware.turnOnHeater();
    }

    private void setLowTempAlarm(double currentTemperature) {
        if (currentTemperature <= WAY_TOO_COLD) {
            controlHardware.turnOnLowTempAlarm();
        } else {
            controlHardware.turnOffLowTempAlarm();
        }
    }

    private void turnOnCooler() {
        controlHardware.turnOnTempRegulator();
        controlHardware.turnOnCooler();
    }

    private void setHighTempAlarm(double currentTemperature) {
        if (currentTemperature >= WAY_TOO_HOT) {
            controlHardware.turnOnHighTempAlarm();
        } else {
            controlHardware.turnOffHighTempAlarm();
        }
    }

    private void turnOffTempRegulators() {
        controlHardware.turnOffTempRegulator();
        controlHardware.turnOffCooler();
        controlHardware.turnOffHeater();
        controlHardware.turnOffLowTempAlarm();
        controlHardware.turnOffHighTempAlarm();
    }
}
