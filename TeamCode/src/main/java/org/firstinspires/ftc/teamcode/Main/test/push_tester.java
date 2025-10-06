package org.firstinspires.ftc.teamcode.Main.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "push tester", group = "test")
public class push_tester extends OpMode {

    private Servo push_1;
    private Servo push_2;
    private Servo clutch_1;
    private Servo clutch_2;

    private boolean crossPressedLast = false;
    private boolean trianglePressedLast = false;

    private final double PUSH_INITIAL = 1.0;
    private final double PUSH_EXTENDED = 0.0;

    private final double CLUTCH_INITIAL_1 = 0.0;
    private final double CLUTCH_INITIAL_2 = 1.0;
    private final double CLUTCH_ENGAGED_1 = 0.1;
    private final double CLUTCH_ENGAGED_2 = 0.9;

    // State tracking for dpad_left sequence
    private boolean crossSequenceActive = false;
    private boolean pushCommanded = false;
    private boolean clutchEngaged = false;
    private ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        push_1 = hardwareMap.get(Servo.class, "push_1");
        push_2 = hardwareMap.get(Servo.class, "push_2");
        clutch_1 = hardwareMap.get(Servo.class, "clutch_1");
        clutch_2 = hardwareMap.get(Servo.class, "clutch_2");

        push_1.setDirection(Servo.Direction.REVERSE);

        push_1.setPosition(PUSH_INITIAL);
        push_2.setPosition(PUSH_INITIAL);
        clutch_1.setPosition(CLUTCH_INITIAL_1);
        clutch_2.setPosition(CLUTCH_INITIAL_2);
    }

    @Override
    public void loop() {

        // === D-PAD LEFT sequence ===
        if (gamepad2.cross && !crossPressedLast) {
            crossSequenceActive = true;
            pushCommanded = false;
            clutchEngaged = false;
            timer.reset();
            crossPressedLast = true;
        } else if (!gamepad2.cross) {
            crossPressedLast = false;
        }

        if (crossSequenceActive) {
            // Step 1: command push to go to 0
            if (!pushCommanded) {
                push_1.setPosition(PUSH_EXTENDED);
                push_2.setPosition(PUSH_EXTENDED);
                pushCommanded = true;
                timer.reset();
            }

            // Step 2: immediately engage clutch
            if (pushCommanded && !clutchEngaged) {
                clutch_1.setPosition(CLUTCH_ENGAGED_1);
                clutch_2.setPosition(CLUTCH_ENGAGED_2);
                clutchEngaged = true;
            }

            // Step 3: wait ~1s for push to reach position 0, then reset clutches
            if (clutchEngaged && timer.milliseconds() > 1000) { // adjust if your servos are slower
                clutch_1.setPosition(CLUTCH_INITIAL_1);
                clutch_2.setPosition(CLUTCH_INITIAL_2);
                crossSequenceActive = false;
            }
        }

        // === D-PAD RIGHT (simple version as before) ===
        if (gamepad2.triangle && !trianglePressedLast) {
            push_1.setPosition(PUSH_INITIAL);
            push_2.setPosition(PUSH_INITIAL);
            engageClutches();
            sleep(500);
            disengageClutches();
            trianglePressedLast = true;
        } else if (!gamepad2.triangle) {
            trianglePressedLast = false;
        }

        telemetry.addData("Push 1 Pos", push_1.getPosition());
        telemetry.addData("Push 2 Pos", push_2.getPosition());
        telemetry.addData("Clutch 1 Pos", clutch_1.getPosition());
        telemetry.addData("Clutch 2 Pos", clutch_2.getPosition());
        telemetry.addData("Left Seq Active", crossSequenceActive);
        telemetry.update();
    }

    private void engageClutches() {
        clutch_1.setPosition(CLUTCH_ENGAGED_1);
        clutch_2.setPosition(CLUTCH_ENGAGED_2);
    }

    private void disengageClutches() {
        clutch_1.setPosition(CLUTCH_INITIAL_1);
        clutch_2.setPosition(CLUTCH_INITIAL_2);
    }

    private void sleep(long ms) {
        long start = System.currentTimeMillis();
        while (System.currentTimeMillis() - start < ms && opModeIsActive()) {
            // wait
        }
    }

    private boolean opModeIsActive() {
        return true;
    }
}
