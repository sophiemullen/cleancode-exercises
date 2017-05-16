package be.swsb.cleancode.ch9;

public interface ControlHardware {
    double getTemp();
    boolean heaterState();
    boolean blowerState();
    boolean coolerState();
    boolean hiTempAlarm();
    boolean loTempAlarm();
    void turnOnBlower();
    void turnOnHeater();
    void turnOnLoTempAlarm();
    void turnOnCooler();
    void turnOnHiTempAlarm();
    void turnOffBlower();
    void turnOffHeater();
    void turnOffLoTempAlarm();
    void turnOffCooler();
    void turnOffHiTempAlarm();
}