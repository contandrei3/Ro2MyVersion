package org.firstinspires.ftc.teamcode.maths_and_systems;



import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.declarations.globals;
import com.bylazar.configurables.annotations.Configurable;

@Configurable
public class functions {

    public static double getshoootpower(RobotMap r)
    {
        double dist=getdistance(r);
        double putere;
            double n=globals.n;
            double m=globals.m;
            putere=m*dist+n;
            putere=Math.max(0, Math.min(putere,1));
        return putere;
    }

    public static double getturretpower (RobotMap r)
    {
        double GOAL_X, GOAL_Y;
        GOAL_X = globals.xRedGoal;
        GOAL_Y = globals.yRedGoal;
        double rx=r.Odo.getPose().getX();
        double ry=r.Odo.getPose().getY();
        double heading=r.Odo.getPose().getHeading();
        //heading=Math.toDegrees(heading);
        double angletogoal = Math.atan2(GOAL_Y - ry, GOAL_X - rx);//asta e unghiul la care trebuie sa ajunga servo ul fata de 0x
        double finalangle=angletogoal-heading; // asta e cat trebuie sa compenseze basically tureta
        //if (finalangle>Math.toRadians(120)) finalangle=Math.toRadians(120);
        //if (finalangle<Math.toRadians(-120)) finalangle=Math.toRadians(-120);
        finalangle = 0.5 + finalangle / Math.toRadians(250);
        finalangle = Math.max(0.05, Math.min(0.95, finalangle));

        return finalangle;


    }
    public static double getdistance (RobotMap r)
    {
        double GOAL_X, GOAL_Y;
            GOAL_X = globals.xRedGoal;
            GOAL_Y = Math.abs(globals.yRedGoal);

        double rx=r.Odo.getPose().getX();
        double ry=Math.abs(r.Odo.getPose().getY());
        return Math.sqrt((rx-GOAL_X)*(rx-GOAL_X)+(ry-GOAL_Y)*(ry-GOAL_Y))/ 39.37;
    }
}
