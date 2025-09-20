package org.firstinspires.ftc.teamcode.framework;

public abstract class Command {
    public abstract void execute();
    public boolean isFinished() { return false; }
    public void end() {}
}
