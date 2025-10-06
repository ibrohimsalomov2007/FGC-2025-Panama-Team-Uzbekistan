package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.teamcode.framework.Subsystem;

public class DriveSubsystem implements Subsystem {
    private final DcMotor leftFront, leftBack, rightFront, rightBack;

    public DriveSubsystem(HardwareMap hw) {
        leftFront  = hw.dcMotor.get("leftFront");
        leftBack   = hw.dcMotor.get("leftBack");
        rightFront = hw.dcMotor.get("rightFront");
        rightBack  = hw.dcMotor.get("rightBack");

        // match driveRobot_tester: reverse left side
        leftFront.setDirection(DcMotorSimple.Direction.REVERSE);
        leftBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(double drive, double turn, boolean slowMode) {
        double leftPower = drive - turn;
        double rightPower = drive + turn;

        if (slowMode) {
            leftPower *= 0.5;
            rightPower *= 0.5
            ;
        }

        leftFront.setPower(leftPower);
        leftBack.setPower(leftPower);
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);
    }

    @Override public void periodic() {}
}
