package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.HangSubsystem;
import java.util.function.DoubleSupplier;

public class HangCommand {
    private final HangSubsystem hang;
    private final DoubleSupplier upTrigger;
    private final DoubleSupplier downTrigger;

    public HangCommand(HangSubsystem hang, DoubleSupplier upTrigger, DoubleSupplier downTrigger) {
        this.hang = hang;
        this.upTrigger = upTrigger;
        this.downTrigger = downTrigger;
    }

    public void execute() {
        double power = upTrigger.getAsDouble() - downTrigger.getAsDouble();
        hang.move(power);
    }
}
