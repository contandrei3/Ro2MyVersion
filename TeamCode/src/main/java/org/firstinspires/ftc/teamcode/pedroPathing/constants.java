package org.firstinspires.ftc.teamcode.pedroPathing;

import androidx.annotation.Nullable;

import com.bylazar.configurables.annotations.Configurable;
import com.pedropathing.control.FilteredPIDFCoefficients;
import com.pedropathing.control.PIDFCoefficients;
import com.pedropathing.follower.Follower;
import com.pedropathing.follower.FollowerConstants;
import com.pedropathing.ftc.FollowerBuilder;
import com.pedropathing.ftc.drivetrains.MecanumConstants;
import com.pedropathing.ftc.localization.constants.PinpointConstants;
import com.pedropathing.paths.PathConstraints;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;


public class constants {
    public static FollowerConstants followerConstants = new FollowerConstants()
            .mass(11.42)
            .forwardZeroPowerAcceleration(-45) //x
            .lateralZeroPowerAcceleration(-60) //
            .translationalPIDFCoefficients(new PIDFCoefficients(0.25, 0, 0.027, 0))
            .headingPIDFCoefficients(new PIDFCoefficients(1.5, 0,0.1,0.00))
            .drivePIDFCoefficients(new FilteredPIDFCoefficients(0.012,0,0.0015 ,0.0,0))
            //0.00055
            .centripetalScaling(0.00055);





    public static MecanumConstants driveConstants = new MecanumConstants()
            .maxPower(1)
            .rightFrontMotorName("rightFront")
            .rightRearMotorName("rightBack")
            .leftRearMotorName("leftBack")
            .leftFrontMotorName("leftFront")
            .leftFrontMotorDirection(DcMotorSimple.Direction.REVERSE)
            .leftRearMotorDirection(DcMotorSimple.Direction.REVERSE)
            .rightFrontMotorDirection(DcMotorSimple.Direction.FORWARD)
            .rightRearMotorDirection(DcMotorSimple.Direction.FORWARD)
            .xVelocity(85) // 94
            .yVelocity(55) // 70
            ;




    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public static PinpointConstants localizerConstants = new PinpointConstants()
            //.forwardPodY(2.76590551)
            //.strafePodX(-5.02874015)
            .forwardPodY(2.7552755)
            .strafePodX(-5.0087401)
            .distanceUnit(DistanceUnit.INCH)
            .hardwareMapName("pinpoint")
            .encoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD)
            .forwardEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD)
            .strafeEncoderDirection(GoBildaPinpointDriver.EncoderDirection.FORWARD);

    public static PathConstraints pathConstraints = new PathConstraints(0.97,0.5,0.5, Math.toRadians(2),0,1,10,1.25);

    public static Follower createFollower(HardwareMap hardwareMap) {
        return new FollowerBuilder(followerConstants, hardwareMap)
                .pinpointLocalizer(localizerConstants)
                .pathConstraints(pathConstraints)
                .mecanumDrivetrain(driveConstants)
                .build();
    }
}
