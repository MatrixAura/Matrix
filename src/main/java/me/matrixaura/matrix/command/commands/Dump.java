package me.matrixaura.matrix.command.commands;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.matrixaura.matrix.client.ConfigManager;
import me.matrixaura.matrix.common.annotations.CommandInfo;
import me.matrixaura.matrix.core.setting.Setting;
import me.matrixaura.matrix.core.setting.settings.*;
import me.matrixaura.matrix.utils.ChatUtil;
import me.matrixaura.matrix.utils.ClassUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

@CommandInfo(command = "dump", description = "Dump some classes.")
public class Dump extends Commands {

    private static final File file;
    private static final Gson gsonPretty = new GsonBuilder().setPrettyPrinting().create();

    static {
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        file = new File(ConfigManager.CONFIG_PATH + "dump/" + formatter.format(date));
    }

    @Override
    public void onCall(String s, String[] args) {
        try {
            if (args.length != 1) ChatUtil.sendNoSpamErrorMessage(getSyntax());
            for (Class clazz : ClassUtil.loadClassByLoader(this.getClass().getClassLoader())) {
                if (clazz.getPackage().getName().startsWith(args[0])) saveClass(clazz.getName(), ClassUtil.readBytes(clazz.getPackage().getName()));
            }
        } catch (Exception e) {
            ChatUtil.sendNoSpamErrorMessage("Package not found.");
        }
    }

    @Override
    public String getSyntax() {
        return "dump <classes package>";
    }

    public void saveClass(String className, byte[] bytes) {
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(className, Arrays.toString(bytes));
            try {
                PrintWriter saveJSon = new PrintWriter(new FileWriter(file));
                saveJSon.println(gsonPretty.toJson(jsonObject));
                saveJSon.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
