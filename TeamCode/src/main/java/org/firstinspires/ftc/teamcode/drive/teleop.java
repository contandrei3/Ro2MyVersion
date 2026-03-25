package org.firstinspires.ftc.teamcode.drive;

import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.maths_and_systems.functions;
import org.firstinspires.ftc.teamcode.systems.ShootingSequence;
import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.systems.Hud;
import org.firstinspires.ftc.teamcode.systems.Ramp;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Turret;
import org.firstinspires.ftc.teamcode.declarations.globals;

@TeleOp(name = "TeleOp", group = "Linear OpMode")
public class teleop extends LinearOpMode {
    @Override
    public void runOpMode() {

        //initializari de servo uri, robotmap etc
        RobotMap r = new RobotMap(hardwareMap);
        Ramp ramp = new Ramp();
        Shooter shooter = new Shooter();
        Hud hud = new Hud();
        ShootingSequence seq = new ShootingSequence();
        Turret turret =  new Turret();

        ramp.CS= Ramp.rampStatus.INITIALIZE;
        shooter.CS=Shooter.shooterStatus.INITIALIZE;
        hud.CS=Hud.hudStatus.CLOSE;
        seq.CS=ShootingSequence.shootingStatus.STOP;

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();

        //r.Odo.recalibrate();
        r.Odo.setStartPose(new Pose(globals.rx, globals.ry, Math.toRadians(globals.rheading)));
        globals.alliance="red";
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            // Drive
            previousGamepad1.copy(currentGamepad1); //ala nou devine vechi
            currentGamepad1.copy(gamepad1); //mi-e lene sa schimb gamepad1 peste tot

            if (currentGamepad1.left_bumper && !previousGamepad1.left_bumper)
            {
                if (globals.alliance=="red")
                    globals.alliance="blue";
                else globals.alliance="red";
            }

            double y  = -gamepad1.left_stick_y;
            double x  =  gamepad1.left_stick_x;
            double rx =  gamepad1.right_stick_x;
            double flpow = y + x + rx;
            double blpow = y - x + rx;
            double frpow = y - x - rx;
            double brpow = y + x - rx;
            double nmax = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            flpow /= nmax;
            blpow /= nmax;
            frpow /= nmax;
            brpow /= nmax;
            r.frontLeftDrive.setPower(flpow);
            r.backLeftDrive.setPower(blpow);
            r.frontRightDrive.setPower(frpow);
            r.backRightDrive.setPower(brpow);

            // Collect - Intake
            if (seq.CS==ShootingSequence.shootingStatus.IDLE){ //aici verificam sa nu interfereze cu miscarea intake ului la shooting
                double putere=gamepad1.right_trigger-gamepad1.left_trigger;
                if (putere <0) putere= Math.max(putere,-0.85);
                if (putere >0) putere= Math.min(putere,0.85);
                r.collect1.setPower(putere);
                r.collect2.setPower(putere);
                //r.hud.setPosition(putere);
            }
            //shootam cu secventa pe x
            if (currentGamepad1.right_bumper && !previousGamepad1.right_bumper) {
                //fastshootingsequence
                seq.CS=ShootingSequence.shootingStatus.PREPARE;
            }
            if (currentGamepad1.cross && !previousGamepad1.cross) {
                //fastshootingsequence
                seq.timer.reset();
                seq.CS=ShootingSequence.shootingStatus.SHOOT;
            }
            //reset de odometrie

            if (currentGamepad1.options && !previousGamepad1.options){
                if (globals.track==true) globals.track=false;
                else globals.track=true;
            }
            turret.CS=Turret.turretStatus.TRACK;
            shooter.update(r, r.Odo.getPose().getX(), r.Odo.getPose().getY());
            ramp.update(r);
            hud.update(r);
            seq.update(r,ramp,shooter, hud, turret);
            turret.update(r, r.Odo.getPose().getX(), r.Odo.getPose().getY());
            r.Odo.update();
            if (currentGamepad1.circle && !previousGamepad1.circle) { //vrem reset de pozitie
                //r.Odo.resetIMU();
                //r.Odo.
                r.Odo.setPose(new Pose (0,0, Math.toRadians(90)));
            }
            globals.rx=r.Odo.getPose().getX();
            globals.ry=r.Odo.getPose().getY();
            globals.rheading=r.Odo.getPose().getHeading();
            // telemetrie
            telemetry.addData("Seq", seq.CS);
            telemetry.addData("Shooter", shooter.CS);
            telemetry.addData("Ramp", ramp.CS);
            telemetry.addData("Hud", hud.CS);
            telemetry.addData("X", r.Odo.getPose().getX());
            telemetry.addData("Y", r.Odo.getPose().getY());
            telemetry.addData("GX", globals.xRedGoal);
            telemetry.addData("GY", globals.yRedGoal);
            telemetry.addData("Heading", Math.toDegrees(r.Odo.getPose().getHeading()));
            telemetry.addData("timer",seq.timer);
            telemetry.addData("shooter current power", r.launch1.getPower());
            telemetry.addData("shooter ideal power", functions.getshoootpower(r));
            telemetry.addData("current distance from goal", functions.getdistance(r));
            telemetry.addData("current turret angle", functions.getturretpower(r));
            telemetry.update();
        }
    }
}