package cn.cookiestudio.aweadyffa;

import cn.cookiestudio.aweadyffa.commands.ChangeSettingCommand;
import cn.cookiestudio.aweadyffa.commands.GetWorldName;
import cn.cookiestudio.aweadyffa.commands.KBACCommand;
import cn.cookiestudio.aweadyffa.ffa.FFAArea;
import cn.cookiestudio.aweadyffa.playersetting.PlayerSettingPool;
import cn.nukkit.Server;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.utils.Config;
import com.google.gson.Gson;
import lombok.Getter;
import cn.cookiestudio.aweadyffa.commands.FFACommand;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

@Getter
public class PluginMain extends PluginBase {
    @Getter
    private static PluginMain instance;
    @Getter
    private static Gson GSON = new Gson();
    private Config ffaConfig;
    private HashMap<String, FFAArea> ffaAreas = new HashMap<>();
    private PlayerSettingPool playerSettingPool;

    @Override
    public void onLoad() {
        this.getLogger().info("Plugin load!");
    }

    @Override
    public void onEnable() {
        instance = this;
        Path configPath = Paths.get(PluginMain.getInstance().getDataFolder().toString(), "ffa-old.yml");
        this.saveResource("ffa-old.yml");
        this.ffaConfig = new Config(this.getDataFolder() + "/ffa-old.yml");
        playerSettingPool = new PlayerSettingPool();
        this.initFFAArea();
        this.registerCommands();
//        Server.getInstance().getScheduler().scheduleRepeatingTask(new PluginTask(this){
//            @Override
//            public void onRun(int i) {
//                if (Server.getInstance().getPlayer("daoge cmd") == null)
//                    return;
//                Player daoge = Server.getInstance().getPlayer("daoge cmd");
//                for (Player player : daoge.level.getPlayers().values()){
//                    if (!(player == daoge)&& daoge.distance(player.getPosition()) <= 5)
//                        player.attack(new EntityDamageByEntityEvent(daoge,player, EntityDamageEvent.DamageCause.ENTITY_ATTACK,0.1F));
//                }
//            }
//        }, 1);
        this.getLogger().info("Plugin enable!");
    }

    @Override
    public void onDisable() {
        playerSettingPool.close();
        this.getLogger().info("Plugin disable!");
    }

    public boolean isPositionInFFAArea(Position position){
        boolean tmp = false;
        for (FFAArea ffaArea : this.ffaAreas.values()){
            if (ffaArea.isInArea(position))
                tmp = true;
        }
        return tmp;
    }

    public FFAArea getPositionFFAArea(Position position){
        for (FFAArea ffaArea : this.ffaAreas.values()){
            if (ffaArea.isInArea(position))
                return ffaArea;
        }
        return null;
    }

    private void initFFAArea(){
        for(String name : ffaConfig.getKeys(false)) {
            FFAArea ffaArea = new FFAArea(name);
            this.ffaAreas.put(name,ffaArea);
        }
    }

    private void registerCommands() {
        Server.getInstance().getCommandMap().register("",new FFACommand("ffa"));
        Server.getInstance().getCommandMap().register("",new KBACCommand("kbac"));
        Server.getInstance().getCommandMap().register("",new GetWorldName("getwn"));
        Server.getInstance().getCommandMap().register("",new ChangeSettingCommand("ffaset"));
    }
}
