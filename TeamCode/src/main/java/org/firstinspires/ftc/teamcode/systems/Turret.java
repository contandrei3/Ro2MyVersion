package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.maths_and_systems.functions;

public class Turret {

    public enum turretStatus {
        INITIALIZE,

        IDLE,
        TRACK,  // se orientează spre goal
    }

    public turretStatus CS = turretStatus.IDLE;
/*
    // coordonate goal rosu
    static final double GOAL_X = 131.51;
    static final double GOAL_Y = 13.66;*/

    public void update(RobotMap r) {
        switch (CS) {
            case INITIALIZE:
            {
                r.turret1.setPosition(0.5);
                r.turret2.setPosition(0.5);
                break;
            }
            case IDLE: {

                break;
            }
            case TRACK: {
                double pos= functions.getturretpower(r);
                r.turret1.setPosition(pos);
                r.turret2.setPosition(pos);
                break;
            }
        }
    }
}