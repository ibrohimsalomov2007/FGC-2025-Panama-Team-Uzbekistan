package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.framework.Subsystem;

public class HangSubsystem implements Subsystem {
    private final DcMotor hang1;
    private final DcMotor hang2;
    private DcMotor suck; // optional motor

    public HangSubsystem(HardwareMap hw) {
        hang1 = hw.get(DcMotor.class, "hang_1");
        hang2 = hw.get(DcMotor.class, "hang_2");

        // Try to get the optional "suck" motor (safe if not present)
        try {
            suck = hw.get(DcMotor.class, "suck");
            suck.setDirection(DcMotor.Direction.REVERSE); // reverse if needed
        } catch (Exception ignored) {
            suck = null;
        }

        // Recommended: brake mode to hold position when power = 0
        hang1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (suck != null) suck.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Moves the hang mechanism up/down gradually with given power.
     * @param power positive = up, negative = down
     */
    public void move(double power) {
        power = Math.max(0.0, Math.min(1.0, power));
        hang1.setPower(power);
        hang2.setPower(power);
        if (suck != null) suck.setPower(power);
    }

    /**
     * Stops all motors immediately.
     */
    public void stop() {
        move(0);
    }

    @Override
    public void periodic() {
        // Nothing to update periodically for now
    }
}
