package org.firstinspires.ftc.teamcode.systems;

import org.firstinspires.ftc.teamcode.declarations.RobotMap;

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
                double relativeAngle = angle - r.Odo.getHeading(); // unghi relativ la robot
                // oleaca de normalizare
                while (relativeAngle > Math.PI) relativeAngle -= 2 * Math.PI;
                while (relativeAngle < -Math.PI) relativeAngle += 2 * Math.PI;
                double turretPos = (relativeAngle + Math.PI) / (2 * Math.PI);
                turretPos = Math.max(0, Math.min(1, turretPos));
                r.turret1.setPosition(turretPos); // ← lipsea
                r.turret2.setPosition(turretPos); // ← lipsea
                break;
            }
        }
    }
}