package org.firstinspires.ftc.teamcode.commands;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

public class DriveCommand {
    private final DriveSubsystem drive;
    private final DoubleSupplier driveAxis;
    private final DoubleSupplier turnAxis;
    private final BooleanSupplier slow;

    public DriveCommand(DriveSubsystem drive,
                        DoubleSupplier driveAxis,
                        DoubleSupplier turnAxis,
                        BooleanSupplier slow) {
        this.drive = drive;
        this.driveAxis = driveAxis;
        this.turnAxis = turnAxis;
        this.slow = slow;
    }

    public void execute() {
        drive.drive(driveAxis.getAsDouble(), turnAxis.getAsDouble(), slow.getAsBoolean());
    }
}
