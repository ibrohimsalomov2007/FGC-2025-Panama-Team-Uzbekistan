package org.firstinspires.ftc.teamcode.Reckless_Bobotik;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@TeleOp
public class Reckless_hang_tester  extends OpMode {
    private DcMotor hang_1;
    private DcMotor hang_2;
    private DcMotor left;
    private DcMotor right;
    @Override
    public void init() {
        hang_1 = hardwareMap.dcMotor.get("hang_1");
        hang_2 = hardwareMap.dcMotor.get("hang_2");
        //hang_1.setDirection(DcMotorSimple.Direction.REVERSE);
        hang_1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang_2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    @Override
    public void loop(){
        double drive = gamepad1.left_stick_y;  // Вперед/назад
        double turn = gamepad1.right_stick_x;  // Повороты

        double leftPower = drive + turn;
        double rightPower = drive - turn;

        left.setPower(leftPower);
        right.setPower(rightPower);
        if (gamepad1.right_bumper){
            rightPower *= 0.1;
            leftPower *= 0.1;
        }
        left.setPower(leftPower);
        right.setPower(rightPower);

        if (gamepad1.left_bumper) {
            hang_1.setPower(1);
            hang_2.setPower(1);
        } else if (gamepad1.left_trigger > 0){
            hang_1.setPower(-1);
            hang_2.setPower(-1);
        } else {
            hang_1.setPower(0);
            hang_2.setPower(0);
        }
    }
}





