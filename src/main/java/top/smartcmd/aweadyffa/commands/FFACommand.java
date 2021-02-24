package top.smartcmd.aweadyffa.commands;

import cn.nukkit.Player;
import cn.nukkit.command.Command;
import cn.nukkit.command.CommandSender;
import cn.nukkit.command.ConsoleCommandSender;
import cn.nukkit.form.element.ElementButton;
import cn.nukkit.form.window.FormWindowSimple;
import top.smartcmd.aweadyffa.FElementButton;
import top.smartcmd.aweadyffa.PluginMain;

public class FFACommand extends Command {

    public static FormWindowSimple getJoinFFAAreaForm() {
        return joinFFAAreaForm;
    }

    private static FormWindowSimple joinFFAAreaForm = new FormWindowSimple("FFA","");

    public FFACommand(String name) {
        super(name);
        this.setDescription("join FFA");
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
            ((Player)commandSender).showFormWindow(joinFFAAreaForm,
                                                   PluginMain.getInstance().getFFAFormId());
        }
        return true;
    }
}
