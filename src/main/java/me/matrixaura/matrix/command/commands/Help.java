package me.matrixaura.matrix.command.commands;

import me.matrixaura.matrix.Matrix;
import me.matrixaura.matrix.client.CommandManager;
import me.matrixaura.matrix.client.ModuleManager;
import me.matrixaura.matrix.command.Command;
import me.matrixaura.matrix.common.annotations.CommandInfo;
import me.matrixaura.matrix.module.modules.client.ClickGUI;
import me.matrixaura.matrix.utils.ChatUtil;
import org.lwjgl.input.Keyboard;

/**
 * Created by B_312 on 01/15/21
 */
@CommandInfo(command = "help", description = "Get helps.")
public class Help extends Command {

    @Override
    public void onCall(String s, String[] args) {
        ChatUtil.printChatMessage("\247b" + Matrix.MOD_NAME + " " + "\247a" + Matrix.MOD_VERSION);
        ChatUtil.printChatMessage("\247c" + "Made by: " + Matrix.AUTHOR);
        ChatUtil.printChatMessage("\247c" + "Github: " + Matrix.GITHUB);
        ChatUtil.printChatMessage("\2473" + "Press " + "\247c" + Keyboard.getKeyName(ModuleManager.getModule(ClickGUI.class).bindSetting.getValue().getKeyCode()) + "\2473" + " to open ClickGUI");
        ChatUtil.printChatMessage("\2473" + "Use command: " + "\2479" + CommandManager.cmdPrefix + "prefix <target prefix>" + "\2473" + " to set command prefix");
        ChatUtil.printChatMessage("\2473" + "List all available commands: " + "\2479" + CommandManager.cmdPrefix + "commands");
    }

    @Override
    public String getSyntax() {
        return "help";
    }

}