package cf.dotexe.bdsm.events.render;

import com.darkmagician6.eventapi.events.Event;
import com.mojang.blaze3d.matrix.MatrixStack;

public class Render3DEvent implements Event {
	
	MatrixStack matrixStack;
	float partialTicks;
	
	public Render3DEvent(MatrixStack matrixStack, float partialTicks) {
		this.matrixStack = matrixStack;
		this.partialTicks = partialTicks;
	}

	public MatrixStack getMatrixStack() {
		return matrixStack;
	}
	
	public float getPartialTicks() {
		return partialTicks;
	}
	
}
