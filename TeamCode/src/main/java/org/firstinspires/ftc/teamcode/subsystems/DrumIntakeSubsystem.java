package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class DrumIntakeSubsystem {
    private final DcMotor suck;

    public DrumIntakeSubsystem(HardwareMap hardwareMap) {
        suck = hardwareMap.dcMotor.get("suck");
    }

    public void setPower(double power) {
        suck.setPower(power);
    }
}
