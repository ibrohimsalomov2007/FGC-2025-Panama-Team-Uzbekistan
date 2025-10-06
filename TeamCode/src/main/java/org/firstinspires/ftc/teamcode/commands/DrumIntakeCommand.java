package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.DrumIntakeSubsystem;
import java.util.function.DoubleSupplier;

public class DrumIntakeCommand {
    private final DrumIntakeSubsystem drum;
    private final DoubleSupplier forwardTrigger;
    private final DoubleSupplier reverseTrigger;

    public DrumIntakeCommand(DrumIntakeSubsystem drum,
                             DoubleSupplier forwardTrigger,
                             DoubleSupplier reverseTrigger) {
        this.drum = drum;
        this.forwardTrigger = forwardTrigger;
        this.reverseTrigger = reverseTrigger;
    }

    public void execute() {
        double power = forwardTrigger.getAsDouble() - reverseTrigger.getAsDouble();
        drum.move(power);
    }
}
