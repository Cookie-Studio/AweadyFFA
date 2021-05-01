package cn.cookiestudio.aweadyffa;

import cn.nukkit.Server;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.Listener;
import cn.nukkit.event.player.PlayerJoinEvent;
import cn.nukkit.utils.Config;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

@Getter
public class PlayerSettings {

    private Config config;
    private Map<String,Entry> settings = new HashMap<>();

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
                        Entry entry = Entry.builder().build();
                        settings.put(name,entry);
                    }else{
                        cache(name);
                    }
                }
            }
        },PluginMain.getInstance());
    }

    public void init() throws Exception {
        Path p = PluginMain.getInstance().getDataFolder().toPath().resolve("playerSettings.json");
        if (!Files.exists(p))
            Files.createFile(p);
        this.config = new cn.nukkit.utils.Config(p.toFile(),Config.JSON);
    }

    public Entry cache(String name){
        String key = name;
        Entry e = Entry.builder()
                .showAttackParticle(config.getBoolean(key + ".showAttackParticle"))
                .randomTp(config.getBoolean(key + ".randomTp")).build();
        settings.put(name,e);
        return e;
    }

    public void write(String name,Entry entry){
        config.set(name,PluginMain.getInstance().getGSON().toJson(entry));
        config.save();
    }

    public void writeAll(){
        for (Map.Entry<String,Entry> e : getSettings().entrySet())
            write(e.getKey(),e.getValue());
    }

    public boolean existInFile(String name){
        return config.exists(name);
    }

    public void close(){
        writeAll();
    }

    @Builder
    @Getter
    @Setter
    public class Entry{
        boolean showAttackParticle;
        boolean randomTp;
    }
}
