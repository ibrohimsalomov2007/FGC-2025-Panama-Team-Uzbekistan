package org.firstinspires.ftc.teamcode.Reckless_Bobotik;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;


@TeleOp (name = "Reckless", group ="TeleOp")
public class Reckless extends LinearOpMode {
    private DcMotor left;
    private DcMotor right;
    private DcMotor core_left;
    private DcMotor core_right;
    private DcMotor accelerator;
    private DcMotor hang_1;
    private DcMotor hang_2;



//    private boolean armMovementTriggered = false;


    @Override
    public void runOpMode() {
        // Инициализация моторов и сервоприводов
        initHardware();
        // Ожидание старта матча
        waitForStart();

        // Основной цикл управления роботом
        while (opModeIsActive()) {
            driveRobot();
            hang();
            rotate();
            Intake();
        }
    }

    private void initHardware() {
        left = hardwareMap.dcMotor.get("left");
        right = hardwareMap.dcMotor.get("right");
        core_left = hardwareMap.dcMotor.get("core_left");
        core_right = hardwareMap.dcMotor.get("core_right");
        hang_1 = hardwareMap.dcMotor.get("hang_1");
        hang_2 = hardwareMap.dcMotor.get("hang_2");
        accelerator = hardwareMap.dcMotor.get("accelerator");
        right.setDirection(DcMotorSimple.Direction.REVERSE);
        //hang_1.setDirection(DcMotorSimple.Direction.REVERSE);
        core_right.setDirection(DcMotorSimple.Direction.REVERSE);
        hang_1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang_2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    private void driveRobot() {
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
    }

    private void hang() {
        if (gamepad2.left_bumper) {
            hang_1.setPower(1);
            hang_2.setPower(1);
        } else if (gamepad2.left_trigger > 0){
            hang_1.setPower(-1);
            hang_2.setPower(-1);
        } else {
            hang_1.setPower(0);
            hang_2.setPower(0);
        }
    }


    private void rotate() {
        if (gamepad1.right_bumper) {
            accelerator.setPower(1);
        } else if (gamepad1.right_trigger > 0) {
            accelerator.setPower(-1);
        } else {
            accelerator.setPower(0);

        }}

        private void Intake() {
            if (gamepad2.left_bumper) {
                core_left.setPower(1);
                core_right.setPower(1);
            } else if (gamepad2.left_trigger > 0) {
                core_left.setPower(-1);
                core_right.setPower(-1);
            } else {
                core_left.setPower(0);
                core_right.setPower(0);
            }
        }
    }