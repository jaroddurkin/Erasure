package app.durkin.erasure.commands;

import org.bukkit.command.CommandSender;

abstract class Command {

    private CommandSender sender;
    private String[] args;

    public Command(CommandSender sender, String[] args) {
        this.sender = sender;
        this.args = args;
    }

    abstract boolean handleCommand();

    public CommandSender getSender() {
        return this.sender;
    }

    public String[] getArguments() {
        return this.args;
    }
}
