package cf.dotexe.bdsm.command.impl.client;

import cf.dotexe.bdsm.command.Command;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.setting.Setting;
import cf.dotexe.bdsm.utils.client.ClientUtils;

public class SettingCommand extends Command {
	  
	public void runCommand(String[] args) {
	    if (args.length < 2) {
	    	ClientUtils.addChatMessage(getHelp());
	    	return;
	    } 
	    Module m = mc.hackedClient.getModuleManager().getModule(args[0]);
	    if (!m.getName().equalsIgnoreCase("Null")) {
	    	Setting setting = mc.hackedClient.getSettingManager().getSettingByName(args[1], m);
	    	
	    	if(setting.isCheck()) {
	    		if(args[2].equalsIgnoreCase("true")) {
	    			setting.setValBoolean(true);
	    			ClientUtils.addChatMessage(setting.getName() + " set to \247aTrue");
	    		} else if(args[2].equalsIgnoreCase("false")) {
	    			setting.setValBoolean(false);
	    			ClientUtils.addChatMessage(setting.getName() + " set to \247cFalse");
	    		} else {
	    			ClientUtils.addChatMessage("Argument not recognized");
	    		}
	    	} else if(setting.isCombo()) {
	    		for(String s: setting.getOptions()) {
	    			if(s.equalsIgnoreCase(args[2])) {
	    				setting.setValString(s);
		    			ClientUtils.addChatMessage(setting.getName() + " set to " + setting.getValString());
		    			return;
	    			}
	    		}
	    		ClientUtils.addChatMessage("Argument not recognized");
	    	} else if(setting.isSlider()) {
	    		try {
	    			Float d = Float.parseFloat(args[2]);
		    		setting.setValDouble(d);
		    		ClientUtils.addChatMessage(setting.getName() + " set to " + d);
	    		} catch(Exception e) {
	    			ClientUtils.addChatMessage("Argument not recognized");
	    		}
	    	} else {
	    		ClientUtils.addChatMessage("Option not recognized.");
	    	}
	    } else {
	    	ClientUtils.addChatMessage(getHelp());
	    } 
	}
	
	public String getHelp() {
	    return "Change setting - <modname> (setting name) (value)";
	}
	
}
