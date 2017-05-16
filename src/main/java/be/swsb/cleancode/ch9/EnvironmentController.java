package be.swsb.cleancode.ch9;

public class EnvironmentController {

    public static final double TOO_HOT = 30.0;
    public static final double TOO_COLD = 11.0;
    public static final double WAY_TOO_HOT = 34.0;
    public static final double WAY_TOO_COLD = 6.0;

    private ControlHardware controlHardware;

    public EnvironmentController(ControlHardware controlHardware) {
        this.controlHardware = controlHardware;
    }

    // Just like Uncle Bob says: I'd rather you not worry about that while reading the test.
    // I'd rather you just worry about whether you agree that the end state of the system is consistent with the temperature being "way too cold".
    public void tic() {
        double currentTemperature = controlHardware.getTemp();

        if (currentTemperature <= TOO_COLD) {
            controlHardware.turnOnBlower();
            controlHardware.turnOnHeater();
            if (currentTemperature <= WAY_TOO_COLD) {
                controlHardware.turnOnLoTempAlarm();
            } else {
                controlHardware.turnOffLoTempAlarm();
            }
        } else if (currentTemperature >= TOO_HOT) {
            controlHardware.turnOnBlower();
            controlHardware.turnOnCooler();
            if (currentTemperature >= WAY_TOO_HOT) {
                controlHardware.turnOnHiTempAlarm();
            } else {
                controlHardware.turnOffHiTempAlarm();
            }
        } else {
            controlHardware.turnOffBlower();
            controlHardware.turnOffCooler();
            controlHardware.turnOffHeater();
            controlHardware.turnOffLoTempAlarm();
            controlHardware.turnOffHiTempAlarm();
        }
    }
}