package me.matrixaura.matrix.command.commands;

import me.matrixaura.matrix.client.ModuleManager;
import me.matrixaura.matrix.command.Command;
import me.matrixaura.matrix.common.annotations.CommandInfo;
import me.matrixaura.matrix.utils.ChatUtil;

import java.util.Objects;

/**
 * Created by killRED on 2020
 * Updated by B_312 on 01/15/21
 */
@CommandInfo(command = "toggle",description = "Toggle selected module or HUD.")
public class Toggle extends Command {

    @Override
    public void onCall(String s, String[] args) {
        try {
            Objects.requireNonNull(ModuleManager.getModuleByName(args[0])).toggle();
        } catch(Exception e) {
            ChatUtil.sendNoSpamErrorMessage(getSyntax());
        }
    }

    @Override
    public String getSyntax() {
        return "toggle <modulename>";
    }

}