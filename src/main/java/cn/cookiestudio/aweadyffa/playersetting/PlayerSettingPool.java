package cn.cookiestudio.aweadyffa.playersetting;

import cn.cookiestudio.aweadyffa.PluginMain;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;
import lombok.Getter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerSettingPool {

    private Config config;
    private Map<String, PlayerSettingMap> settings = new HashMap<>();

    public PlayerSettingPool(){
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
                    cache(name);
                }
            }
        }, PluginMain.getInstance());
        for (Player player : Server.getInstance().getOnlinePlayers().values()){//reload fixed
            cache(player.getName());
        }
    }

    public void init() throws Exception {
        Path p = Paths.get(PluginMain.getInstance().getDataFolder().toString(),"playerSettings.json");
        if (!Files.exists(p))
            Files.createFile(p);
        this.config = new cn.nukkit.utils.Config(p.toFile(),Config.JSON);
    }

    public PlayerSettingMap cache(String name){
        return cache(name,true);
    }

    public PlayerSettingMap cache(String name, boolean createIfMessing){
        String key = name;
        if (!existInFile(name)){
            PlayerSettingMap entry = PlayerSettingMap.builder().build();
            settings.put(name,entry);
            return entry;
        }
        PlayerSettingMap e = PlayerSettingMap.builder()
                .showAttackParticle(config.getBoolean(key + ".showAttackParticle"))
                .randomTp(config.getBoolean(key + ".randomTp"))
                .particleType(config.getString(key + ".particleType"))
                .build();
        settings.put(name,e);
        return e;
    }

    public void write(String name, PlayerSettingMap entry){
        config.set(name,PluginMain.getInstance().getGSON().toJson(entry));
        config.save();
    }

    public void writeAll(){
        for (Map.Entry<String, PlayerSettingMap> e : getSettings().entrySet())
            write(e.getKey(),e.getValue());
    }

    public boolean existInFile(String name){
        return config.exists(name);
    }

    public void close(){
        writeAll();
    }
}
