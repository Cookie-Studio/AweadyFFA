package cn.cookiestudio.aweadyffa.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;

public class GetWorldName extends Command {
    public GetWorldName(String name) {
        super(name);
        this.setPermission("op");
        this.setDescription("get world name");
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage("控制台不能使用此指令");
        }else{
            Player sender = (Player) commandSender;
            sender.sendMessage("world name: " + sender.getLevel().getName());
        }
        return true;
    }
}
