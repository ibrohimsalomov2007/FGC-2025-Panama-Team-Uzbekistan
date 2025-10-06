package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.framework.Subsystem;

public class PushSubsystem implements Subsystem {

    private final Servo push1;
    private final Servo push2;
    private final Servo clutch1;
    private final Servo clutch2;

    // Servo positions
    private static final double PUSH_INITIAL = 1.0;
    private static final double PUSH_EXTENDED = 0.0;

    private static final double CLUTCH_INITIAL_1 = 1.0;
    private static final double CLUTCH_INITIAL_2 = 1.0;
    private static final double CLUTCH_ENGAGED_1 = 0.9;
    private static final double CLUTCH_ENGAGED_2 = 0.9;

    // State machine variables
    private boolean crossSequenceActive = false;
    private boolean pushCommanded = false;
    private boolean clutchEngaged = false;
    private boolean triangleSequenceActive = false;

    private final ElapsedTime timer = new ElapsedTime();

    public PushSubsystem(HardwareMap hw) {
        push1 = hw.get(Servo.class, "push_1");
        push2 = hw.get(Servo.class, "push_2");
        clutch1 = hw.get(Servo.class, "clutch_1");
        clutch2 = hw.get(Servo.class, "clutch_2");

        push1.setDirection(Servo.Direction.REVERSE);

        // Initial positions
        push1.setPosition(PUSH_INITIAL);
        push2.setPosition(PUSH_INITIAL);
        clutch1.setPosition(CLUTCH_INITIAL_1);
        clutch2.setPosition(CLUTCH_INITIAL_2);
    }

    // === Cross button starts 3-step sequence ===
    public void startCrossSequence() {
        crossSequenceActive = true;
        pushCommanded = false;
        clutchEngaged = false;
        timer.reset();
    }

    // === Triangle button triggers retract + clutch engage/disengage ===
    public void startTriangleSequence() {
        triangleSequenceActive = true;
        push1.setPosition(PUSH_INITIAL);
        push2.setPosition(PUSH_INITIAL);
        engageClutches();
        timer.reset();
    }

    @Override
    public void periodic() {
        // CROSS sequence
        if (crossSequenceActive) {
            // Step 1: Push to extended (0.0)
            if (!pushCommanded) {
                push1.setPosition(PUSH_EXTENDED);
                push2.setPosition(PUSH_EXTENDED);
                pushCommanded = true;
                timer.reset();
            }

            // Step 2: Engage clutch immediately
            if (pushCommanded && !clutchEngaged) {
                engageClutches();
                clutchEngaged = true;
            }

            // Step 3: After ~1000ms, disengage clutch and end sequence
            if (clutchEngaged && timer.milliseconds() > 1000) {
                disengageClutches();
                crossSequenceActive = false;
            }
        }

        // TRIANGLE sequence (simple push retract + 500ms clutch)
        if (triangleSequenceActive && timer.milliseconds() > 500) {
            disengageClutches();
            triangleSequenceActive = false;
        }
    }

    private void engageClutches() {
        clutch1.setPosition(CLUTCH_ENGAGED_1);
        clutch2.setPosition(CLUTCH_ENGAGED_2);
    }

    private void disengageClutches() {
        clutch1.setPosition(CLUTCH_INITIAL_1);
        clutch2.setPosition(CLUTCH_INITIAL_2);
    }

    public double getPush1Pos() { return push1.getPosition(); }
    public double getPush2Pos() { return push2.getPosition(); }
    public double getClutch1Pos() { return clutch1.getPosition(); }
    public double getClutch2Pos() { return clutch2.getPosition(); }
    public boolean isCrossSequenceActive() { return crossSequenceActive; }
}
