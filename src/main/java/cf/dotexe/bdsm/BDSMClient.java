package cf.dotexe.bdsm;

import org.lwjgl.glfw.GLFW;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import cf.dotexe.bdsm.command.CommandManager;
import cf.dotexe.bdsm.events.client.KeyPressEvent;
import cf.dotexe.bdsm.events.player.UpdateEvent;
import cf.dotexe.bdsm.friend.FriendManager;
import cf.dotexe.bdsm.module.ModuleManager;
import cf.dotexe.bdsm.screens.ImguiScreen;
import cf.dotexe.bdsm.setting.SettingManager;
import cf.dotexe.bdsm.utils.client.Logger;
import cf.dotexe.bdsm.utils.client.Logger.LogState;
import cf.dotexe.bdsm.utils.client.LoginThread;
import cf.dotexe.bdsm.utils.client.auth.Alt;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.optifine.player.CapeImageBuffer;
import net.optifine.util.TextureUtils;

public class BDSMClient implements ClientSupport {
	
	private String name = "BDSM";
	private String version = "0.2.0";
	private String author = "Michael Shipley";
	
	public String getName() {
		return name;
	}

	public String getVersion() {
		return version;
	}

	public String getAuthor() {
		return author;
	}
	
	private ModuleManager moduleManager;
	private SettingManager settingManager;
	private FriendManager friendManager;
	private CommandManager commandManager;
	
	public ModuleManager getModuleManager() {
		return this.moduleManager;
	}
	
	public SettingManager getSettingManager() {
		return this.settingManager;
	}
	
	public FriendManager getFriendManager() {
		return this.friendManager;
	}
	
	public CommandManager getCommandManager() {
		return this.commandManager;
	}
	
	private Gson gson;
	
	public Gson getGson() {
		return this.gson;
	}
	
	private ImguiScreen imgui;
	
	public BDSMClient() {
		Logger.log(LogState.Normal, "Starting " + this.name + " Client | Version " + this.version);
		
		mc.hackedClient = this;
		
		Logger.log(LogState.Normal, "Initializing EventManager");
		EventManager.register(this);
		
		Logger.log(LogState.Normal, "Initializing Gson with pretty printing");
		this.gson = new GsonBuilder().setPrettyPrinting().create(); 
		
		Logger.log(LogState.Normal, "Initializing FriendManager");
		this.friendManager = new FriendManager();
		Logger.log(LogState.Normal, "Initializing SettingsManager");
		this.settingManager = new SettingManager();
		Logger.log(LogState.Normal, "Initializing ModuleManager");
		this.moduleManager = new ModuleManager();
		Logger.log(LogState.Normal, "Initializing CommandManager");
		this.commandManager = new CommandManager();
		
		Logger.log(LogState.Normal, "Loading FriendManager config");
		this.friendManager.loadConfig(gson);
		Logger.log(LogState.Normal, "Saving FriendManager config");
		this.friendManager.saveConfig(gson);
		
		Logger.log(LogState.Normal, "Loading ModuleManager config");
		this.moduleManager.loadConfig(gson);
		Logger.log(LogState.Normal, "Saving ModuleManager config");
		this.moduleManager.saveConfig(gson);
		
		Logger.log(LogState.Normal, "Loading SettingManager config");
		this.settingManager.loadConfig(gson);
		Logger.log(LogState.Normal, "Saving SettingManager config");
		this.settingManager.saveConfig(gson);
		
		Logger.log(LogState.Normal, "Initializing ImGui");
		this.imgui = new ImguiScreen();
		
		Runtime.getRuntime().addShutdownHook(new Thread("BDSM Client shutdown thread") {
			public void run() {
				Logger.log(LogState.Normal, "Saving FriendManager config");
				mc.hackedClient.friendManager.saveConfig(gson);
				
				Logger.log(LogState.Normal, "Saving ModuleManager config");
				mc.hackedClient.moduleManager.saveConfig(gson);
				
				Logger.log(LogState.Normal, "Saving SettingManager config");
				mc.hackedClient.settingManager.saveConfig(gson);
			}
		});
	}
	
	@EventTarget
	public void onUpdate(UpdateEvent event) {
		for(Entity e: mc.world.getAllEntities()) {
    		if(e instanceof PlayerEntity) {
    			PlayerEntity pe = (PlayerEntity) e;
    			AbstractClientPlayerEntity acpe = (AbstractClientPlayerEntity) pe;
    			ResourceLocation rl = new ResourceLocation("dotexe/cape12.png");
	            CapeImageBuffer capeimagebuffer = new CapeImageBuffer(acpe, rl);
	            ResourceLocation resourcelocation1 = TextureUtils.LOCATION_TEXTURE_EMPTY;
	            SimpleTexture st = new SimpleTexture(rl);
    			if(this.getFriendManager().isFriend(pe.getName().getString())) {
    				if(acpe.getLocationCape() == null || acpe.getLocationCape().getPath() != rl.getPath()) {
    					acpe.setLocationOfCape(rl);
        	            mc.getTextureManager().loadTexture(rl, st);
    				}
    			}
    		}
    	}
    	for(Entity e: mc.world.getAllEntities()) {
    		if(e instanceof PlayerEntity) {
    			PlayerEntity pe = (PlayerEntity) e;
    			AbstractClientPlayerEntity acpe = (AbstractClientPlayerEntity) pe;
    			ResourceLocation rl = new ResourceLocation("dotexe/cape12.png");
	            CapeImageBuffer capeimagebuffer = new CapeImageBuffer(acpe, rl);
	            ResourceLocation resourcelocation1 = TextureUtils.LOCATION_TEXTURE_EMPTY;
	            SimpleTexture st = new SimpleTexture(rl);
    			if(!this.getFriendManager().isFriend(pe.getName().getString())) {
    				if(acpe.getLocationCape() != null) {
    					acpe.setLocationOfCape(null);
        	            mc.getTextureManager().deleteTexture(rl);
    				}
    			}
    		}
    	}
	}
	
	@EventTarget
	public void onKeyPress(KeyPressEvent event) {
		if(event.getKeyCode() == GLFW.GLFW_KEY_RIGHT_SHIFT) {
			mc.displayGuiScreen(this.imgui);
		}
	}
	
}
