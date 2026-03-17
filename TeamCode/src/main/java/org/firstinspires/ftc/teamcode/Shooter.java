package org.firstinspires.ftc.teamcode;

public class Shooter {

    public enum shooterStatus {
        INITIALIZE,
        STOP,
        SHOOT
    }

    public shooterStatus CS = shooterStatus.INITIALIZE;
    double stop=0.05;
    double putere= 0.5;
    public void update(RobotMap r) {
        if (CS == shooterStatus.INITIALIZE) {
            r.launch1.setPower(stop);
            r.launch2.setPower(stop);
        } else if (CS == shooterStatus.STOP) {
            r.launch1.setPower(stop);
            r.launch2.setPower(stop);
        } else if (CS == shooterStatus.SHOOT) {
            r.launch1.setPower(putere);
            r.launch2.setPower(putere);
        }
    }
}