package org.firstinspires.ftc.teamcode.utils;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

public class odo {
    private final org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver pinpoint;
    private double x, y, heading;
    private final double xOffset = -4.2716535433, yOffset = 1.575;



    public odo(HardwareMap hardwareMap, String name) {
        pinpoint = hardwareMap.get(org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver.class, "pinpoint");
        pinpoint.setOffsets(xOffset, yOffset, DistanceUnit.INCH);
        pinpoint.setEncoderResolution(org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver.EncoderDirection.FORWARD, org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.resetPosAndIMU();
        x = 0.0;
        y = 0.0;
        heading = 0.0;
    }

    public void recalibrate()
    {
        pinpoint.recalibrateIMU();
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
    public org.firstinspires.ftc.teamcode.utils.GoBildaPinpointDriver getPinpoint(){return pinpoint;}
    public static class Position{
        public final double x,y,heading;
        public Position(double x,double y,double heading){this.x=x;this.y=y;this.heading=heading;}
    }

}

