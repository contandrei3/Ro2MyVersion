package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.declarations.globals;
import org.firstinspires.ftc.teamcode.maths_and_systems.functions;
import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class Turret {

    public enum turretStatus {
        INITIALIZE,

        IDLE,
        TRACK,  // se orientează spre goal
        FRONT,
    }

    public turretStatus CS = turretStatus.IDLE;
/*
    // coordonate goal rosu
    static final double GOAL_X = 131.51;
    static final double GOAL_Y = 13.66;*/

    public void update(RobotMap r, double rx, double ry) {
        switch (CS) {
            case INITIALIZE:
            {
                CS= turretStatus.FRONT;
                break;
            }
            case IDLE: {

                break;
            }
            case TRACK: {
                //if (globals.track==false) CS= turretStatus.FRONT;
                if (globals.track==false){
                    CS= turretStatus.FRONT;
                    break;
                }
                double pos= functions.getturretpower(r);
                //pos=0.5;
                r.turret1.setPosition(pos);
                r.turret2.setPosition(pos);
                break;
            }
            case FRONT: {
                r.turret1.setPosition(0.5);
                r.turret2.setPosition(0.5);
                break;
            }
        }
    }
}