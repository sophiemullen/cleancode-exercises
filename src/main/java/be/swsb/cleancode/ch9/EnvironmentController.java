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
            controlHardware.turnOnTempRegulator();
            controlHardware.turnOnHeater();
            if (currentTemperature <= WAY_TOO_COLD) {
                controlHardware.turnOnLowTempAlarm();
            } else {
                controlHardware.turnOffLowTempAlarm();
            }
        } else if (currentTemperature >= TOO_HOT) {
            controlHardware.turnOnTempRegulator();
            controlHardware.turnOnCooler();
            if (currentTemperature >= WAY_TOO_HOT) {
                controlHardware.turnOnHighTempAlarm();
            } else {
                controlHardware.turnOffHighTempAlarm();
            }
        } else {
            controlHardware.turnOffTempRegulator();
            controlHardware.turnOffCooler();
            controlHardware.turnOffHeater();
            controlHardware.turnOffLowTempAlarm();
            controlHardware.turnOffHighTempAlarm();
        }
    }
}

//    int month = 8;
//    String monthString;
//        switch (month) {
//                case 1:  monthString = "January";
//                break;
//                case 2:  monthString = "February";
//                break;
//                case 3:  monthString = "March";
//                break;
//                case 4:  monthString = "April";
//                break;
//                case 5:  monthString = "May";
//                break;
//                case 6:  monthString = "June";
//                break;
//                case 7:  monthString = "July";
//                break;
//                case 8:  monthString = "August";
//                break;
//                case 9:  monthString = "September";