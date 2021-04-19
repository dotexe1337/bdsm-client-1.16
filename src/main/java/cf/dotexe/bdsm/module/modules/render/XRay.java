package cf.dotexe.bdsm.module.modules.render;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.render.SetOpaqueCubeEvent;
import cf.dotexe.bdsm.events.render.ShouldSideBeRenderedEvent;
import cf.dotexe.bdsm.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.settings.AmbientOcclusionStatus;

public class XRay extends Module {
	
	public static ArrayList<Block> xrayBlocks = new ArrayList<Block>();
	
	public AmbientOcclusionStatus oldAO;
	
	public XRay(int bind, String name, String displayName, Category category) {
		super(bind, name, displayName, category);
		
		this.xrayBlocks.add(Blocks.COAL_ORE);
        this.xrayBlocks.add(Blocks.DIAMOND_ORE);
        this.xrayBlocks.add(Blocks.EMERALD_ORE);
        this.xrayBlocks.add(Blocks.GOLD_ORE);
        this.xrayBlocks.add(Blocks.IRON_ORE);
        this.xrayBlocks.add(Blocks.LAPIS_ORE);
        this.xrayBlocks.add(Blocks.NETHER_QUARTZ_ORE);
        this.xrayBlocks.add(Blocks.REDSTONE_ORE);
        this.xrayBlocks.add(Blocks.COAL_BLOCK);
        this.xrayBlocks.add(Blocks.DIAMOND_BLOCK);
        this.xrayBlocks.add(Blocks.EMERALD_BLOCK);
        this.xrayBlocks.add(Blocks.GOLD_BLOCK);
        this.xrayBlocks.add(Blocks.IRON_BLOCK);
        this.xrayBlocks.add(Blocks.LAPIS_BLOCK);
        this.xrayBlocks.add(Blocks.QUARTZ_BLOCK);
        this.xrayBlocks.add(Blocks.REDSTONE_BLOCK);
        this.xrayBlocks.add(Blocks.GLOWSTONE);
        this.xrayBlocks.add(Blocks.ANCIENT_DEBRIS);
	}
	
	@EventTarget
	public void onShouldSideBeRendered(ShouldSideBeRenderedEvent event) {
		event.setShouldReturn(true);
		if(xrayBlocks.contains(event.getBlock())) {
			event.setReturnValue(true);
		}else {
			event.setReturnValue(false);
		}
	}
	
	@EventTarget
	public void onSetOpaqueCube(SetOpaqueCubeEvent event) {
		event.setCancelled(true);
	}
	
	@Override
	public void onToggle() {
		super.onToggle();
		mc.worldRenderer.loadRenderers();
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		oldAO = mc.gameSettings.ambientOcclusionStatus;
        mc.gameSettings.ambientOcclusionStatus = AmbientOcclusionStatus.OFF;
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		if(oldAO != null) {
			mc.gameSettings.ambientOcclusionStatus = oldAO;
		}
	}
	
}
