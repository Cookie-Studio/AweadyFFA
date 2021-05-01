package cn.cookiestudio.aweadyffa.utils;

import cn.cookiestudio.aweadyffa.PluginMain;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ConfigCopy {
    public static void copy(String fileName){
        Path configPath = Paths.get(PluginMain.getInstance().getDataFolder().toString(),fileName);
        try {
            Files.copy(PluginMain.class.getClassLoader().getResourceAsStream(fileName), configPath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
