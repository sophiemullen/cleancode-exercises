package be.swsb.cleancode.ch9;

public class MockControlHardware implements ControlHardware {
    private double temp;
    private boolean heaterState;
    private boolean blowerState;
    private boolean coolerState;
    private boolean hiTempAlarm;
    private boolean loTempAlarm;

    void setTemp(double temp) {
        this.temp = temp;
    }

    @Override
    public double getTemp() {
        return temp;
    }

    @Override
    public boolean heaterState() {
        return heaterState;
    }

    @Override
    public boolean blowerState() {
        return blowerState;
    }

    @Override
    public boolean coolerState() {
        return coolerState;
    }

    @Override
    public boolean hiTempAlarm() {
        return hiTempAlarm;
    }

    @Override
    public boolean loTempAlarm() {
        return loTempAlarm;
    }

    @Override
    public void turnOnBlower() {
        this.blowerState = true;
    }

    @Override
    public void turnOnHeater() {
        this.heaterState = true;
    }

    @Override
    public void turnOnLoTempAlarm() {
        this.loTempAlarm = true;
    }

    @Override
    public void turnOnCooler() {
        this.coolerState = true;
    }

    @Override
    public void turnOnHiTempAlarm() {
        this.hiTempAlarm = true;
    }

    @Override
    public void turnOffBlower() {
        this.blowerState = false;
    }

    @Override
    public void turnOffHeater() {
        this.heaterState = false;
    }

    @Override
    public void turnOffLoTempAlarm() {
        this.loTempAlarm = false;
    }

    @Override
    public void turnOffCooler() {
        this.coolerState = false;
    }

    @Override
    public void turnOffHiTempAlarm() {
        this.hiTempAlarm = false;
    }
}
