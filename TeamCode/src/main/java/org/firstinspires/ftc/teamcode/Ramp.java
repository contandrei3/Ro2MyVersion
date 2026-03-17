package org.firstinspires.ftc.teamcode;

public class Ramp {

    public enum rampStatus {
        INITIALIZE,
        COLLECT,
        SHOOT
    }

    public rampStatus CS = rampStatus.INITIALIZE;
    double shoot=0.78;
    double stop=0.38;
    public void update(RobotMap r) {
        if (CS == rampStatus.INITIALIZE) {
            r.ramp.setPosition(stop);
        } else if (CS == rampStatus.COLLECT) {
            r.ramp.setPosition(stop);
        } else if (CS == rampStatus.SHOOT) {
            r.ramp.setPosition(shoot);
        }
    }
}