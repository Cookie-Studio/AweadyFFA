package cn.cookiestudio.aweadyffa.commands;

import cn.cookiestudio.aweadyffa.FElementButton;
import cn.cookiestudio.aweadyffa.playersetting.PlayerSettingMap;
import cn.cookiestudio.aweadyffa.PluginMain;
import cn.cookiestudio.easy4form.window.BFormWindowSimple;
import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.response.FormResponseSimple;

import java.util.stream.Collectors;

public class FFACommand extends Command {

    private static BFormWindowSimple joinFFAAreaForm;

    static {
        joinFFAAreaForm = BFormWindowSimple.getBuilder().setButtons(PluginMain.getInstance().getFfaAreas().values()
                .stream()
                .map((a) -> new FElementButton(a.getAreaName(),a))
                .collect(Collectors.toList()))
                .setResponseAction((e) -> {
                    FElementButton fElementButton = (FElementButton) ((FormResponseSimple)e.getResponse()).getClickedButton();
                    PlayerSettingMap entry = PluginMain.getInstance().getPlayerSettingPool().getSettings().get(e.getPlayer().getName());
                    fElementButton.getFfaArea().joinAndTp(e.getPlayer(), entry.isRandomTp());})
                .setTitle("FFA")
                .build();
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
//            if (PluginMain.getInstance().isPositionInFFAArea((Player)commandSender))
//                PluginMain.getInstance().getPositionFFAArea((Player)commandSender).exit((Player)commandSender);
            getJoinFFAAreaForm().sendToPlayer((Player) commandSender);
        }
        return true;
    }
}
