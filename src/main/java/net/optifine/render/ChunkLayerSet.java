package net.optifine.render;

import net.minecraft.client.renderer.RenderType;

public class ChunkLayerSet
{
    private boolean[] layers = new boolean[RenderType.CHUNK_RENDER_TYPES.length];

    public void add(RenderType renderType)
    {
        this.layers[renderType.ordinal()] = true;
    }

    public boolean contains(RenderType renderType)
    {
        return this.layers[renderType.ordinal()];
    }
}
