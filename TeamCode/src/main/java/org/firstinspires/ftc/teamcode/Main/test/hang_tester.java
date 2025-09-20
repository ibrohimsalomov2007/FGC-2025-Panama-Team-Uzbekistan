
package org.firstinspires.ftc.teamcode.Main.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
//@Disabled
public class hang_tester extends OpMode {

    private DcMotorEx hang_1;
    private DcMotorEx hang_2;
    private DcMotorEx hang_3;
    private DcMotorEx rightFront;
    private DcMotorEx rightLift;
    private DcMotorEx middleLift;
    private DcMotorEx intake;
    private Servo servo;
    private Servo servo_1;
    private Servo dropper;
    private Servo turn;
    private Servo rotate;
    private Servo rotate_2;
    private Servo claw;
    private Servo arm_right;
    private Servo arm_left;

    public static double p = 0.002, i = 0, d = 0.000;
    public static double f = 0.0; // Feedforward
    private final double ticks_in_degree =  700 / 180.0;

    public static int target = 100;

    @Override
    public void init() {

//        intake = hardwareMap.get(DcMotorEx.class, "intake_motor");
//        intake.setDirection(DcMotorEx.Direction.REVERSE);
        hang_1 = hardwareMap.get(DcMotorEx.class, "hang_1");
        hang_2 = hardwareMap.get(DcMotorEx.class, "hang_2");
        hang_3 = hardwareMap.get(DcMotorEx.class, "hang_3");
//        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");
//        intake = hardwareMap.get(DcMotorEx.class, "intake");

//        servo = hardwareMap.get(Servo.class, "servo");
//        servo_1 = hardwareMap.get(Servo.class, "servo_1");
        hang_2.setDirection(DcMotorEx.Direction.REVERSE);

        hang_1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang_2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang_3.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);


        //for lifts
//        leftLift = hardwareMap.get(DcMotorEx.class, "leftLift");
//        rightLift = hardwareMap.get(DcMotorEx.class, "rightLift");
//        middleLift = hardwareMap.get(DcMotorEx.class, "middleLift");
//        leftLift.setMode(DcMotorEx.RunMode.STOP_AND_RESET_ENCODER);
//        leftLift.setMode(DcMotorEx.RunMode.RUN_USING_ENCODER);
//        rightLift.setDirection(DcMotorEx.Direction.REVERSE);
//        controller = new PIDController(p, i, d);
//
//        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//    }

    }
    @Override
    public void loop() {


        if(gamepad1.dpad_up) {
            hang_1.setPower(1);
            hang_2.setPower(1);
            hang_3.setPower(1);
        } else if (gamepad1.dpad_down){
            hang_1.setPower(-1);
            hang_2.setPower(-1);
            hang_3.setPower(-1);
        } else {
            hang_1.setPower(0);
            hang_2.setPower(0);
            hang_3.setPower(0);
        }
//         servo_1.setPosition(0.55);



//         servo_1.setPosition(0.285);
    }




//
//        controller.setPID(p, i, d);
//
//        int intakePos = intake.getCurrentPosition();
//        double pid = controller.calculate(intakePos, target);
//        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
//        double power = pid + ff;
//
//        intake.setPower(power);


    //for lifts
//        int leftPos = leftLift.getCurrentPosition();
//        int rightPos = rightLift.getCurrentPosition();
//
//        double pid = controller.calculate(leftPos, target);
//        double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;
//        double power = pid + ff;
//
//        leftLift.setPower(power);
//        rightLift.setPower(leftLift.getPower());
//
//        telemetry.addData("target", target);
//        telemetry.addData("leftLIFT", leftPos);
//        telemetry.addData("rightLIFT", rightPos);
//        telemetry.addData("power", power);
//        telemetry.update();
}
