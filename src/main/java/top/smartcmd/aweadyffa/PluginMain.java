package top.smartcmd.aweadyffa;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.event.entity.EntityDamageByBlockEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.form.window.FormWindowSimple;
import cn.nukkit.level.Position;
import cn.nukkit.plugin.PluginBase;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import top.smartcmd.aweadyffa.commands.FFACommand;
import top.smartcmd.aweadyffa.commands.GetWorldName;
import top.smartcmd.aweadyffa.commands.KBACCommand;
import top.smartcmd.aweadyffa.utils.ConfigCopy;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;

public class PluginMain extends PluginBase {
    private static PluginMain pluginMain;
    private Config ffaConfig;
    private HashMap<String, FFAArea> ffaAreas = new HashMap<>();
    private int ffaFormId = 28758724;
    private int kbacFormId = 96785457;

    @Override
    public void onLoad() {
        this.getLogger().info("Plugin load!");
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Plugin enable!");
        pluginMain = this;
        Path configPath = Paths.get(PluginMain.getInstance().getDataFolder().toString(),"ffa.yml");
        if (!Files.exists(configPath)) {
            this.getDataFolder().mkdir();
            ConfigCopy.copy("ffa.yml");
        }
        this.ffaConfig = new Config(this.getDataFolder() + "/ffa.yml");
        this.initFFAArea();
        this.registerCommand();
        this.registerListener();
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
    }

    @Override
    public void onDisable() {
        this.getLogger().info("Plugin disable!");
    }

    public static PluginMain getInstance(){
        return pluginMain;
    }

    public Config getFFAConfig() {
        return ffaConfig;
    }

    public int getKbacFormId() {
        return kbacFormId;
    }

    public HashMap<String, FFAArea> getFfaAreas() {
        return ffaAreas;
    }

    public int getFFAFormId() {
        return ffaFormId;
    }

    public boolean isPositionInFFAArea(Position position){
        boolean tmp = false;
        for (FFAArea ffaArea : this.ffaAreas.values()){
            if (ffaArea.isInArea(position))
                tmp = true;
        }
        return tmp;
    }

    private void initFFAArea(){
        for(String name : ffaConfig.getKeys(false)) {
            FFAArea ffaArea = new FFAArea(name);
            this.ffaAreas.put(name,ffaArea);
            FFACommand.getJoinFFAAreaForm().addButton(new FElementButton(name,ffaArea));
        }
    }

    private void registerCommand() {
        Server.getInstance().getCommandMap().register("",new FFACommand("ffa"));
        Server.getInstance().getCommandMap().register("",new KBACCommand("kbac"));
        Server.getInstance().getCommandMap().register("",new GetWorldName("getwn"));
    }

    private void registerListener(){
        Server.getInstance().getPluginManager().registerEvents(new FormListener(),this);
    }
}
