package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;

public class Ramp {

    public enum rampStatus {
        INITIALIZE,
        COLLECT,
        SHOOT
    }

    public rampStatus CS = rampStatus.INITIALIZE;
    double shoot=0.78; //pus
    double stop=0.38; //distanta mare
    public void update(RobotMap r) {
        switch (CS)
        {
            case INITIALIZE: {
                r.ramp.setPosition(stop);
                break;
            }
            case COLLECT: {
                r.ramp.setPosition(stop);
                break;
            }
            case SHOOT: {
                r.ramp.setPosition(shoot);
                break;
            }
        }
    }
}