package org.firstinspires.ftc.teamcode.maths_and_systems;



import org.firstinspires.ftc.teamcode.declarations.RobotMap;

public class functions {

    public static double getshoootpower(RobotMap r)
    {
        double rx=r.Odo.getX();
        double ry=r.Odo.getY();
        double dist=getdistance(rx,ry);
        return 0.05*dist+0.73;
    }
    public static double getdistance (double rx, double ry)
    {
        double GOAL_X = 131.51;
        double GOAL_Y = 13.66;
        return Math.sqrt((rx-GOAL_X)*(rx-GOAL_X)+(ry-GOAL_Y)*(ry-GOAL_Y))/ 39.37;
    }
    public static double getturretpower (RobotMap r)
    {
        double GOAL_X = 131.51;
        double GOAL_Y = 13.66;
        double rx=r.Odo.getX();
        double ry=r.Odo.getY();
        double heading=r.Odo.getHeading();
        double angleToGoal = Math.atan2(Math.abs(rx - GOAL_X), Math.abs(GOAL_Y - ry));
        double turretTarget = angleToGoal * (-1) - heading + Math.PI/2;
        return angletopow(turretTarget);
    }
    public static double angletopow (double angle)
    {
        return (0.5+angle/Math.toRadians(250));
    }
}
