package org.firstinspires.ftc.teamcode;

public class Turret {

    public enum turretStatus {
        IDLE,
        TRACK,  // se orientează spre goal
    }

    public turretStatus CS = turretStatus.IDLE;

    // coordonate goal rosu
    static final double GOAL_X = 131.51;
    static final double GOAL_Y = 13.66;

    public void update(RobotMap r) {
        switch (CS) {
            case IDLE: {

                break;
            }
            case TRACK: {
                double robotX = r.Odo.getX();
                double robotY = r.Odo.getY();
                double angle = Math.atan2(GOAL_Y - robotY, GOAL_X - robotX);
                double turretPos = (angle + Math.PI) / (2 * Math.PI);
                r.turret1.setPosition(turretPos); // ← lipsea
                r.turret2.setPosition(turretPos); // ← lipsea
                break;
            }
        }
    }
}