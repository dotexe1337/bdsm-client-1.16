package cf.dotexe.bdsm.module.modules.render;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.darkmagician6.eventapi.EventTarget;

import cf.dotexe.bdsm.events.render.RenderHUDEvent;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.setting.Setting;
import cf.dotexe.bdsm.utils.render.ColorUtils;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class HUD extends Module {

	private Setting watermark;
	private Setting watermarkMode;
	private Setting arrayList;
	private Setting radar;
	private Setting coords;
	private Setting direction;
	private Setting fps;
	private Setting colorMode;
	
	public HUD(int bind, String name, Category category) {
		super(bind, name, category);
		this.setState(true);
		this.setHidden(true);
		watermark = new Setting("Watermark", this, true);
		
		ArrayList<String> watermarkModes = new ArrayList<String>();
		watermarkModes.add("Text");
		watermarkModes.add("Logo");
		
		watermarkMode = new Setting("WatermarkMode", "Watermark Mode", this, "Text", watermarkModes);
		
		ArrayList<String> colorModes = new ArrayList<String>();
		colorModes.add("Random");
		colorModes.add("Category");
		colorModes.add("Rainbow");
		this.colorMode = new Setting("ColorMode", "Color Mode", this, "Random", colorModes);
		
		arrayList = new Setting("ArrayList", this, true);
		radar = new Setting("Radar", this, true);
		coords = new Setting("Coords", this, true);
		direction = new Setting("Direction", this, true);
		fps = new Setting("FPS", this, true);
		mc.hackedClient.getSettingManager().addSetting(watermark);
		mc.hackedClient.getSettingManager().addSetting(watermarkMode);
		mc.hackedClient.getSettingManager().addSetting(arrayList);
		mc.hackedClient.getSettingManager().addSetting(colorMode);
		mc.hackedClient.getSettingManager().addSetting(radar);
		mc.hackedClient.getSettingManager().addSetting(coords);
		mc.hackedClient.getSettingManager().addSetting(direction);
		mc.hackedClient.getSettingManager().addSetting(fps);
	}
	
	@EventTarget
	public void onRenderHUD(RenderHUDEvent event) {
		int topRightY = 2;
		int topLeftY = 2;
		int bottomLeftY = event.getHeight() - 10;
		if(this.watermark.getValBoolean()) {
			if(this.watermarkMode.getValString().equalsIgnoreCase("Text")) {
				event.getFontRenderer().drawStringWithShadow(event.getMatrixStack(), "BDSM \2477" + mc.hackedClient.getVersion() + " (rel-1.16.5)", 2, 2, 0xfff48fb1);
	            topLeftY += 10;
			} else if(this.watermarkMode.getValString().equalsIgnoreCase("Logo")) {
	            mc.getTextureManager().bindTexture(new ResourceLocation("dotexe/logo1.png"));
	            AbstractGui.blit(event.getMatrixStack(), 2, 2, 0, 0, 75, 75, 75, 75);
	            topLeftY += 78;
			}
		}
		if(this.arrayList.getValBoolean()) {
			for(Module m: mc.hackedClient.getModuleManager().getModulesForArrayList()) {
				if(!m.isHidden() && m.getState()) {
					String name = m.getDisplayName();
					if(m.getSuffix().length() != 0) {
						name += " \2477" + m.getSuffix();
					}
					switch(this.colorMode.getValString()) {
					case "Random":
						event.getFontRenderer().drawStringWithShadow(event.getMatrixStack(), name, event.getWidth() - event.getFontRenderer().getStringWidth(name) - 2, topRightY, m.getColor());
						break;
					case "Category":
						event.getFontRenderer().drawStringWithShadow(event.getMatrixStack(), name, event.getWidth() - event.getFontRenderer().getStringWidth(name) - 2, topRightY, m.getCategoryColor());
						break;
					case "Rainbow":
						event.getFontRenderer().drawStringWithShadow(event.getMatrixStack(), name, event.getWidth() - event.getFontRenderer().getStringWidth(name) - 2, topRightY, ColorUtils.setRainbow(50000000L * topRightY, 1.0F).getRGB());
						break;
					}
					topRightY += 10;
				}
			}
		}
		if(this.radar.getValBoolean()) {
			List<Entity> players = new ArrayList();
			for (Object o : mc.world.getAllEntities()) {
				Entity e = (Entity)o;
		          	if (e instanceof PlayerEntity && e != mc.player)
		          		players.add(e); 
		    }  
		    players.sort((Comparator)new Comparator<PlayerEntity>() {
		    	@Override
		    	public int compare(PlayerEntity m1, PlayerEntity m2) {
		    		String s1 = (mc.hackedClient.getFriendManager().isFriend(m1.getName().getString()) ? mc.hackedClient.getFriendManager().getAliasName(m1.getName().getString()) : m1.getName().getString()) + " " + MathHelper.floor(m1.getDistance(mc.player));
		    		String s2 = (mc.hackedClient.getFriendManager().isFriend(m2.getName().getString()) ? mc.hackedClient.getFriendManager().getAliasName(m2.getName().getString()) : m2.getName().getString()) + " " + MathHelper.floor(m2.getDistance(mc.player));
		            return mc.fontRenderer.getStringWidth(s2) - mc.fontRenderer.getStringWidth(s1);
		        }
		    });
		    for(Entity e: players) {
		    	String prefix = "";
				PlayerEntity pe = (PlayerEntity) e;
				if(pe.getName().getString().equalsIgnoreCase("Freecam")) return;
				if(mc.hackedClient.getFriendManager().isFriend(pe.getName().getString())) {
        			prefix = "\2479";
        		}else {
        			prefix = pe.getDistance(mc.player) <= 64 ? "\247c" : "\247a";
        		}
		    	if(mc.hackedClient.getFriendManager().isFriend(e.getName().getString())) {
					mc.fontRenderer.drawStringWithShadow(event.getMatrixStack(), prefix + mc.hackedClient.getFriendManager().getAliasName(pe.getName().getString()) + " \2477" + MathHelper.floor(pe.getDistance(mc.player)), 2, topLeftY, 0xffffffff);
				}else {
					mc.fontRenderer.drawStringWithShadow(event.getMatrixStack(), prefix + pe.getName().getString() + " \2477" + MathHelper.floor(pe.getDistance(mc.player)), 2, topLeftY, 0xffffffff);
				}
		    	topLeftY += 10;
		    }
		}
		if(this.coords.getValBoolean()) {
			mc.fontRenderer.drawStringWithShadow(event.getMatrixStack(), "XYZ: \2477" + Math.floor(mc.player.getPosX()) + ", " + Math.floor(mc.player.getPosY()) + ", " + Math.floor(mc.player.getPosZ()), 2, bottomLeftY, 0xffffffff);
			bottomLeftY -= 10;
		}
		if(this.direction.getValBoolean()) {
			mc.fontRenderer.drawStringWithShadow(event.getMatrixStack(),"Direction: \2477" + mc.player.getHorizontalFacing().getString().substring(0, 1).toUpperCase() + mc.player.getHorizontalFacing().getString().substring(1), 2, bottomLeftY, 0xffffffff);
			bottomLeftY -= 10;
		}
		if(this.fps.getValBoolean()) {
			mc.fontRenderer.drawStringWithShadow(event.getMatrixStack(),"FPS: \2477" + mc.debugFPS, 2, bottomLeftY, 0xffffffff);
			bottomLeftY -= 10;
		}
	}
	
}

