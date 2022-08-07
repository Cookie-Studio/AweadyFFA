package cn.cookiestudio.aweadyffa.ffa;

import cn.cookiestudio.aweadyffa.PluginMain;
import cn.cookiestudio.aweadyffa.playersetting.PlayerSettingMap;
import cn.cookiestudio.aweadyffa.utils.PlayerHealthUtil;
import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Attribute;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityDamageEvent;
import cn.nukkit.event.entity.EntityMotionEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.math.Vector3f;
import cn.nukkit.network.protocol.SpawnParticleEffectPacket;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import lombok.Getter;
import lombok.Setter;
import me.onebone.economyapi.EconomyAPI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

@Getter
@Setter
public class FFAArea {
    private ArrayList<Item> ffaItems = new ArrayList<>();
    private ArrayList<Item> ffaArmors = new ArrayList<>();
    private HashSet<Player> players = new HashSet<>();
    private Position cornerPosition1;
    private Position cornerPosition2;
    private Position spawnPosition;
    private boolean allowAttackDamage;
    private boolean bloodBackWhenKill;
    private boolean allowBreakBlock;
    private boolean allowPlaceBlock;
    private boolean resetEquipmentWhenKill;
    private String areaName;
    private String joinTitle;
    private String joinSubTitle;
    private String joinMessage;
    private String joinActionbar;
    private String exitTitle;
    private String exitSubTitle;
    private String exitMessage;
    private String exitActionbar;
    private String killTitle;
    private String killSubTitle;
    private String killMessage;
    private String killActionbar;
    private String killBroadCast;
    private FFAAreaKBInfo ffaAreaKBInfo = new FFAAreaKBInfo();
    private int playerMaxHealth;
    private int clearItemsTime;
    private int moneyGiveCountWhenKill;
    private int moneyRemoveCountWhenKill;

    public FFAArea(String name){
        this.areaName = name;
        Config config = PluginMain.getInstance().getFfaConfig();
        ArrayList posInfo = (ArrayList) config.get(name + ".position");
        ArrayList spawn = (ArrayList) config.get(name + ".spawn");
        spawnPosition = new Position((int)spawn.get(0),(int)spawn.get(1),(int)spawn.get(2),Server.getInstance().getLevelByName((String)spawn.get(3)));
        cornerPosition1 = new Position((int) posInfo.get(0),(int)posInfo.get(1),(int)posInfo.get(2), spawnPosition.level);
        cornerPosition2 = new Position((int)posInfo.get(3),(int)posInfo.get(4),(int)posInfo.get(5), spawnPosition.level);
        this.allowAttackDamage = (boolean) config.get(name +".allow-attack-damage");
        this.joinTitle = (String) config.get(name +".join-title");
        this.joinSubTitle = (String) config.get(name +".join-subtitle");
        this.joinMessage = (String) config.get(name +".join-message");
        this.joinActionbar = (String) config.get(name +".join-actionbar");
        this.exitTitle = (String) config.get(name +".exit-title");
        this.exitSubTitle = (String) config.get(name +".exit-subtitle");
        this.exitMessage = (String) config.get(name +".exit-message");
        this.exitActionbar = (String) config.get(name +".exit-actionbar");
        this.killTitle = (String) config.get(name +".kill-title");
        this.killSubTitle = (String) config.get(name +".kill-subtitle");
        this.killMessage = (String) config.get(name +".kill-message");
        this.killActionbar = (String) config.get(name +".kill-actionbar");
        this.ffaAreaKBInfo.setAc((Integer) config.get(name + ".ac"));
        this.ffaAreaKBInfo.setBaseKB((double) config.get(name + ".basekb"));
        this.ffaAreaKBInfo.setXzkb((double) config.get(name + ".xzkb"));
        this.ffaAreaKBInfo.setYkb((double) config.get(name + ".ykb"));
        this.ffaAreaKBInfo.setXzkb_g((double) config.get(name + ".xzkb-g"));
        this.ffaAreaKBInfo.setYkb_g((double) config.get(name + ".ykb-g"));
        this.bloodBackWhenKill = (boolean) config.get(name + ".blood-back-when-kill");
        this.resetEquipmentWhenKill = (boolean) config.get(name + ".reset-equipment-when-kill");
        this.allowBreakBlock = (boolean) config.get(name + ".allow-break-block");
        this.allowPlaceBlock = (boolean) config.get(name + ".allow-place-block");
        this.playerMaxHealth = (int) config.get(name + ".player-max-health");
        this.killBroadCast = (String) config.get(name + ".kill-broad-cast");
        this.clearItemsTime = config.getInt(name + ".clear-items-time") * 4;
        if (this.clearItemsTime == 0) this.clearItemsTime = 1;
        this.moneyGiveCountWhenKill = (int) config.get(name + ".money-give-count-when-kill");
        this.moneyRemoveCountWhenKill = (int) config.get(name + ".money-remove-count-when-dead");

        if (config.get(name + ".equipment") != null)
            for (HashMap equipment : (ArrayList<HashMap>)config.get(name + ".equipment")){
            int id = (Integer) equipment.get("id");
            Item item = Item.get(id, (int) equipment.get("meta"));
            item.setCount((Integer) equipment.get("count"));
            item.setCustomName((String) equipment.get("name"));
            item.setLore((String) equipment.get("lore"));
            if (equipment.get("enchantment") != null)
                for (HashMap enchantment : ((ArrayList<HashMap>)equipment.get("enchantment")))
                    item.addEnchantment(Enchantment.get((Integer) enchantment.get("id")).setLevel((Integer) enchantment.get("level")));
            this.ffaItems.add(item);
        }

        if (config.get(name + ".armor") != null)
            for (HashMap armor : (ArrayList<HashMap>)config.get(name + ".armor")){
                int id = (int) armor.get("id");
                Item item = Item.get(id);
                if (armor.get("enchantment") != null)
                    for (HashMap enchantment : ((ArrayList<HashMap>)armor.get("enchantment")))
                        item.addEnchantment(Enchantment.get((Integer) enchantment.get("id")).setLevel((Integer) enchantment.get("level")));
                this.ffaArmors.add(item);
        }

        Server.getInstance().getPluginManager().registerEvents(this.new Listener(), PluginMain.getInstance());
        Server.getInstance().getScheduler().scheduleRepeatingTask(this.new FFAAreaTask(PluginMain.getInstance()),5);
    }

    public void join(Player player){
        this.players.add(player);
        player.removeAllEffects();
        player.getInventory().clearAll();
        player.setGamemode(0);
        for (Item item : this.ffaItems)
            player.getInventory().addItem(item);
        try{
            player.getInventory().setArmorContents(this.ffaArmors.toArray(new Item[0]));
        }catch(Throwable t){
            t.printStackTrace();
        }
        player.sendTitle(this.joinTitle,this.joinSubTitle);
        player.sendMessage(this.joinMessage);
        player.sendActionBar(this.joinActionbar);
        player.setMaxHealth(playerMaxHealth);
        player.setHealth(playerMaxHealth);

        Server.getInstance().getScheduler().scheduleRepeatingTask(new JoinTask(PluginMain.getInstance(),player),1);
    }

    public void joinAndTp(Player player,boolean randomTp){
        AreaExistWhiteList.add(player);
        join(player);
        if (randomTp)
            player.teleport(getRandomTeleportPosition());
        else
            player.teleport(getTeleportPosition());
        AreaExistWhiteList.remove(player);
    }

    public Position getTeleportPosition(){
        return this.spawnPosition;
    }

    public Position getRandomTeleportPosition(){
        Position position = getRandomPosition();
        while(!(position.getLevelBlock().getId() == 0 && position.add(0,1,0).getLevelBlock().getId() == 0))
            position = getRandomPosition();
        return position;
    }

    private Position getRandomPosition(){
        Random r = new Random();
        double x = r.nextInt((int) (cornerPosition2.x - cornerPosition1.x)) + 1 + cornerPosition1.x;
        double z = r.nextInt((int) (cornerPosition2.z - cornerPosition1.z)) + 1 + cornerPosition1.z;
        return new Position(x, this.spawnPosition.y,z, this.spawnPosition.level);
    }

    public FFAAreaKBInfo getFfaAreaKBInfo() {
        return ffaAreaKBInfo;
    }

    private class Listener implements cn.nukkit.event.Listener{
        @EventHandler(
                priority = EventPriority.HIGHEST
        )
        public void onDamage(EntityDamageByEntityEvent event){
            if (!(event.getDamager() instanceof Player
                    && event.getEntity() instanceof Player
                    && players.contains(event.getEntity())
                    && players.contains(event.getDamager())))
                return;
            FFAAreaKBInfo ffaAreaKBInfo = getFfaAreaKBInfo();
            event.setAttackCooldown(ffaAreaKBInfo.getAc());
            event.setKnockBack(Float.parseFloat(String.valueOf(ffaAreaKBInfo.getBaseKB())));
            Player entity = (Player) event.getEntity();
            Player damager = (Player) event.getDamager();
            if (!allowAttackDamage) {
                entity.setHealth(entity.getMaxHealth());
                return;
            }
            if (entity.getHealth() - event.getFinalDamage() < 1){
                event.setCancelled();

                entity.setHealth(entity.getMaxHealth());

                //spawn light
                EntityLightning lightning = new EntityLightning(entity.getPosition().getChunk(),EntityLightning.getDefaultNBT(entity.getPosition()));
                lightning.setEffect(false);
                lightning.spawnToAll();

                AreaExistWhiteList.add(entity);
                entity.teleport(spawnPosition);
                exit(entity);
                AreaExistWhiteList.remove(entity);

                damager.sendTitle(killTitle,killSubTitle);
                damager.sendMessage(killMessage);
                damager.sendActionBar(killActionbar);

                if (bloodBackWhenKill)
                    damager.setHealth(damager.getMaxHealth());
                if (!killBroadCast.isEmpty())
                    for (Player player : players)
                        player.sendMessage(killBroadCast.replaceAll("\\{Damager\\}",damager.getName()).replaceAll("\\{Dead\\}",entity.getName()));
                if (resetEquipmentWhenKill){
                    damager.getInventory().clearAll();
                    for (Item item : ffaItems)
                        damager.getInventory().addItem(item);
                    try{
                        damager.getInventory().setArmorContents(ffaArmors.toArray(new Item[0]));
                    }catch(Throwable t){
                        t.printStackTrace();
                    }
                }

                //give/remove money
                EconomyAPI.getInstance().addMoney(damager,moneyGiveCountWhenKill);
                EconomyAPI.getInstance().reduceMoney(entity,moneyRemoveCountWhenKill);

//                entity.teleport(getTeleportPosition());
//                joinFFAArea(entity);

//                Server.getInstance().getPluginManager().callEvent(new PlayerDeathEvent(entity, new Item[0], entity.getName() + " dead",0));
                return;
            }

            PlayerSettingMap entry = PluginMain.getInstance().getPlayerSettingPool().getSettings().get(damager.getName());
            if (entry.isShowAttackParticle()){
                Vector3f spawn = event.getEntity().getPosition().asVector3f();
                spawn.add(0,1F,0);
                SpawnParticleEffectPacket packet = new SpawnParticleEffectPacket();
                packet.position = spawn;
                packet.identifier = entry.getParticleType();
                (damager).dataPacket(packet);
            }

            if (entry.isShowEnemyHealthInActionbar())
                damager.sendActionBar(PlayerHealthUtil.changeToString(entity.getHealth(),entity.getMaxHealth()));
        }

        @EventHandler
        public void onFFAAreaBlockBreak(BlockBreakEvent event){
            if (isInArea(event.getBlock().getLocation()) && event.getPlayer().getGamemode() != 1 && !allowBreakBlock)
                event.setCancelled();
        }

        @EventHandler
        public void onFFAAreaBlockPlace(BlockPlaceEvent event){
            if (isInArea(event.getBlock().getLocation()) && event.getPlayer().getGamemode() != 1 && !allowPlaceBlock)
                event.setCancelled();
        }

        @EventHandler(
                priority = EventPriority.HIGHEST
        )
        public void onMotion(EntityMotionEvent event) {
            Vector3 v;
            if (!isInArea(event.getEntity()))
                return;
            if (event.getEntity().isOnGround()) {
                FFAAreaKBInfo ffaAreaKBInfo = getFfaAreaKBInfo();
                v = event.getMotion();
                v.x *= ffaAreaKBInfo.getXzkb_g();
                v = event.getMotion();
                v.y *= ffaAreaKBInfo.getYkb_g();
                v = event.getMotion();
                v.z *= ffaAreaKBInfo.getXzkb_g();
            } else {
                v = event.getMotion();
                v.x *= ffaAreaKBInfo.getXzkb();
                v = event.getMotion();
                v.y *= ffaAreaKBInfo.getYkb();
                v = event.getMotion();
                v.z *= ffaAreaKBInfo.getXzkb();
            }
        }

//        @EventHandler
//        public void onPlayerQuit(PlayerQuitEvent event){
//            if (players.contains(event.getPlayer())){
//                exit(event.getPlayer());
//            }
//        }
    }

    private class FFAAreaTask extends PluginTask{

        private int runTick;

        public FFAAreaTask(Plugin owner) {
            super(owner);
        }

        @Override
        public void onRun(int i) {
            for (Player player : cornerPosition1.level.getPlayers().values()){
                if (!players.contains(player) && isInArea(player) && !AreaExistWhiteList.exist(player))
                    join(player);
                if (((players.contains(player) && !isInArea(player)) || !player.isOnline()) && !AreaExistWhiteList.exist(player))
                    exit(player);
                if (isInArea(player))
                    player.getFoodData().setLevel(20);
            }
            if (clearItemsTime > 0) {
                if (runTick / clearItemsTime == 1) {
                    for (Entity entity : spawnPosition.getLevel().getEntities())
                        if (entity instanceof EntityItem && isInArea(entity.getPosition()))
                            spawnPosition.getLevel().removeEntity(entity);
                    runTick = 0;
                } else {
                    runTick++;
                }
            }
//            for (Player player2 : players){
//                if (player2.getName().equals("daoge cmd")){
//                    position1.level.addParticleEffect(player2.getPosition(), ParticleEffect.SOUL);
//                }
//            }
        }
    }

    @Getter
    public static class AreaExistWhiteList {
        private static HashSet<Player> whiteList = new HashSet<>();

        public static boolean exist(Player player){
            return whiteList.contains(player);
        }

        public static void remove(Player player){
            whiteList.remove(player);
        }

        public static void add(Player player){
            whiteList.add(player);
        }
    }

    public class FFAAreaKBInfo{

        private int ac = 10;
        private double baseKB = 0.4;
        private double xzkb_g = 1.0;
        private double ykb_g = 1.0;
        private double xzkb = 0.5;
        private double ykb = 0.5;

        public int getAc() {
            return ac;
        }

        public void setAc(int ac) {
            this.ac = ac;
            PluginMain.getInstance().getFfaConfig().set(areaName + ".ac",ac);
            PluginMain.getInstance().getFfaConfig().save();
        }

        public double getBaseKB() {
            return baseKB;
        }

        public void setBaseKB(double baseKB) {
            this.baseKB = baseKB;
            PluginMain.getInstance().getFfaConfig().set(areaName + ".basekb",baseKB);
            PluginMain.getInstance().getFfaConfig().save();
        }

        public double getXzkb_g() {
            return xzkb_g;
        }

        public void setXzkb_g(double xzkb_g) {
            this.xzkb_g = xzkb_g;
            PluginMain.getInstance().getFfaConfig().set(areaName + ".xzkb-g",xzkb_g);
            PluginMain.getInstance().getFfaConfig().save();
        }

        public double getYkb_g() {
            return ykb_g;
        }

        public void setYkb_g(double ykb_g) {
            this.ykb_g = ykb_g;
            PluginMain.getInstance().getFfaConfig().set(areaName + ".ykb-g",ykb_g);
            PluginMain.getInstance().getFfaConfig().save();
        }

        public double getXzkb() {
            return xzkb;
        }

        public void setXzkb(double xzkb) {
            this.xzkb = xzkb;
            PluginMain.getInstance().getFfaConfig().set(areaName + ".xzkb",xzkb);
            PluginMain.getInstance().getFfaConfig().save();
        }

        public double getYkb() {
            return ykb;
        }

        public void setYkb(double ykb) {
            this.ykb = ykb;
            PluginMain.getInstance().getFfaConfig().set(areaName + ".ykb",ykb);
            PluginMain.getInstance().getFfaConfig().save();
        }
    }

    public boolean isInArea(Position pos){
        return pos.level.getName().equals(this.cornerPosition1.level.getName())
                && pos.x >= cornerPosition1.x
                && pos.y >= cornerPosition1.y
                && pos.z >= cornerPosition1.z
                && pos.x <= cornerPosition2.x
                && pos.y <= cornerPosition2.y
                && pos.z <= cornerPosition2.z
                && pos.getLevel() == cornerPosition1.getLevel() ? true : false;
    }

    public void exit(Player player){
        this.players.remove(player);
        player.sendTitle(this.exitTitle,this.exitSubTitle);
        player.sendMessage(this.exitMessage);
        player.sendActionBar(this.exitActionbar);
        player.setGamemode(2);
        player.removeAllEffects();
        player.getInventory().clearAll();
        player.setMaxHealth(20);
        player.setHealth(20);
    }

//    private class DeadTask extends PluginTask{
//
//        private Player player;
//
//        public DeadTask(Player player){
//            super(PluginMain.getInstance());
//            this.player = player;
//        }
//
//        @Override
//        public void onRun(int i) {
//            cn.cookiestudio.lobbysystem.PluginMain.getInstance().getLobby().teleportPlayerToLobby(player);
//            player.setGamemode(0);
//        }
//    }

    private class JoinTask extends PluginTask{

        private Player player;
        private double keepTime = 2.5 * 20;
        private int currentTime = 0;

        public JoinTask(Plugin owner,Player player) {
            super(owner);
            this.player = player;
            Server.getInstance().getPluginManager().registerEvents(new Listener(),PluginMain.getInstance());
        }

        @Override
        public void onRun(int i) {
            this.player.setAttribute(Attribute.getAttribute(10).setValue(1.0F * (float)((keepTime - currentTime) / keepTime)));
            this.player.sendExperienceLevel((int)((keepTime - currentTime) / 20));
            this.currentTime++;
            if (this.currentTime > this.keepTime)
                this.cancel();
        }

        private class Listener implements cn.nukkit.event.Listener{
            @EventHandler
            public void onEntityDamage(EntityDamageEvent event){
                if (!(event.getEntity() == JoinTask.this.player && !JoinTask.this.getHandler().isCancelled()))
                    return;
                event.setCancelled();
            }

            @EventHandler
            public void onEntityDamageByEntity(EntityDamageByEntityEvent event){
                if (!(event.getDamager() == JoinTask.this.player && !JoinTask.this.getHandler().isCancelled()))
                    return;
                event.setCancelled();
            }
        }
    }
}