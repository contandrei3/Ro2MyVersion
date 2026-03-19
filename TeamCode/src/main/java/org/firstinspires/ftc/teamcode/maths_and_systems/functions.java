package org.firstinspires.ftc.teamcode.maths_and_systems;



import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.declarations.globals;
import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class functions {

    public static double getshoootpower(RobotMap r)
    {
        double rx=r.Odo.getPose().getX();
        double ry=r.Odo.getPose().getY();
        double dist=getdistance(rx,ry);
        double putere;
        if (dist < 2.5) {
            double n=0.05;
            double m=0.73;
            putere=n*dist+m;
            putere=Math.max(0, Math.min(putere,1));
        }
        else putere=0.9;
        return putere;
    }

    public static double getturretpower (RobotMap r)
    {
        double GOAL_X, GOAL_Y;
        if (globals.alliance==1) {//daca alianta e rosie
        GOAL_X = globals.xRedGoal;
        GOAL_Y = globals.yRedGoal;}
        else{
        GOAL_X = globals.xBlueGoal;
        GOAL_Y = globals.yBlueGoal;}
        double rx=r.Odo.getPose().getX();
        double ry=r.Odo.getPose().getY();
        double heading=r.Odo.getPose().getHeading();
        double angletogoal = Math.atan2(GOAL_Y - ry, GOAL_X - rx);//asta e unghiul la care trebuie sa ajunga servo ul fata de 0x
        double finalangle=heading-angletogoal; // asta e cat trebuie sa compenseze basically tureta
        //if (finalangle>Math.toRadians(120)) finalangle=Math.toRadians(120);
        //if (finalangle<Math.toRadians(-120)) finalangle=Math.toRadians(-120);
        finalangle = 0.5 + finalangle / Math.toRadians(250);
        finalangle = Math.max(0.05, Math.min(0.95, finalangle));
        return finalangle;

    }
    public static double getdistance (double rx, double ry)
    {
        double GOAL_X, GOAL_Y;
        if (globals.alliance==1) {//daca alianta e rosie
            GOAL_X = globals.xRedGoal;
            GOAL_Y = globals.yRedGoal;}
        else{
            GOAL_X = globals.xBlueGoal;
            GOAL_Y = globals.yBlueGoal;}
        return Math.sqrt((rx-GOAL_X)*(rx-GOAL_X)+(ry-GOAL_Y)*(ry-GOAL_Y))/ 39.37;
    }
    public static double angletopow (double angle)
    {
        return (0.5+angle/Math.toRadians(250));
    }
}
