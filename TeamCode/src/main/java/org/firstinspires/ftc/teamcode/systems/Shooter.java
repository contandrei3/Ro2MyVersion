package org.firstinspires.ftc.teamcode.systems;


import org.firstinspires.ftc.teamcode.declarations.RobotMap;

public class Shooter {

    public enum shooterStatus {
        INITIALIZE,
        STOP,
        SHOOT,
        SHOOT_FAR,
    }

    public shooterStatus CS = shooterStatus.INITIALIZE;
    //update(RobotMap r);
    double stop=0.05;


    public void update(RobotMap r) {
        if (CS == shooterStatus.INITIALIZE) {
            r.launch1.setPower(stop);
            r.launch2.setPower(stop);
        } else if (CS == shooterStatus.STOP) {
            r.launch1.setPower(stop);
            r.launch2.setPower(stop);
        } else if (CS == shooterStatus.SHOOT) {
            double GOAL_X = 131.51;
            double GOAL_Y = 13.66;
            double dist=Math.sqrt((GOAL_X - r.Odo.getX())*(GOAL_X - r.Odo.getX())+(GOAL_Y - r.Odo.getY())*(GOAL_Y - r.Odo.getY()))/39.37; //distanta in metrii
            double pumniimeicreiernare=0.05*dist+0.73;
            r.launch1.setPower(pumniimeicreiernare);
            r.launch2.setPower(pumniimeicreiernare);
        } else if (CS==shooterStatus.SHOOT_FAR)
        {
            r.launch1.setPower(0.90);
            r.launch2.setPower(0.90);
        }
    }
}