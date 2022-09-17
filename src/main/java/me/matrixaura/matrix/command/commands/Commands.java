package me.matrixaura.matrix.command.commands;


import me.matrixaura.matrix.client.CommandManager;
import me.matrixaura.matrix.command.Command;
import me.matrixaura.matrix.common.annotations.CommandInfo;
import me.matrixaura.matrix.utils.ChatUtil;

/**
 * Created by B_312 on 01/15/21
 */
@CommandInfo(command = "commands", description = "Lists all commands.")
public class Commands extends Command {

    @Override
    public void onCall(String s, String[] args) {
        ChatUtil.printChatMessage("\247b" + "Commands:");
        try {
            for (Command cmd : CommandManager.getInstance().commands) {
                if (cmd == this) {
                    continue;
                }
                ChatUtil.printChatMessage("\247b" + cmd.getSyntax().replace("<", "\2473<\2479").replace(">", "\2473>") + "\2478" + " - " + cmd.getDescription());
            }
        } catch (Exception e) {
            ChatUtil.sendNoSpamErrorMessage(getSyntax());
        }
    }

    @Override
    public String getSyntax() {
        return "commands";
    }

}