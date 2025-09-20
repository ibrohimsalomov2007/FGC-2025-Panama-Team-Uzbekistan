package org.firstinspires.ftc.teamcode.Reckless_Bobotik;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class Reckless_Intake_tester extends OpMode {
    private DcMotor core_left;
    private DcMotor core_right;

    // PID coefficients
    private double kP = 0.2;
    private double kI = 0.001;
    private double kD = 0.01;

    private int leftTarget = 0;
    private int rightTarget = 0;

    private boolean lastTriangleState = false;
    private boolean toggleForward = true;

    // PID state variables
    private double leftIntegral = 0;
    private double leftLastError = 0;
    private double rightIntegral = 0;
    private double rightLastError = 0;

    @Override
    public void init() {
        core_left = hardwareMap.dcMotor.get("core_left");
        core_right = hardwareMap.dcMotor.get("core_right");

        // Reset encoders
        core_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        core_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        core_left.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        core_right.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        // Keep forward directions; PID error will handle reversal
        core_left.setDirection(DcMotor.Direction.FORWARD);
        core_right.setDirection(DcMotor.Direction.FORWARD);

        // Startup targets: move both motors simultaneously
        leftTarget = 200;  // use larger counts for reliability
        rightTarget = 200;
    }

    @Override
    public void loop() {
        // Move both motors toward their targets
        boolean leftDone = moveMotorPID(core_left, leftTarget, false);
        boolean rightDone = moveMotorPID(core_right, rightTarget, true);

        // Triangle toggle after startup movement completed
        boolean currentTriangle = gamepad1.triangle;
        if (currentTriangle && !lastTriangleState && leftDone && rightDone) {
            int delta = toggleForward ? 10 : -10;
            leftTarget += delta;
            rightTarget += delta;
            toggleForward = !toggleForward;
        }
        lastTriangleState = currentTriangle;

        // Telemetry
        telemetry.addData("Left Target", leftTarget);
        telemetry.addData("Right Target", rightTarget);
        telemetry.addData("Left Current", core_left.getCurrentPosition());
        telemetry.addData("Right Current", core_right.getCurrentPosition());
        telemetry.update();
    }

    // PID controller for one motor with direction correction
    private boolean moveMotorPID(DcMotor motor, int target, boolean isRight) {
        int current = motor.getCurrentPosition();
        // Correct error for right motor if physically mounted reversed
        int error = (isRight ? -1 : 1) * (target - current);

        double power;
        if (isRight) {
            rightIntegral += error;
            double derivative = error - rightLastError;
            power = kP * error + kI * rightIntegral + kD * derivative;
            rightLastError = error;
        } else {
            leftIntegral += error;
            double derivative = error - leftLastError;
            power = kP * error + kI * leftIntegral + kD * derivative;
            leftLastError = error;
        }

        // Clip power
        power = Math.max(-1.0, Math.min(1.0, power));

        // Stop motor if within tolerance
        if (Math.abs(error) < 5) { // tolerance increased for reliability
            motor.setPower(0);
            return true; // target reached
        } else {
            motor.setPower(power);
            return false; // still moving
        }
    }
}
