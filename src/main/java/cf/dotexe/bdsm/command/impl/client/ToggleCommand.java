package cf.dotexe.bdsm.command.impl.client;

import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.command.Command;
import cf.dotexe.bdsm.utils.client.ClientUtils;

public class ToggleCommand extends Command {
	
	public ToggleCommand() {
		this.setNames(new String[]{"toggle", "t"});
	}
	
	public void runCommand(String[] args) {
		String modName = "";
	    if (args.length > 1)
	    	modName = args[1]; 
	    Module module = mc.hackedClient.getModuleManager().getModule(modName);
	    if (module.getName().equalsIgnoreCase("null")) {
	    	ClientUtils.addChatMessage("Invalid Module.");
	    	return;
	    } 
	    module.toggle();
	    ClientUtils.addChatMessage(String.valueOf(String.valueOf(module.getDisplayName())) + " is now " + (module.getState() ? "\247aenabled" : "\247cdisabled"));
	    mc.hackedClient.getModuleManager().saveConfig(mc.hackedClient.getGson());
	}
	  
	  
	public String getHelp() {
		return "<t/toggle> (module) - Toggles a module on or off";
	}
}
