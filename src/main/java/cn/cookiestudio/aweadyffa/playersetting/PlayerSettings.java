package cn.cookiestudio.aweadyffa.playersetting;

import cn.cookiestudio.aweadyffa.PluginMain;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;
import lombok.Getter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerSettings {

    private Config config;
    private Map<String,PlayerSettingEntry> settings = new HashMap<>();

    public PlayerSettings(){
        try {
            init();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Server.getInstance().getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void onPlayerJoin(PlayerJoinEvent event){
                String name = event.getPlayer().getName();
                if (!settings.containsKey(name)){
                    if (!existInFile(name)){
                        PlayerSettingEntry entry = PlayerSettingEntry.builder().build();
                        settings.put(name,entry);
                    }else{
                        cache(name);
                    }
                }
            }
        }, PluginMain.getInstance());
    }

    public void init() throws Exception {
        Path p = PluginMain.getInstance().getDataFolder().toPath().resolve("playerSettings.json");
        if (!Files.exists(p))
            Files.createFile(p);
        this.config = new cn.nukkit.utils.Config(p.toFile(),Config.JSON);
    }

    public PlayerSettingEntry cache(String name){
        String key = name;
        PlayerSettingEntry e = PlayerSettingEntry.builder()
                .showAttackParticle(config.getBoolean(key + ".showAttackParticle"))
                .randomTp(config.getBoolean(key + ".randomTp")).build();
        settings.put(name,e);
        return e;
    }

    public void write(String name,PlayerSettingEntry entry){
        config.set(name,PluginMain.getInstance().getGSON().toJson(entry));
        config.save();
    }

    public void writeAll(){
        for (Map.Entry<String,PlayerSettingEntry> e : getSettings().entrySet())
            write(e.getKey(),e.getValue());
    }

    public boolean existInFile(String name){
        return config.exists(name);
    }

    public void close(){
        writeAll();
    }
}
