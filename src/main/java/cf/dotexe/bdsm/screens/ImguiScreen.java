package cf.dotexe.bdsm.screens;

import java.util.HashSet;
import java.util.Objects;

import com.mojang.blaze3d.matrix.MatrixStack;

import cf.dotexe.bdsm.ClientSupport;
import cf.dotexe.bdsm.module.Module;
import cf.dotexe.bdsm.module.Module.Category;
import cf.dotexe.bdsm.setting.Setting;
import imgui.ImGui;
import imgui.flag.ImGuiCol;
import imgui.flag.ImGuiStyleVar;
import imgui.gl3.ImGuiImplGl3;
import imgui.glfw.ImGuiImplGlfw;
import imgui.type.ImDouble;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket.PositionPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.StringTextComponent;

public class ImguiScreen extends Screen implements ClientSupport {
	
	public ImguiScreen() {
		super(new StringTextComponent("ImguiScreen"));
	}

	private ImGuiImplGlfw imguiGlfw = new ImGuiImplGlfw();
	private ImGuiImplGl3 imguiGl3 = new ImGuiImplGl3();
	
	private static HashSet<Integer> keyBuffer = new HashSet<Integer>();
	
	@Override
	public void init() {
		ImGui.createContext();
		
		ImGui.getIO().setIniFilename("bdsm-imgui.ini");
		imguiGlfw.init(mc.getMainWindow().getHandle(), false);
		imguiGl3.init("#version 110");
		ImGui.pushStyleVar(ImGuiStyleVar.WindowRounding, 0.0f);
		ImGui.pushStyleVar(ImGuiStyleVar.ChildRounding, 0.0f);
		ImGui.pushStyleVar(ImGuiStyleVar.FrameRounding, 0.0f);
		ImGui.pushStyleVar(ImGuiStyleVar.GrabRounding, 0.0f);
		ImGui.pushStyleVar(ImGuiStyleVar.PopupRounding, 0.0f);
		ImGui.pushStyleVar(ImGuiStyleVar.ScrollbarRounding, 0.0f);
		ImGui.pushStyleVar(ImGuiStyleVar.TabRounding, 0.0f);
	}
	
	 // Prevents Minecraft from pausing the game whenever we open the GUI.
    @Override
    public boolean isPauseScreen() {
        return false;
    }

    // Tells imgui to enter a character, when typing on a textbox or similar.
    @Override
    public boolean charTyped(char chr, int keyCode) {
        if (ImGui.getIO().getWantTextInput()) {
            ImGui.getIO().addInputCharacter(chr);
        }
        
        super.charTyped(chr, keyCode);
        return true;
    }

    // Passes mouse scrolling to imgui.
    @Override
    public boolean mouseScrolled(double d, double e, double amount) {
        if (ImGui.getIO().getWantCaptureMouse()) {
            ImGui.getIO().setMouseWheel((float) amount);
        }

        super.mouseScrolled(d, e, amount);
        return true;
    }

    // Passes keypresses for imgui to handle.
    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (ImGui.getIO().getWantCaptureKeyboard()) {
            ImGui.getIO().setKeysDown(keyCode, true);
            keyBuffer.add(keyCode);
        }

        // Skip handling of the ESC key, because Minecraft uses it to close the screen.
        if (keyCode == 256) {
            ImGui.getIO().setKeysDown(keyCode, false);
        }

        super.keyPressed(keyCode, scanCode, modifiers);
        return true;
    }

    // Tells imgui the keys pressed have been released.
    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        ImGui.getIO().setKeysDown(keyCode, false);
        keyBuffer.remove(keyCode);

        super.keyReleased(keyCode, scanCode, modifiers);
        return true;
    }

    @Override
    public void onClose() {
        // When Minecraft closes the screen, clear the key buffer.
        for (int keyCode: keyBuffer) {
            ImGui.getIO().setKeysDown(keyCode, false);
        }
        keyBuffer.clear();

        super.onClose();
    }

    boolean offhandCrash = false;
    
    boolean showVclipWindow = false;
    boolean showTpWindow = false;
    
    boolean showCombatWindow = false;
    boolean showPlayerWindow = false;
    boolean showMovementWindow = false;
    boolean showWorldWindow = false;
    boolean showRenderWindow = false;
    boolean showExploitsWindow = false;
    boolean showMiscWindow = false;
    
    ImDouble vclipHeight = new ImDouble(0);
    
    ImDouble tpX = new ImDouble(0);
    ImDouble tpY = new ImDouble(255);
    ImDouble tpZ = new ImDouble(0);
    
    @Override
    public void render(MatrixStack matrix, int x, int y, float partialTicks) {
    	imguiGlfw.newFrame();
        ImGui.newFrame();
        
        if(ImGui.beginMainMenuBar()) {
        	if(ImGui.beginMenu("Commands")) {
        		if(ImGui.menuItem("VClip")) {
        			showVclipWindow = !showVclipWindow;
        		}
        		if(ImGui.menuItem("Teleport")) {
        			showTpWindow = !showTpWindow;
        		}
        		ImGui.endMenu();
        	}
        	if(ImGui.beginMenu("Crashes")) {
        		if(ImGui.checkbox("Off Hand", this.offhandCrash)) {
        			this.offhandCrash = !this.offhandCrash;
        		}
        		ImGui.endMenu();
        	}
        	if(ImGui.beginMenu("Windows")) {
        		if(ImGui.checkbox("Combat", this.showCombatWindow)) {
        			this.showCombatWindow = !this.showCombatWindow;
        		}
        		if(ImGui.checkbox("Player", this.showPlayerWindow)) {
        			this.showPlayerWindow = !this.showPlayerWindow;
        		}
        		if(ImGui.checkbox("Movement", this.showMovementWindow)) {
        			this.showMovementWindow = !this.showMovementWindow;
        		}
        		if(ImGui.checkbox("World", this.showWorldWindow)) {
        			this.showWorldWindow = !this.showWorldWindow;
        		}
        		if(ImGui.checkbox("Render", this.showRenderWindow)) {
        			this.showRenderWindow = !this.showRenderWindow;
        		}
        		if(ImGui.checkbox("Exploits", this.showExploitsWindow)) {
        			this.showExploitsWindow = !this.showExploitsWindow;
        		}
        		if(ImGui.checkbox("Misc", this.showMiscWindow)) {
        			this.showMiscWindow = !this.showMiscWindow;
        		}
        		ImGui.endMenu();
        	}
        	ImGui.endMainMenuBar();
        }
        
        if(this.offhandCrash) {
        	for(int i = 0; i < 999999; i++) {
    			mc.getConnection().sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ZERO, Direction.DOWN));
    		}
        }
        
        if(showVclipWindow) {
        	if(ImGui.begin("VClip")) {
				ImGui.inputDouble("Height", vclipHeight);
				if(ImGui.button("Okay")) {
					mc.player.setPosition(mc.player.getPosX(), mc.player.getPosY() + vclipHeight.get(), mc.player.getPosZ());
					showVclipWindow = false;
				}
				ImGui.end();
			}
        }
        
        if(showTpWindow) {
        	if(ImGui.begin("Teleport")) {
				ImGui.inputDouble("X", tpX);
				ImGui.inputDouble("Y", tpY);
				ImGui.inputDouble("Z", tpZ);
				if(ImGui.button("Okay")) {
					mc.getConnection().sendPacket(new PositionPacket(tpX.get(), -100.0, tpZ.get(), mc.player.isOnGround()));
			        mc.player.setPosition(tpX.get(), tpY.get(), tpZ.get());
					showTpWindow = false;
				}
				ImGui.end();
			}
        }
        
        if(showCombatWindow) {
        	 if(ImGui.begin("Combat")) {
         		for(Module m: mc.hackedClient.getModuleManager().getModules()) {
                 	if(m.getCategory() == Category.Combat) {
                 		if(ImGui.collapsingHeader(m.getDisplayName())) {
                 			if(m.getState()) { 
                				ImGui.pushStyleColor(ImGuiCol.Button, 0f, 0.90196078431f, 0.46274509803f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0f, 0.90196078431f, 0.46274509803f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0f, 0.90196078431f, 0.46274509803f, 0.95f);
                 			}
                			else {
                				ImGui.pushStyleColor(ImGuiCol.Button, 0.86666666666f, 0.1725490196f, 0f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.86666666666f, 0.1725490196f, 0f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.86666666666f, 0.1725490196f, 0f, 0.95f);
                			}
                			if(ImGui.button(m.getState() ? "Enabled" : "Disabled")) {
                				m.toggle();
                			}
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			if(ImGui.checkbox("Hidden", m.isHidden())) {
                				m.setHidden(!m.isHidden());
                			}
                 			for(Setting s: mc.hackedClient.getSettingManager().getSettings()) {
                 				if(s.getParentMod() == m) {
                 					if(s.isCheck()) {
                 						if(ImGui.checkbox(s.getDisplayName(), s.bval)) {
                 							s.bval = !s.bval;
                 						}
                 					}
                 					else if(s.isSlider()) {
                 						float[] fA = {s.dval};
                 						if(ImGui.sliderFloat(s.getDisplayName(), fA, s.getMin(), s.getMax())) {
                 							s.dval = fA[0];
                 						}
                 					}
                 					else if(s.isCombo()) {
                 						String[] items = new String[s.options.size()];
                 						for(int i = 0; i < s.options.size(); i++) {
                 							items[i] = s.options.get(i);
                 						}
                 						int idx = s.options.indexOf(s.sval);
                 						if(ImGui.beginCombo(s.getDisplayName(), items[idx])) {
                 							for(int n = 0; n < items.length; n++) {
                 								boolean isSelected = (idx == n);
                 								if(ImGui.selectable(items[n], isSelected)) {
                 									idx = n;
                 									s.sval = items[idx];
                 								}
                 							}
                 							ImGui.endCombo();
                 						}
                 					}
                 				}
                 			}
                 		}
                 	}
                 }
         	}
        	ImGui.end();
        }
        
        if(showPlayerWindow) {
        	if(ImGui.begin("Player")) {
        		for(Module m: mc.hackedClient.getModuleManager().getModules()) {
                	if(m.getCategory() == Category.Player) {
                		if(ImGui.collapsingHeader(m.getDisplayName())) {
                			if(m.getState()) { 
                				ImGui.pushStyleColor(ImGuiCol.Button, 0f, 0.90196078431f, 0.46274509803f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0f, 0.90196078431f, 0.46274509803f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0f, 0.90196078431f, 0.46274509803f, 0.95f);
                 			}
                			else {
                				ImGui.pushStyleColor(ImGuiCol.Button, 0.86666666666f, 0.1725490196f, 0f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.86666666666f, 0.1725490196f, 0f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.86666666666f, 0.1725490196f, 0f, 0.95f);
                			}
                			if(ImGui.button(m.getState() ? "Enabled" : "Disabled")) {
                				m.toggle();
                			}
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			if(ImGui.checkbox("Hidden", m.isHidden())) {
                				m.setHidden(!m.isHidden());
                			}
                			for(Setting s: mc.hackedClient.getSettingManager().getSettings()) {
                				if(s.getParentMod() == m) {
                					if(s.isCheck()) {
                						if(ImGui.checkbox(s.getDisplayName(), s.bval)) {
                							s.bval = !s.bval;
                						}
                					}
                					else if(s.isSlider()) {
                 						float[] fA = {s.dval};
                 						if(ImGui.sliderFloat(s.getDisplayName(), fA, s.getMin(), s.getMax())) {
                 							s.dval = fA[0];
                 						}
                					}
                					else if(s.isCombo()) {
                						String[] items = new String[s.options.size()];
                						for(int i = 0; i < s.options.size(); i++) {
                							items[i] = s.options.get(i);
                						}
                						int idx = s.options.indexOf(s.sval);
                						if(ImGui.beginCombo(s.getDisplayName(), items[idx])) {
                							for(int n = 0; n < items.length; n++) {
                								boolean isSelected = (idx == n);
                								if(ImGui.selectable(items[n], isSelected)) {
                									idx = n;
                									s.sval = items[idx];
                								}
                							}
                							ImGui.endCombo();
                						}
                					}
                				}
                			}
                		}
                	}
                }
        	}
        	ImGui.end();
        }
        
        if(showMovementWindow) {
        	if(ImGui.begin("Movement")) {
        		for(Module m: mc.hackedClient.getModuleManager().getModules()) {
                	if(m.getCategory() == Category.Movement) {
                		if(ImGui.collapsingHeader(m.getDisplayName())) {
                			if(m.getState()) { 
                				ImGui.pushStyleColor(ImGuiCol.Button, 0f, 0.90196078431f, 0.46274509803f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0f, 0.90196078431f, 0.46274509803f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0f, 0.90196078431f, 0.46274509803f, 0.95f);
                 			}
                			else {
                				ImGui.pushStyleColor(ImGuiCol.Button, 0.86666666666f, 0.1725490196f, 0f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.86666666666f, 0.1725490196f, 0f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.86666666666f, 0.1725490196f, 0f, 0.95f);
                			}
                			if(ImGui.button(m.getState() ? "Enabled" : "Disabled")) {
                				m.toggle();
                			}
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			if(ImGui.checkbox("Hidden", m.isHidden())) {
                				m.setHidden(!m.isHidden());
                			}
                			for(Setting s: mc.hackedClient.getSettingManager().getSettings()) {
                				if(s.getParentMod() == m) {
                					if(s.isCheck()) {
                						if(ImGui.checkbox(s.getDisplayName(), s.bval)) {
                							s.bval = !s.bval;
                						}
                					}
                					else if(s.isSlider()) {
                 						float[] fA = {s.dval};
                 						if(ImGui.sliderFloat(s.getDisplayName(), fA, s.getMin(), s.getMax())) {
                 							s.dval = fA[0];
                 						}
                					}
                					else if(s.isCombo()) {
                						String[] items = new String[s.options.size()];
                						for(int i = 0; i < s.options.size(); i++) {
                							items[i] = s.options.get(i);
                						}
                						int idx = s.options.indexOf(s.sval);
                						if(ImGui.beginCombo(s.getDisplayName(), items[idx])) {
                							for(int n = 0; n < items.length; n++) {
                								boolean isSelected = (idx == n);
                								if(ImGui.selectable(items[n], isSelected)) {
                									idx = n;
                									s.sval = items[idx];
                								}
                							}
                							ImGui.endCombo();
                						}
                					}
                				}
                			}
                		}
                	}
                }
        	}
        	ImGui.end();
        }
        
        if(showWorldWindow) {
        	if(ImGui.begin("World")) {
        		for(Module m: mc.hackedClient.getModuleManager().getModules()) {
                	if(m.getCategory() == Category.World) {
                		if(ImGui.collapsingHeader(m.getDisplayName())) {
                			if(m.getState()) { 
                				ImGui.pushStyleColor(ImGuiCol.Button, 0f, 0.90196078431f, 0.46274509803f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0f, 0.90196078431f, 0.46274509803f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0f, 0.90196078431f, 0.46274509803f, 0.95f);
                 			}
                			else {
                				ImGui.pushStyleColor(ImGuiCol.Button, 0.86666666666f, 0.1725490196f, 0f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.86666666666f, 0.1725490196f, 0f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.86666666666f, 0.1725490196f, 0f, 0.95f);
                			}
                			if(ImGui.button(m.getState() ? "Enabled" : "Disabled")) {
                				m.toggle();
                			}
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			if(ImGui.checkbox("Hidden", m.isHidden())) {
                				m.setHidden(!m.isHidden());
                			}
                			for(Setting s: mc.hackedClient.getSettingManager().getSettings()) {
                				if(s.getParentMod() == m) {
                					if(s.isCheck()) {
                						if(ImGui.checkbox(s.getDisplayName(), s.bval)) {
                							s.bval = !s.bval;
                						}
                					}
                					else if(s.isSlider()) {
                						ImGui.sliderFloat(s.getDisplayName(), new float[] {s.dval}, s.getMin(), s.getMax());
                					}
                					else if(s.isCombo()) {
                						String[] items = new String[s.options.size()];
                						for(int i = 0; i < s.options.size(); i++) {
                							items[i] = s.options.get(i);
                						}
                						int idx = s.options.indexOf(s.sval);
                						if(ImGui.beginCombo(s.getDisplayName(), items[idx])) {
                							for(int n = 0; n < items.length; n++) {
                								boolean isSelected = (idx == n);
                								if(ImGui.selectable(items[n], isSelected)) {
                									idx = n;
                									s.sval = items[idx];
                								}
                							}
                							ImGui.endCombo();
                						}
                					}
                				}
                			}
                		}
                	}
                }
        	}
        	ImGui.end();
        }
        
        if(showRenderWindow) {
        	if(ImGui.begin("Render")) {
        		for(Module m: mc.hackedClient.getModuleManager().getModules()) {
                	if(m.getCategory() == Category.Render) {
                		if(ImGui.collapsingHeader(m.getDisplayName())) {
                			if(m.getState()) { 
                				ImGui.pushStyleColor(ImGuiCol.Button, 0f, 0.90196078431f, 0.46274509803f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0f, 0.90196078431f, 0.46274509803f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0f, 0.90196078431f, 0.46274509803f, 0.95f);
                 			}
                			else {
                				ImGui.pushStyleColor(ImGuiCol.Button, 0.86666666666f, 0.1725490196f, 0f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.86666666666f, 0.1725490196f, 0f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.86666666666f, 0.1725490196f, 0f, 0.95f);
                			}
                			if(ImGui.button(m.getState() ? "Enabled" : "Disabled")) {
                				m.toggle();
                			}
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			if(ImGui.checkbox("Hidden", m.isHidden())) {
                				m.setHidden(!m.isHidden());
                			}
                			for(Setting s: mc.hackedClient.getSettingManager().getSettings()) {
                				if(s.getParentMod() == m) {
                					if(s.isCheck()) {
                						if(ImGui.checkbox(s.getDisplayName(), s.bval)) {
                							s.bval = !s.bval;
                						}
                					}
                					else if(s.isSlider()) {
                 						float[] fA = {s.dval};
                 						if(ImGui.sliderFloat(s.getDisplayName(), fA, s.getMin(), s.getMax())) {
                 							s.dval = fA[0];
                 						}
                					}
                					else if(s.isCombo()) {
                						String[] items = new String[s.options.size()];
                						for(int i = 0; i < s.options.size(); i++) {
                							items[i] = s.options.get(i);
                						}
                						int idx = s.options.indexOf(s.sval);
                						if(ImGui.beginCombo(s.getDisplayName(), items[idx])) {
                							for(int n = 0; n < items.length; n++) {
                								boolean isSelected = (idx == n);
                								if(ImGui.selectable(items[n], isSelected)) {
                									idx = n;
                									s.sval = items[idx];
                								}
                							}
                							ImGui.endCombo();
                						}
                					}
                				}
                			}
                		}
                	}
                }
        	}
        	ImGui.end();
        }
        
        if(showExploitsWindow) {
        	if(ImGui.begin("Exploits")) {
        		for(Module m: mc.hackedClient.getModuleManager().getModules()) {
                	if(m.getCategory() == Category.Exploits) {
                		if(ImGui.collapsingHeader(m.getDisplayName())) {
                			if(m.getState()) { 
                				ImGui.pushStyleColor(ImGuiCol.Button, 0f, 0.90196078431f, 0.46274509803f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0f, 0.90196078431f, 0.46274509803f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0f, 0.90196078431f, 0.46274509803f, 0.95f);
                 			}
                			else {
                				ImGui.pushStyleColor(ImGuiCol.Button, 0.86666666666f, 0.1725490196f, 0f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.86666666666f, 0.1725490196f, 0f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.86666666666f, 0.1725490196f, 0f, 0.95f);
                			}
                			if(ImGui.button(m.getState() ? "Enabled" : "Disabled")) {
                				m.toggle();
                			}
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			if(ImGui.checkbox("Hidden", m.isHidden())) {
                				m.setHidden(!m.isHidden());
                			}
                			for(Setting s: mc.hackedClient.getSettingManager().getSettings()) {
                				if(s.getParentMod() == m) {
                					if(s.isCheck()) {
                						if(ImGui.checkbox(s.getDisplayName(), s.bval)) {
                							s.bval = !s.bval;
                						}
                					}
                					else if(s.isSlider()) {
                 						float[] fA = {s.dval};
                 						if(ImGui.sliderFloat(s.getDisplayName(), fA, s.getMin(), s.getMax())) {
                 							s.dval = fA[0];
                 						}
                					}
                					else if(s.isCombo()) {
                						String[] items = new String[s.options.size()];
                						for(int i = 0; i < s.options.size(); i++) {
                							items[i] = s.options.get(i);
                						}
                						int idx = s.options.indexOf(s.sval);
                						if(ImGui.beginCombo(s.getDisplayName(), items[idx])) {
                							for(int n = 0; n < items.length; n++) {
                								boolean isSelected = (idx == n);
                								if(ImGui.selectable(items[n], isSelected)) {
                									idx = n;
                									s.sval = items[idx];
                								}
                							}
                							ImGui.endCombo();
                						}
                					}
                				}
                			}
                		}
                	}
                }
        	}
        	ImGui.end();
        }
        
        if(showMiscWindow) {
        	if(ImGui.begin("Misc")) {
        		for(Module m: mc.hackedClient.getModuleManager().getModules()) {
                	if(m.getCategory() == Category.Misc) {
                		if(ImGui.collapsingHeader(m.getDisplayName())) {
                			if(m.getState()) { 
                				ImGui.pushStyleColor(ImGuiCol.Button, 0f, 0.90196078431f, 0.46274509803f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0f, 0.90196078431f, 0.46274509803f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0f, 0.90196078431f, 0.46274509803f, 0.95f);
                 			}
                			else {
                				ImGui.pushStyleColor(ImGuiCol.Button, 0.86666666666f, 0.1725490196f, 0f, 1.0f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonHovered, 0.86666666666f, 0.1725490196f, 0f, 0.75f);
                				ImGui.pushStyleColor(ImGuiCol.ButtonActive, 0.86666666666f, 0.1725490196f, 0f, 0.95f);
                			}
                			if(ImGui.button(m.getState() ? "Enabled" : "Disabled")) {
                				m.toggle();
                			}
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			ImGui.popStyleColor();
                			if(ImGui.checkbox("Hidden", m.isHidden())) {
                				m.setHidden(!m.isHidden());
                			}
                			for(Setting s: mc.hackedClient.getSettingManager().getSettings()) {
                				if(s.getParentMod() == m) {
                					if(s.isCheck()) {
                						if(ImGui.checkbox(s.getDisplayName(), s.bval)) {
                							s.bval = !s.bval;
                						}
                					}
                					else if(s.isSlider()) {
                 						float[] fA = {s.dval};
                 						if(ImGui.sliderFloat(s.getDisplayName(), fA, s.getMin(), s.getMax())) {
                 							s.dval = fA[0];
                 						}
                					}
                					else if(s.isCombo()) {
                						String[] items = new String[s.options.size()];
                						for(int i = 0; i < s.options.size(); i++) {
                							items[i] = s.options.get(i);
                						}
                						int idx = s.options.indexOf(s.sval);
                						if(ImGui.beginCombo(s.getDisplayName(), items[idx])) {
                							for(int n = 0; n < items.length; n++) {
                								boolean isSelected = (idx == n);
                								if(ImGui.selectable(items[n], isSelected)) {
                									idx = n;
                									s.sval = items[idx];
                								}
                							}
                							ImGui.endCombo();
                						}
                					}
                				}
                			}
                		}
                	}
                }
        	}
        	ImGui.end();
        }

        ImGui.render();
        imguiGl3.renderDrawData(Objects.requireNonNull(ImGui.getDrawData()));
    }
	
}
