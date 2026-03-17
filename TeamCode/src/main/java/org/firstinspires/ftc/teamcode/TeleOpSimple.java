package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.RobotMap;

@TeleOp(name = "TeleOpSimple", group = "Linear OpMode")
public class TeleOpSimple extends LinearOpMode {
    @Override
    public void runOpMode() {
        RobotMap r = new RobotMap(hardwareMap);
        Ramp ramp = new Ramp();
        ramp.CS= Ramp.rampStatus.INITIALIZE;
        waitForStart();
        while (opModeIsActive() && !isStopRequested()) {

            // Drive
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

            // Collect
            double apasright = Math.min(gamepad1.right_trigger, 0.85);
            double apasleft  = gamepad1.left_trigger;
            if (apasright > 0.1) {
                r.collect1.setPower(apasright);
                r.collect2.setPower(apasright);
            } else if (apasleft > 0.1) {
                r.collect1.setPower(-apasleft);
                r.collect2.setPower(-apasleft);
            } else {
                r.collect1.setPower(0);
                r.collect2.setPower(0);
            }

            //Punem rampa pe x
            if (gamepad1.cross==true)
            ramp.CS= Ramp.rampStatus.SHOOT;
            else ramp.CS= Ramp.rampStatus.COLLECT;
            ramp.update(r);
        }
    }
}