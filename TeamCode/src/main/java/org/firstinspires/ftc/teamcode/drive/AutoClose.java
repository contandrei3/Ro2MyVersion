package org.firstinspires.ftc.teamcode.drive;

import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.IDLE;
import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.PREPARE;
import static org.firstinspires.ftc.teamcode.systems.ShootingSequence.shootingStatus.WILL_SHOOT;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.geometry.BezierLine;
import com.pedropathing.geometry.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.declarations.globals;
import org.firstinspires.ftc.teamcode.systems.Hud;
import org.firstinspires.ftc.teamcode.systems.Ramp;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.ShootingSequence;
import org.firstinspires.ftc.teamcode.systems.Turret;
import com.pedropathing.follower.Follower;

import org.firstinspires.ftc.teamcode.pedroPathing.constants;
@Configurable
@Autonomous(name = "AutoClose", group = "Linear OpMode")
public class AutoClose  extends LinearOpMode {


    enum Status {
        START,
        SHOOT,
        GO_LINE1,
        COLLECT_LINE1,
        SHOOT_LINE1,
        GO_LINE2,
        COLLECT_LINE2,
        SHOOT_LINE2,
        GO_GATE,
        COLLECT_GATE,
        SHOOT_GATE,
        PARK,
        IDLE,
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
        ElapsedTime gentimer = new ElapsedTime();
        ElapsedTime smoltimer = new ElapsedTime();

        //Pose turretPose = new Pose (115,-116 ,Math.toRadians(46));

        globals.alliance="red";
        //Follower drive = constants.createFollower(hardwareMap);
        //facem pozitii



        Pose startPose    = new Pose(116.36, 111, Math.toRadians(33.5));
        Pose shootPose    = new Pose(73.5,  75,  Math.toRadians(16));
        Pose preLine1     = new Pose(90, 73,  Math.toRadians(0));
        Pose finishline1 = new Pose(115, 73,  Math.toRadians(0));
        Pose preLine2     = new Pose(82, 54,  Math.toRadians(0));
        Pose finishline2 = new Pose(115, 54,  Math.toRadians(0));
        Pose gogate = new Pose (119, 56, Math.toRadians(0));
        Pose opengate= new Pose (125,52.4, Math.toRadians(31));
        Pose park = new Pose (38.5,37.75, Math.toRadians(90));
        //Pose startturret = new Pose (112, -121, Math.toRadians(45));
        r.Odo.recalibrate();
        Follower drive = constants.createFollower(hardwareMap);
        drive.setStartingPose(startPose);
        r.Odo.setPose(startPose);

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

        Path gotogate = new Path (new BezierLine (shootPose,gogate));
        gotogate.setLinearHeadingInterpolation(shootPose.getHeading(),gogate.getHeading());

        Path collectgate = new Path (new BezierLine (gogate,opengate));
        collectgate.setLinearHeadingInterpolation(gogate.getHeading(),opengate.getHeading());

        Path shootgate = new Path (new BezierLine(opengate,shootPose));
        shootgate.setLinearHeadingInterpolation(opengate.getHeading(), shootPose.getHeading());
        Path gopark = new Path (new BezierLine(shootPose,park));
        gopark.setLinearHeadingInterpolation(shootPose.getHeading(),park.getHeading());

        waitForStart();

        drive.setStartingPose(startPose);
        r.Odo.setStartPose(startPose);
        r.Odo.setPose(startPose); // HAIII ODATA MERGIIIII

        Status status= Status.START;
        gentimer.reset();

        while (opModeIsActive() && !isStopRequested()) {
            switch (status) {
                case START: {
                    globals.colllected_line1=false;
                    globals.colllected_line2=false;
                    drive.setPose(startPose);
                    seq.CS= PREPARE; //porinim tureta si shooter ul
                    status= Status.SHOOT;
                    drive.followPath(shootPreload);
                    break;
                }
                case SHOOT:{
                    if (!drive.isBusy() && seq.CS== PREPARE) { //daca am ajuns la pozitie si tureta e in windup adk am colectat
                        seq.timer.reset();
                        seq.CS= ShootingSequence.shootingStatus.SHOOT;
                    }
                    else if (!drive.isBusy() && seq.CS== IDLE){ //adk am terminat de shootat
                        if (globals.colllected_line1 == false) status=Status.GO_LINE1;
                        else if (globals.colllected_line1 == true && globals.colllected_line2 == false) status=Status.GO_LINE2;
                        else status=Status.GO_GATE;
                    }
                    break;
                }
                case GO_LINE1:{
                    drive.followPath(goline1);
                    status=Status.COLLECT_LINE1;
                    break;
                }
                case COLLECT_LINE1:{
                    if (!drive.isBusy()){
                        drive.setMaxPower(0.3);
                        r.collect1.setPower(0.9);
                        r.collect2.setPower(0.9);
                        drive.followPath(collectline1);
                        status=Status.SHOOT_LINE1;
                    }
                    break;
                }
                case SHOOT_LINE1:{
                    if (!drive.isBusy()){ //dupa ce am colecat oprim intake urile
                        drive.setMaxPower(0.9);
                        r.collect1.setPower(0);
                        r.collect2.setPower(0);
                        globals.colllected_line1=true;
                        drive.followPath(shootline1);
                        seq.CS= PREPARE;
                        status=Status.SHOOT;
                    }
                    break;
                }
                case GO_LINE2:{
                    drive.followPath(goline2);
                    status=Status.COLLECT_LINE2;
                    break;
                }
                case COLLECT_LINE2:{
                    if (!drive.isBusy()){
                        drive.setMaxPower(0.3);
                        r.collect1.setPower(0.9);
                        r.collect2.setPower(0.9);
                        drive.followPath(collectline2);
                        status=Status.SHOOT_LINE2;
                    }
                    break;
                }
                case SHOOT_LINE2:{
                    if (!drive.isBusy()){ //dupa ce am colecat oprim intake urile
                        drive.setMaxPower(0.9);
                        r.collect1.setPower(0);
                        r.collect2.setPower(0);
                        globals.colllected_line2=true;
                        drive.followPath(shootline2);
                        seq.CS= ShootingSequence.shootingStatus.PREPARE;
                        status=Status.SHOOT;
                    }
                    break;
                }
                case GO_GATE:{
                    drive.followPath(gotogate);
                    status=Status.COLLECT_GATE;
                    break;
                }
                case COLLECT_GATE:{
                    if (!drive.isBusy()){
                        drive.setMaxPower(0.3);
                        r.collect1.setPower(0.9);
                        r.collect2.setPower(0.9);
                        drive.followPath(collectgate);
                        status=Status.SHOOT_GATE;
                    }
                    break;
                }
                case SHOOT_GATE:{
                    if (!drive.isBusy()){ //dupa ce am colecat oprim intake urile
                        sleep(2000);
                        drive.setMaxPower(0.9);
                        r.collect1.setPower(0);
                        r.collect2.setPower(0);
                        //globals.colllected_line2=true;
                        drive.followPath(shootgate);
                        seq.CS= ShootingSequence.shootingStatus.WILL_SHOOT;
                        status=Status.SHOOT;
                    }
                    break;
                }
                case PARK:{
                    drive.followPath(gopark);
                    status=Status.IDLE;
                    break;
                }
                case IDLE:{
                    break;
                }
            }
            telemetry.addData("Case:", status);
            telemetry.addData("Line1?:" ,globals.colllected_line1);
            telemetry.addData("Line2?:" ,globals.colllected_line2);
            telemetry.addData("Pose::" ,drive.getPose());
            telemetry.addData("X", drive.getPose().getX());
            telemetry.addData("Y", drive.getPose().getX());
            telemetry.update();

            globals.rx=drive.getPose().getX();
            globals.ry=drive.getPose().getY();
            globals.rheading=drive.getPose().getHeading();

            turret.CS= Turret.turretStatus.TRACK;
            turret.update(r, drive.getPose().getX() , drive.getPose().getY());

            drive.update();
            shooter.update(r, drive.getPose().getX() , drive.getPose().getY());
            ramp.update(r);
            hud.update(r);
            seq.update(r,ramp,shooter, hud, turret);

            //r.Odo.recalibrate();
            //r.Odo.setPose(drive.getPose()); // sincronizezi Odo cu Pedro
        }
    }

}
