package org.firstinspires.ftc.teamcode.Main.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp
public class arm_Intake_tester extends OpMode {

    private CRServo take_1;
    private CRServo take_2;
    private DcMotor door;

    private Servo clutch_1;
    private Servo clutch_2;

    private boolean intakeReverse = false;
    private boolean dpadDownPressedLast = false;
    private boolean dpadUpPressedLast = false;
    private boolean dpadLeftPressedLast = false;
    private boolean dpadRightPressedLast = false;

    private int doorStartPosition = 0;
    private int targetPosition = 0;

    // PID constants — tune if needed
    private static final double kP = 0.02;
    private static final double kI = 0.006;
    private static final double kD = 0.001;
    private static final double kF = 0.1;
    private static final double INTEGRAL_LIMIT = 50.0;

    private double integralSum = 0;
    private double lastError = 0;

    private ElapsedTime timer = new ElapsedTime();

    // Servo positions
    private final double CLUTCH_INITIAL = 0.0;
    private final double CLUTCH_EXTENDED = 1.0;

    @Override
    public void init() {
        take_1 = hardwareMap.get(CRServo.class, "take_1");
        take_2 = hardwareMap.get(CRServo.class, "take_2");
        door = hardwareMap.get(DcMotor.class, "door");

        clutch_1 = hardwareMap.get(Servo.class, "clutch_1");
        clutch_2 = hardwareMap.get(Servo.class, "clutch_2");
        clutch_1.setDirection(Servo.Direction.REVERSE);

        take_1.setDirection(CRServo.Direction.REVERSE);

        door.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        door.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        door.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        door.setDirection(DcMotor.Direction.REVERSE);

        doorStartPosition = 0;
        targetPosition = doorStartPosition;

        clutch_1.setPosition(CLUTCH_INITIAL);
        clutch_2.setPosition(CLUTCH_INITIAL);

        timer.reset();
    }

    @Override
    public void loop() {

        // --- D-PAD UP (move -30) ---
        if (gamepad2.dpad_up && !dpadUpPressedLast) {
            targetPosition -= 150;
            dpadUpPressedLast = true;
        } else if (!gamepad2.dpad_up) {
            dpadUpPressedLast = false;
        }

        // --- X BUTTON (run intakes forward while holding) ---
        if (gamepad2.x) {
            take_1.setPower(1.0);
            take_2.setPower(1.0);
        } else if (!intakeReverse) {
            take_1.setPower(0.0);
            take_2.setPower(0.0);
        }

        // --- D-PAD DOWN (toggle reverse/stopped) ---
        if (gamepad2.dpad_down) {
            if (!dpadDownPressedLast) {
                if (!intakeReverse) {
                    take_1.setPower(-1.0);
                    take_2.setPower(-1.0);
                    intakeReverse = true;
                } else {
                    take_1.setPower(0.0);
                    take_2.setPower(0.0);
                    intakeReverse = false;
                    targetPosition = doorStartPosition;
                }
            }
            dpadDownPressedLast = true;
        } else {
            dpadDownPressedLast = false;
        }

        // --- D-PAD LEFT (set clutch servos to +1) ---
        if (gamepad2.dpad_left && !dpadLeftPressedLast) {
            clutch_1.setPosition(CLUTCH_EXTENDED);
            clutch_2.setPosition(CLUTCH_EXTENDED);
            dpadLeftPressedLast = true;
        } else if (!gamepad2.dpad_left) {
            dpadLeftPressedLast = false;
        }

        // --- D-PAD RIGHT (set clutch servos to -1 logical / 0.0 actual) ---
        if (gamepad2.dpad_right && !dpadRightPressedLast) {
            clutch_1.setPosition(CLUTCH_INITIAL);
            clutch_2.setPosition(CLUTCH_INITIAL);
            dpadRightPressedLast = true;
        } else if (!gamepad2.dpad_right) {
            dpadRightPressedLast = false;
        }

        // ---------- PID POSITION CONTROL (new style) -----------
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
        telemetry.addData("Clutch Pos", clutch_1.getPosition());
        telemetry.update();
    }
}
