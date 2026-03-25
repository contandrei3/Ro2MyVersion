package org.firstinspires.ftc.teamcode.declarations;

import com.pedropathing.ftc.localization.localizers.PinpointLocalizer;
import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
//import org.firstinspires.ftc.teamcode.maths_and_systems.odo;
import org.firstinspires.ftc.teamcode.pedroPathing.constants;


public class RobotMap {
    public DcMotorEx frontLeftDrive  = null;
    public DcMotorEx backLeftDrive   = null;
    public DcMotorEx frontRightDrive = null;
    public DcMotorEx backRightDrive  = null;
    public DcMotorEx collect1 = null;
    public DcMotorEx collect2 = null;
    public DcMotorEx launch1 = null;
    public DcMotorEx launch2 = null;
    public Servo ramp= null;
    public Servo hud = null;
    public Servo turret1=null;
    public Servo turret2=null;
    public PinpointLocalizer Odo = null;
    public RobotMap(HardwareMap hwMap) {
        //motoare de sasiu
        frontLeftDrive  = hwMap.get(DcMotorEx.class, "leftFront");
        backLeftDrive   = hwMap.get(DcMotorEx.class, "leftBack");
        frontRightDrive = hwMap.get(DcMotorEx.class, "rightFront");
        backRightDrive  = hwMap.get(DcMotorEx.class, "rightBack");

        frontLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);
        backRightDrive.setDirection(DcMotorSimple.Direction.FORWARD);

        frontLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeftDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRightDrive.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //intake

        collect1= hwMap.get(DcMotorEx.class, "collect1");
        collect2= hwMap.get(DcMotorEx.class, "collect2");

        collect1.setDirection(DcMotorEx.Direction.REVERSE);
        collect2.setDirection(DcMotorEx.Direction.FORWARD);

        collect1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        collect2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        collect1.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        collect2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //launch -motare
        launch1= hwMap.get(DcMotorEx.class, "launch1");
        launch2= hwMap.get(DcMotorEx.class, "launch2");

        launch1.setDirection(DcMotorEx.Direction.REVERSE);
        launch2.setDirection(DcMotorEx.Direction.FORWARD);

        launch1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launch2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        launch2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        launch2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        //ramp-servo
        ramp= hwMap.get(Servo.class, "ramp");

        //hud
        hud= hwMap.get(Servo.class, "hud");

        //tureta (2 servouri)
        turret1=hwMap.get(Servo.class, "turret1");
        turret2=hwMap.get(Servo.class, "turret2");

        //odo din pedro

        Odo= new PinpointLocalizer(hwMap, constants.localizerConstants);
        Odo.setStartPose(new Pose(0,0,Math.toRadians(90)));

        //PID uri

    }
}