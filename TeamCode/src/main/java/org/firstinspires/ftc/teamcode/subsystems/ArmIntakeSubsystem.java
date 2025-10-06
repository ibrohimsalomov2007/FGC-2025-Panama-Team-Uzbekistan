package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.framework.Subsystem;

/**
 * Mirrors arm_Intake_tester behavior:
 * - take_1 and take_2 are CRServos
 * - door is a DcMotor with encoder driven by PID to targetPosition
 * - pressing gamepad2.dpad_up subtracts 150 ticks from target (edge-detected)
 * - pressing gamepad2.dpad_down resets target to start position (edge-detected)
 * - the CRServo manual controls (x/b) should be handled by the command
 */
public class ArmIntakeSubsystem implements Subsystem {
    private final CRServo take1, take2;
    private final DcMotor door;

    // PID constants from your tester
    private static final double kP = 0.02;
    private static final double kI = 0.006;
    private static final double kD = 0.001;
    private static final double kF = 0.1;
    private static final double INTEGRAL_LIMIT = 50.0;

    private double integralSum = 0;
    private double lastError = 0;
    private int targetPosition;
    private int doorStartPosition;
    private final ElapsedTime timer = new ElapsedTime();

    public ArmIntakeSubsystem(HardwareMap hw) {
        take1 = hw.get(CRServo.class, "take_1");
        take2 = hw.get(CRServo.class, "take_2");
        door = hw.dcMotor.get("door");

        take1.setDirection(CRServo.Direction.REVERSE);
        door.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        door.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        door.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        door.setDirection(DcMotor.Direction.REVERSE);

        doorStartPosition = door.getCurrentPosition();
        targetPosition = doorStartPosition;
        timer.reset();
    }

    // edge-triggered actions are handled by the command; these are direct setters:
    public void stepTargetMinus150() {
        targetPosition -= 150;
    }

    public void resetTargetToStart() {
        targetPosition = doorStartPosition;
    }

    public void setTakePower(double p) {
        take1.setPower(p);
        take2.setPower(p);
    }

    // call from main loop to run PID
    @Override
    public void periodic() {
        double dt = timer.seconds();
        if (dt < 1e-3) dt = 1e-3;

        double current = door.getCurrentPosition();
        double error = targetPosition - current;

        integralSum += error * dt;
        integralSum = Math.max(-INTEGRAL_LIMIT, Math.min(INTEGRAL_LIMIT, integralSum));

        double derivative = (error - lastError) / dt;
        double output = (kP * error) + (kI * integralSum) + (kD * derivative) + kF;

        output = Math.max(-1.0, Math.min(1.0, output));
        door.setPower(output);

        lastError = error;
        timer.reset();
    }

    public int getDoorPosition() { return door.getCurrentPosition(); }
    public int getTargetPosition() { return targetPosition; }
}
