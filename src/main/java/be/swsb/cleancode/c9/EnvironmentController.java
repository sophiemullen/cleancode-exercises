package be.swsb.cleancode.c9;

public class EnvironmentController {

    private ControlHardware controlHardware;

    public EnvironmentController(ControlHardware controlHardware) {
        this.controlHardware = controlHardware;
    }

    // Just like Uncle Bob says: I'd rather you not worry about that while reading the test.
    // I'd rather you just worry about whether you agree that the end state of the system is consistent with the temperature being "way too cold".
    public void tic() {
        double currentTemperature = controlHardware.getTemp();

        if (currentTemperature <= TemperatureType.TOO_COLD) {
            turnOnHeater();
            setLowTempAlarm(currentTemperature);
        } else if (currentTemperature >= TemperatureType.TOO_HOT) {
            turnOnCooler();
            setHighTempAlarm(currentTemperature);
        } else {
            currentTemperature = TemperatureType.JUST_RIGHT;
            turnOffTempRegulators();
        }
    }

    private void turnOnHeater() {
        controlHardware.turnOnTempRegulator();
        controlHardware.turnOnHeater();
    }

    private void setLowTempAlarm(double currentTemperature) {
        if (currentTemperature <= TemperatureType.WAY_TOO_COLD) {
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
        if (currentTemperature >= TemperatureType.WAY_TOO_HOT) {
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
