package org.firstinspires.ftc.teamcode.Main.test;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp
public class drum_Intake_tester extends OpMode {
    private DcMotor suck; //интейк
    @Override
 public void init() {
        suck = hardwareMap.dcMotor.get("suck");
    }
    @Override
    public void loop(){

        if (gamepad2.dpad_up) {
            suck.setPower(1);
        } else if (gamepad2.dpad_down) {
            suck.setPower(-1);
        } else {
            suck.setPower(0);
        }
    }
}


