package cn.cookiestudio.aweadyffa.commands;

import cn.cookiestudio.aweadyffa.FElementButton;
import cn.cookiestudio.aweadyffa.playersetting.PlayerSettingEntry;
import cn.cookiestudio.aweadyffa.playersetting.PlayerSettings;
import cn.cookiestudio.aweadyffa.PluginMain;
import cn.cookiestudio.easy4form.window.BFormWindowSimple;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;

public class FFACommand extends Command {

    private static BFormWindowSimple joinFFAAreaForm = new BFormWindowSimple("FFA","");

    static {
        joinFFAAreaForm.setResponseAction((e) -> {
            if (e.getResponse() == null)
                return;
            FElementButton fElementButton = (FElementButton) ((FormResponseSimple)e.getResponse()).getClickedButton();
            PlayerSettingEntry entry = PluginMain.getInstance().getPlayerSettings().getSettings().get(e.getPlayer().getName());
            fElementButton.getFfaArea().joinAndTp(e.getPlayer(), entry.isRandomTp());
        });
    }

    public FFACommand(String name) {
        super(name);
        this.setDescription("join FFA");
    }


    public static BFormWindowSimple getJoinFFAAreaForm() {
        return joinFFAAreaForm;
    }

    @Override
    public boolean execute(CommandSender commandSender, String s, String[] strings) {
        if (commandSender instanceof ConsoleCommandSender){
            commandSender.sendMessage("不能在控制台使用此命令");
            return true;
        }else{
            for (ElementButton button : joinFFAAreaForm.getButtons()){
                FElementButton button1 = (FElementButton) button;
                button1.setText(button1.getFfaArea().getAreaName() + "\n" + "online: " + button1.getFfaArea().getPlayers().size());
            }
            getJoinFFAAreaForm().sendToPlayer((Player) commandSender);
        }
        return true;
    }
}
