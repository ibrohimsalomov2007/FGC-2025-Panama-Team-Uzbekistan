package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class ArmIntakeSubsystem {
    private final Servo door1;
    private final Servo door2;
    private final CRServo take1;
    private final CRServo take2;

    // state variables (moved from tester loop)
    private boolean doorsOpen = false;
    private boolean dpadDownPressedLast = false;

    public ArmIntakeSubsystem(HardwareMap hardwareMap) {
        door1 = hardwareMap.get(Servo.class, "door_1");
        door2 = hardwareMap.get(Servo.class, "door_2");
        take1 = hardwareMap.get(CRServo.class, "take_1");
        take2 = hardwareMap.get(CRServo.class, "take_2");

        door2.setDirection(Servo.Direction.REVERSE);
        take1.setDirection(CRServo.Direction.REVERSE);
    }

    public void dpadUpAction() {
        take1.setPower(1);
        take2.setPower(1);
        door1.setPosition(2);
        door2.setPosition(2);
    }

    public void dpadDownAction() {
        if (!dpadDownPressedLast) {
            if (!doorsOpen) {
                take1.setPower(-1);
                take2.setPower(-1);
                door1.setPosition(2);
                door2.setPosition(2);
                doorsOpen = true;
            } else {
                take1.setPower(0);
                take2.setPower(0);
                door1.setPosition(0);
                door2.setPosition(0);
                doorsOpen = false;
            }
        }
        dpadDownPressedLast = true;
    }

    public void resetDpadDownFlag() {
        dpadDownPressedLast = false;
    }
}
