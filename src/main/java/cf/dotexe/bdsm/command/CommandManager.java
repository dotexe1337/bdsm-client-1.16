package cf.dotexe.bdsm.command;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.command.impl.client.BindCommand;
import cf.dotexe.bdsm.command.impl.client.FriendCommand;
import cf.dotexe.bdsm.command.impl.client.HelpCommand;
import cf.dotexe.bdsm.command.impl.client.SettingCommand;
import cf.dotexe.bdsm.command.impl.client.ToggleCommand;
import cf.dotexe.bdsm.command.impl.client.UnknownCommand;
import cf.dotexe.bdsm.events.player.ChatMessageEvent;
import cf.dotexe.bdsm.module.Module;

public class CommandManager implements ClientSupport {
	
	private ArrayList<Command> commandList = new ArrayList<Command>();
	
	public ArrayList<Command> getCommandList() {
		return this.commandList;
	}
	
	private SettingCommand settingCommand = new SettingCommand();
	private final UnknownCommand unknownCommand = new UnknownCommand();
	  
	public CommandManager() {
		EventManager.register(this);
		
		commandList.add(new HelpCommand());
		commandList.add(new FriendCommand());
		commandList.add(new ToggleCommand());
		commandList.add(new BindCommand());
		
		ArrayList<String> nameList = new ArrayList<String>();
		for(Module m: mc.hackedClient.getModuleManager().getModules()) {
			nameList.add(m.getName());
		}
		settingCommand.setNames(nameList.<String>toArray(new String[0]));
		commandList.add(settingCommand);
	}
	
	@EventTarget
	public void onChatMessage(ChatMessageEvent event) {
		if(event.getMessage().startsWith("$")) {
			event.setCancelled(true);
			String message = event.getMessage().substring(1);
			String[] cmd = message.split(" ");
			Command command = getCommandFromMessage(message);
			command.runCommand(cmd);
		}
	}
	  
	public Command getCommandFromMessage(String message) {
	    for (Command command: this.commandList) {
	    	if (command.getNames() == null)
	    		return (Command)new UnknownCommand(); 
	    	String[] names;
	    	for (int length = (names = command.getNames()).length, i = 0; i < length; i++) {
	    		String name = names[i];
	    		if (message.split(" ")[0].equalsIgnoreCase(name))
	    			return command; 
	    	} 
	    } 
	    return (Command)unknownCommand;
	}
	
}