package me.matrixaura.matrix.command.commands;


import me.matrixaura.matrix.client.CommandManager;
import me.matrixaura.matrix.command.Command;
import me.matrixaura.matrix.common.annotations.CommandInfo;
import me.matrixaura.matrix.utils.ChatUtil;
import me.matrixaura.matrix.utils.SoundUtil;

/**
 * Created by killRED on 2020
 * Updated by B_312 on 01/15/21
 */
@CommandInfo(command = "prefix", description = "Set command prefix.")
public class Prefix extends Command {

    @Override
    public void onCall(String s, String[] args) {
        if (args.length <= 0) {
            ChatUtil.sendNoSpamErrorMessage("Please specify a new prefix!");
            return;
        }
        if (args[0] != null) {
            CommandManager.cmdPrefix = args[0];
            ChatUtil.sendNoSpamMessage("Prefix set to " + ChatUtil.SECTIONSIGN + "b" + args[0] + "!");
            SoundUtil.playButtonClick();
        }
    }

    @Override
    public String getSyntax() {
        return "prefix <char>";
    }

}
