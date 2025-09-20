package org.firstinspires.ftc.teamcode.Main;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.subsystems.DriveSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.HangSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.ArmIntakeSubsystem;
import org.firstinspires.ftc.teamcode.subsystems.DrumIntakeSubsystem;

import org.firstinspires.ftc.teamcode.commands.DriveCommand;
import org.firstinspires.ftc.teamcode.commands.HangCommand;
import org.firstinspires.ftc.teamcode.commands.ArmIntakeCommand;
import org.firstinspires.ftc.teamcode.commands.DrumIntakeCommand;

@TeleOp(name = "heartless", group = "TeleOp")
public class heartless extends LinearOpMode {

    private DriveSubsystem driveSubsystem;
    private HangSubsystem hangSubsystem;
    private ArmIntakeSubsystem armIntakeSubsystem;
    private DrumIntakeSubsystem drumIntakeSubsystem;

    private DriveCommand driveCommand;
    private HangCommand hangCommand;
    private ArmIntakeCommand armIntakeCommand;
    private DrumIntakeCommand drumIntakeCommand;

    @Override
    public void runOpMode() throws InterruptedException {
        // Init subsystems
        driveSubsystem = new DriveSubsystem(hardwareMap);
        hangSubsystem = new HangSubsystem(hardwareMap);
        armIntakeSubsystem = new ArmIntakeSubsystem(hardwareMap);
        drumIntakeSubsystem = new DrumIntakeSubsystem(hardwareMap);

        // Init commands
        driveCommand = new DriveCommand(
                driveSubsystem,
                () -> -gamepad1.left_stick_y,   // forward/backward
                () -> gamepad1.left_stick_x,  // turn
                () -> gamepad1.right_bumper);

        hangCommand = new HangCommand(
                hangSubsystem,
                () -> gamepad1.dpad_up,
                () -> gamepad1.dpad_down
        );

        armIntakeCommand = new ArmIntakeCommand(
                armIntakeSubsystem,
                () -> gamepad2.dpad_up,
                () -> gamepad2.dpad_down
        );

        drumIntakeCommand = new DrumIntakeCommand(
                drumIntakeSubsystem,
                () -> gamepad2.right_bumper,
                () -> gamepad2.left_bumper
        );

        waitForStart();

        while (opModeIsActive()) {
            // Run commands each loop
            driveCommand.execute();
            hangCommand.execute();
            armIntakeCommand.execute();
            drumIntakeCommand.execute();
        }
    }
}
