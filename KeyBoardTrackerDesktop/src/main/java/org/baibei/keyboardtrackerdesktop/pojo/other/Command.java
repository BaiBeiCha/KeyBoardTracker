package org.baibei.keyboardtrackerdesktop.pojo.other;

import java.util.Arrays;

public class Command {

    private String command;
    private String[] params;

    public Command() {}

    public Command(String command, String[] params) {
        this.command = command;
        this.params = params;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getParams() {
        return params;
    }

    public void setParams(String[] params) {
        this.params = params;
    }

    public static Command handle(String input) {
        Command cmd = new Command();
        String[] args = input.split(" ");
        cmd.setCommand(args[0]);
        String[] params = new String[args.length - 1];
        for (int i = 1; i < args.length; i++) {
            if (args[i] != null) {
                params[i - 1] = args[i];
            }
        }

        cmd.setParams(params);
        return cmd;
    }

    public String toString() {
        return "Command: " + command + " " + Arrays.toString(params);
    }
}
