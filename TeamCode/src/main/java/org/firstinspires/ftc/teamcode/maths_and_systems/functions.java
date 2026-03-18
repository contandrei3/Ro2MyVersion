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
        double n=0.05;
        double m=0.73;
        double putere=n*dist+m;
        putere=Math.max(0, Math.min(putere,1));
        return putere;
    }

    public static double getturretpower (RobotMap r)
    {
        double GOAL_X = globals.xRedGoal;
        double GOAL_Y = globals.yRedGoal;
        double rx=r.Odo.getPose().getX();
        double ry=r.Odo.getPose().getY();
        double heading=r.Odo.getPose().getHeading();
        double angletogoal = Math.atan2(GOAL_Y - ry, GOAL_X - rx);//asta e unghiul la care trebuie sa ajunga servo ul fata de 0x
        double finalangle=angletogoal-heading; // asta e cat trebuie sa compenseze basically tureta
        if (finalangle>Math.toRadians(120)) finalangle=Math.toRadians(120);
        if (finalangle<Math.toRadians(-120)) finalangle=Math.toRadians(-120);
        finalangle=0.5+finalangle/Math.toRadians(250); //asta e ce urmeaza sa ii dau turetei si mai jos e normalizarea
        return finalangle;

    }
    public static double getdistance (double rx, double ry)
    {
        double GOAL_X = globals.xRedGoal;
        double GOAL_Y = globals.yRedGoal;
        return Math.sqrt((rx-GOAL_X)*(rx-GOAL_X)+(ry-GOAL_Y)*(ry-GOAL_Y))/ 39.37;
    }
    public static double angletopow (double angle)
    {
        return (0.5+angle/Math.toRadians(250));
    }
}
