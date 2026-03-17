package org.firstinspires.ftc.teamcode;
import static org.firstinspires.ftc.teamcode.ShootingSequence.shootingStatus.IDLE;
import static org.firstinspires.ftc.teamcode.ShootingSequence.shootingStatus.PREPARE;
import static org.firstinspires.ftc.teamcode.ShootingSequence.shootingStatus.SHOOT;
import static org.firstinspires.ftc.teamcode.ShootingSequence.shootingStatus.STOP;

import com.qualcomm.robotcore.util.ElapsedTime;
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
    ElapsedTime timer = new ElapsedTime();
    public void update(RobotMap r, Ramp ramp, Shooter shooter, Hud hud, Turret turret ) {
        switch (CS)
        {
            case IDLE: {break;}
            case PREPARE:
            {
                shooter.CS= Shooter.shooterStatus.SHOOT;
                hud.CS= Hud.hudStatus.CLOSE;
                turret.CS=Turret.turretStatus.TRACK;
                if (timer.seconds() > 1)
                {timer.reset(); CS=SHOOT;}
                break;
            }
            case SHOOT: {
                ramp.CS= Ramp.rampStatus.SHOOT;
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
                break;
            }
        }

    }
}