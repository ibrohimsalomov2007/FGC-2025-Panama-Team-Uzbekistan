package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.ArmIntakeSubsystem;

import java.util.function.BooleanSupplier;

public class ArmIntakeCommand {
    private final ArmIntakeSubsystem arm;
    private final BooleanSupplier dpadUp;
    private final BooleanSupplier dpadDown;
    private final BooleanSupplier squareX; // x button
    private final BooleanSupplier circleB; // b button

    private boolean dpadUpHandledLast = false;
    private boolean dpadDownHandledLast = false;

    public ArmIntakeCommand(ArmIntakeSubsystem arm,
                            BooleanSupplier dpadUp,
                            BooleanSupplier dpadDown,
                            BooleanSupplier squareX,
                            BooleanSupplier circleB) {
        this.arm = arm;
        this.dpadUp = dpadUp;
        this.dpadDown = dpadDown;
        this.squareX = squareX;
        this.circleB = circleB;
    }

    public void execute() {
        // D-PAD up edge: step target -150
        if (dpadUp.getAsBoolean()) {
            if (!dpadUpHandledLast) {
                arm.stepTargetMinus150();
                dpadUpHandledLast = true;
            }
        } else {
            dpadUpHandledLast = false;
        }

        // D-PAD down edge: reset to start
        if (dpadDown.getAsBoolean()) {
            if (!dpadDownHandledLast) {
                arm.resetTargetToStart();
                dpadDownHandledLast = true;
            }
        } else {
            dpadDownHandledLast = false;
        }

        // CRServo manual control: X -> forward, B -> reverse, else stop
        if (squareX.getAsBoolean()) {
            arm.setTakePower(1.0);
        } else if (circleB.getAsBoolean()) {
            arm.setTakePower(-1.0);
        } else {
            // no manual drum input here; keep 0
            arm.setTakePower(0.0);
        }

        // PID runs in subsystem.periodic() from main loop
    }
}

