package cn.cookiestudio.aweadyffa.commands;

import cn.cookiestudio.easy4form.window.BFormWindowCustom;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.response.FormResponseCustom;
import cn.cookiestudio.aweadyffa.FFAArea;
import cn.cookiestudio.aweadyffa.PluginMain;

import java.util.ArrayList;
import java.util.Arrays;

public class KBACCommand extends Command {

    private static BFormWindowCustom formWindowCustom = new BFormWindowCustom("kbac settings");

    static{
        formWindowCustom.addElement(new ElementDropdown("选择一个FFAArea",Arrays.asList(PluginMain.getInstance().getFfaAreas().keySet().toArray(new String[0]))));
        ArrayList<String> list = new ArrayList<>();
        list.add("basekb");
        list.add("xzkb");
        list.add("ykb");
        list.add("xzkb-g");
        list.add("ykb-g");
        list.add("ac");
        formWindowCustom.addElement(new ElementDropdown("选择参数类型",list));
        ArrayList<String> list2 = new ArrayList<>();
        list2.add("set");
        list2.add("add");
        list2.add("get");
        formWindowCustom.addElement(new ElementDropdown("选择一个操作",list2));
        formWindowCustom.addElement(new ElementInput("输入数值（get操作这里请留空）"));

        formWindowCustom.setResponseAction((e) -> {
            if (e.getResponse() == null)
                return;
            FormResponseCustom response = (FormResponseCustom) e.getResponse();
            String ffaArea = response.getDropdownResponse(0).getElementContent();
            String type = response.getDropdownResponse(1).getElementContent();
            String option = response.getDropdownResponse(2).getElementContent();
            String input = response.getInputResponse(3);
            switch(type){
                case "basekb":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setBaseKB(ffaAreaKBInfo.getBaseKB() + Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setBaseKB(Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            e.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getBaseKB()));
                            break;
                    }
                    break;
                case "xzkb-g":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setXzkb_g(ffaAreaKBInfo.getXzkb_g() + Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setXzkb_g(Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            e.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getXzkb()));
                            break;
                    }
                    break;
                case "ykb-g":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setYkb_g(ffaAreaKBInfo.getYkb_g() + Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setYkb_g(Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            e.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getYkb_g()));
                            break;
                    }
                    break;
                case "xzkb":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setXzkb(ffaAreaKBInfo.getXzkb() + Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setXzkb(Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            e.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getXzkb()));
                            break;
                    }
                    break;
                case "ykb":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setYkb(ffaAreaKBInfo.getYkb() + Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setYkb(Double.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            e.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getYkb()));
                            break;
                    }
                    break;
                case "ac":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setYkb(ffaAreaKBInfo.getAc() + Integer.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setAc(Integer.valueOf(input));
                            e.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            e.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getAc()));
                            break;
                    }
                    break;
                }
            }
        );
    }

    public KBACCommand(String name) {
        super(name);
        this.setDescription("set FFAArea kbac");
        this.setPermission("op");
        this.setDescription("/kbac <FFAArea_name> <basekb/xzkb/ykb/xzkb-g/ykb-g> <add/set> <int/double>");
    }

    public static BFormWindowCustom getFormWindowCustom() {
        return formWindowCustom;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage("不能在控制台使用此指令");
        }else if(!commandSender.isOp()) {
            return true;
        }else{
            getFormWindowCustom().sendToPlayer((Player) commandSender);
            return true;
        }
        return true;
    }
}
