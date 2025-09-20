package org.firstinspires.ftc.teamcode.framework;

import java.util.ArrayList;
import java.util.List;

public class CommandScheduler {
    private static CommandScheduler instance;
    private final List<Command> activeCommands = new ArrayList<>();

    public static CommandScheduler getInstance() {
        if (instance == null) {
            instance = new CommandScheduler();
        }
        return instance;
    }

    public void schedule(Command command) {
        activeCommands.add(command);
    }

    public void run() {
        List<Command> finished = new ArrayList<>();
        for (Command command : activeCommands) {
            command.execute();
            if (command.isFinished()) {
                command.end();
                finished.add(command);
            }
        }
        activeCommands.removeAll(finished);
    }
}

