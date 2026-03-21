package org.firstinspires.ftc.teamcode.systems;


import static org.firstinspires.ftc.teamcode.systems.Shooter.shooterStatus.IDLE;
import static org.firstinspires.ftc.teamcode.systems.Shooter.shooterStatus.INITIALIZE;
import static org.firstinspires.ftc.teamcode.systems.Shooter.shooterStatus.STOP;
//import static org.firstinspires.ftc.teamcode.systems.Shooter.shooterStatus.WINDUP;
import static org.firstinspires.ftc.teamcode.systems.Shooter.shooterStatus.SHOOT;

import static java.lang.Thread.sleep;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.maths_and_systems.functions;

public class Shooter {

    public enum shooterStatus {
        INITIALIZE,
        IDLE,
        STOP,
        SHOOT,
    }

    public shooterStatus CS = INITIALIZE;
    //update(RobotMap r);
    double stop = 0;
    double winduppower = 0.9;

    public void update(RobotMap r) {
        switch (CS) {
            case INITIALIZE: {
                r.launch1.setPower(stop);
                r.launch2.setPower(stop);
                CS = IDLE;
                break;
            }
            case IDLE: {
                break;
            }

            case SHOOT: {
                double shootpow = functions.getshoootpower(r);
                r.launch1.setPower(shootpow);
                r.launch2.setPower(shootpow);
                break;
            }
            case STOP: {
                r.launch1.setPower(0);
                r.launch2.setPower(0);
                CS = IDLE;
                break;
            }
        }
    }
}
