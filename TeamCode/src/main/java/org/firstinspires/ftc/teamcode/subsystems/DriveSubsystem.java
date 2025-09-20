package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DriveSubsystem {
    private final DcMotor leftFront, leftBack, rightFront, rightBack;

    public DriveSubsystem(HardwareMap hw) {
        leftFront = hw.get(DcMotor.class, "leftFront");
        leftBack = hw.get(DcMotor.class, "leftBack");
        rightFront = hw.get(DcMotor.class, "rightFront");
        rightBack = hw.get(DcMotor.class, "rightBack");

        rightFront.setDirection(DcMotorSimple.Direction.REVERSE);
        rightBack.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void drive(double drive, double turn, boolean slowMode) {
        double leftPower = drive - turn;
        double rightPower = drive + turn;

        if (slowMode) {
            leftPower *= 0.5;
            rightPower *= 0.5;
        }

        leftFront.setPower(leftPower);
        leftBack.setPower(leftPower);
        rightFront.setPower(rightPower);
        rightBack.setPower(rightPower);
    }
}
