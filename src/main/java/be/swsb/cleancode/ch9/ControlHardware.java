package be.swsb.cleancode.ch9;

public interface ControlHardware {
    double getTemp();
    boolean heaterState();
    boolean blowerState();
    boolean coolerState();
    boolean highTempAlarm();
    boolean lowTempAlarm();
    void turnOnTempRegulator();
    void turnOnHeater();
    void turnOnLowTempAlarm();
    void turnOnCooler();
    void turnOnHighTempAlarm();
    void turnOffTempRegulator();
    void turnOffHeater();
    void turnOffLowTempAlarm();
    void turnOffCooler();
    void turnOffHighTempAlarm();
}