package org.firstinspires.ftc.teamcode.drive;

import static org.firstinspires.ftc.teamcode.drive.AutoClose.Status.POS_LINE1;
import static org.firstinspires.ftc.teamcode.drive.AutoClose.Status.POS_LINE2;
import static org.firstinspires.ftc.teamcode.drive.AutoClose.Status.SHOOT;
import static org.firstinspires.ftc.teamcode.drive.AutoClose.Status.SHOOT_LINE1;
import static org.firstinspires.ftc.teamcode.drive.AutoClose.Status.START;

import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.declarations.globals;
import org.firstinspires.ftc.teamcode.systems.Hud;
import org.firstinspires.ftc.teamcode.systems.Ramp;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.ShootingSequence;
import org.firstinspires.ftc.teamcode.systems.Turret;
import com.pedropathing.follower.Follower;
import com.pedropathing.paths.PathChain;
import org.firstinspires.ftc.teamcode.pedroPathing.constants;

@Autonomous(name = "AutoClose", group = "Linear OpMode")
public class AutoClose  extends LinearOpMode {


    enum Status {
        START,
        SHOOT,
        SHOOT_STOP,
        POS_LINE1,
        COLLECT_LINE1,
        SHOOT_LINE1,
        POS_LINE2,
        COLLECT_LINE2,
        SHOOT_LINE2,
        GO_GATE,
        COLLECT_GATE,
        SHOOT_GATE,
    }

    @Override
    public void runOpMode()
    {
        RobotMap r = new RobotMap(hardwareMap);


        Ramp ramp = new Ramp();
        Shooter shooter = new Shooter();
        Hud hud = new Hud();
        ShootingSequence seq = new ShootingSequence();
        Turret turret =  new Turret();

        r.Odo.recalibrate();
        globals.alliance="red";
        Follower drive = constants.createFollower(hardwareMap);
        //facem pozitii
        Pose startPose    = new Pose(126.918, 118.039, Math.toRadians(37.5));
        Pose shootPose    = new Pose(80.805,  86.275,  Math.toRadians(45));
        Pose preLine1     = new Pose(100.296, 83.698,  Math.toRadians(0));
        Pose finishline1 = new Pose(130, 83.760,  Math.toRadians(0));
        Pose preLine2     = new Pose(100.296, 60,  Math.toRadians(0));
        Pose finishline2 = new Pose(130, 60,  Math.toRadians(0));
        drive.setStartingPose(startPose);

        //facem path uri

        Path shootPreload = new Path (new BezierLine (startPose,shootPose));
        shootPreload.setLinearHeadingInterpolation(startPose.getHeading(), shootPose.getHeading());

        Path goline1 = new Path (new BezierLine (shootPose,preLine1));
        goline1.setLinearHeadingInterpolation(shootPose.getHeading(), preLine1.getHeading());

        Path collectline1 = new Path (new BezierLine (preLine1,finishline1));
        collectline1.setLinearHeadingInterpolation(preLine1.getHeading(), finishline1.getHeading());

        Path shootline1 = new Path (new BezierLine(finishline1, shootPose));
        shootline1.setLinearHeadingInterpolation(finishline1.getHeading(), shootPose.getHeading());

        Path goline2 = new Path (new BezierLine (shootPose,preLine2));
        goline2.setLinearHeadingInterpolation(shootPose.getHeading(), preLine2.getHeading());

        Path collectline2 = new Path (new BezierLine (preLine2,finishline2));
        collectline2.setLinearHeadingInterpolation(preLine2.getHeading(), finishline2.getHeading());

        Path shootline2 = new Path (new BezierLine(finishline2, shootPose));
        shootline2.setLinearHeadingInterpolation(finishline2.getHeading(), shootPose.getHeading());

        waitForStart();
        Status status = START;
        while (opModeIsActive() && !isStopRequested()) {
            switch (status) {
                case START:{
                   drive.followPath(shootPreload);
                   status= SHOOT;
                   break;
                }
                case SHOOT:{
                    if (!drive.isBusy())
                    {
                        seq.timer.reset();
                        seq.CS=ShootingSequence.shootingStatus.PREPARE;
                        status= Status.SHOOT_STOP;
                    }
                    break;
                }
                case SHOOT_STOP:{
                    if (seq.CS== ShootingSequence.shootingStatus.IDLE) { //daca am terminat de shootat decidem ce facem in continuare
                        if (globals.colllected_line1==false) status=POS_LINE1;
                        if (globals.colllected_line1==true && globals.colllected_line2==false) status=POS_LINE2;
                        if (globals.colllected_line1==true && globals.colllected_line2==true) status= Status.GO_GATE;
                    }
                    break;
                }

                case POS_LINE1:{
                    globals.colllected_line1=true;
                    drive.followPath(goline1);
                    status=Status.COLLECT_LINE1;
                    break;
                }
                case COLLECT_LINE1:{
                    if (!drive.isBusy())
                    {//pornim intake urile
                    r.collect1.setPower(0.9);
                    r.collect2.setPower(0.9);
                    drive.followPath(collectline1);
                    status=SHOOT_LINE1;
                    }
                    break;
                }
                case SHOOT_LINE1:{
                    if (!drive.isBusy()){
                        //sleep (500);
                        r.collect1.setPower(0);
                        r.collect2.setPower(0);
                        //avem (presupunem) toate bilele in roboteul nostruuuuuu deci mergem sa sutam
                        drive.followPath(shootline1);
                        status=Status.SHOOT;
                    }
                    break;
                }
                case POS_LINE2:{
                    globals.colllected_line2=true;
                    drive.followPath(goline2);
                    status=Status.COLLECT_LINE2;
                    break;
                }
                case COLLECT_LINE2:{
                    if (!drive.isBusy())
                    {//pornim intake urile
                        r.collect1.setPower(0.9);
                        r.collect2.setPower(0.9);
                        drive.followPath(collectline2);
                        status=Status.SHOOT_LINE2;
                    }
                    break;
                }
                case SHOOT_LINE2:{
                    if (!drive.isBusy()){
                        //sleep (500);
                        r.collect1.setPower(0);
                        r.collect2.setPower(0);
                        //avem (presupunem) toate bilele in roboteul nostruuuuuu deci mergem sa sutam
                        drive.followPath(shootline2);
                        status=Status.SHOOT;
                    }
                    break;
                }
            }
            drive.update();
            turret.CS=Turret.turretStatus.TRACK;
            shooter.update(r);
            ramp.update(r);
            hud.update(r);
            seq.update(r,ramp,shooter, hud, turret);
            turret.update(r);
            r.Odo.update();
        }
    }

}
