package cn.cookiestudio.aweadyffa.commands;

import cn.cookiestudio.aweadyffa.playersetting.PlayerSettingEntry;
import cn.cookiestudio.aweadyffa.PluginMain;
import cn.cookiestudio.easy4form.window.BFormWindowCustom;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementToggle;
import cn.nukkit.form.response.FormResponseCustom;

public class ChangeSettingCommand extends Command {
    public ChangeSettingCommand(String name) {
        super(name);
    }

    @Override
    public boolean execute(CommandSender sender, String s, String[] strings) {
        if (sender instanceof ConsoleCommandSender){
            sender.sendMessage("不能在控制台使用此命令");
            return true;
        }else{
            Player player = (Player)sender;
            BFormWindowCustom custom = new BFormWindowCustom("Settings");
            PlayerSettingEntry entry = PluginMain.getInstance().getPlayerSettings().getSettings().get(player.getName());
            custom.addElement(new ElementToggle("randomTp",entry.isRandomTp()));
            custom.addElement(new ElementToggle("showAttackParticle",entry.isShowAttackParticle()));
            custom.setResponseAction((e) -> {
                if (e.getResponse() == null)
                    return;
                FormResponseCustom response = (FormResponseCustom) e.getResponse();
                entry.setRandomTp(response.getToggleResponse(0));
                entry.setShowAttackParticle(response.getToggleResponse(1));
            });
            custom.sendToPlayer(player);
        }
        return true;
    }
}
