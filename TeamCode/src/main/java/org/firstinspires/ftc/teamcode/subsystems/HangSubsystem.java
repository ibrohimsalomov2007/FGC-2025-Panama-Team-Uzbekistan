package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class HangSubsystem {
    private final DcMotor hang1, hang2, hang3;

    public HangSubsystem(HardwareMap hw) {
        hang1 = hw.get(DcMotor.class, "hang_1");
        hang2 = hw.get(DcMotor.class, "hang_2");
        hang3 = hw.get(DcMotor.class, "hang_3");

        hang1.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void move(int direction) {
        hang1.setPower(direction);
        hang2.setPower(direction);
        hang3.setPower(direction);
    }

    public void stop() {
        move(0);
    }
}
