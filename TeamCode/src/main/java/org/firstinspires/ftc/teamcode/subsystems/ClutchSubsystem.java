package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.framework.Subsystem;

public class ClutchSubsystem implements Subsystem {
    private final Servo clutch1;
    private final Servo clutch2;

    private static final double CLUTCH_INITIAL_1 = 1.0;
    private static final double CLUTCH_INITIAL_2 = 1.0;
    private static final double CLUTCH_EXTENDED_1 = 0.0;
    private static final double CLUTCH_EXTENDED_2 = 0.0;
    private static final double CLUTCH_ENGAGED_1 = 0.1;
    private static final double CLUTCH_ENGAGED_2 = 0.9;

    public ClutchSubsystem(HardwareMap hw) {
        clutch1 = hw.get(Servo.class, "clutch_1");
        clutch2 = hw.get(Servo.class, "clutch_2");
        clutch1.setDirection(Servo.Direction.REVERSE);
        clutch1.setPosition(CLUTCH_INITIAL_1);
        clutch2.setPosition(CLUTCH_INITIAL_2);
    }

    public void engage() {
        clutch1.setPosition(CLUTCH_ENGAGED_1);
        clutch2.setPosition(CLUTCH_ENGAGED_2);
    }

    public void disengageToInitial() {
        clutch1.setPosition(CLUTCH_INITIAL_1);
        clutch2.setPosition(CLUTCH_INITIAL_2);
    }

    public void extendBoth() {
        clutch1.setPosition(CLUTCH_EXTENDED_1);
        clutch2.setPosition(CLUTCH_EXTENDED_2);
    }

    @Override public void periodic() {}
}
