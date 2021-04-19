package cf.dotexe.bdsm.utils.client;

import cf.dotexe.bdsm.ClientSupport;
import net.minecraft.util.text.StringTextComponent;

public class ClientUtils implements ClientSupport {
	
	public static void addChatMessage(String s) {
		mc.ingameGUI.getChatGUI().printChatMessage(new StringTextComponent("\247d[BDSM]\2477: \247r" + s));
	}
	
}
