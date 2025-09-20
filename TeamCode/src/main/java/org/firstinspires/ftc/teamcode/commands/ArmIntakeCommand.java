package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.framework.Command;
import org.firstinspires.ftc.teamcode.subsystems.ArmIntakeSubsystem;

import java.util.function.BooleanSupplier;

public class ArmIntakeCommand extends Command {
    private final ArmIntakeSubsystem intake;
    private final BooleanSupplier dpadUp;
    private final BooleanSupplier dpadDown;

    public ArmIntakeCommand(ArmIntakeSubsystem intake,
                            BooleanSupplier dpadUp,
                            BooleanSupplier dpadDown) {
        this.intake = intake;
        this.dpadUp = dpadUp;
        this.dpadDown = dpadDown;
    }

    @Override
    public void execute() {
        if (dpadUp.getAsBoolean()) {
            intake.dpadUpAction();
        } else if (dpadDown.getAsBoolean()) {
            intake.dpadDownAction();
        } else {
            intake.resetDpadDownFlag();
        }
    }
}
