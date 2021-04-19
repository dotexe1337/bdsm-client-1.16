package cf.dotexe.bdsm.module.modules.misc;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.ChatMessageEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.utils.render.ColorUtils;

public class FancyChat extends Module {

	public FancyChat(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
	}
	
	@EventTarget
	public void onChatMessage(ChatMessageEvent event) {
		if(!event.getMessage().startsWith("$") && !event.getMessage().startsWith("/"))
			event.setMessage(event.getMessage() + " 『ＢＤＳＭ　Ｃｌｉｅｎｔ』");
	}

}
