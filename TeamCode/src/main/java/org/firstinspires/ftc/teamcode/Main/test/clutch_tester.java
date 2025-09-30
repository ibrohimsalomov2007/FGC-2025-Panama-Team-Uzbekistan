package org.firstinspires.ftc.teamcode.Main.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "clutch tester", group = "test")
public class clutch_tester extends OpMode {

    private Servo clutch_1;
    private Servo clutch_2;

    private boolean dpadLeftPressedLast = false;
    private boolean dpadRightPressedLast = false;

    private final double CLUTCH_INITIAL = 1.0;
    private final double CLUTCH_EXTENDED = 0.0;

    @Override
    public void init() {
        clutch_1 = hardwareMap.get(Servo.class, "clutch_1");
        clutch_2 = hardwareMap.get(Servo.class, "clutch_2");
        clutch_1.setDirection(Servo.Direction.REVERSE);

        clutch_1.setPosition(CLUTCH_INITIAL);
        clutch_2.setPosition(CLUTCH_INITIAL);
    }

    @Override
    public void loop() {

        // --- D-PAD LEFT: Extend clutch ---
        if (gamepad2.dpad_left && !dpadLeftPressedLast) {
            clutch_1.setPosition(CLUTCH_EXTENDED);
            clutch_2.setPosition(CLUTCH_EXTENDED);
            dpadLeftPressedLast = true;
        } else if (!gamepad2.dpad_left) {
            dpadLeftPressedLast = false;
        }

        // --- D-PAD RIGHT: Return clutch to initial ---
        if (gamepad2.dpad_right && !dpadRightPressedLast) {
            clutch_1.setPosition(CLUTCH_INITIAL);
            clutch_2.setPosition(CLUTCH_INITIAL);
            dpadRightPressedLast = true;
        } else if (!gamepad2.dpad_right) {
            dpadRightPressedLast = false;
        }

        telemetry.addData("Clutch Pos", clutch_1.getPosition());
        telemetry.update();
    }
}
