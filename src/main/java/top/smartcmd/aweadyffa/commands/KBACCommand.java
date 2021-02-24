package top.smartcmd.aweadyffa.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementDropdown;
import cn.nukkit.form.element.ElementInput;
import cn.nukkit.form.window.FormWindowCustom;
import top.smartcmd.aweadyffa.FFAArea;
import top.smartcmd.aweadyffa.PluginMain;

import java.util.ArrayList;
import java.util.Arrays;

public class KBACCommand extends Command {

    private static FormWindowCustom formWindowCustom = new FormWindowCustom("kbac settings");

    public static FormWindowCustom getFormWindowCustom() {
        return formWindowCustom;
    }

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
    }

    public KBACCommand(String name) {
        super(name);
        this.setDescription("set FFAArea kbac");
        this.setPermission("op");
        this.setDescription("/kbac <FFAArea_name> <basekb/xzkb/ykb/xzkb-g/ykb-g> <add/set> <int/double>");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage("不能在控制台使用此指令");
        }else if(!commandSender.isOp()) {
            return true;
        }else{
            ((Player)commandSender).showFormWindow(formWindowCustom,PluginMain.getInstance().getKbacFormId());
            return true;
        }
        return true;
    }
}
