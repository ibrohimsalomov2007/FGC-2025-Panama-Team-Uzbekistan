package org.firstinspires.ftc.teamcode.Main;

import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.framework.CommandScheduler;
import org.firstinspires.ftc.teamcode.commands.*;
import org.firstinspires.ftc.teamcode.subsystems.*;

@TeleOp(name = "heartless", group = "TeleOp")
public class heartless extends LinearOpMode {

    private DriveSubsystem drive;
    private HangSubsystem hang;
    private ArmIntakeSubsystem armIntake;
    private DrumIntakeSubsystem drumIntake;
    private ClutchSubsystem clutch;
    private PushSubsystem push;

    private DriveCommand driveCmd;
    private HangCommand hangCmd;
    private ArmIntakeCommand armIntakeCmd;
    private DrumIntakeCommand drumIntakeCmd;
    private ClutchCommand clutchCmd;
    private PushCommand pushCmd;
    private Vision vision;
    private GamepadEx gamepadEx1;
    private GamepadEx gamepadEx2;

    @Override
    public void runOpMode() {
        // init subsystems
        drive = new DriveSubsystem(hardwareMap);
        hang = new HangSubsystem(hardwareMap);
        armIntake = new ArmIntakeSubsystem(hardwareMap);
        drumIntake = new DrumIntakeSubsystem(hardwareMap);
        clutch = new ClutchSubsystem(hardwareMap);
        push = new PushSubsystem(hardwareMap);
        vision = new Vision(hardwareMap);
        gamepadEx1 = new GamepadEx(gamepad1);
        gamepadEx2 = new GamepadEx(gamepad2);

        // init commands
        driveCmd = new DriveCommand(drive,
                () -> gamepad1.left_stick_y,
                () -> gamepad1.right_stick_x,
                () -> gamepad1.right_bumper);

        hangCmd = new HangCommand(hang,
                () -> gamepad1.right_trigger,
                () -> gamepad1.left_trigger);

        armIntakeCmd = new ArmIntakeCommand(armIntake,
                () -> gamepad2.dpad_up,
                () -> gamepad2.dpad_down,
                () -> gamepad2.x,
                () -> gamepad2.b);

        drumIntakeCmd = new DrumIntakeCommand(
                drumIntake,
                () -> gamepad2.right_trigger,
                () -> gamepad2.left_trigger);

        clutchCmd = new ClutchCommand(clutch,
                () -> gamepad2.dpad_left,
                () -> gamepad2.dpad_right);

        pushCmd = new PushCommand(push,
                () -> gamepad2.cross,
                () -> gamepad2.triangle);

        // ✅ Create DetectTagCommand
        DetectTagCommand detectTagCmd = new DetectTagCommand(vision, telemetry, gamepad1);

        waitForStart();

        CommandScheduler scheduler = CommandScheduler.getInstance();
        boolean detectRunning = false;

        while (opModeIsActive()) {
            // execute subsystem commands
            driveCmd.execute();
            hangCmd.execute();
            armIntakeCmd.execute();
            drumIntakeCmd.execute();
            clutchCmd.execute();
            pushCmd.execute();

            // ✅ Manual handling of DetectTagCommand with X button
            if (gamepad1.x) {
                if (!detectRunning) {
                    detectTagCmd.initialize();
                    detectRunning = true;
                }
                detectTagCmd.execute();

                if (detectTagCmd.isFinished()) {
                    detectTagCmd.end(false);
                    detectRunning = false;
                }
            } else if (detectRunning) {
                detectTagCmd.end(true); // stop if X released before finish
                detectRunning = false;
            }

            // periodic work
            armIntake.periodic();
            push.periodic();

            // telemetry
            telemetry.addData("Door pos", armIntake.getDoorPosition());
            telemetry.addData("Door target", armIntake.getTargetPosition());
            telemetry.update();

            idle(); // important for LinearOpMode
        }

        // cleanup
        scheduler.cancelAll();
    }
}
