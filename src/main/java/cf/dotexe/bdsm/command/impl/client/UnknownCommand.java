package cf.dotexe.bdsm.command.impl.client;

import cf.dotexe.bdsm.command.Command;
import cf.dotexe.bdsm.utils.client.ClientUtils;

public class UnknownCommand extends Command {
	
	public void runCommand(String[] args) {
		ClientUtils.addChatMessage("Unknown command. Type \"help\" for a list of commands");
	}
	
}
