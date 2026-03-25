package org.firstinspires.ftc.teamcode.systems;
import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.IDLE;
import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.SHOOT;
import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.STOP;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.declarations.globals;
import org.firstinspires.ftc.teamcode.maths_and_systems.functions;

public class ShootingSequence {

    public enum shootingStatus {
        IDLE,
        PREPARE,
        SHOOT,
        STOP,
        WILL_SHOOT,
    }
    // coordonate goal rosu in inch
/*    static final double GOAL_X = 131.51;
    static final double GOAL_Y = 13.66;*/
    public shootingStatus CS = IDLE;
    public ElapsedTime timer = new ElapsedTime();
    public void update(RobotMap r, Ramp ramp, Shooter shooter, Hud hud, Turret turret ) {
        switch (CS)
        {
            case IDLE: {break;}
            case PREPARE:
            {
                shooter.CS=Shooter.shooterStatus.SHOOT; //pornim shooterul
                turret.CS=Turret.turretStatus.TRACK;
               // shooter.update(r);
                hud.CS=Hud.hudStatus.CLOSE;
                r.hud.setPosition(globals.hudangle);
                break;
            }
            case SHOOT: {
                ramp.CS=Ramp.rampStatus.SHOOT;
                turret.CS=Turret.turretStatus.TRACK;
                r.collect1.setPower(0.9);
                r.collect2.setPower(0.9);
                //ramp.update(r);
                if (timer.seconds()>=2 ) {CS=STOP; }
                break;
            }

            case STOP: {
                ramp.CS= Ramp.rampStatus.COLLECT;
                //hud.CS=Hud.hudStatus.INITIALIZE;
                shooter.CS= Shooter.shooterStatus.STOP;
                turret.CS= Turret.turretStatus.IDLE;
                r.collect1.setPower(0);
                r.collect2.setPower(0);
                r.launch1.setPower(0);
                r.launch2.setPower(0);
                CS=IDLE;
                break;
            }
        }

    }
}