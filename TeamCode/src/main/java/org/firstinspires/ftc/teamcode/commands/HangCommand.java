package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.HangSubsystem;

import java.util.function.BooleanSupplier;

public class HangCommand {
    private final HangSubsystem hang;
    private final BooleanSupplier up, down;

    public HangCommand(HangSubsystem hang, BooleanSupplier up, BooleanSupplier down) {
        this.hang = hang;
        this.up = up;
        this.down = down;
    }

    public void execute() {
        if (up.getAsBoolean()) hang.move(1);
        else if (down.getAsBoolean()) hang.move(-1);
        else hang.stop();
    }
}
