package cf.dotexe.bdsm.module;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.events.client.KeyPressEvent;
import cf.dotexe.bdsm.module.Module.Category;
import cf.dotexe.bdsm.module.modules.combat.AntiKnockback;
import cf.dotexe.bdsm.module.modules.combat.AutoCrystal;
import cf.dotexe.bdsm.module.modules.combat.AutoTotem;
import cf.dotexe.bdsm.module.modules.combat.KillAura;
import cf.dotexe.bdsm.module.modules.misc.FancyChat;
import cf.dotexe.bdsm.module.modules.movement.EntityFly;
import cf.dotexe.bdsm.module.modules.movement.FallFly;
import cf.dotexe.bdsm.module.modules.movement.Fly;
import cf.dotexe.bdsm.module.modules.movement.Freecam;
import cf.dotexe.bdsm.module.modules.movement.NoSlowdown;
import cf.dotexe.bdsm.module.modules.movement.ScreenWalk;
import cf.dotexe.bdsm.module.modules.movement.Speed;
import cf.dotexe.bdsm.module.modules.movement.Sprint;
import cf.dotexe.bdsm.module.modules.movement.Step;
import cf.dotexe.bdsm.module.modules.player.AntiHunger;
import cf.dotexe.bdsm.module.modules.player.NoFall;
import cf.dotexe.bdsm.module.modules.player.Sneak;
import cf.dotexe.bdsm.module.modules.render.Chams;
import cf.dotexe.bdsm.module.modules.render.FullBright;
import cf.dotexe.bdsm.module.modules.render.HUD;
import cf.dotexe.bdsm.module.modules.render.NameTags;
import cf.dotexe.bdsm.module.modules.render.Night;
import cf.dotexe.bdsm.module.modules.render.NoWeather;
import cf.dotexe.bdsm.module.modules.render.PlayerESP;
import cf.dotexe.bdsm.module.modules.render.XRay;
import cf.dotexe.bdsm.module.modules.world.FastPlace;
import cf.dotexe.bdsm.module.modules.world.SpeedMine;
import cf.dotexe.bdsm.utils.client.Logger;
import cf.dotexe.bdsm.utils.client.Logger.LogState;

public class ModuleManager implements ClientSupport {
	
	private ArrayList<Module> modules = new ArrayList<Module>();
	
	public ArrayList<Module> getModules() {
		return this.modules;
	}
	
	public ArrayList<Module> getModulesForArrayList() {
		ArrayList<Module> renderList = this.modules;
	    renderList.sort(new Comparator<Module>() {
	    	public int compare(Module m1, Module m2) {
	            String s1 = String.format("%s" + ((m1.getSuffix().length() > 0) ? " %s" : ""), new Object[] { m1.getDisplayName(), m1.getSuffix() });
	            String s2 = String.format("%s" + ((m2.getSuffix().length() > 0) ? " %s" : ""), new Object[] { m2.getDisplayName(), m2.getSuffix() });
	            return mc.fontRenderer.getStringWidth(s2) - mc.fontRenderer.getStringWidth(s1);
	        }
	    });
	    return renderList;
	}
	
	public Module getModule(String s) {
		for(Module m: this.modules) {
			if(m.getName().equalsIgnoreCase(s)) {
				return m;
			}
		}
		return new Module(0, "Null", Category.Misc);
	}
	
	public void addModule(Module m) {
		this.modules.add(m);
	}
	
	public ModuleManager() {
		EventManager.register(this);
		
		Logger.log(LogState.Normal, "Adding modules to ModuleManager");
		addModule(new NameTags(0, "NameTags", "Name Tags", Category.Render));
		addModule(new FullBright(0, "FullBright", "Full Bright", Category.Render));
		addModule(new PlayerESP(0, "PlayerESP", "Player ESP", Category.Render));
		addModule(new XRay(KeyEvent.VK_X, "XRay", "X-Ray", Category.Render));
		addModule(new Night(0, "Night", Category.Render));
		addModule(new NoWeather(0, "NoWeather", "No Weather", Category.Render));
		addModule(new Chams(0, "Chams", Category.Render));
		//addModule(new Tracers(0, "Tracers", Category.Render));
		addModule(new Fly(KeyEvent.VK_M, "Fly", Category.Movement));
		addModule(new EntityFly(KeyEvent.VK_N, "EntityFly", "Entity Fly", Category.Movement));
		addModule(new Speed(KeyEvent.VK_G, "Speed", Category.Movement));
		addModule(new Freecam(KeyEvent.VK_V, "Freecam", Category.Movement));
		addModule(new Step(0, "Step", Category.Movement));
		addModule(new Sprint(0, "Sprint", Category.Movement));
		addModule(new NoSlowdown(0, "NoSlowdown", "No Slowdown", Category.Movement));
		addModule(new FallFly(0, "FallFly", "Fall Fly", Category.Movement));
		addModule(new ScreenWalk(0, "ScreenWalk", "Screen Walk", Category.Movement));
		addModule(new NoFall(0, "NoFall", "No Fall", Category.Player));
		addModule(new Sneak(KeyEvent.VK_Z, "Sneak", Category.Player));
		addModule(new AntiHunger(0, "AntiHunger", "Anti Hunger", Category.Player));
		addModule(new KillAura(KeyEvent.VK_R, "KillAura", "Kill Aura", Category.Combat));
		addModule(new AutoCrystal(KeyEvent.VK_B, "AutoCrystal", "Auto Crystal", Category.Combat));
		addModule(new AutoTotem(0, "AutoTotem", "Auto Totem", Category.Combat));
		addModule(new AntiKnockback(0, "AntiKnockback", "Anti Knockback", Category.Combat));
		//addModule(new Surround(0, "Surround", Category.Combat)); /* Bugged */
		//addModule(new Untrap(0, "Untrap", Category.Combat)); /* Bugged */
		addModule(new SpeedMine(KeyEvent.VK_H, "SpeedMine", "Speed Mine", Category.World));
		addModule(new FastPlace(KeyEvent.VK_J, "FastPlace", "Fast Place", Category.World));
		addModule(new FancyChat(0, "FancyChat", "Fancy Chat", Category.Misc));
		addModule(new HUD(0, "HUD", Category.Render));
	}
	
	public void loadConfig(Gson gson) {
		for(Module m: this.modules) {
			File file = new File(mc.gameDir + File.separator + "BDSM" + File.separator + "modules" + File.separator + m.getName() + ".json");
			try (FileReader reader = new FileReader(file)) {
				Map<String, Object> map = gson.fromJson(reader, new TypeToken<Map<String, Object>>() {}.getType());
				m.setBind((int)Math.round((double)map.get("bind")));
				m.setState((boolean)map.get("toggled"));
				Logger.log(LogState.Normal, "Loaded module " + m.getName() + " from Json!");
			} catch (JsonSyntaxException e) {
				Logger.log(LogState.Error, "Json syntax error in ModuleManager.loadConfig()!");
				e.printStackTrace();
			} catch (JsonIOException e) {
				Logger.log(LogState.Error, "Json I/O exception in ModuleManager.loadConfig()!");
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				Logger.log(LogState.Error, "Json file not found exception in ModuleManager.loadConfig()!");
				e.printStackTrace();
			} catch (IOException e1) {
				Logger.log(LogState.Error, "Json I/O exception in ModuleManager.loadConfog()!");
				e1.printStackTrace();
			}
		}
	}
	
	public void saveConfig(Gson gson) {
		for(Module m: this.modules) {
			File file = new File(mc.gameDir + File.separator + "BDSM" + File.separator + "modules" + File.separator + m.getName() + ".json");
			if(!file.exists()) {
				new File(mc.gameDir + File.separator + "BDSM" + File.separator + "modules").mkdirs();
				try {
					file.createNewFile();
					Logger.log(LogState.Normal, "Created new Json file: " + file.getName());
				} catch (IOException e) {
					Logger.log(LogState.Error, "File.createNewFile() I/O exception in ModuleManager.saveConfig()!");
				}
			}
			try (FileWriter writer = new FileWriter(file)) {
	            Map<String, Object> map = new HashMap<>();
	            map.put("name", m.getName());
	            map.put("bind", m.getBind());
	            map.put("toggled", m.getState());
	            gson.toJson(map, writer);
	            Logger.log(LogState.Normal, "Wrote Json file!");
	        } catch (IOException e) {
	            Logger.log(LogState.Error, "I/O exception in writing to Json: " + file.getName());
	        }
		}
	}
	
	@EventTarget
	public void onKeyPress(KeyPressEvent event) {
		for(Module m: this.modules) {
			if(event.getKeyCode() == m.getBind()) {
				m.toggle();
			}
		}
	}
	
}
