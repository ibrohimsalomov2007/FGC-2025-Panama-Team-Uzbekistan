package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.framework.Command;
import org.firstinspires.ftc.teamcode.subsystems.DrumIntakeSubsystem;

import java.util.function.BooleanSupplier;

public class DrumIntakeCommand extends Command {
    private final DrumIntakeSubsystem drum;
    private final BooleanSupplier dpadUp;
    private final BooleanSupplier dpadDown;

    public DrumIntakeCommand(DrumIntakeSubsystem drum,
                             BooleanSupplier dpadUp,
                             BooleanSupplier dpadDown) {
        this.drum = drum;
        this.dpadUp = dpadUp;
        this.dpadDown = dpadDown;
    }

    @Override
    public void execute() {
        if (dpadUp.getAsBoolean()) {
            drum.setPower(1);
        } else if (dpadDown.getAsBoolean()) {
            drum.setPower(-1);
        } else {
            drum.setPower(0);
        }
    }
}
