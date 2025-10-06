package org.firstinspires.ftc.teamcode.framework;

import java.util.ArrayList;
import java.util.List;

public class CommandScheduler {
    private static CommandScheduler instance;
    private final List<Command> active = new ArrayList<>();

    public static CommandScheduler getInstance() {
        if (instance == null) instance = new CommandScheduler();
        return instance;
    }

    public void schedule(Command c) {
        if (c != null) active.add(c);
    }

    public void run() {
        List<Command> finished = new ArrayList<>();
        for (Command c : new ArrayList<>(active)) {
            c.execute();
            if (c.isFinished()) {
                c.end();
                finished.add(c);
            }
        }
        active.removeAll(finished);
    }

    public void cancelAll() {
        for (Command c : active) c.end();
        active.clear();
    }
}
