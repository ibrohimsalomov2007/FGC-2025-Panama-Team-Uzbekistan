package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveCommand {
    private final DriveSubsystem drive;
    private final DoubleSupplier driveAxis;     // forward/back
    private final DoubleSupplier turnAxis;      // turn
    private final BooleanSupplier slowMode;     // right bumper

    public DriveCommand(DriveSubsystem drive,
                        DoubleSupplier driveAxis,
                        DoubleSupplier turnAxis,
                        BooleanSupplier slowMode) {
        this.drive = drive;
        this.driveAxis = driveAxis;
        this.turnAxis = turnAxis;
        this.slowMode = slowMode;
    }

    public void execute() {
        drive.drive(
                driveAxis.getAsDouble(),
                turnAxis.getAsDouble(),
                slowMode.getAsBoolean()
        );
    }
}
