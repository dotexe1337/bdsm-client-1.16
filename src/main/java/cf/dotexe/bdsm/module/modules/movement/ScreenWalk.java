package cf.dotexe.bdsm.module.modules.movement;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.InputUpdateEvent;
import cf.dotexe.bdsm.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.EditSignScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.util.InputMappings;

public class ScreenWalk extends Module {
	
	public ScreenWalk(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		this.setState(true);
	}
	
	@EventTarget
	public void onInput(InputUpdateEvent event) {
		KeyBinding[] moveKeys = {mc.gameSettings.keyBindRight, mc.gameSettings.keyBindLeft,
                mc.gameSettings.keyBindBack, mc.gameSettings.keyBindForward, mc.gameSettings.keyBindJump,
                mc.gameSettings.keyBindSprint};
		
		 if ((mc.currentScreen instanceof Screen)
	                && !(mc.currentScreen instanceof ChatScreen) && !(mc.currentScreen instanceof EditSignScreen)) {
			 KeyBinding[] array;
	            int length = (array = moveKeys).length;
	            for (int i = 0; i < length; i++) {
	                KeyBinding key = array[i];
	                key.setPressed(InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), key.keyCode.getKeyCode()));
	            }
		 }
	}
	
}
