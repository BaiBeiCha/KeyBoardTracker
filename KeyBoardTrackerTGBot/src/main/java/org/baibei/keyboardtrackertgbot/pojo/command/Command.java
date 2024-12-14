package org.baibei.keyboardtrackertgbot.pojo.command;

import java.util.Arrays;

public class Command {

    private String command;
    private String[] arguments_before = new String[0];
    private String[] arguments_after = new String[0];
    private String[] arguments = new String[0];

    public Command(String command, String[] arguments_before, String[] arguments_after) {
        this.command = command;
        this.arguments_before = arguments_before;
        this.arguments_after = arguments_after;
    }

    public Command(String command) {
        this.command = command;
    }

    public Command() {}

    public Command setArguments() {
        this.arguments = new String[arguments_before.length + arguments_after.length];
        System.arraycopy(arguments_before, 0, this.arguments, 0, arguments_before.length);
        System.arraycopy(arguments_after, 0, this.arguments, arguments_before.length, arguments_after.length);
        return this;
    }

    public String[] getFirstTwoArguments() {
        return new String[] {arguments[0], arguments[1]};
    }

    public String[] getOtherArguments() {
        String[] other_args = new String[arguments.length - 2];
        System.arraycopy(arguments, 2, other_args, 0, other_args.length);
        return other_args;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public String[] getArguments() {
        return arguments;
    }

    public void setArguments(String[] arguments) {
        this.arguments = arguments;
    }

    public String[] getArguments_before() {
        return arguments_before;
    }

    public void setArguments_before(String[] arguments_before) {
        this.arguments_before = arguments_before;
    }

    public String[] getArguments_after() {
        return arguments_after;
    }

    public void setArguments_after(String[] arguments_after) {
        this.arguments_after = arguments_after;
    }

    public String toString() {
        return "Command: " + command + " " + Arrays.toString(arguments);
    }
}
