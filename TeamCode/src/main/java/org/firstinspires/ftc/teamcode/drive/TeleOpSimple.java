package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.systems.ShootingSequence;
import org.firstinspires.ftc.teamcode.declarations.RobotMap;
import org.firstinspires.ftc.teamcode.systems.Hud;
import org.firstinspires.ftc.teamcode.systems.Ramp;
import org.firstinspires.ftc.teamcode.systems.Shooter;
import org.firstinspires.ftc.teamcode.systems.Turret;

@TeleOp(name = "TeleOpSimple", group = "Linear OpMode")
public class TeleOpSimple extends LinearOpMode {
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
        hud.CS=Hud.hudStatus.INITIALIZE;
        seq.CS=ShootingSequence.shootingStatus.IDLE;

        Gamepad currentGamepad1 = new Gamepad();
        Gamepad previousGamepad1 = new Gamepad();
        r.Odo.recalibrate();


        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            // Drive
            previousGamepad1.copy(currentGamepad1); //ala nou devine vechi
            currentGamepad1.copy(gamepad1); //mi-e lene sa schimb gamepad1 peste tot


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
            double apasright = Math.min(gamepad1.right_trigger, 0.85);
            double apasleft  = Math.min(gamepad1.left_trigger, 0.85);
            if (apasright > 0.1) {
                r.collect1.setPower(apasright);
                r.collect2.setPower(apasright);
            } else if (apasleft > 0.1) {
                r.collect1.setPower(-apasleft);
                r.collect2.setPower(-apasleft);
            } else {
                r.collect1.setPower(0);
                r.collect2.setPower(0);
            }}

            //shooting
            if (gamepad1.cross && !previousGamepad1.cross) {
                //fastshootingsequence
                seq.timer.reset();
                seq.CS=ShootingSequence.shootingStatus.PREPARE;
                seq.timer.reset();
            }
            //reset de odometrie
            if (gamepad1.circle && !previousGamepad1.circle) { //vrem reset de pozitie
            r.Odo.recalibrate();
            }
            shooter.update(r);
            ramp.update(r);
            hud.update(r);
            seq.update(r,ramp,shooter, hud, turret);
            turret.update(r);
            r.Odo.update();

            // telemetrie
            telemetry.addData("Seq", seq.CS);
            telemetry.addData("Shooter", shooter.CS);
            telemetry.addData("Ramp", ramp.CS);
            telemetry.addData("X", r.Odo.getX());
            telemetry.addData("Y", r.Odo.getY());
            telemetry.addData("Heading", Math.toDegrees(r.Odo.getHeading()));
            telemetry.update();
        }
    }
}