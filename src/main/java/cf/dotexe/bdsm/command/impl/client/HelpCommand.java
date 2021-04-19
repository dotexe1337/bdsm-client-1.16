package cf.dotexe.bdsm.command.impl.client;

import cf.dotexe.bdsm.command.Command;
import cf.dotexe.bdsm.utils.client.ClientUtils;
import net.minecraft.client.Minecraft;

public class HelpCommand extends Command {
	
	public HelpCommand() {
		this.setNames(new String[] {"help"});
	}
	
	public void runCommand(String[] args) {
		for(Command c: Minecraft.getInstance().hackedClient.getCommandManager().getCommandList()) {
			ClientUtils.addChatMessage(c.getHelp());
		}
	}
	
	public String getHelp() {
	    return "<help> - Show this help page";
	}
}
