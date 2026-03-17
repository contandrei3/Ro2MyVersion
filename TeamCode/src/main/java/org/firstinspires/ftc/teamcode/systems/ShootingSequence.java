package org.firstinspires.ftc.teamcode.systems;
import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.IDLE;
import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.SHOOT;
import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.STOP;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;

public class ShootingSequence {

    public enum shootingStatus {
        IDLE,
        PREPARE,
        SHOOT,
        STOP,

    }
    // coordonate goal rosu in inch
    static final double GOAL_X = 131.51;
    static final double GOAL_Y = 13.66;
    public shootingStatus CS = IDLE;
    public ElapsedTime timer = new ElapsedTime();
    public void update(RobotMap r, Ramp ramp, Shooter shooter, Hud hud, Turret turret ) {
        switch (CS)
        {
            case IDLE: {break;}
            case PREPARE:
            {
                double GOAL_X = 131.51;
                double GOAL_Y = 13.66;
                double dist=Math.sqrt((GOAL_X - r.Odo.getX())*(GOAL_X - r.Odo.getX())+(GOAL_Y - r.Odo.getY())*(GOAL_Y - r.Odo.getY()))/39.37; //distanta in metrii
                if (dist<=2.5){ //close
                    {shooter.CS= Shooter.shooterStatus.SHOOT;
                hud.CS= Hud.hudStatus.CLOSE;}
                else
                {
                    shooter.CS= Shooter.shooterStatus.SHOOT_FAR;
                    hud.CS= Hud.hudStatus.FAR;
                }
                turret.CS=Turret.turretStatus.TRACK;
                turret.update(r);
                if (timer.seconds() > 1)
                {timer.reset(); CS=SHOOT;}
                break;
            }
            case SHOOT: {
                ramp.CS= Ramp.rampStatus.SHOOT;
                r.collect1.setPower(0.8);
                r.collect2.setPower(0.8);

                if (timer.seconds()>2)
                    CS= STOP;
                break;
            }
            case STOP: {
                ramp.CS= Ramp.rampStatus.COLLECT;
                hud.CS=Hud.hudStatus.INITIALIZE;
                shooter.CS= Shooter.shooterStatus.STOP;
                turret.CS= Turret.turretStatus.IDLE;
                CS=IDLE;
                r.collect1.setPower(0);
                r.collect2.setPower(0);
                break;
            }
        }

    }
}