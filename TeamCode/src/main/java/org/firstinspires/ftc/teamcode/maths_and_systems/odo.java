package org.firstinspires.ftc.teamcode.maths_and_systems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.declarations.globals;

public class odo {
    private final GoBildaPinpointDriver pinpoint;
    private double x, y, heading;
    private final double xOffset = -5.0087401, yOffset = 2.7552755;



    public odo(HardwareMap hardwareMap, String name) {
        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(xOffset, yOffset, DistanceUnit.INCH);
        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
        x = 8;
        y = 8;
        heading = Math.toRadians(90);
    }

    public void recalibrate() {
        pinpoint.recalibrateIMU();
        pinpoint.setPosition(new Pose2D(
                DistanceUnit.INCH, 0, 0, AngleUnit.RADIANS, Math.toRadians(90)
        ));
    }

    public void update(){
        pinpoint.update();
        x=pinpoint.getPosition().getX(DistanceUnit.INCH);
        y=pinpoint.getPosition().getY(DistanceUnit.INCH);
        heading=pinpoint.getPosition().getHeading(AngleUnit.RADIANS);
    }
    public double getX(){return x;}
    public double getY(){return y;}
    public double getHeading(){return heading;}
    public Position getPosition(){return new Position(x,y,heading);}
    public GoBildaPinpointDriver getPinpoint(){return pinpoint;}
    public static class Position{
        public final double x,y,heading;
        public Position(double x,double y,double heading){this.x=x;this.y=y;this.heading=heading;}
    }


}

