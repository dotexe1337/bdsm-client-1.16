package cf.dotexe.bdsm.events.render;

import com.darkmagician6.eventapi.events.Event;
import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.gui.FontRenderer;

public class RenderHUDEvent implements Event {
	
	private MatrixStack matrixStack;
	private int width;
	private int height;
	private FontRenderer fontRenderer;
	
	public MatrixStack getMatrixStack() {
		return this.matrixStack;
	}
	
	public int getWidth() {
		return this.width;
	}
	
	public int getHeight() {
		return this.height;
	}
	
	public FontRenderer getFontRenderer() {
		return this.fontRenderer;
	}
	
	public RenderHUDEvent(MatrixStack matrixStack, int width, int height, FontRenderer fontRenderer) {
		this.matrixStack = matrixStack;
		this.width = width;
		this.height = height;
		this.fontRenderer = fontRenderer;
	}
	
}
