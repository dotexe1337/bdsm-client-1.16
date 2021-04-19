package cf.dotexe.bdsm.command.impl.client;

import java.awt.event.KeyEvent;

import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.command.Command;
import cf.dotexe.bdsm.utils.client.ClientUtils;
import cf.dotexe.bdsm.utils.client.Logger;
import cf.dotexe.bdsm.utils.client.Logger.LogState;

public class BindCommand extends Command {
	
	public BindCommand() {
		this.setNames(new String[] {"bind", "b"});
	}
	
	public void runCommand(String[] args) {
	    String modName = "";
	    String keyName = "";
	    if (args.length > 1) {
	    	modName = args[1];
	    	if (args.length > 2)
	    		keyName = args[2]; 
	    } 
	    Module module = mc.hackedClient.getModuleManager().getModule(modName);
	    if (module.getName().equalsIgnoreCase("null")) {
	    	ClientUtils.addChatMessage("Invalid module.");
	    	return;
	    } 
	    if (keyName.equalsIgnoreCase("NONE")) {
	    	ClientUtils.addChatMessage(String.valueOf(String.valueOf(module.getDisplayName())) + "'s bind has been cleared.");
	    	module.setBind(0);
	    	mc.hackedClient.getModuleManager().saveConfig(mc.hackedClient.getGson());
	    	return;
	    } 
	    try {
	    	module.setBind((int)KeyEvent.class.getField("VK_" + keyName.toUpperCase()).getInt(null));
		    mc.hackedClient.getModuleManager().saveConfig(mc.hackedClient.getGson());
			if ((int)KeyEvent.class.getField("VK_" + keyName.toUpperCase()).getInt(null) == 0) {
				ClientUtils.addChatMessage("Invalid key entered, Bind cleared.");
			} else {
				ClientUtils.addChatMessage(String.valueOf(String.valueOf(module.getDisplayName())) + " bound to " + keyName);
			}
		} catch (IllegalArgumentException e) {
			Logger.log(LogState.Error, "Illegal argument exception in BindCommand!");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Logger.log(LogState.Error, "Illegal access exception in BindCommand!");
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			Logger.log(LogState.Error, "No such field exception in BindCommand!");
			e.printStackTrace();
		} catch (SecurityException e) {
			Logger.log(LogState.Error, "Security exception in bind command");
			e.printStackTrace();
		} 
	}
	  
	public String getHelp() {
	    return "<bind/b> (module) (key/none) - Bind a module to a key, or clear a bind.";
	}
	
}