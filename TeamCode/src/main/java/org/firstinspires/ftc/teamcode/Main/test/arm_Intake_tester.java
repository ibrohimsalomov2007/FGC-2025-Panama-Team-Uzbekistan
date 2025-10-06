package org.firstinspires.ftc.teamcode.Main.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "arm_Intake_tester", group = "test")
public class arm_Intake_tester extends OpMode {

    private CRServo take_1;
    private CRServo take_2;
    private DcMotor door;

    private boolean dpadUpPressedLast = false;
    private boolean dpadDownPressedLast = false;

    private int doorStartPosition = 0;
    private int targetPosition = 0;

    // PID constants
    private static final double kP = 0.02;
    private static final double kI = 0.006;
    private static final double kD = 0.001;
    private static final double kF = 0.1;
    private static final double INTEGRAL_LIMIT = 50.0;

    private double integralSum = 0;
    private double lastError = 0;

    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        take_1 = hardwareMap.get(CRServo.class, "take_1");
        take_2 = hardwareMap.get(CRServo.class, "take_2");
        door = hardwareMap.get(DcMotor.class, "door");

        take_1.setDirection(CRServo.Direction.REVERSE);

        door.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        door.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        door.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        door.setDirection(DcMotor.Direction.REVERSE);

        doorStartPosition = 0;
        targetPosition = doorStartPosition;

        timer.reset();
    }

    @Override
    public void loop() {

        // --- D-PAD UP: move -150 ticks ---
        if (gamepad2.dpad_up && !dpadUpPressedLast) {
            targetPosition -= 150;
            dpadUpPressedLast = true;
        } else if (!gamepad2.dpad_up) {
            dpadUpPressedLast = false;
        }

        // --- D-PAD DOWN: return to initial position ---
        if (gamepad2.dpad_down && !dpadDownPressedLast) {
            targetPosition = doorStartPosition;
            dpadDownPressedLast = true;
        } else if (!gamepad2.dpad_down) {
            dpadDownPressedLast = false;
        }

        // --- CRServos Control with Square and Circle ---
        if (gamepad2.x) { // Square button → forward
            take_1.setPower(1.0);
            take_2.setPower(1.0);
        } else if (gamepad2.b) { // Circle button → reverse
            take_1.setPower(-1.0);
            take_2.setPower(-1.0);
        } else {
            take_1.setPower(0.0);
            take_2.setPower(0.0);
        }

        // ---------- PID POSITION CONTROL ----------
        double deltaTime = timer.seconds();
        if (deltaTime < 0.001) deltaTime = 0.001;

        double currentPos = door.getCurrentPosition();
        double error = targetPosition - currentPos;

        integralSum += error * deltaTime;
        integralSum = Math.max(-INTEGRAL_LIMIT, Math.min(INTEGRAL_LIMIT, integralSum));

        double derivative = (error - lastError) / deltaTime;
        double output = (kP * error) + (kI * integralSum) + (kD * derivative) + kF;

        output = Math.max(-1.0, Math.min(1.0, output));
        door.setPower(output);

        lastError = error;
        timer.reset();

        telemetry.addData("Door Pos", currentPos);
        telemetry.addData("Target Pos", targetPosition);
        telemetry.addData("Output", output);
        telemetry.update();
    }
}
