package top.smartcmd.aweadyffa;

import cn.nukkit.event.EventHandler;
import cn.nukkit.event.player.PlayerFormRespondedEvent;
import cn.nukkit.form.response.FormResponseCustom;
import cn.nukkit.form.response.FormResponseSimple;

public class FormListener implements cn.nukkit.event.Listener {
    @EventHandler
    public void onPlayerFormResponded(PlayerFormRespondedEvent event){
        if (event.getResponse() == null)
            return;
        if (event.getFormID() == PluginMain.getInstance().getFFAFormId()){
            FElementButton fElementButton = (FElementButton) ((FormResponseSimple)event.getResponse()).getClickedButton();
            event.getPlayer().teleport(fElementButton.getFfaArea().getTeleportPosition());
            fElementButton.getFfaArea().joinFFAArea(event.getPlayer());
            return;
        }
        if (event.getFormID() == PluginMain.getInstance().getKbacFormId()){
            FormResponseCustom response = (FormResponseCustom) event.getResponse();
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
                            event.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setBaseKB(Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            event.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getBaseKB()));
                            break;
                    }
                break;
                case "xzkb-g":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setXzkb_g(ffaAreaKBInfo.getXzkb_g() + Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setXzkb_g(Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            event.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getXzkb()));
                            break;
                    }
                break;
                case "ykb-g":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setYkb_g(ffaAreaKBInfo.getYkb_g() + Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setYkb_g(Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            event.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getYkb_g()));
                            break;
                    }
                break;
                case "xzkb":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setXzkb(ffaAreaKBInfo.getXzkb() + Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setXzkb(Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            event.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getXzkb()));
                            break;
                    }
                break;
                case "ykb":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setYkb(ffaAreaKBInfo.getYkb() + Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setYkb(Double.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            event.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getYkb()));
                            break;
                    }
                break;
                case "ac":
                    switch(option){
                        case "add":
                            FFAArea.FFAAreaKBInfo ffaAreaKBInfo = PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo();
                            ffaAreaKBInfo.setYkb(ffaAreaKBInfo.getAc() + Integer.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "set":
                            PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().setAc(Integer.valueOf(input));
                            event.getPlayer().sendMessage("success");
                            break;
                        case "get":
                            event.getPlayer().sendMessage(String.valueOf(PluginMain.getInstance().getFfaAreas().get(ffaArea).getFfaAreaKBInfo().getAc()));
                            break;
                    }
                break;
            }
        }
    }
}
