package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.PushSubsystem;
import java.util.function.BooleanSupplier;

public class PushCommand {

    private final PushSubsystem push;
    private final BooleanSupplier cross;
    private final BooleanSupplier triangle;

    private boolean crossPressedLast = false;
    private boolean trianglePressedLast = false;

    public PushCommand(PushSubsystem push, BooleanSupplier cross, BooleanSupplier triangle) {
        this.push = push;
        this.cross = cross;
        this.triangle = triangle;
    }

    public void execute() {
        // CROSS sequence (on press)
        if (cross.getAsBoolean() && !crossPressedLast) {
            push.startCrossSequence();
            crossPressedLast = true;
        } else if (!cross.getAsBoolean()) {
            crossPressedLast = false;
        }

        // TRIANGLE sequence (on press)
        if (triangle.getAsBoolean() && !trianglePressedLast) {
            push.startTriangleSequence();
            trianglePressedLast = true;
        } else if (!triangle.getAsBoolean()) {
            trianglePressedLast = false;
        }

        // Always update subsystem logic
        push.periodic();
    }
}
