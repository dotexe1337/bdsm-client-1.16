package cf.dotexe.bdsm.command.impl.client;

import cf.dotexe.bdsm.command.Command;
import cf.dotexe.bdsm.utils.client.ClientUtils;

public class FriendCommand extends Command {
	
	public FriendCommand() {
		this.setNames(new String[] {"friend", "f"});
	}
	
	public void runCommand(String[] args) {
		if (args.length < 3) {
			ClientUtils.addChatMessage(getHelp());
			return;
		} 
		if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("a")) {
			String alias = args[2];
			if (args.length > 3) {
				alias = args[3];
			} 
			if (mc.hackedClient.getFriendManager().isFriend(args[2]) && args.length < 3) {
				ClientUtils.addChatMessage(String.valueOf(String.valueOf(args[2])) + " is already your friend.");
				return;
			} 
			mc.hackedClient.getFriendManager().removeFriend(args[2]);
			mc.hackedClient.getFriendManager().addFriend(args[2], alias);
			ClientUtils.addChatMessage("Added " + args[2] + ((args.length > 3) ? (" as " + alias) : ""));
		} else if (args[1].equalsIgnoreCase("del") || args[1].equalsIgnoreCase("d")) {
			if (mc.hackedClient.getFriendManager().isFriend(args[2])) {
				mc.hackedClient.getFriendManager().removeFriend(args[2]);
				ClientUtils.addChatMessage("Removed friend: " + args[2]);
			} else {
				ClientUtils.addChatMessage(String.valueOf(String.valueOf(args[2])) + " is not your friend.");
			} 
		} else {
			ClientUtils.addChatMessage(getHelp());
		} 
	}
  
	public String getHelp() {
		return "<friend> (add <a> | del <d>) (name) (alias)";
	}
	
}