package cf.dotexe.bdsm.module.modules.combat;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.module.Module;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket.Action;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class AutoTotem extends Module {
	
	public AutoTotem(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
	}
    
    @EventTarget
    public void onUpdate(UpdateEvent event) {
    	if (mc.player.getHeldItemOffhand().getItem() == Items.TOTEM_OF_UNDYING) return;
		
		for (int i = 9; i < 44; i++) {
			if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
				mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, mc.player);
				mc.playerController.windowClick(0, 45, 0, ClickType.PICKUP, mc.player);
				return;
			}
		}
		
		for (int i = 0; i < 8; i++) {
			if (mc.player.inventory.getStackInSlot(i).getItem() == Items.TOTEM_OF_UNDYING) {
				mc.playerController.windowClick(0, i, 0, ClickType.PICKUP, mc.player);
				mc.playerController.windowClick(0, -106, 0, ClickType.PICKUP, mc.player);
				return;
			}
		}
    }
	
}
