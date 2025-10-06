package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.framework.Subsystem;

public class DrumIntakeSubsystem implements Subsystem {
    private final DcMotor hang1;
    private final DcMotor hang2;
    private DcMotor suck; // optional motor

    public DrumIntakeSubsystem(HardwareMap hw) {
        hang1 = hw.get(DcMotor.class, "hang_1");
        hang2 = hw.get(DcMotor.class, "hang_2");

        // Try to get the optional "suck" motor (safe if not present)
        try {
            suck = hw.get(DcMotor.class, "suck");
            suck.setDirection(DcMotor.Direction.REVERSE); // reverse if needed
        } catch (Exception ignored) {
            suck = null;
        }

        hang1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hang2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        if (suck != null) suck.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    /**
     * Moves the drum intake motors with the given power.
     * @param power positive = forward, negative = reverse
     */
    public void move(double power) {
        hang1.setPower(power);
        hang2.setPower(power);
        if (suck != null) suck.setPower(power);
    }

    /**
     * Stops all drum intake motors.
     */
    public void stop() {
        move(0);
    }

    @Override
    public void periodic() {
        // Nothing here for now
    }
}
