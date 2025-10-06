package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.ClutchSubsystem;
import java.util.function.BooleanSupplier;

public class ClutchCommand {
    private final ClutchSubsystem clutch;
    private final BooleanSupplier dpadLeft;
    private final BooleanSupplier dpadRight;

    public ClutchCommand(ClutchSubsystem clutch, BooleanSupplier dpadLeft, BooleanSupplier dpadRight) {
        this.clutch = clutch;
        this.dpadLeft = dpadLeft;
        this.dpadRight = dpadRight;
    }

    public void execute() {
        if (dpadLeft.getAsBoolean()) {
            // extend (tester used CLUTCH_EXTENDED on dpad_left)
            clutch.extendBoth();
        } else if (dpadRight.getAsBoolean()) {
            // return to initial
            clutch.disengageToInitial();
        }
    }
}
