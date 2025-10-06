
package org.firstinspires.ftc.teamcode.Main.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
//@Disabled
public class hang_tester extends OpMode {

    private DcMotorEx hang_1;
    private DcMotorEx hang_2;
    private DcMotorEx suck;

    @Override
    public void init() {

//        intake = hardwareMap.get(DcMotorEx.class, "intake_motor");
//        intake.setDirection(DcMotorEx.Direction.REVERSE);
        hang_1 = hardwareMap.get(DcMotorEx.class, "hang_1");
        hang_2 = hardwareMap.get(DcMotorEx.class, "hang_2");
        suck = hardwareMap.get(DcMotorEx.class, "suck");
//        intake = hardwareMap.get(DcMotorEx.class, "intake");

//        servo = hardwareMap.get(Servo.class, "servo");
//        servo_1 = hardwareMap.get(Servo.class, "servo_1");
        //hang_2.setDirection(DcMotorEx.Direction.REVERSE);
        suck.setDirection(DcMotorSimple.Direction.REVERSE);

        hang_1.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        hang_2.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
        suck.setZeroPowerBehavior(DcMotorEx.ZeroPowerBehavior.BRAKE);
    }
    @Override
    public void loop() {


        if(gamepad1.dpad_left) {
            hang_1.setPower(1);
            hang_2.setPower(1);
            suck.setPower(1);
        } else if (gamepad1.dpad_right){
            hang_1.setPower(-1);
            hang_2.setPower(-1);
            suck.setPower(-1);
        } else {
            hang_1.setPower(0);
            hang_2.setPower(0);
            suck.setPower(0);
        }
    }
}
