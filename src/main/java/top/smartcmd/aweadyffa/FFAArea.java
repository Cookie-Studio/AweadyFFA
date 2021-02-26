package top.smartcmd.aweadyffa;

import cn.nukkit.Player;
import cn.nukkit.Server;
import cn.nukkit.entity.Entity;
import cn.nukkit.entity.item.EntityItem;
import cn.nukkit.entity.weather.EntityLightning;
import cn.nukkit.event.EventHandler;
import cn.nukkit.event.EventPriority;
import cn.nukkit.event.block.BlockBreakEvent;
import cn.nukkit.event.block.BlockPlaceEvent;
import cn.nukkit.event.entity.EntityDamageByEntityEvent;
import cn.nukkit.event.entity.EntityMotionEvent;
import cn.nukkit.event.player.PlayerQuitEvent;
import cn.nukkit.item.Item;
import cn.nukkit.item.enchantment.Enchantment;
import cn.nukkit.level.Position;
import cn.nukkit.math.Vector3;
import cn.nukkit.plugin.Plugin;
import cn.nukkit.scheduler.PluginTask;
import cn.nukkit.utils.Config;
import me.onebone.economyapi.EconomyAPI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class FFAArea {
    private ArrayList<Item> ffaItems = new ArrayList<>();
    private ArrayList<Item> ffaArmors = new ArrayList<>();
    private Position position1;
    private Position position2;
    private Position position3;
    private boolean allowAttackDamage;
    private boolean bloodBackWhenKill;
    private boolean allowBreakBlock;
    private boolean allowPlaceBlock;
    private boolean killBroadCast;
    private boolean clearItems;
    private boolean resetEquipmentWhenKill;
    private String areaName;
    private HashSet<Player> players = new HashSet<>();
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
    private String killBroadCastText;
    private FFAAreaKBInfo ffaAreaKBInfo = new FFAAreaKBInfo();
    private int playerMaxHealth;
    private int clearItemsTime;
    private int moneyGiveCountWhenKill;
    private int moneyRemoveCountWhenKill;

    public ArrayList<Item> getFfaItems() {
        return ffaItems;
    }

    public ArrayList<Item> getFfaArmors() {
        return ffaArmors;
    }

    public Position getPosition1() {
        return position1;
    }

    public Position getPosition2() {
        return position2;
    }

    public Position getPosition3() {
        return position3;
    }

    public boolean isAllowAttackDamage() {
        return allowAttackDamage;
    }

    public boolean isBloodBackWhenKill() {
        return bloodBackWhenKill;
    }

    public boolean isAllowBreakBlock() {
        return allowBreakBlock;
    }

    public boolean isAllowPlaceBlock() {
        return allowPlaceBlock;
    }

    public boolean isKillBroadCast() {
        return killBroadCast;
    }

    public boolean isClearItems() {
        return clearItems;
    }

    public String getAreaName() {
        return areaName;
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public String getJoinTitle() {
        return joinTitle;
    }

    public String getJoinSubTitle() {
        return joinSubTitle;
    }

    public String getJoinMessage() {
        return joinMessage;
    }

    public String getJoinActionbar() {
        return joinActionbar;
    }

    public String getExitTitle() {
        return exitTitle;
    }

    public String getExitSubTitle() {
        return exitSubTitle;
    }

    public String getExitMessage() {
        return exitMessage;
    }

    public String getExitActionbar() {
        return exitActionbar;
    }

    public String getKillTitle() {
        return killTitle;
    }

    public String getKillSubTitle() {
        return killSubTitle;
    }

    public String getKillMessage() {
        return killMessage;
    }

    public String getKillActionbar() {
        return killActionbar;
    }

    public String getKillBroadCastText() {
        return killBroadCastText;
    }

    public int getPlayerMaxHealth() {
        return playerMaxHealth;
    }

    public int getClearItemsTime() {
        return clearItemsTime;
    }

    public FFAArea(String name){
        this.areaName = name;
        Config config = PluginMain.getInstance().getFFAConfig();
        ArrayList posInfo = (ArrayList) config.get(name + ".position");
        position1 = new Position((int) posInfo.get(0),(int)posInfo.get(1),(int)posInfo.get(2), Server.getInstance().getLevelByName((String)posInfo.get(9)));
        position2 = new Position((int)posInfo.get(3),(int)posInfo.get(4),(int)posInfo.get(5), Server.getInstance().getLevelByName((String)posInfo.get(9)));
        position3 = new Position((int)posInfo.get(6),(int)posInfo.get(7),(int)posInfo.get(8), Server.getInstance().getLevelByName((String)posInfo.get(9)));
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
        this.killBroadCast = (boolean) config.get(name + ".kill-broad-cast.open");
        this.killBroadCastText = (String) config.get(name + ".kill-broad-cast.text");
        this.clearItems = (boolean) config.get(name + ".clear-items.open");
        this.clearItemsTime = (int) config.get(name + ".clear-items.time") * 4;
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

    public void joinFFAArea(Player player){
        this.players.add(player);
        player.removeAllEffects();
        player.getInventory().clearAll();
        player.setGamemode(0);
        for (Item item : this.ffaItems)
            player.getInventory().addItem(item);
        try{
            player.getInventory().setArmorContents((Item[]) this.ffaArmors.toArray(new Item[0]));
        }catch(Throwable t){
            t.printStackTrace();
        }
        player.sendTitle(this.joinTitle,this.joinSubTitle);
        player.sendMessage(this.joinMessage);
        player.sendActionBar(this.joinActionbar);
        player.setMaxHealth(playerMaxHealth);
        player.setHealth(playerMaxHealth);
    }

    public Position getTeleportPosition(){
        return this.position3;
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
                    && FFAArea.this.players.contains(event.getEntity())
                    && FFAArea.this.players.contains(event.getDamager())))
                return;
            FFAAreaKBInfo ffaAreaKBInfo = FFAArea.this.getFfaAreaKBInfo();
            event.setAttackCooldown(ffaAreaKBInfo.getAc());
            event.setKnockBack(Float.parseFloat(String.valueOf(ffaAreaKBInfo.getBaseKB())));
            if (!FFAArea.this.allowAttackDamage) {
                event.getEntity().setHealth(event.getEntity().getMaxHealth());
                return;
            }
            if (event.getEntity().getHealth() - event.getFinalDamage() < 1){
                event.setCancelled();

                Player damager = (Player) event.getDamager();
                Player entity = (Player)event.getEntity();

                entity.setGamemode(3);
                entity.setHealth(entity.getMaxHealth());
                entity.sendActionBar("spawn in 5 second...");
                Server.getInstance().getScheduler().scheduleDelayedTask(new DeadTask(entity),20 * 5);

                damager.sendTitle(FFAArea.this.killTitle,FFAArea.this.killSubTitle);
                damager.sendMessage(FFAArea.this.killMessage);
                damager.sendActionBar(FFAArea.this.killActionbar);

                if (FFAArea.this.bloodBackWhenKill)
                    damager.setHealth(damager.getMaxHealth());
                if (FFAArea.this.killBroadCast)
                    for (Player player : FFAArea.this.players)
                        player.sendMessage(FFAArea.this.killBroadCastText.replaceAll("\\{Damager\\}",damager.getName()).replaceAll("\\{Dead\\}",entity.getName()));
                if (FFAArea.this.resetEquipmentWhenKill){
                    damager.getInventory().clearAll();
                    for (Item item : FFAArea.this.ffaItems)
                        damager.getInventory().addItem(item);
                    try{
                        damager.getInventory().setArmorContents(FFAArea.this.ffaArmors.toArray(new Item[0]));
                    }catch(Throwable t){
                        t.printStackTrace();
                    }
                }

                //spawn light
                EntityLightning lightning = new EntityLightning(entity.getPosition().getChunk(),EntityLightning.getDefaultNBT(entity.getPosition()));
                lightning.setEffect(false);
                lightning.spawnToAll();

                //give/remove money
                EconomyAPI.getInstance().addMoney(damager,moneyGiveCountWhenKill);
                EconomyAPI.getInstance().reduceMoney(entity,moneyRemoveCountWhenKill);

//                entity.teleport(FFAArea.this.getTeleportPosition());
//                FFAArea.this.joinFFAArea(entity);
            }
        }

        @EventHandler
        public void onFFAAreaBlockBreak(BlockBreakEvent event){
            if (isInArea(event.getBlock().getLocation()) && event.getPlayer().getGamemode() != 1 && !FFAArea.this.allowBreakBlock)
                event.setCancelled();
        }

        @EventHandler
        public void onFFAAreaBlockPlace(BlockPlaceEvent event){
            if (isInArea(event.getBlock().getLocation()) && event.getPlayer().getGamemode() != 1 && !FFAArea.this.allowPlaceBlock)
                event.setCancelled();
        }

        @EventHandler(
                priority = EventPriority.HIGHEST
        )
        public void onMotion(EntityMotionEvent event) {
            Vector3 v;
            if (!FFAArea.this.isInArea(event.getEntity()))
                return;
            if (event.getEntity().isOnGround()) {
                FFAAreaKBInfo ffaAreaKBInfo = FFAArea.this.getFfaAreaKBInfo();
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

        @EventHandler
        public void onPlayerQuit(PlayerQuitEvent event){
            if (FFAArea.this.players.contains(event.getPlayer())){
                FFAArea.this.players.remove(event.getPlayer());
            }
        }
    }

    private class FFAAreaTask extends PluginTask{

        private int runTick;

        public FFAAreaTask(Plugin owner) {
            super(owner);
        }

        @Override
        public void onRun(int i) {
            for (Player player : position1.level.getPlayers().values()){
                if (!FFAArea.this.players.contains(player) && FFAArea.this.isInArea(player))
                    FFAArea.this.joinFFAArea(player);
                if (FFAArea.this.players.contains(player) && !FFAArea.this.isInArea(player)){
                    FFAArea.this.exitFFAArea(player);
                }
                if (FFAArea.this.isInArea(player))
                    player.getFoodData().setLevel(20);
            }
            if (runTick / FFAArea.this.clearItemsTime == 1){
                for (Entity entity : FFAArea.this.position3.getLevel().getEntities())
                    if (entity instanceof EntityItem && FFAArea.this.isInArea(entity.getPosition()))
                        FFAArea.this.position3.getLevel().removeEntity(entity);
                runTick = 0;
            }else
                runTick++;
//            for (Player player2 : FFAArea.this.players){
//                if (player2.getName().equals("daoge cmd")){
//                    FFAArea.this.position1.level.addParticleEffect(player2.getPosition(), ParticleEffect.SOUL);
//                }
//            }
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
            PluginMain.getInstance().getFFAConfig().set(areaName + ".ac",ac);
            PluginMain.getInstance().getFFAConfig().save();
        }

        public double getBaseKB() {
            return baseKB;
        }

        public void setBaseKB(double baseKB) {
            this.baseKB = baseKB;
            PluginMain.getInstance().getFFAConfig().set(areaName + ".basekb",baseKB);
            PluginMain.getInstance().getFFAConfig().save();
        }

        public double getXzkb_g() {
            return xzkb_g;
        }

        public void setXzkb_g(double xzkb_g) {
            this.xzkb_g = xzkb_g;
            PluginMain.getInstance().getFFAConfig().set(areaName + ".xzkb-g",xzkb_g);
            PluginMain.getInstance().getFFAConfig().save();
        }

        public double getYkb_g() {
            return ykb_g;
        }

        public void setYkb_g(double ykb_g) {
            this.ykb_g = ykb_g;
            PluginMain.getInstance().getFFAConfig().set(areaName + ".ykb-g",ykb_g);
            PluginMain.getInstance().getFFAConfig().save();
        }

        public double getXzkb() {
            return xzkb;
        }

        public void setXzkb(double xzkb) {
            this.xzkb = xzkb;
            PluginMain.getInstance().getFFAConfig().set(areaName + ".xzkb",xzkb);
            PluginMain.getInstance().getFFAConfig().save();
        }

        public double getYkb() {
            return ykb;
        }

        public void setYkb(double ykb) {
            this.ykb = ykb;
            PluginMain.getInstance().getFFAConfig().set(areaName + ".ykb",ykb);
            PluginMain.getInstance().getFFAConfig().save();
        }
    }

    public boolean isInArea(Position pos){
        return pos.level.getName().equals(this.position1.level.getName())
                && pos.x >= position1.x
                && pos.y >= position1.y
                && pos.z >= position1.z
                && pos.x <= position2.x
                && pos.y <= position2.y
                && pos.z <= position2.z
                && pos.getLevel() == position1.getLevel() ? true : false;
    }

    private void exitFFAArea(Player player){
        this.players.remove(player);
        player.sendTitle(this.exitTitle,this.exitSubTitle);
        player.sendMessage(this.exitMessage);
        player.sendActionBar(this.exitActionbar);
        player.setGamemode(2);
        if (!PluginMain.getInstance().isPositionInFFAArea(player)){
            player.removeAllEffects();
            player.getInventory().clearAll();
        }
        player.setMaxHealth(20);
        player.setHealth(20);
    }

    private class DeadTask extends PluginTask{

        private Player player;

        public DeadTask(Player player){
            super(PluginMain.getInstance());
            this.player = player;
        }

        @Override
        public void onRun(int i) {
            player.teleport(player.getSpawn());
            player.setGamemode(0);
        }
    }
}